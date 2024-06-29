package model;

public class Prodotto {
    private String idProdotto;
    private String nome;
    private String descrizione;
    private String gusto;
    private String categoria;
    private String immagine;
    private int quantita;
    private int peso;
    private int sconto;
    private float prezzo;
    private int calorie;
    private int grassi;
    private int proteine;
    private int carboidrati;

    private boolean evidenza;

    public Prodotto()
    {

    }

    public Prodotto(String idProdotto, String nome, String descrizione, float prezzo,int quantita,
                    String categoria,String gusto,int calorie,int grassi, int carboidrati,
                    int proteine,int peso,String immagine,int sconto, boolean evidenza) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.gusto = gusto;
        this.categoria = categoria;
        this.immagine = immagine;
        this.quantita = quantita;
        this.peso = peso;
        this.sconto = sconto;
        this.prezzo = prezzo;
        this.calorie = calorie;
        this.grassi = grassi;
        this.proteine = proteine;
        this.carboidrati = carboidrati;
        this.evidenza = evidenza;
    }



    public Prodotto(String idProdotto, String nome, String categoria, float prezzo, String gusto){
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.gusto = gusto;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getGusto() {
        return gusto;
    }

    public void setGusto(String gusto) {
        this.gusto = gusto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getGrassi() {
        return grassi;
    }

    public void setGrassi(int grassi) {
        this.grassi = grassi;
    }

    public int getProteine() {
        return proteine;
    }

    public void setProteine(int proteine) {
        this.proteine = proteine;
    }

    public int getCarboidrati() {
        return carboidrati;
    }

    public void setCarboidrati(int carboidrati) {
        this.carboidrati = carboidrati;
    }

    public boolean isEvidenza() {
        return evidenza;
    }

    public void setEvidenza(boolean evidenza) {
        this.evidenza = evidenza;
    }
}
