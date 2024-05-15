package model;

public class DettaglioOrdine {
    private int idDettaglioOrdine;
    private int idOrdine;
    private int idProdotto;
    private int quantita;
    private float prezzo;

    public DettaglioOrdine(int idDettaglioOrdine, int idOrdine, int idProdotto, int quantita, float prezzo) {
        this.idDettaglioOrdine = idDettaglioOrdine;
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public DettaglioOrdine(int idOrdine, int idProdotto, int quantita, float prezzo) {
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public DettaglioOrdine()
    {

    }

    public int getIdDettaglioOrdine() {
        return idDettaglioOrdine;
    }

    public void setIdDettaglioOrdine(int idDettaglioOrdine) {
        this.idDettaglioOrdine = idDettaglioOrdine;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
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
