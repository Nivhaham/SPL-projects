import atexit
import sqlite3
import DTO
import DAO
from datetime import datetime


class _Repository:
    def __init__(self):
        self._conn = sqlite3.connect('database.db')
        self.vaccines = DAO._vaccines(self._conn)
        self.suppliers = DAO._suppliers(self._conn)
        self.clinics = DAO._clinics(self._conn)
        self.logistics = DAO._logistics(self._conn)

    def _closing(self):
        self._conn.commit()
        self._conn.close()

    def create_db_tables(self):
        cursor = self._conn.cursor()
        # create vaccines table
        command1 = """CREATE TABLE IF NOT EXISTS vaccines
        (id INTEGER PRIMARY KEY,
         date DATE NOT NULL,
         supplier INTEGER REFERENCES Supplier(id),
         quantity INTEGER NOT NULL )"""
        # create suppliers table
        command2 = """CREATE TABLE IF NOT EXISTS suppliers
        (id INTEGER PRIMARY KEY,
        name STRING NOT NULL,
        logistic INTEGER REFERENCES Logistic(id) )"""
        # create clinics table
        command3 = """CREATE TABLE IF NOT EXISTS clinics
        (id INTEGER PRIMARY KEY,
        location STRING NOT NULL,
        demand INTEGER NOT NULL,
        logistic INTEGER REFERENCES Logistic(id))"""
        # create logistics table

        command4 = """CREATE TABLE IF NOT EXISTS logistics 
        (id INTEGER PRIMARY KEY,
        name STRING NOT NULL,
        count_sent INTEGER NOT NULL,
        count_received INTEGER NOT NULL )"""

        cursor.execute(command1)
        cursor.execute(command2)
        cursor.execute(command3)
        cursor.execute(command4)

    def populate_db(self, vacs, sups, clis, logs):
        for i in vacs:
            i = i.split(',')
            vaccine = DTO.vaccines(*i)
            insert_vac = DAO._vaccines(self._conn)
            insert_vac.insert(vaccine)
        for i in sups:
            i = i.split(',')
            supplier = DTO.suppliers(*i)
            insert_sup = DAO._suppliers(self._conn)
            insert_sup.insert(supplier)
        for i in clis:
            i = i.split(',')
            clinic = DTO.clinics(*i)
            insert_clinic = DAO._clinics(self._conn)
            insert_clinic.insert(clinic)
        for i in logs:
            i = i.split(',')
            logistic = DTO.logistics(*i)
            insert_logistic = DAO._logistics(self._conn)
            insert_logistic.insert(logistic)

    def drop_db_tables(self):
        cursor = self._conn
        cursor.execute("""DROP TABLE vaccines""")
        cursor.execute("""DROP TABLE suppliers""")
        cursor.execute("""DROP TABLE clinics""")
        cursor.execute("""DROP TABLE logistics""")

    def update_logistics_count_sent(self, location, amount):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT logistic FROM clinics WHERE location =(?) """, [location])
        logistic_id = cursor.fetchone()[0]
        cursor.execute("""SELECT count_sent from logistics where id=(?)""", [logistic_id])
        new_amount = int(cursor.fetchone()[0]) + int(amount)
        cursor.execute("""UPDATE logistics
                          SET count_sent=(?)
                          WHERE id=(?) """, [new_amount, logistic_id])


    def update_logistics_count_received(self, sup_id, amount):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT logistic FROM suppliers WHERE id=(?)""", [sup_id])
        log_id = cursor.fetchone()[0]
        cursor.execute("""SELECT count_received FROM logistics WHERE id=(?) """, [log_id])
        new_amount = cursor.fetchone()[0] + amount
        cursor.execute("""UPDATE logistics  SET count_received=(?) WHERE id=(?) """, [new_amount, log_id])

    def receive_shipment(self, name, amount, date):
        cursor = self._conn.cursor()
        # cursor.execute("SELECT * FROM vaccines ORDER BY id DESC LIMIT 1")
        if (self.are_any_vaccine_left() == True):
            cursor.execute("""SELECT MAX(id) FROM vaccines""")
            last = cursor.fetchone()[0] + 1
        else:
            last = 1
        cursor.execute("""SELECT id FROM suppliers WHERE name=(?)""", [name])
        supp_id = cursor.fetchone()[0]
        amount = int(amount)

        # TODO: make this object datetime
        # date = datetime.strptime(date, '%Y-%m-%d')
        vaccine_to_add = DTO.vaccines(last, date, supp_id, amount)
        adding_vaccine = DAO._vaccines(cursor)
        adding_vaccine.insert(vaccine_to_add)
        self.update_logistics_count_received(supp_id, amount)

    def send_shipment(self, location, amount):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT id,quantity,supplier FROM vaccines ORDER BY date ASC""")
        all_quant = cursor.fetchall()
        amount_arrived = amount
        sum1 = 0
        amount = int(amount)
        for quant in all_quant:
            if (amount - quant[1] >= 0):
                sum1 += quant[1]
                amount -= quant[1]
                # now need to remove from db the row
                sup_id = quant[2]
                self.update_logistics_count_sent(location, quant[1])
                # self.update_logistics_count_sent(sup_id, quant[1])
                cursor.execute("""DELETE FROM vaccines WHERE id=(?)""", [quant[0]])
                # check here if there are vaccines left,
                if (self.are_any_vaccine_left() == False):
                    print("no more Vaccines, sending what left")
                    cursor.execute("""SELECT demand FROM clinics WHERE location=(?)""", [location])
                    new_demand = cursor.fetchone()[0]
                    new_demand = int(new_demand) - int(sum1)
                    cursor.execute("""UPDATE clinics
                                      SET demand=(?)
                                      WHERE location=(?) """, [new_demand, location])

                    break
            # have enough vaccine to send
            else:
                sum1 += amount
                # update the logistics received and sent now
                self.update_logistics_count_sent(location, amount)
                # self.update_logistics_count_sent(sup_id, amount)
                # take vaccines to logistic
                cursor.execute("""SELECT * FROM vaccines ORDER BY date ASC""")
                new_quant = int(quant[1]) - int(amount)
                cursor.execute("""UPDATE vaccines
                                        SET quantity=(?)
                                        WHERE id=(?) """, [new_quant, quant[0]])

                # subtract amount from demand
                cursor.execute("""SELECT demand FROM clinics WHERE location=(?)""", [location])
                new_demand = cursor.fetchone()[0]
                new_demand = int(new_demand) - int(amount_arrived)
                cursor.execute("""UPDATE clinics
                                                     SET demand=(?)
                                                     WHERE location=(?) """, [new_demand, location])
                break

    def get_total_inventory(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT SUM(quantity) FROM vaccines""")
        return cursor.fetchone()[0]

    def get_total_demand(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT SUM(demand) FROM clinics""")
        return cursor.fetchone()[0]

    def are_any_vaccine_left(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT count(*) FROM vaccines""")
        if (cursor.fetchall()[0][0] == 0):
            return False
        return True

    def is_db_empty(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT count(*) FROM vaccines""")
        if (cursor.fetchall()[0][0] == 0):
            cursor.execute("""SELECT count(*) FROM suppliers""")
            if (cursor.fetchall()[0][0] == 0):
                cursor.execute("""SELECT count(*) FROM clinics""")
                if (cursor.fetchall()[0][0] == 0):
                    cursor.execute("""SELECT count(*) FROM logistics""")
                    if (cursor.fetchall()[0][0] == 0):
                        return True
        return False

    def orders(self, orders, output_file):
        total_received = 0
        total_sent = 0
        total_inventory = self.get_total_inventory()
        total_demand = self.get_total_demand()
        for line in orders:
            # print(line)
            ## receive shipment total inventory bigger

            if len(line.split(',')) == 3:
                self.receive_shipment(*line.split(','))
                total_received = total_received + int(line.split(',')[1])
                total_inventory = self.get_total_inventory()
                write_this = str(total_inventory) + ',' + str(total_demand) + ',' + str(total_received) + ',' + str(
                    total_sent)
                open(output_file, 'a').write(f'{write_this}\n')

            # sent shipment total demand lower
            if len(line.split(',')) == 2:
                if (self.are_any_vaccine_left()):
                    self.send_shipment(*line.split(','))
                total_sent = total_sent + int(line.split(',')[1])
                total_demand = self.get_total_demand()
                total_inventory = self.get_total_inventory()
                write_this = str(total_inventory) + ',' + str(total_demand) + ',' + str(total_received) + ',' + str(
                    total_sent)
                open(output_file, 'a').write(f'{write_this}\n')


repo = _Repository()
atexit.register(repo._closing)
