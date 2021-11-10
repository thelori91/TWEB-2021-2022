from tkinter import ttk
from tkinter import filedialog
from tkinter import *
from PIL import ImageTk
import AutoGrid as ag
import os

# TODO
# 1 SOLUZIONE PER UN TIMER CHE CANCELLI MESSAGGI DELLA printOutLabel NO time.sleep() or Event.wait
# 2 AGGIUNGERE ALGORITMO


root = Tk()
root.minsize(600, 500)
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


class my_exception_handler(Exception):
    def __init__(self, value):  # codice inutile
        self.value = value


def callback():
    try:
        path_file = file_name.get()
        if path_file == '':
            raise FileNotFoundError
        file_extension = os.path.splitext(file_name.get())
        if file_extension[1] == '.txt':
            openFileFun = open(path_file, 'r').read().splitlines()
            row_number = len(openFileFun)
            column_number = len(openFileFun[0])
            print(row_number, column_number)
            print_out_label['text'] = 'File correctly opened'
        else:
            raise my_exception_handler('File extension not accepted')
    except FileNotFoundError:
        print_out_label['text'] = 'File not found'
    except my_exception_handler:
        print_out_label['text'] = 'File extension not accepted'


def open_dialog():  # Function that open a file with dialog

    root.filename = filedialog.askopenfilename(initialdir="/", title="Select file",
                                               filetypes=(("txt files", "*.txt"), ("all files", "*.*")))
    if root.filename == '':
        print_out_label['text'] = 'No file selected'
    else:
        file_name.delete(0, END)
        file_name.insert(0, root.filename)
        print_out_label['text'] = 'File correctly opened'


# Function that handle the row's slider event
def row_slider_changed(event):
    sub_matrix_height = int(row_slider.get())
    automated_grid.set_matrix_height(sub_matrix_height)


# Function that handle the column's slider2 event
def column_slider_changed(event):
    sub_matrix_width = int(column_slider.get())
    automated_grid.set_matrix_width(sub_matrix_width)


# RadioButton
draw_radio_button = Radiobutton(content, text="draw", value=0)
erase_radio_button = Radiobutton(content, text="erase", value=1)

# Button
photo = ImageTk.PhotoImage(file='fileIMG.png')
dialog_button = Button(content, image=photo, border=0, command=open_dialog)
search_button = Button(content, text="search pattern", border=0)  # command = avvio algoritmo

# Sliders
row_current_value = DoubleVar()
column_current_value = DoubleVar()
row_current_value.set(1)
column_current_value.set(1)
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
search_button.grid(column=3, row=5, columnspan=2, )  # command = avvio algoritmo


# triggers on window size changed
def redraw_automated_grid():
    # root.update()
    automated_grid.recalibrate_width_height()


# root.update()
automated_grid = ag.Grid(canvas, 4, 5)

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
