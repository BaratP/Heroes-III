package org.example.data.Varazslatok;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.data.Egysegek.Egyseg;
import org.example.data.Hosok.Hos;

import java.util.ArrayList;

public class Feltamasztas extends Varazslat{

    /**
     * Feltámasztás konstruktorát használva létrehozhatunk egy feltámasztás varazslatot
     * felüldejiniáljuk a varazsEfekt metódust
     *
     * ha ezt a varázslatot egy egységre használják akkor az adott egység
     * vissza gyógyul és feltámad nade persze nem zombiként.
     *
     * a feltámasztott egységek száma nem haladhatja meg a csatába vitt
     * egységek számát.
     *
     * Ha az egész (pl Ijász) sereg meghal akkor őket már sajnos
     * nem lehet meg gyógyítani
     */

    //Egy kiválasztott saját egység feltámasztása.
    //Maximális gyógyítás mértéke: (varázser® * 50)
    //(de az eredeti egységszámnál több nem lehet)

    public Feltamasztas() {
        super(120,6);
    }

    @Override
    public void varazsEfekt(Hos hos, ArrayList<Egyseg> lista, Egyseg tamadott, ImageView img, AnchorPane ancPane, FlowPane floSorrend) {
        int gyogyitas=hos.getVarazsero()*50;
        System.out.println("gyogyítás mértéke: "+gyogyitas);
        if (tamadott.getUtolsoEgysegElet()+gyogyitas<tamadott.getElet()) {
            tamadott.setUtolsoEgysegElet(tamadott.getUtolsoEgysegElet()+gyogyitas);
            System.out.println("csak az utolsó egység gyógyult: " + tamadott.getUtolsoEgysegElet());
        }
        else {
            gyogyitas=gyogyitas-(tamadott.getElet()-tamadott.getUtolsoEgysegElet());
            tamadott.setUtolsoEgysegElet(tamadott.getElet());
            int gyogyitottdb=gyogyitas/tamadott.getElet();
            if (tamadott.getDb()+gyogyitottdb>=tamadott.getVettMennyiseg()) {
                tamadott.setDb(tamadott.getVettMennyiseg());
                System.out.println(tamadott.getVettMennyiseg());
                System.out.println("A sereg összes tagja újra a csatában!");
            }
            else {
                tamadott.setDb(tamadott.getDb()+gyogyitottdb);
                System.out.println("A felgyógyított sereged ennyi egységből áll: "+tamadott.getDb());
            }
        }


    }
}
