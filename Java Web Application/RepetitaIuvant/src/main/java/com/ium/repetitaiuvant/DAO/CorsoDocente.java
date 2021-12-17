package DAO;

public class CorsoDocente {
    private Corsi corsi;
    private Docenti docenti;

    public CorsoDocente(Corsi corsi, Docenti docenti) {
        this.corsi = corsi;
        this.docenti = docenti;
    }

    public Corsi getCorsi() {
        return corsi;
    }

    public Docenti getDocenti() {
        return docenti;
    }

    @Override
    public String toString() {
        return "CorsoDocente: " + " Corso= " + corsi +" ,Docente= " + docenti;
    }
}
