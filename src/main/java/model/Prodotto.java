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
    private float calorie;
    private float grassi;
    private float proteine;
    private float carboidrati;

    public Prodotto()
    {

    }

    public Prodotto(String idProdotto, String nome, String descrizione, float prezzo,int quantita,String categoria,String gusto,float calorie,float grassi,float carboidrati,float proteine,int peso,String immagine,int sconto) {
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
    }

    public Prodotto(String nome, String descrizione, float prezzo,int quantita,String categoria,String gusto,float calorie,float grassi,float carboidrati,float proteine,int peso,String immagine,int sconto) {
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

    public float getCalorie() {
        return calorie;
    }

    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }

    public float getGrassi() {
        return grassi;
    }

    public void setGrassi(float grassi) {
        this.grassi = grassi;
    }

    public float getProteine() {
        return proteine;
    }

    public void setProteine(float proteine) {
        this.proteine = proteine;
    }

    public float getCarboidrati() {
        return carboidrati;
    }

    public void setCarboidrati(float carboidrati) {
        this.carboidrati = carboidrati;
    }
}
