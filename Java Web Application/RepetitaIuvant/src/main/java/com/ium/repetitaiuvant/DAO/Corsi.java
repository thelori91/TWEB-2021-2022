package DAO;

public class Corsi {
    private int IDcorso;
    private String titoloCorso;

    public Corsi(int IDcorso, String titoloCorso) {
        this.IDcorso = IDcorso;
        this.titoloCorso = titoloCorso;
    }

    public int getIDcorso() {
        return IDcorso;
    }

    public String getTitoloCorso() {
        return titoloCorso;
    }

    @Override
    public String toString() {
        return IDcorso + " "+ titoloCorso;
    }
}
