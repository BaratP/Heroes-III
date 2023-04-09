package org.example.data.Egysegek;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.data.Hosok.Hos;

import static org.example.AppController.hos;

public class Ijasz extends Egyseg{

    /**
     * Ijász az egység egyik gyerekosztálya
     *
     * konsruktorral be tudunk állítani egy íjászhoz méltó képességeket
     * Az íjász különleges képessége a HarcController nevű osztályban lett lekezelve
     */

    public Ijasz () {
        super("ijasz",6,2,4,7,4,9,true,0,7,true);
    }
    //Lövés: az egység távolsági támadást tud végrehajtani, de csak abban az esetben, ha nincs
    //ellenséges egység a közvetlen közelében.
}

