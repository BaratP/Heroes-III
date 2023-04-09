package org.example.data.Hosok;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.data.Egysegek.Egyseg;
import org.example.data.Varazslatok.Varazslat;

import java.util.ArrayList;

import static org.example.AppController.hos;
import static org.example.HarcController.egysegLista;

public class Hos {

    /**
     * A hos osztály rengeteg adattagot tárol
     * támadás védekezés varázserő , tehát az összes szükséges
     * adatát egy hősnek (méga  köpeny méretét is ^^)
     *
     * a hasznos (és kevésbé hasznos) adattagokhoz tartoznak
     * getter setter metódusok egy paraméteres konstruktor
     * és egy paraméter nélküli is.
     *
     * a hősünkkel meg lehet ütni egy egységet a megut nevű
     * metódussal. Ha olyan erősen ütjük meg
     * hogy az egész sereg belehal akkor a sereg lekerül a
     * pályárol majd kikerül a listábol
     *
     * a hősünk ha vett varázsltokat akkor tud varázsolni is
     * varázsolni csak akkor tud ha van elegendő manája
     * a képesség elkántálásához
     */

    private int tamadas;
    private int vedekezes;
    private int varazsero;
    private int tudas;
    private int moral;
    private int szerencse;
    private int arany;
    private boolean varazsoltUtott=false;
    private boolean villam;
    private String kopenyMeret="Pici";
    private boolean tuzlabda;
    private boolean revive;
    private int mana;



    public boolean isVillam() {
        return villam;
    }

    public void setVillam(boolean villam) {
        this.villam = villam;
    }

    public boolean isTuzlabda() {
        return tuzlabda;
    }

    public void setTuzlabda(boolean tuzlabda) {
        this.tuzlabda = tuzlabda;
    }

    public boolean isRevive() {
        return revive;
    }

    public void setRevive(boolean revive) {
        this.revive = revive;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStatCost() {
        return statCost;
    }

    public void setStatCost(int statCost) {
        this.statCost = statCost;
    }

    private int statCost=5;

    public boolean isVarazsoltUtott() {
        return varazsoltUtott;
    }

    public void setVarazsoltUtott(boolean varazsoltUtott) {
        this.varazsoltUtott = varazsoltUtott;
    }

    public int getTamadas() {
        return tamadas;
    }

    public void setTamadas(int tamadas) {
        this.tamadas = tamadas;
    }

    public int getVedekezes() {
        return vedekezes;
    }

    public void setVedekezes(int vedekezes) {
        this.vedekezes = vedekezes;
    }

    public int getVarazsero() {
        return varazsero;
    }

    public void setVarazsero(int varazsero) {
        this.varazsero = varazsero;
    }

    public int getTudas() {
        return tudas;
    }

    public void setTudas(int tudas) {
        this.tudas = tudas;
    }

    public int getMoral() {
        return moral;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public int getSzerencse() {
        return szerencse;
    }

    public void setSzerencse(int szerencse) {
        this.szerencse = szerencse;
    }

    public int getArany() {
        return arany;
    }

    public void setArany(int arany) {
        this.arany = arany;
    }

    public Hos(int tamadas, int vedekezes, int varazsero, int tudoas, int moral, int szerencse, int arany) {
        this.tamadas = tamadas;
        this.vedekezes = vedekezes;
        this.varazsero = varazsero;
        this.tudas = tudoas;
        this.moral = moral;
        this.szerencse = szerencse;
        this.arany=arany;
    }

    public Hos() {
        this.tamadas=1;
        this.vedekezes=1;
        this.varazsero=1;
        this.tudas=1;
        this.moral=1;
        this.szerencse=1;
    }

    public void megut(Egyseg tamadott, ImageView img,AnchorPane ancPane) {
        int sebzes = this.tamadas*10;
        sebzes=tamadott.sebzestCsokkent(sebzes);
        if (sebzes>tamadott.getElet()*tamadott.getDb()+tamadott.getUtolsoEgysegElet()) {
            tamadott.setI(9);
            tamadott.setJ(11);
            ancPane.getChildren().remove(img);
        }
        else {
            if (sebzes > tamadott.getUtolsoEgysegElet()) {
                sebzes -= tamadott.getUtolsoEgysegElet();
                tamadott.setUtolsoEgysegElet(tamadott.getElet());
                tamadott.setDb(tamadott.getDb() - 1);
            }
            else tamadott.setUtolsoEgysegElet(tamadott.getUtolsoEgysegElet() - sebzes);
            int meghaltEgyseg = sebzes / tamadott.getElet();
            tamadott.setDb(tamadott.getDb() - meghaltEgyseg);
        }
        varazsoltUtott=true;
    }

    public void varazsol(Varazslat varazslat, Egyseg egyseg, ArrayList<Egyseg> lista , ImageView img, AnchorPane ancPane, FlowPane floSorrend) {
        if (varazslat.getManacost()<=this.mana) {
            mana=mana-varazslat.getManacost();
            varazslat.varazsEfekt(hos, lista, egyseg, img, ancPane,floSorrend);
            varazsoltUtott=true;
        }
    }
}
