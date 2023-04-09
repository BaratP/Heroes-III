package org.example.data.Egysegek;

public class Griff extends Egyseg{

    /**
     * a Griff konstruktora beállítja az egység adatait a Griffel megegyező tipusúra
     *
     * a visszatamadReset függvény lehetővé teszi hogy a griff végtelenszer vissza tudjon támadni
     */

    public Griff() {
        super("Griff",15,5,10,30,7,15,true,0,30,true);
    }

    @Override
    public void visszatamadReset() {
        this.setVisszaTamad(true);
    }
}
