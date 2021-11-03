from tkinter import messagebox
from tkinter import ttk
from tkinter import filedialog
from tkinter import *
from PIL import ImageTk

root = Tk()
root.minsize(600, 450)
fileRead = False
root.title("Finding Pattern")
content = ttk.Frame(root, padding=(3, 3, 12, 12))
frame = ttk.Frame(content, borderwidth=5, relief="ridge", width=200, height=100)
searchLabel = ttk.Label(content, text="File Name")
rowLabel = ttk.Label(content, text="Row")
columnLabel = ttk.Label(content, text="Column")
fileName = ttk.Entry(content)


def openFile():  # function that open file with entrybox
    global fileRead
    if fileRead is False:
        pathFile = fileName.get()
        openFileFun = open(pathFile, "r").readlines()
        rowNumber = len(openFileFun)
        columnNumber = len(openFileFun[0])
        print(rowNumber, columnNumber)
        fileRead = True
    else:
        messagebox.showerror(title="Error file", message="File already opened")


def openDialog():  # function that open file with dialog
    global fileRead
    if fileRead is False:
        root.filename = filedialog.askopenfilename(initialdir="/", title="Select file",
                                                   filetypes=(("txt files", "*.txt"), ("all files", "*.*")))
        fileRead = True
        print(root.filename)
    else:
        messagebox.showerror(title="Error file", message="File already opened")


def resetFindingPattern():  # function that re start application
    global fileRead
    fileRead = False


# Button
openFileButton = ttk.Button(content, text="Open File", command=openFile)
photo = ImageTk.PhotoImage(file="fileIMG.png")
dialogButton = Button(content, image=photo, border=0, command=openDialog)
resetButton = ttk.Button(content, text="Reset", command=resetFindingPattern)
file = ttk.Button(content)

# Sliders
slider = ttk.Scale(content, from_=0, to=100, orient='horizontal')
slider2 = ttk.Scale(content, from_=0, to=100, orient='horizontal')

content.grid(column=0, row=0, sticky=(N, S, E, W))
frame.grid(column=0, row=0, columnspan=3, rowspan=5, sticky=(N, S, E, W))
searchLabel.grid(column=3, row=0, columnspan=2, sticky=(N, W), padx=5)
fileName.grid(column=3, row=1, columnspan=2, sticky=(N, E, W), pady=5, padx=5)
dialogButton.grid(column=5, row=1, sticky=(N, W), padx=5, pady=8)
rowLabel.grid(column=3, row=2, sticky=(N, W), padx=5)
slider.grid(column=3, row=3, sticky=(N, E, W), padx=5)
columnLabel.grid(column=4, row=2, sticky=(N, W), padx=5)
slider2.grid(column=4, row=3, sticky=(N, E, W), padx=5)
openFileButton.grid(column=3, row=4, pady=100)
resetButton.grid(column=4, row=4, pady=100)

root.rowconfigure(0, weight=1)
root.columnconfigure(0, weight=1)
content.columnconfigure(0, weight=3)
content.columnconfigure(1, weight=3)
content.columnconfigure(2, weight=3)
content.columnconfigure(3, weight=1)
content.columnconfigure(4, weight=1)
content.rowconfigure(1, weight=1)

root.mainloop()
