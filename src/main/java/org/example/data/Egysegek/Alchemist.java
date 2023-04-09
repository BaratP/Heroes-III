package org.example.data.Egysegek;

public class Alchemist extends Egyseg{

    /**
     * az alchemist konstruktora beállítja az egység adatait az alchemist adataira
     *
     * az alchemist különeleg képessége a dobás ami lényegében ugyan az mint az íjász lövése
     * az alchemist különleges képessége a HarcControllerben van lekezelve
     */

    public Alchemist() {
        super("Alchemist",10,5,8,15,5,13,true,0,15,true);
    }
}
