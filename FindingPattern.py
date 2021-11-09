from tkinter import ttk
from tkinter import filedialog
from tkinter import *
from PIL import ImageTk
from canvas import *
import os

# TODO
# 1 SISTEMARE CODICE
# 2 SOLUZIONE PER UN TIMER CHE CANCELLI MESSAGGI DELLA printOutLabel NO time.sleep() or Event.wait
# 3 AGGIUNGERE CANVAS
# 4 AGGIUNGERE ALGORITMO


root = Tk()
root.minsize(600, 500)
fileRead = False
root.title('Finding Pattern')
content = ttk.Frame(root, padding=(3, 3, 12, 12))
searchLabel = ttk.Label(content, text='File Name (with extension .txt)')
fileName = ttk.Entry(content)
printOutLabel = ttk.Label(content, text='')
rowLabel = ttk.Label(content, text='Row')
columnLabel = ttk.Label(content, text='Column')
canvas = Canvas(content, bg='yellow', width = 200, height = 100) 

class MyError(Exception):
    def __init__(self, value):  # codice inutile
        self.value = value


def openFile():  # Function that open a file with the fileName Entrybox
    global fileRead
    if fileRead is False:
        try:
            pathFile = fileName.get()
            if pathFile == '':
                raise FileNotFoundError
            file_extension = os.path.splitext(fileName.get())
            if file_extension[1] == '.txt':
                openFileFun = open(pathFile, 'r').read().splitlines()
                rowNumber = len(openFileFun)
                columnNumber = len(openFileFun[0])
                fileRead = True
                print(rowNumber, columnNumber)
                printOutLabel['text'] = 'File correctly opened'
            else:
                raise MyError('File extension not accepted')
        except FileNotFoundError:
            printOutLabel['text'] = 'File not found'
        except MyError:
            printOutLabel['text'] = 'File extension not accepted'
    else:
        printOutLabel['text'] = 'File already opened'


def openDialog():  # Function that open a file with dialog
    global fileRead
    if fileRead is False:
        root.filename = filedialog.askopenfilename(initialdir="/", title="Select file",
                                                   filetypes=(("txt files", "*.txt"), ("all files", "*.*")))
        if root.filename == '':
            printOutLabel['text'] = 'No file selected'
        else:
            printOutLabel['text'] = 'File correctly opened'
            fileRead = True
    else:
        printOutLabel['text'] = 'File already opened'


def resetFindingPattern():  # Function that restart application
    global fileRead
    fileRead = False
    printOutLabel['text'] = ''


def slider_changed(event):  # DA USARE PER VALORE DELLO SLIDER 1
    global sub_matrix_height 
    sub_matrix_height = int(slider.get())
    initialize_matrix()
    redraw_automated_grid()



def slider_changed2(event):  # DA USARE PER VALORE DELLO SLIDER 2
    global sub_matrix_width
    sub_matrix_width = int(slider2.get())
    initialize_matrix()
    redraw_automated_grid()


# Button
openFileButton = ttk.Button(content, text='Open File', command=openFile)
photo = ImageTk.PhotoImage(file='fileIMG.png')
dialogButton = Button(content, image=photo, border=0, command=openDialog)
resetButton = ttk.Button(content, text='Reset', command=resetFindingPattern)
file = ttk.Button(content)

# Sliders
current_value = DoubleVar()
current_value2 = DoubleVar()
slider = ttk.Scale(content, from_=1, to=10, orient='horizontal', variable=current_value, command=slider_changed)
slider2 = ttk.Scale(content, from_=1, to=10, orient='horizontal', variable=current_value2, command=slider_changed2)

content.grid(column=0, row=0, sticky=(N, S, E, W))
canvas.grid(column=0, row=0, columnspan=3, rowspan=5, sticky=(N, S, E, W))
searchLabel.grid(column=3, row=0, columnspan=2, sticky=(N, W), padx=5)
fileName.grid(column=3, row=1, columnspan=2, sticky=(N, E, W), pady=5, padx=5)
dialogButton.grid(column=5, row=1, sticky=(N, W), padx=5, pady=8)
printOutLabel.grid(column=3, row=2, columnspan=2, sticky=(N, W), padx=5, pady=70)
rowLabel.grid(column=3, row=3, sticky=(N, W), padx=5)
slider.grid(column=3, row=4, sticky=(N, E, W), padx=5)
columnLabel.grid(column=4, row=3, sticky=(N, W), padx=5)
slider2.grid(column=4, row=4, sticky=(N, E, W), padx=5)
openFileButton.grid(column=3, row=4, pady=100)
resetButton.grid(column=4, row=4, pady=100)


def initialize_matrix():
    global matrix
    global sub_matrix_height
    global sub_matrix_width
    print('init {} {}'.format(sub_matrix_height, sub_matrix_width))
    matrix = []
    for i in range(sub_matrix_height):
        matrix.append([])
        for j in range(sub_matrix_width):
            matrix[i].append(False)

def redraw_automated_grid():
    global automated_grid
    print('redrawing')
    root.update()
    automated_grid.recalibrate_width_height()
    automated_grid.draw_canvas(matrix)


initialize_matrix()
root.update()
automated_grid = Grid(canvas)

root.bind('<Configure>', lambda event:
        redraw_automated_grid()
)
root.rowconfigure(0, weight=1)
root.columnconfigure(0, weight=1)
content.columnconfigure(0, weight=3)
content.columnconfigure(1, weight=3)
content.columnconfigure(2, weight=3)
content.columnconfigure(3, weight=1)
content.columnconfigure(4, weight=1)
content.rowconfigure(1, weight=1)

root.mainloop()
