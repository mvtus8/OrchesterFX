package com.example.orchesterfx.model;

public class Dychovy extends Nastroj {
    private int pocetDier;
    private String Ladenie;

    public Dychovy(String druh, double cena, String zvuk, int pocet, int pocetDier, String ladenie) {
        super(druh, cena, zvuk, pocet);
        setPocetDier(pocetDier);
        setLadenie(ladenie);
    }

    public int getPocetDier() {
        return pocetDier;
    }

    public String getLadenie() {
        return Ladenie;
    }

    public void setPocetDier(int pocetDier) {
        if (pocetDier < 0)
            throw new IllegalArgumentException("PocetDier must be non-negative");
        this.pocetDier = pocetDier;
    }

    public void setLadenie(String ladenie) {
        if (ladenie == null || ladenie.trim().isEmpty())
            throw new IllegalArgumentException("Ladenie cannot be null or empty");
        String val = ladenie.trim();
        if (!val.matches("(?i)^[cdefgah]+$"))
            throw new IllegalArgumentException(
                    "Ladenie must contain only the letters c,d,e,f,g,a,h (case-insensitive)");
        Ladenie = val.toLowerCase();
    }

    @Override
    public String toString() {
        return "DychovyNastroj{" +
                "druh='" + getDruh() + '\'' +
                ", cena=" + getCena() +
                ", zvuk='" + getZvuk() + '\'' +
                ", pocet=" + getPocet() +
                ", pocetDier=" + pocetDier +
                ", Ladenie='" + Ladenie + '\'' +
                '}';
    }
}