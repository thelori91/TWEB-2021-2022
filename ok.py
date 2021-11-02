from tkinter import *


class findPattern(Frame):# algoritmo
    def __init__(self, parent):
        Frame.__init__(self, parent)
        print(matrix[0][0]["text"])

class initMatrix(Frame):
    def __init__(self, parent):
        Frame.__init__(self, parent)

        def changeState(row, colum):
            if (matrix[row][colum]["text"] == "0"):
                matrix[row][colum].configure(text="1")
            else:
                matrix[row][colum].configure(text="0")

        def gettextinput():
            righe = int(num_righe.get("1.0", "end"))
            colonne = int(num_colonne.get("1.0", "end"))
            print(righe, colonne)
            for x in range(righe):
                matrix.append([])
                for y in range(colonne):
                    btn = Button(frame)
                    matrix[x].append(btn)
                    matrix[x][y].config(text="0", command=lambda row=x, col=y: changeState(row, col))
                    matrix[x][y].grid(column=x, row=y, sticky="news")
            frame.columnconfigure(tuple(range(righe)), weight=1)
            frame.rowconfigure(tuple(range(colonne)), weight=1)
            btn_read = Button(root, height=1, width=10, text="Cerca Pattern", command= lambda: findPattern(root))
            btn_read.grid()
            newWindow.destroy()

        newWindow = Toplevel(root)
        newWindow.title('Dimensione Matrice')
        newWindow.geometry('400x200')
        Label(newWindow, text="Inserisci Dimensione Matrice\n").pack()
        Label(newWindow, text="Inserisci Numero Righe").pack()
        num_righe = Text(newWindow, height=1, width=10)
        num_righe.pack()
        Label(newWindow, text="Inserisci Numero Colonne").pack()
        num_colonne = Text(newWindow, height=1, width=10)
        num_colonne.pack()
        btn_read = Button(newWindow, height=1, width=10, text="Inserisci", command=gettextinput)
        btn_read.pack()


class readFile(Frame):
    def __init__(self, parent):
        Frame.__init__(self, parent)

        def gettextinput():
            file = nome_file_da_leggere.get("1.0", "end")
            string = ''
            for s in file:  # delete \n to path
                if s != '\n':
                    string += s
            lettura_file = open(string, "r").readlines()
            numero_colonne = len(lettura_file[0])
            numero_righe = len(lettura_file)
            print(numero_righe, numero_colonne)
            initMatrix(root) # class that create the matrix of button
            new_window.destroy()


        new_window = Toplevel(root)
        new_window.title('Lettura File')
        new_window.geometry('400x200')
        Label(new_window, text="Inserisci Nome File").pack()
        nome_file_da_leggere = Text(new_window, height=1, width=30)
        nome_file_da_leggere.pack()
        btn_read = Button(new_window, height=1, width=10, text="Inserisci File", command=gettextinput)
        btn_read.pack()


###############   Main Program  #################
root = Tk()
root.title("Pattern")
frame = Frame(root)
root.rowconfigure(0, weight=1)
root.columnconfigure(0, weight=1)
frame.grid(row=0, column=0, sticky="news")
grid = Frame(frame)
grid.grid(sticky="news", column=0, row=7, columnspan=2)
frame.rowconfigure(7, weight=1)
frame.columnconfigure(0, weight=1)
matrix = []
readFile(root)
root.mainloop()
