class Error(Exception):
    '''Base class for exceptions in this module.'''
    pass

class ColorError(Error):
    '''Exception raised for invalid color value given for a GridSquare'''

    def __init__(self, valuegiven):
        self.valuegiven = valuegiven
        self.message = 'ColorError: Expected white, light gray, or dark gray, but given: \' '+valuegiven+'\'.'

class CoordinatesError(Error):
    '''Exception raised for invalid coordinate values given for a GridSquare'''

    def __init__(self, givenx, giveny ):
        self.givenx = givenx
        self.giveny = giveny
        self.message = 'CoordinatesError: Expected x value between 0 and 159 and y value between 0 and 119, but given x value: ' + givenx + ' and y value: ' + giveny + '.'
