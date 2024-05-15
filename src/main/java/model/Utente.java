package model;

import java.util.Date;

public class Utente {
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private Date dataNascita;

    private String indirizzo;
    private String telefono;
    private int poteri;

    public Utente()
    {}

    public Utente(String email, String password, String nome, String cognome, String codiceFiscale, Date dataNascita, String indirizzo, String telefono, int poteri) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.poteri = poteri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public java.sql.Date getDataNascita() {
        return (java.sql.Date) dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPoteri() {
        return poteri;
    }

    public void setPoteri(int poteri) {
        this.poteri = poteri;
    }
}
