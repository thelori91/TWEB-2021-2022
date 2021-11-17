from tkinter import ttk
from tkinter import filedialog
from tkinter import *
from PIL import ImageTk
import os
import AutoGrid as ag
import Algorithm as algo

root = Tk()
root.minsize(590, 590)
root.title('Finding Pattern')
content = ttk.Frame(root, padding=(3, 3, 12, 12))
search_label = ttk.Label(content, text='File Name (with extension .txt)')
sv = StringVar()
sv.trace("w", lambda name, index, mode, sv=sv: callback())
file_name = ttk.Entry(content, textvariable=sv)
print_out_label = ttk.Label(content, text='')
row_label = ttk.Label(content, text='Row')
column_label = ttk.Label(content, text='Column')
canvas = Canvas(content, bg='yellow', width=200, height=100)
file_load = False


# Class defined to handle our Exceptions
class my_exception_handler(Exception):
    def __init__(self, value): 
        self.value = value


def callback():
    global file_load
    try:
        path_file = file_name.get()
        if path_file == '':
            raise FileNotFoundError
        file_extension = os.path.splitext(file_name.get())
        if file_extension[1] == '.txt':
            print_out_label.configure(foreground="green")
            open(path_file, 'r').read().splitlines() 
            print_out_label['text'] = 'File correctly opened'
            file_load = True
        else:
            print_out_label.configure(foreground="red")
            raise my_exception_handler('File extension not accepted')
    except FileNotFoundError:
        print_out_label.configure(foreground="red")
        print_out_label['text'] = 'File not found'
        file_load = False
    except my_exception_handler:
        print_out_label.configure(foreground="red")
        print_out_label['text'] = 'File extension not accepted'
        file_load = False


# Function that open a file with dialog
def open_dialog():
    global file_load
    root.filename = filedialog.askopenfilename(initialdir="/", title="Select file",
                                               filetypes=(("txt files", "*.txt"), ("all files", "*.*")))
    if root.filename == '':
        print_out_label.configure(foreground="red")
        print_out_label['text'] = 'No file selected'
        file_load = False
    else:
        file_name.delete(0, END)
        file_name.insert(0, root.filename)
        print_out_label.configure(foreground="green")
        print_out_label['text'] = 'File correctly opened'
        file_load = True


# Function that handle the row's slider event
def row_slider_changed(event):
    sub_matrix_height = int(row_slider.get())
    automated_grid.set_matrix_height(sub_matrix_height)


# Function that handle the column's slider2 event
def column_slider_changed(event):
    sub_matrix_width = int(column_slider.get())
    automated_grid.set_matrix_width(sub_matrix_width)


# Function used by Draw's radio button to draw on canvas
def draw_selected():
    automated_grid.set_is_drawing(True)


# Function used by Erase's radio button to erase on canvas
def erase_selected():
    automated_grid.set_is_drawing(False)


def file_to_matrix(path):
    file = open(path, 'r').read().splitlines() 
    mat = []
    for line in file:
        line = list(line.replace(' ', ''))
        wrong_character = False
        for c in line:
            if c not in ['0', '1']:
                wrong_character = True
                print('File contains unrequired character: {}, please check it'.format(c))
                print('Skipping line...')
        if(line != [] and not wrong_character):
            mat.append(line)
    return mat


# Function that use the finding pattern Algorithm
# to search pattern and print on window the result
def search_and_show_result():
    global automated_grid
    global file_load
    try:
        if file_load is True:
            pat = convert_boolean_to_char(automated_grid.matrix)
            results = []

            matrix = file_to_matrix(file_name.get())

            results.append(algo.find_pattern(matrix, pat))
            results.append(algo.find_pattern(matrix, algo.rotate90(pat)))
            results.append(algo.find_pattern(matrix, algo.rotate90(algo.rotate90(pat))))
            results.append(algo.find_pattern(matrix, algo.rotate90(algo.rotate90(algo.rotate90(pat)))))
            
            print_res = ''
            if len(results[0]) != 0:                                      
                print_res = '0° Number of time: ' + str(len(results[0])) + ', coordinates: ' + str(results[0]) + '\n'
            if len(results[1])!= 0:  
                print_res = print_res + '90° Number of time: ' + str(len(results[1])) + ', coordinates: ' + str(results[1]) + '\n'
            if len(results[2]) != 0:
                print_res = print_res + '180° Number of time: ' + str(len(results[2])) + ', coordinates: ' + str(results[2]) + '\n'
            if len(results[3]) != 0:   
                print_res = print_res + '270° Number of time: ' + str(len(results[3])) + ', coordinates: ' + str(results[3]) + '\n'

            if print_res == '':
                print_out_label.configure(foreground="yellow")
                print_out_label['text'] = 'Pattern not found'
            else: 
                print_out_label.configure(foreground="green")   
                print_out_label['text'] = print_res
        else:
            raise my_exception_handler('Please select a file')
    except my_exception_handler:
        print_out_label.configure(foreground="red")
        print_out_label['text'] = 'Please select a file'
        file_load = False


def convert_boolean_to_char(matrix):
    ret = []
    for i in range(len(matrix)):
        ret.append([])
        for j in range(len(matrix[0])):
            ret[i].append('1' if matrix[i][j] else '0')
    return ret


# Function that cleans everything on cavans
def erase_automated_grid():
    automated_grid.initialize_matrix(int(row_slider.get()), int(column_slider.get()))


# RadioButton
erase_radio_button = Radiobutton(content, text='Erase', value=0, command=erase_selected)
draw_radio_button = Radiobutton(content, text='Draw', value=1, command=draw_selected)

# Button
photo = ImageTk.PhotoImage(file='./Assets/fileIMG.png')
dialog_button = Button(content, image=photo, border=0, command=open_dialog)
search_button = Button(content, text='Search Pattern', border=0, command=search_and_show_result)
reset_matrix_button = Button(content, text='Erase All', border=0, command=erase_automated_grid)

# Sliders
row_current_value = DoubleVar()
column_current_value = DoubleVar()
row_current_value.set(5)
column_current_value.set(5)
row_slider = ttk.Scale(content, from_=1, to=10, orient='horizontal', variable=row_current_value,
                       command=row_slider_changed)
column_slider = ttk.Scale(content, from_=1, to=10, orient='horizontal', variable=column_current_value,
                          command=column_slider_changed)

content.grid(column=0, row=0, sticky=(N, S, E, W))
canvas.grid(column=0, row=0, columnspan=3, rowspan=6, sticky=(N, S, E, W))
search_label.grid(column=3, row=0, columnspan=2, sticky=(N, W), padx=5)
file_name.grid(column=3, row=1, columnspan=2, sticky=(N, E, W), pady=5, padx=5)
dialog_button.grid(column=5, row=1, sticky=(N, W), padx=5, pady=8)
print_out_label.grid(column=3, row=2, columnspan=2, sticky=(N, W), padx=5, pady=70)
row_label.grid(column=3, row=3, sticky=(N, W), padx=5)
row_slider.grid(column=3, row=4, sticky=(N, E, W), padx=5)
column_label.grid(column=4, row=3, sticky=(N, W), padx=5)
column_slider.grid(column=4, row=4, sticky=(N, E, W), padx=5)
draw_radio_button.grid(column=3, row=4, pady=100)
erase_radio_button.grid(column=4, row=4, pady=100)
search_button.grid(column=3, row=5)
reset_matrix_button.grid(column=4, row=5)


# Triggers on window size changed
def redraw_automated_grid():
    root.update()
    automated_grid.recalibrate_width_height()


root.update()
automated_grid = ag.Grid(canvas, 5, 5)

root.bind('<Configure>', lambda event: redraw_automated_grid())
root.rowconfigure(0, weight=1)
root.columnconfigure(0, weight=1)
content.columnconfigure(0, weight=3)
content.columnconfigure(1, weight=3)
content.columnconfigure(2, weight=3)
content.columnconfigure(3, weight=1)
content.columnconfigure(4, weight=1)
content.rowconfigure(1, weight=1)

root.mainloop()
