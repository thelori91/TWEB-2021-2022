package DAO;

public class Docenti {
    private int IDdocente;
    private String nome;
    private String cognome;


    public Docenti(int IDdocente, String nome, String cognome) {
        this.IDdocente = IDdocente;
        this.nome = nome;
        this.cognome = cognome;

    }

    public int getIDdocente() {
        return IDdocente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    @Override
    public String toString() {
        return IDdocente + " " + nome + " " + cognome;
    }
}
