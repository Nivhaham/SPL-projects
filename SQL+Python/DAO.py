import DTO
import sqlite3


class _vaccines:
    def __init__(self, conn):
        self._conn = conn

    def insert(self, vaccine):
        self._conn.execute("""INSERT INTO vaccines (id, date, supplier, quantity) Values (?, ?, ?, ?) """
                           , [vaccine.id, vaccine.date, vaccine.supplier, vaccine.quantity])

    def find(self, vaccine_id):
        c = self._conn.cursor()
        c.execute("""
                    SELECT * FROM vaccines WHERE id = ?
                """, [vaccine_id])
        #print(type(*c.fetchone()))
        return DTO.vaccines(*c.fetchone())

    def get_total_inventory(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT SUM(quantity) FROM vaccines""")
        return cursor.fetchone()[0]

    def get_total_demand(self):
        cursor = self._conn.cursor()
        cursor.execute("""SELECT SUM(demand) FROM clinics""")
        return cursor.fetchone()[0]

class _suppliers:
    def __init__(self, conn):
        self._conn = conn

    def insert(self, supplier):
        self._conn.execute("""INSERT INTO suppliers (id, name, logistic) Values (?, ?, ?) """
                           , [supplier.id, supplier.name, supplier.logistic])

    def find(self, name):
        c = self._conn.cursor()
        c.execute("""
                    SELECT id FROM suppliers WHERE name = ?
                """, [name])

        return DTO.suppliers(*c.fetchone())


class _clinics:
    def __init__(self, conn):
        self._conn = conn

    def insert(self, clinic):
        self._conn.execute("""INSERT INTO clinics (id, location, demand, logistic) Values (?, ?, ?, ?) """
                           , [clinic.id, clinic.location, clinic.demand, clinic.logistic])

    def find(self, clinic_id):
        c = self._conn.cursor()
        c.execute("""
                    SELECT id FROM clinics WHERE id = ?
                """, [clinic_id])

        return DTO.clinics(*c.fetchone())


class _logistics:
    def __init__(self, conn):
        self._conn = conn

    def insert(self, logistic):
        self._conn.execute("""INSERT INTO logistics (id, name, count_sent, count_received) Values (?, ?, ?, ?) """
                           , [logistic.id, logistic.name, logistic.count_sent, logistic.count_received])

    def find(self, logistic_id):
        c = self._conn.cursor()
        c.execute("""
                    SELECT id FROM logistics WHERE id = ?
                """, [logistic_id])
        return DTO.logistics(*c.fetchone())
