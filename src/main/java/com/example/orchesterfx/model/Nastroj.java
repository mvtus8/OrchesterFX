package com.example.orchesterfx.model;

public class Nastroj {
    private String druh;
    private double cena;
    private String zvuk;
    private int pocet;

    public Nastroj(String druh, double cena, String zvuk, int pocet) {
        setDruh(druh);
        setCena(cena);
        setZvuk(zvuk);
        setPocet(pocet);
    }

    public String getDruh() {
        return druh;
    }

    public double getCena() {
        return cena;
    }

    public String getZvuk() {
        return zvuk;
    }

    public int getPocet() {
        return pocet;
    }

    public void setDruh(String druh) {
        if (druh == null || druh.trim().isEmpty())
            throw new IllegalArgumentException("Druh cannot be null or empty");
        this.druh = druh.trim();
    }

    public void setCena(double cena) {
        if (Double.isNaN(cena) || cena < 0)
            throw new IllegalArgumentException("Cena must be non-negative");
        this.cena = cena;
    }

    public void setZvuk(String zvuk) {
        if (zvuk == null || zvuk.trim().isEmpty())
            throw new IllegalArgumentException("Zvuk cannot be null or empty");
        this.zvuk = zvuk.trim();
    }

    public void setPocet(int pocet) {
        if (pocet < 0)
            throw new IllegalArgumentException("Pocet must be non-negative");
        this.pocet = pocet;
    }

    @Override
    public String toString() {
        return "Nastroj{" +
                "druh='" + druh + '\'' +
                ", cena=" + cena +
                ", zvuk='" + zvuk + '\'' +
                ", pocet=" + pocet +
                '}';
    }
}
