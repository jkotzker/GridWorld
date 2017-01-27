'''
Authors: Joseph Kotzker and Esther Shimanovich

Library of classes for use in terrain generation on a grid
'''

import exceptions

class GridSquare:

    '''Coorinates in the Grid'''
    coordinates = (-1,-1)

    '''Highway membership'''
    memberOfVerticalHighway = False
    memberOfHorizontalHighway = False

    '''Square color: can be 'white', 'light gray', or 'dark gray' once initialized'''
    color = ''

    '''Compute the cost of traversing from self to targetSquare and return float value of cost'''
    def computeTraversalCost(targetSquare):
        #TODO: determine cost of traversing from self to targetSquare
        return

    '''Validate color value, return True if valid and False otherwise'''
    def validateColor(col):
        if col == 'white':
            return True
        elif col == 'light gray':
            return True
        elif col == 'dark gray':
            return True
        else:
            return False

    '''Validate coordinate values, return True if valid and False otherwise'''
    def validateCoordinates(x, y):
        if x < 0 or x > 159:
            return False
        elif y < 0 or y > 119:
            return False
        else:
            return True

    '''Set a new color value for the GridSquare'''
    def setColor(col):
        if self.validateColor(col) == True:
            self.color = color
        else:
            raise exceptions.ColorError(col)

    '''Constructor for new GridSquare objects'''
    def __init__(self, x, y, color):
        if self.validateCoordinates(x, y) == True:
            self.coordinates = (x, y)
        else:
            raise CoordinatesError(x, y)

        if self.validateColor(color) == True:
            self.color = color
        else:
            raise exceptions.ColorError(color)
