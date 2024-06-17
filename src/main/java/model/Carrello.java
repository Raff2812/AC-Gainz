package model;

import java.util.List;

public class Carrello {

    private String emailUtente;

    private String idProdotto = "";
    private int quantita;
    private float prezzo;

    public Carrello(String emailUtente, String idProdotto, int quantita, float prezzo) {
        this.emailUtente = emailUtente;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public Carrello(String emailUtente) {
        this.emailUtente = emailUtente;
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




    public String getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String idProdotto) {
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

    public static void main(String[] args){
        //Carrello carrello = new Carrello("raffaelecoppola603@gmail.com", "B001", 2, 45);
        CarrelloDAO dao = new CarrelloDAO();
        //dao.doSave(carrello);

        /*Carrello x = new Carrello("luigiaureimma21@gmail.com");
        dao.doSave(x);*/


        Prodotto p = new Prodotto("PW001", "Pre-Workout Boost", "Formula pre-allenamento per aumentare energia e concentrazione", 29.99F, 45, "pre-workout", "Arancia", 10, 0, 2, 3, 300, "http://example.com/preworkout1.jpg", 10);
        /*Prodotto p1 = new Prodotto("PW002", "Pre-Workout Boost", "Formula pre-allenamento per aumentare energia e concentrazione", 29.99F, 45, "pre-workout", "Arancia", 10, 0, 2, 3, 300, "http://example.com/preworkout1.jpg", 10);
        dao.addProductToCart("raffaelecoppola603@gmail.com", p );
        dao.addProductToCart("raffaelecoppola603@gmail.com", p1);

        dao.removeItem("raffaelecoppola603@gmail.com", p1.getIdProdotto());*/

        dao.removeItem("raffaelecoppola603@gmail.com", p.getIdProdotto());
    }
}


