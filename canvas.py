import tkinter as tk
import math

matrix = [
    [False, False],
    [False, False]
]

sub_matrix_height = 10 
sub_matrix_width = 3
class Grid:
    mouse_pressed = False
    is_drawing = True #true: drawing   false:erasing

    #get a Grid object
    def __init__(self,canvas):
        self.mouse_pressed = False
        self.canvas = canvas
        self.recalibrate_width_height()
        self.canvas.bind('<Motion>',lambda event: self.update_matrix(event))
        self.canvas.bind('<ButtonPress-1>', lambda event: self.set_flag(True))    
        self.canvas.bind('<ButtonRelease-1>', lambda event: self.set_flag(False))
        self.draw_canvas(matrix)

    def recalibrate_width_height(self):
        self.width = self.canvas.winfo_width()
        self.height = self.canvas.winfo_height()

    def set_flag(self, value):
        self.mouse_pressed = value

    #should be called every time the matrix has changed
    def update_matrix(self, event):
        if self.mouse_pressed:
            global matrix #TODO understand why this is different from the global one
            print(matrix)
            square_height = self.height / len(matrix)
            square_width = self.width / len(matrix[0])
            col = math.floor(event.x / square_width)
            row = math.floor(event.y / square_height)
            matrix[row][col] = self.is_drawing
            self.draw_canvas(matrix)

    #draw on canvas based on its size and the value of a matrix
    def draw_canvas(self, matrix):
        self.canvas.delete('all')
        square_height = self.height / len(matrix)
        square_width = self.width / len(matrix[0])
        for i in range(len(matrix)):
            for j in range(len(matrix[0])):
                coords = [j*square_width, i*square_height, (j * square_width) + square_width, (i*square_height) + square_height]
                if matrix[i][j]:
                    self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='black',outline = 'green')
                else:
                    self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='white', outline = 'green') 
   
    def set_is_drawing(self,value):
        self.is_drawing = value
#TODO: Rename methods