package model;

import java.util.Date;

public class Ordine {
    private String emailUtente;
    private int idOrdine;
    private String stato;
    private float totale;
    private java.sql.Date dataOrdine;

    public Ordine(int idOrdine, java.sql.Date dataOrdine, String stato, float totale,String emailUtente) {
        this.emailUtente = emailUtente;
        this.idOrdine = idOrdine;
        this.stato = stato;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }

    public Ordine(java.sql.Date dataOrdine, String stato, float totale,String emailUtente) {
        this.emailUtente = emailUtente;
        this.stato = stato;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
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

    public java.sql.Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(java.sql.Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }
}
