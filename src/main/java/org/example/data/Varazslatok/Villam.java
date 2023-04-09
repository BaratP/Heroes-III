package org.example.data.Varazslatok;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.data.Egysegek.Egyseg;
import org.example.data.Hosok.Hos;

import java.util.ArrayList;
import java.util.Objects;

import static org.example.HarcController.*;

public class Villam extends Varazslat {

    /**
     * villam konstruktorát használva létrehozhatunk egy villam varazslatot
     * felüldejiniáljuk a varazsEfekt metódust
     * a villámmal az ellenfél egységének tudunk megrázó élményt biztosítani
     * ha meghal az egység először lekerül a pályáról majd kikerül a listábol is
     */

    //Egy kiválasztott ellenséges egységre
    //(varázser® * 30) sebzés okozása


    public Villam() {
        super(60,5);
    }

    @Override
    public void varazsEfekt(Hos hos, ArrayList<Egyseg> lista, Egyseg tamadott, ImageView img, AnchorPane ancPane, FlowPane floSorrend) {
        int sebzes = hos.getVarazsero()*30;
        sebzes=tamadott.sebzestCsokkent(sebzes);
        if (sebzes>tamadott.getElet()*tamadott.getDb()+tamadott.getUtolsoEgysegElet()) {
            int removeImageId=0;
            ancPane.getChildren().remove(img);
            for (int i = 0; i < egysegListaIW.size(); i++) {
                if (Objects.equals(egysegListaIW.get(i).getId().toString(),tamadott.getNev())) {
                    removeImageId=i;
                }
            }
            for (int i = 0; i < floSorrend.getChildren().size(); i++) {
                if (Objects.equals(egysegListaIW.get(removeImageId).getId(),floSorrend.getChildren().get(i).getId())) {
                    floSorrend.getChildren().remove(i);
                }
            }
            for (int i = 0; i < egysegLista.size(); i++) {
                if (egysegLista.get(i).getNev()==egysegListaIW.get(removeImageId).getId()) {
                    egysegLista.remove(i);
                    return;
                }
            }
            egysegListaIW.remove(removeImageId);
            varazslatbanMeghalt=true;
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
    }
}
