package model;

public class Variante {
    private int idVariante;
    private String idProdotto;
    private int idGusto;
    private String gusto;
    private int idConfezione;
    private int pesoConfezione;
    private int quantita; //Quantità disponibile
    private float prezzo;
    private int sconto;
    private boolean evidenza;


    public String getGusto() {
        return gusto;
    }

    public void setGusto(String gusto) {
        this.gusto = gusto;
    }

    public int getPesoConfezione() {
        return pesoConfezione;
    }

    public void setPesoConfezione(int pesoConfezione) {
        this.pesoConfezione = pesoConfezione;
    }

    public int getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(int idVariante) {
        this.idVariante = idVariante;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getIdGusto() {
        return idGusto;
    }

    public void setIdGusto(int idGusto) {
        this.idGusto = idGusto;
    }

    public int getIdConfezione() {
        return idConfezione;
    }

    public void setIdConfezione(int idConfezione) {
        this.idConfezione = idConfezione;
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

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public boolean isEvidenza() {
        return evidenza;
    }

    public void setEvidenza(boolean evidenza) {
        this.evidenza = evidenza;
    }
}
