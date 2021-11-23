import tkinter as tk
import math



class Grid:
    #get a Grid object
    def __init__(self,canvas, height, width):
        self.is_drawing = True #true: drawing   false:erasing
        self.mouse_pressed = False
        self.canvas = canvas
        self.recalibrate_width_height()
        self.matrix = self.initialize_matrix(height, width)
        self.canvas.bind('<Motion>',lambda event: self.update_matrix(event))
        self.canvas.bind('<ButtonPress-1>', lambda event: self.set_flag(True))    
        self.canvas.bind('<ButtonRelease-1>', lambda event: self.set_flag(False))
        self.draw_canvas()

    def update_matrix(self, event):
        if self.mouse_pressed and event.x > 0 and event.y > 0 and event.x < self.width and event.y < self.height:
            square_height = self.height / len(self.matrix)
            square_width = self.width / len(self.matrix[0])
            col = math.floor(event.x / square_width)
            row = math.floor(event.y / square_height)
            self.matrix[row][col] = self.is_drawing
            self.draw_canvas()

    def draw_canvas(self):
        self.canvas.delete('all')
        if hasattr(self, 'matrix') and hasattr(self , 'canvas') and hasattr(self, 'height'):
            square_height = self.height / len(self.matrix)
            square_width = self.width / len(self.matrix[0])
            for i in range(len(self.matrix)):
                for j in range(len(self.matrix[0])):
                    coords = [j*square_width, i*square_height, (j * square_width) + square_width, (i*square_height) + square_height]
                    if self.matrix[i][j]:
                        self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='black',outline = 'green')
                    else:
                        self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='white', outline = 'green') 
    
    def set_is_drawing(self,value):
        self.is_drawing = value

    def initialize_matrix(self, height, width):
            self.matrix = []
            for i in range(height):
                self.matrix.append([])
                for j in range(width):
                    self.matrix[i].append(False)
            self.draw_canvas()
            return self.matrix

    def recalibrate_width_height(self):
        self.width = self.canvas.winfo_width()
        self.height = self.canvas.winfo_height()
        self.draw_canvas()

    def set_flag(self, value):
        self.mouse_pressed = value

    def set_matrix_height(self, n):
        self.initialize_matrix(n , len(self.matrix[0]))

    def set_matrix_width(self, n):
        self.initialize_matrix(len(self.matrix), n)
