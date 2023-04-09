package org.example.data.Varazslatok;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.data.Egysegek.Egyseg;
import org.example.data.Hosok.Hos;

import java.util.ArrayList;
import java.util.Objects;

import static org.example.AppController.hos;
import static org.example.HarcController.*;

public class Tuzlabda extends Varazslat {

    /**
     * tűzlabda konstruktorát használva létrehozhatunk egy tűzlabda varazslatot
     * felüldejiniáljuk a varazsEfekt metódust
     * egy egység koordinátája alapján megsebez mindenkit aki körülötte áll és magát
     * az egységet is független attol hogy csapattárs vagy ellenfél
     *
     * üres mezőre kattintva a HarcController osztály
     * létrehoz egy új egységet az adott koordinátára
     * majd rávarázsolja a tűzlabdát
     *
     * a meghalt egységek először lekerülnek a pályáról majd kikerülnek a listábol is
     */

    //Egy kiválasztott mez® körüli 3x3-as területen lév®
    //összes (saját, illetve ellenséges) egységre
    //(varázser® * 20) sebzés okozása

    public Tuzlabda() {
        super(120, 9);
    }

    @Override
    public void varazsEfekt(Hos hos, ArrayList<Egyseg> lista, Egyseg tamadott, ImageView img, AnchorPane ancPane, FlowPane floSorrend) {
        int sebzes = hos.getVarazsero()*20;
        sebzes=tamadott.sebzestCsokkent(sebzes);
        if (sebzes>tamadott.getElet()*tamadott.getDb()+tamadott.getUtolsoEgysegElet()) {
            ancPane.getChildren().remove(img);
            for (int i = 0; i < floSorrend.getChildren().size(); i++) {
                if (Objects.equals(img.getId(),floSorrend.getChildren().get(i).getId())) {
                    floSorrend.getChildren().remove(i);
                }
            }
            egysegListaIW.remove(img);
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
