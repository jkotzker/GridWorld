'''
Authors: Joseph Kotzker and Esther Shimanovich

Initialized Array of grid_squares that includes cost dictionaries
'''

from .grid_square import *

class grid:
    GridSquares = []

    white = {
        'horiz': 0.5,
        'vert': 0.5,
        'diagonal': (2**0.5)/2
    }
    light_gray = {
        'horiz': 1,
        'vert': 1,
        'diagonal': 2**0.5
    }

    def PrintGrid(self):
        #TODO: create function to print out grid
        return

    def __init__(self):
        self.GridSquares = [GridSquare(self, x,y,'white') for x in range(160) for y in range(120)]
