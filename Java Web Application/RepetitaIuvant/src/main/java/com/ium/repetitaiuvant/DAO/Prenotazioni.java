package DAO;

public class Prenotazioni {
    private Corsi course;
    private Docenti teacher;
    private Utenti user;
    private String slot;

    public Prenotazioni(Corsi course, Docenti teacher, Utenti user, String slot) {
        this.course = course;
        this.teacher = teacher;
        this.user = user;
        this.slot = slot;
    }

    public Corsi getCourse() {
        return course;
    }

    public Docenti getTeacher() {
        return teacher;
    }

    public Utenti getUser() {
        return user;
    }

    public String getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "Prenotazioni " +
                "Corso= " + course +
                ", Docente= " + teacher +
                ", Utente= " + user +
                ", Slot= " + slot;
    }
}
