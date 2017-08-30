package sample;

import java.util.ArrayList;

public class Afspeellijst {
    private String naam;
    private int hoeveelheid;
    private ArrayList<Nummer> nummers;



    public Afspeellijst(String naam, int hoeveelheid, Nummer[] nummer) {
        this.naam = naam;
        this.hoeveelheid = hoeveelheid;
        nummers = new ArrayList<>();
    }
}
