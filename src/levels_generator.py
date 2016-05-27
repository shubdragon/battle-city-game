import os
import sqlite3

if __name__ == '__main__':
    directory = os.path.abspath(os.path.join(os.path.dirname( __file__ ), '..', 'data/worlds'))

    # Open source file
    source = open(directory + '/' + 'static_levels_file.dat', 'r')

    # Open connection to db file
    db = directory + '/' + 'levels.db'
    connection = sqlite3.connect(db)

    # Transform data from file to data in sql
    for line in source:
        print line