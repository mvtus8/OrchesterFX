package com.example.orchesterfx.model;

public class RytmickyNastroj extends Nastroj {
    private int pocetZvukov;

    public RytmickyNastroj(String druh, double cena, String zvuk, int pocet, int pocetZvukov) {
        super(druh, cena, zvuk, pocet);
        setPocetZvukov(pocetZvukov);
    }

    public int getPocetZvukov() {
        return pocetZvukov;
    }

    public void setPocetZvukov(int pocetZvukov) {
        if (pocetZvukov < 0)
            throw new IllegalArgumentException("PocetZvukov must be non-negative");
        this.pocetZvukov = pocetZvukov;
    }

    @Override
    public String toString() {
        return "RytmickyNastroj{" +
                "druh='" + getDruh() + '\'' +
                ", cena=" + getCena() +
                ", zvuk='" + getZvuk() + '\'' +
                ", pocet=" + getPocet() +
                ", pocetZvukov=" + pocetZvukov +
                '}';
    }
}
