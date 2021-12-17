package DAO;

public class Utenti {
    private int IDutente;
    private String account;
    private String password;
    private String ruolo;

    public Utenti(int IDutente, String account, String password, String ruolo) {
        this.IDutente = IDutente;
        this.account = account;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getIDutente() {
        return IDutente;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return IDutente + " " + account + " " + ruolo;
    }
}
