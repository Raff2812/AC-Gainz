package model;

public class Carrello {
    private String emailUtente;
    private int idCarrello;
    private int idProdotto;
    private int quantita;
    private float prezzo;

    public Carrello( int idCarrello,String emailUtente, int idProdotto, int quantita, float prezzo) {
        this.emailUtente = emailUtente;
        this.idCarrello = idCarrello;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public Carrello(String emailUtente, int idProdotto, int quantita, float prezzo) {
        this.emailUtente = emailUtente;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public Carrello()
    {

    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public int getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(int idCarrello) {
        this.idCarrello = idCarrello;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
}
