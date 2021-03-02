class Read_Parse_file:

    def read_and_parse_config(self,config_file):
        ## returns as tuple each cell is a row
        with open(config_file) as config_file:
            return config_file.readlines()
        # for line in config_file:
        #    config += line
        #   print(line)

    def what_to_insert_to_db(self, config_file):
        config_file_var = self.read_and_parse_config(self, config_file)
        # print(config_file)
        config = config_file_var[0].split(',')
        rows_of_vaccines_to_read = config[0]
        rows_of_suppliers_to_read = config[1]
        rows_of_clinics_to_read = config[2]
        rows_of_logistics_to_read = config[3]
        starting_at = 1
        i = 1
        vaccines, suppliers, clinics, logistics = [], [], [], []
        # print(rows_of_suppliers_to_read)
        while i < int(rows_of_vaccines_to_read) + starting_at:
            # print(config_file[i])
            vaccines.append(config_file_var[i])
            i = i + 1
        # print(vaccines)
        starting_at = i
        while i < int(rows_of_suppliers_to_read) + starting_at:
            # print(config_file[i])
            suppliers.append(config_file_var[i])
            i = i + 1
        starting_at = i
        while i < int(rows_of_clinics_to_read) + starting_at:
            # print(config_file[i])
            clinics.append(config_file_var[i])
            i = i + 1
        starting_at = i
        while i < int(rows_of_logistics_to_read) + starting_at:
            # print(config_file[i])
            logistics.append(config_file_var[i])
            i = i + 1

        return vaccines, suppliers, clinics, logistics

    def get_vaccines_conf(self,config_file):
        a, b, c, d = Read_Parse_file.what_to_insert_to_db(self,config_file)
        return a

    def get_suppliers_conf(self,config_file):
        a, b, c, d = Read_Parse_file.what_to_insert_to_db(self,config_file)
        return b

    def get_clinics_conf(self,config_file):
        a, b, c, d = Read_Parse_file.what_to_insert_to_db(self,config_file)
        return c

    def get_logistics_conf(self,config_file):
        a, b, c, d = Read_Parse_file.what_to_insert_to_db(self,config_file)
        return d
    def read_and_parse_orders(self,order_file):
        with open(order_file) as config_file:
            return config_file.readlines()