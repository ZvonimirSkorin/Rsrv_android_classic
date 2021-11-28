package com.example.reserve.dodatci;

public class raspored {
private String adresa;
    private String vrijeme;
    private String datum;
    private String imefirme;

    public raspored(String adresa, String vrijeme, String datum, String imefirme) {
        this.adresa = adresa;
        this.vrijeme = vrijeme;
        this.datum = datum;
        this.imefirme = imefirme;
    }
    public raspored(){}

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getImefirme() {
        return imefirme;
    }

    public void setImefirme(String imefirme) {
        this.imefirme = imefirme;
    }
}
