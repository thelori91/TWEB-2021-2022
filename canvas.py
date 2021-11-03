import tkinter as tk
import math

#get an empty canvas
def get_canvas(root,width,height):
    canvas = tk.Canvas(root, bg='grey', width = width, height = height)
    return canvas

#draw on canvas based on its size and the value of a matrix
def draw_canvas(matrix, canvas):
    square_height = canvas.winfo_reqheight() / len(matrix)
    square_width = canvas.winfo_reqheight() / len(matrix[0])
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            coords = [i*square_height, j*square_width, (i*square_height) + square_height, (j * square_width) + square_width]
            if matrix[i][j] == True:
                canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='black',outline = 'green')
            else:
               canvas.create_rectangle(coords[0],coords[1],coords[2], coords[3],fill='white', outline = 'green') 

def update_matrix(event):
    global matrix
    global canvas
    print('Click at ' + str(event.x), str(event.y))
    square_height = canvas.winfo_reqheight() / len(matrix)
    square_width = canvas.winfo_reqheight() / len(matrix[0])
    row = math.floor(event.x / square_width)
    col = math.floor(event.y / square_height)
    matrix[row][col] = not matrix[row][col]
    draw_canvas(matrix = matrix, canvas = canvas)


top = tk.Tk()
canvas = get_canvas(top, 600,600)

matrix = [
    [False, False, False],
    [False, False, False],
    [False, False, False]
]
draw_canvas(matrix, canvas)

canvas.bind('<Button-1>', update_matrix)

canvas.pack()
top.mainloop()
