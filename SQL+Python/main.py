import atexit

from Read_Parse_file import Read_Parse_file
from Repository import repo
import DTO
import DAO
import os
import sys


def main(config_file, order_file, output_file):
    cursor = repo._conn.cursor()
    repo.create_db_tables()
    config = Read_Parse_file.read_and_parse_config(Read_Parse_file, config_file)
    v, s, c, lo = Read_Parse_file.what_to_insert_to_db(Read_Parse_file, config_file)
    # populate_db only once
    if (repo.is_db_empty()):
        repo.populate_db(v, s, c, lo)
    print(config)
    orders = Read_Parse_file.read_and_parse_orders(Read_Parse_file, order_file)
    print(orders)

    repo.orders(orders, output_file)


# repo.drop_db_tables()

if __name__ == '__main__':
    main(sys.argv[1], sys.argv[2], sys.argv[3])
