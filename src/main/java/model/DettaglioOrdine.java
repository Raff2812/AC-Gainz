package model;

public class DettaglioOrdine {
    private int idOrdine;
    private String idProdotto;
    private int quantita;
    private float prezzo;

    public DettaglioOrdine(int idOrdine, String idProdotto, int quantita, float prezzo) {
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzo = prezzo;
    }

    public DettaglioOrdine()
    {

    }


    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
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
}
