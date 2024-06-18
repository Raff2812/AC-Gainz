package model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

    private String emailUtente;

    private String idProdotto = "";
    private String nomeProdotto;
    private int quantita;
    private float prezzo;



    public Carrello(String emailUtente, String idProdotto, String nomeProdotto, int quantita, float prezzo) {
        this.emailUtente = emailUtente;
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public Carrello(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public Carrello()
    {

    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
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
      CarrelloDAO carrelloDAO = new CarrelloDAO();

      List<Carrello> cart = new ArrayList<>();

      carrelloDAO.doRemoveCartByUser("raffaelecoppola603@gmail.com");
    }
}


