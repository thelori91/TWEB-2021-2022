import tkinter as tk
import math

class Grid:
    mouse_pressed = False
    is_drawing = True #true: drawing   false:erasing

    #get a Grid object
    def __init__(self,root,width,height,matrix):
        self.mouse_pressed = False
        self.canvas = tk.Canvas(root, bg='grey', width = width, height = height) 
        self.canvas.bind('<Motion>',lambda event: self.update_matrix(event, matrix))
        self.canvas.bind('<ButtonPress-1>', lambda event: self.set_flag(True))    
        self.canvas.bind('<ButtonRelease-1>', lambda event: self.set_flag(False))
        self.draw_canvas(matrix)

    def set_flag(self, value):
        self.mouse_pressed = value

    #should be called every time the matrix has changed
    def update_matrix(self, event, matrix):
        if self.mouse_pressed:
            square_height = self.canvas.winfo_reqheight() / len(matrix)
            square_width = self.canvas.winfo_reqheight() / len(matrix[0])
            row = math.floor(event.x / square_width)
            col = math.floor(event.y / square_height)
            matrix[row][col] = self.is_drawing
            self.draw_canvas(matrix = matrix)

    #draw on canvas based on its size and the value of a matrix
    def draw_canvas(self, matrix):
        square_height = self.canvas.winfo_reqheight() / len(matrix)
        square_width = self.canvas.winfo_reqheight() / len(matrix[0])
        for i in range(len(matrix)):
            for j in range(len(matrix[0])):
                coords = [i*square_height, j*square_width, (i*square_height) + square_height, (j * square_width) + square_width]
                if matrix[i][j] == True:
                    self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='black',outline = 'green')
                else:
                    self.canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='white', outline = 'green') 
   
    def set_is_drawing(self,value):
        self.is_drawing = value

top = tk.Tk()

matrix = [
    [False, False, False],
    [False, False, False],
    [False, False, False]
]

grid = Grid(top, 600, 600, matrix)

grid.canvas.pack()
top.mainloop()