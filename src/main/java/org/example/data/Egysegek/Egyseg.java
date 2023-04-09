package org.example.data.Egysegek;

public class Egyseg {

    /**
     * Az egység osztály egy őssztály
     * Az egység osztály sok adattagot tartalmaz.
     * vettMennyiség név ár sebés élet és még sok hasonló.
     * a legtöbb adattaghoz tartozik getter setter
     * (amelyekhez nem azok nem kellenek)
     * (de vannak olyanok is amik végül nem kelettek)
     *
     * létezik paraméteres és paraméter nélküli konstruktora
     * és tartalmaz 2 metódust amit azért hoztam létre
     * hogy a gyerek osztályok felül tudják írni
     */

    private int vettMennyiseg;
    private String nev;
    private int ar;
    private int sebzestol;
    private int sebzesig;
    private int elet;
    private int sebesseg;
    private int kezdemenyezes;
    private boolean specialis;
    private int db=0;
    private int utolsoEgysegElet;
    private int i;
    private int j;
    private boolean visszaTamad;


    public void setVettMennyiseg(int vettMennyiseg) {
        this.vettMennyiseg = vettMennyiseg;
    }

    public int getVettMennyiseg() {
        return vettMennyiseg;
    }

    public boolean isVisszaTamad() {
        return visszaTamad;
    }

    public void setVisszaTamad(boolean visszaTamad) {
        this.visszaTamad = visszaTamad;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getSebzestol() {
        return sebzestol;
    }

    public void setSebzestol(int sebzestol) {
        this.sebzestol = sebzestol;
    }

    public int getSebzesig() {
        return sebzesig;
    }

    public void setSebzesig(int sebzesig) {
        this.sebzesig = sebzesig;
    }

    public int getElet() {
        return elet;
    }

    public void setElet(int elet) {
        this.elet = elet;
    }

    public int getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(int sebesseg) {
        this.sebesseg = sebesseg;
    }

    public int getKezdemenyezes() {
        return kezdemenyezes;
    }

    public void setKezdemenyezes(int kezdemenyezes) {
        this.kezdemenyezes = kezdemenyezes;
    }

    public boolean isSpecialis() {
        return specialis;
    }

    public void setSpecialis(boolean specialis) {
        this.specialis = specialis;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public int getUtolsoEgysegElet() {
        return utolsoEgysegElet;
    }

    public void setUtolsoEgysegElet(int utolsoEgysegElet) {
        this.utolsoEgysegElet = utolsoEgysegElet;
    }

    public Egyseg(String nev, int ar, int sebzestol, int sebzesig, int elet, int sebesseg, int kezdemenyezes, boolean specialis, int db, int utolsoEgysegElet, boolean visszaTamad) {
        this.nev = nev;
        this.ar = ar;
        this.sebzestol = sebzestol;
        this.sebzesig = sebzesig;
        this.elet = elet;
        this.sebesseg = sebesseg;
        this.kezdemenyezes = kezdemenyezes;
        this.specialis = specialis;
        this.db = db;
        this.utolsoEgysegElet = utolsoEgysegElet;
        this.visszaTamad=visszaTamad;
    }

    public Egyseg() {
        this.nev = "none";
        this.ar = 1;
        this.sebzestol = 1;
        this.sebzesig = 2;
        this.elet = 1;
        this.sebesseg = 1;
        this.kezdemenyezes = 1;
        this.specialis = false;
        this.visszaTamad = true;
    }

    public void visszatamadReset() {
    }

    public int sebzestCsokkent(int sebzes) {
        return sebzes;
    }
}
