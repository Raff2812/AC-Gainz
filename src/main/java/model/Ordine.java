package model;

import java.util.Date;

public class Ordine {
    private static int last = 0;
    private String emailUtente;
    private int idOrdine;
    private String stato;
    private float totale;
    private Date dataOrdine;

    public Ordine(int idOrdine, String emailUtente, Date dataOrdine, String stato, float totale) {
        this.emailUtente = emailUtente;
        this.idOrdine = idOrdine;
        this.stato = stato;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }

    public Ordine(Date dataOrdine, String stato, float totale,String emailUtente) {
        this.idOrdine = last++;
        this.emailUtente = emailUtente;
        this.stato = stato;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }


    public Ordine(String emailUtente, float totale){
        this.emailUtente = emailUtente;
        this.totale = totale;
        this.stato = "In esecuzione";
        this.dataOrdine = new Date();
    }

    public Ordine()
    {}

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }
}
