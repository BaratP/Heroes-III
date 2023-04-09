package org.example.data.Egysegek;

public class Harcos extends Egyseg{

    /**
     * Harcos osztály konstruktore létrehoz egy harcos adattal rendelkező
     * egységet.
     *
     * a sebzesCsokkent a harcos különleges képessége
     * minden őt érő (kivéve a hős általi) sebzést felezi
     */

    public Harcos() {
        super("Harcos",20,2,5,50,2,14,true,0,50,true);
    }

    @Override
    public int sebzestCsokkent(int sebzes) {
        return (sebzes/2);
    }
}


