package org.example.data.Varazslatok;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.data.Egysegek.Egyseg;
import org.example.data.Hosok.Hos;

import java.util.ArrayList;

import static org.example.HarcController.egysegLista;

public class Varazslat {

    /**
     * Ős osztály
     * öröklődik:
     * belőle a varazslat ára , mana köoltsége
     * getter setter metódusok
     * varazsEfekt metódus.
     * az utóbbi egy álatlános varázslatnál nem csinál semmit
     * hisz minden varázslat egyedi efektel bír
     */

    private int ar;
    private int manacost;

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getManacost() {
        return manacost;
    }

    public void setManacost(int manacost) {
        this.manacost = manacost;
    }

    public Varazslat(int ar, int manacost) {
        this.ar = ar;
        this.manacost = manacost;
    }

    public Varazslat() {
        this.ar=120;
        this.manacost=5;
    }

    public void varazsEfekt (Hos hos, ArrayList<Egyseg> lista, Egyseg tamadott, ImageView img, AnchorPane ancPane, FlowPane floSorrend) {
    }
}
