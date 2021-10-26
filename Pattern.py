from tkinter import *

finestra = Tk()
finestra.title("Pattern")


class matrice(Frame):
    def __init__(self, parent, rows, columns):
        Frame.__init__(self, parent)
        text_var = []
        entries = []
        x2 = 0
        y2 = 0
        for i in range(rows):
            # append an empty list to your two arrays
            # so you can append to those later
            text_var.append([])
            entries.append([])
            for j in range(columns):
                # append your StringVar and Entry
                text_var[i].append(StringVar())
                entries[i].append(Entry(finestra, textvariable=text_var[i][j], width=3))
                entries[i][j].place(x=60 + x2, y=50 + y2)
                x2 += 30
            y2 += 30
            x2 = 0


class inizializzazioneMatrice(Frame):
    def __init__(self, parent):
        Frame.__init__(self, parent)

        def gettextinput():
            righe = num_righe.get("1.0", "end")
            colonne = num_colonne.get("1.0", "end")
            print(righe, colonne)
            matrice(self, int(righe), int(colonne))
            newWindow.destroy()

        newWindow = Toplevel(finestra)
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


class letturaFile(Frame):
    def __init__(self, parent):
        Frame.__init__(self, parent)

        def gettextinput():
            file = nome_file_da_leggere.get("1.0", "end")
            string = ''
            for s in file:  # tolgo a capo
                if s != '\n':
                    string += s
            lettura_file = open(string, "r").readlines()
            numero_colonne = len(lettura_file[0])
            numero_righe = len(lettura_file)
            print(numero_righe, numero_colonne)
            inizializzazioneMatrice(finestra)
            new_window.destroy()

        new_window = Toplevel(finestra)
        new_window.title('Lettura File')
        new_window.geometry('400x200')
        Label(new_window, text="Inserisci Nome File").pack()
        nome_file_da_leggere = Text(new_window, height=1, width=30)
        nome_file_da_leggere.pack()
        btn_read = Button(new_window, height=1, width=10, text="Inserisci File", command=gettextinput)
        btn_read.pack()


numero_colonne = 0
numero_righe = 0
finestra.geometry('500x200')  # Dimensione finestra
letturaFile(finestra)  # Richiamo classe per la lettura da file
finestra.mainloop()
