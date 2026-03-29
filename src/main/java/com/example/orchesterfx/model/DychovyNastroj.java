package com.example.orchesterfx.model;

public class DychovyNastroj extends Nastroj {
    private int pocetDier;
    private String Ladenie;

    public DychovyNastroj(String druh, double cena, String zvuk, int pocet, int pocetDier, String ladenie) {
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
        // Accept single note names optionally with a flat (b) or sharp (#), e.g. A, Bb,
        // C#
        if (!val.matches("(?i)^[A-G](#|b)?$"))
            throw new IllegalArgumentException(
                    "Ladenie must be a note name A-G optionally followed by # or b (e.g. A, Bb, C#)");
        Ladenie = val.toUpperCase();
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
