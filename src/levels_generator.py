import os
import sqlite3

if __name__ == '__main__':
    directory = os.path.abspath(os.path.join(os.path.dirname( __file__ ), '..', 'data/worlds'))

    # Open source file
    source = open(directory + '/' + 'static_levels_file.dat', 'r')

    # Open connection to db file
    db = directory + '/' + 'levels.db'
    connection = sqlite3.connect(db)
    c = connection.cursor()

    # Transform data from file to data in sql
    command = ''
    line_edit = ''
    table_name = ''
    for line in source:
        if line[0] == ':':
            table_name = line[1:].replace('\n', '')
            command = 'CREATE TABLE IF NOT EXISTS "' + table_name + '"('
            for i in range(1, 17):
                command += 'cell_' + str(i) + ' CHARACTER(4), '
            command += 'cell_17 CHARACTER(4))'
            c.execute(command)
        if line[0] != ':':
            line_edit = line.replace('\n', '')
            command = 'INSERT INTO "' + table_name + '" VALUES('
            for i in line_edit.split(','):
                command += '"' + i + '",'
            command = command[0:-1]
            command += ')'
            c.execute(command)
    connection.commit()
    c.close()
    connection.close()