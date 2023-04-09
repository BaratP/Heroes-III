package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.example.data.Egysegek.*;
import org.example.data.Hosok.Hos;
import org.example.data.Varazslatok.Feltamasztas;
import org.example.data.Varazslatok.Tuzlabda;
import org.example.data.Varazslatok.Varazslat;
import org.example.data.Varazslatok.Villam;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.AppController.*;
import static org.example.UnitController.*;

public class HarcController implements Initializable {

    /**
     *Ez az osztály felelős a harc.fxml file kezeléséért.
     * Az fxml file alap értelmezetten nem tartalmazza a mezőt azt majd az initialize
     * függvény fogja legenerálni.
     *
     * Az osztály működése:
     * tekinthetjük ugy az osztályt mint egy ciklus vezérelt kódot, ahol a ciklus változót
     * kattintással növeljük vagy állítjuk vissza 0 ra.
     * következő főbb esetekben következik be:
     * ha a felhasználó rákattint egy üres mezőre
     * ha a felhasználó rákattint egy képre (egység/varázslat)
     *
     * A kód 2 részre osztható:
     * egység kihelyezési fázis:
     * mezőre kattintásnál ilyenkor egységet kisérelünk meg kihelyeni
     * ha kihelyeztük az összes megvásárolt egységet átlépünk a következő szakaszba
     *
     * köztes szakasz. a köztes szakasz az az utolsó egység lerakása és a z első kattintás között van
     * ilyenkor megjelennek új felületek
     *
     * első kattintás után kezdődik a csata innentől a program fel van készítve minden
     * érvénytelen inputra (pl:saját egység támadása)
     * innentől kezdve 3 lehetőségünk van egy egységgel:
     * támadás
     * mozgás
     * várakozás
     * illetve egy 4. lehetőség a varázslás hogyha éppen a mi körünk van
     *
     * Támadás csak akkor következhet be ha az ellenfél közvetlen a támadó
     * egységünk körül helyezkedik el.
     * Az ijásznál és az alchemist-nél ez akkor is bekövetkezhet ha nincs körülötte
     * ellenfél
     *
     * a harc nyomon követését a program futásakor a jobb felső sarokban lévő gombbal tehetjük meg
     * ami megjeleníti nekünk a harc részletes adatait.
     *
     * ha meghal az egyik játékos összes egysége egy-két új mozgás után a játék eléri a végét és
     * győztes sorsol
     */


    /*
        ha átállítod a reszletesKiiras-t "true" ra akkor a csatában részletesebben információt kapsz a csatárol
        ha átállítod a reszletesKiiras-t "false"ra akkor  átláthatóbb de kevesebb  információt kapsz a csatárol
        pl.:
            public boolean reszletesKiiras=true;
            public boolean reszletesKiiras=false;
    */

    public boolean reszletesKiiras=true;
    public static boolean varazslatbanMeghalt=false;
    public boolean kiiratBool=false;
    public Egyseg griffE= new Griff();
    public Egyseg alchemistE = new Alchemist();
    public Egyseg ijaszE = new Ijasz();
    public Egyseg harcosE = new Harcos();
    public Egyseg foldmuvesE = new Foldmuves();
    public Hos ellensegesHos = new Hos();
    public boolean sorrendkialakit=true;
    public boolean radSelected=false;
    public Varazslat varazslat;
    public int egysegreVarazsol=0;
    public static ArrayList<Egyseg> egysegLista;
    public int modValaszto=0;
    public int modValasztoSzamlalo=0;
    public static int egysegSzamlalo=0;
    public ArrayList<Pair<Integer,ImageView>> egysegListaPair = new ArrayList<Pair<Integer,ImageView>>();
    public static ArrayList<ImageView> egysegListaIW = new ArrayList<ImageView>();
    public static int k=0;
    public boolean nyertesCsata=false;

    @FXML TextArea kiirat;
    @FXML Label labKorSzamlalo;
    @FXML AnchorPane ancAktualisEgyseg;
    @FXML ImageView imgAktualisegyseg;
    @FXML AnchorPane ancPane;
    @FXML RadioButton radFoldmuves;
    @FXML RadioButton radHarcos;
    @FXML RadioButton radIjasz;
    @FXML RadioButton radAlchemist;
    @FXML RadioButton radGriff;
    @FXML FlowPane floPane;
    @FXML FlowPane floSorrend;
    @FXML ImageView imgFireBall;
    @FXML ImageView imgRevive;
    @FXML ImageView imgThunder;
    @FXML ImageView ImgUtes;
    @FXML FlowPane floSpell;
    @FXML TitledPane paneKiirat;

    public void egysegTorol (int szam) {
        for (int i = 0; i < egysegLista.size(); i++) {
            if (egysegLista.get(i).getNev()==egysegListaIW.get(szam).getId()) {
                egysegLista.remove(i);
                return;
            }
        }
    }

    public int eredmenyVizsgal() {
        int E=0;
        int S=0;
        for (int i = 0; i < egysegListaIW.size(); i++) {
            if (megkeresIW(egysegListaIW.get(i)).getNev().contains("E")) E=1;
            else S=1;
        }
        if (E==1 && S==0) return 2;
        else if (S==1 && E==0) return 1;
        else return 0;
    }

    public void listafeltolt () {
        if (!egysegLista.contains(griffE)) egysegLista.add(griffE);
        if (!egysegLista.contains(alchemistE)) egysegLista.add(alchemistE);
        if (!egysegLista.contains(ijaszE)) egysegLista.add(ijaszE);
        if (!egysegLista.contains(harcosE)) egysegLista.add(harcosE);
        if (!egysegLista.contains(foldmuvesE)) egysegLista.add(foldmuvesE);
    }

    public void visszaTamadVisszaAllit () {
        for (int i=0;i<egysegListaIW.size();i++) {
            megkeresIW(egysegListaIW.get(i)).setVisszaTamad(true);
        }
    }

    public void visszaTamad (Egyseg tamado,Egyseg tamadott,ImageView img) {
        if (!nyertesCsata) {
            kiirat.setText(kiirat.getText() + "\nVissza támadás:");
            int random=(int) (Math.random() * 100) + 1;
            int duplaz;  //crit
            if (tamado.getNev().contains("E")) {
                System.out.println("vissza ellen:"+random+" "+ellensegesHos.getSzerencse() * 5);
                if (random <= ellensegesHos.getSzerencse() * 5) duplaz = 2;
                else duplaz = 1;
            } else {
                System.out.println("vissza sajat:"+random+" "+hos.getSzerencse() * 5);
                if (random <= hos.getSzerencse() * 5) duplaz = 2;
                else duplaz = 1;
            }
            if (duplaz == 2) kiirat.setText(kiirat.getText() + "\nKritikus találat!");
            int sebzes = (int) (Math.random() * (tamado.getSebzesig() - tamado.getSebzestol()) + tamado.getSebzestol());
            if (reszletesKiiras) kiirat.setText(kiirat.getText() + "\n//kiindulási sebzés: " + sebzes);
            sebzes *= tamado.getDb();
            if (reszletesKiiras) kiirat.setText(kiirat.getText() + "\n//kiindulási * db: " + sebzes);
            if (tamado.getNev().contains("E"))
                sebzes = (int) (sebzes * (1 + ((double) ellensegesHos.getTamadas() / 10)));
            else sebzes = (int) (sebzes * (1 + ((double) hos.getTamadas() / 10)));
            if (reszletesKiiras) kiirat.setText(kiirat.getText() + "\n//sebzes + hős támadás pontaji: " + sebzes);
            if (tamado.getNev().contains("E")) sebzes = (int) (sebzes * (1 - hos.getVedekezes() * (double) 5 / 100));
            else sebzes = (int) (sebzes * (1 - ellensegesHos.getVedekezes() * (double) 5 / 100));
            if (reszletesKiiras)
                kiirat.setText(kiirat.getText() + "\n//sebzes - ellenséges hős védekezés pontjai: " + sebzes);
            sebzes = tamadott.sebzestCsokkent(sebzes);
            if (reszletesKiiras)
                kiirat.setText(kiirat.getText() + "\n//ha az adott egység sebzést csökkent mert harcos: " + sebzes);
            sebzes = sebzes / 2; // visszaTámad
            sebzes = sebzes * duplaz; //crit
            kiirat.setText(kiirat.getText() + "\nSebzés: " + sebzes);
            if (sebzes > tamadott.getElet() * tamadott.getDb() + tamadott.getUtolsoEgysegElet()) {
                kiirat.setText(kiirat.getText() + "\nEz így nem biztos hogy megérte. Vagy mégis? (meghalt)");
                mozgat(img, 9, 11);
                ancPane.getChildren().remove(img);
                int removeImageId=0;
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
                egysegTorol(removeImageId);
                egysegListaIW.remove(removeImageId);
            } else {
                if (sebzes >= tamadott.getUtolsoEgysegElet()) {
                    sebzes -= tamadott.getUtolsoEgysegElet();
                    tamadott.setUtolsoEgysegElet(tamadott.getElet());
                    tamadott.setDb(tamadott.getDb() - 1);
                    kiirat.setText(kiirat.getText() + "\nMeghalt a sebesült (utolsó) egység");
                } else {
                    tamadott.setUtolsoEgysegElet(tamadott.getUtolsoEgysegElet() - sebzes);
                    kiirat.setText(kiirat.getText() + "\nCsak megsebesült az utolsó egység és ennyi élete maradt:" + tamadott.getUtolsoEgysegElet());
                }
                int meghaltEgyseg = sebzes / tamadott.getElet();
                tamadott.setDb(tamadott.getDb() - meghaltEgyseg);
                kiirat.setText(kiirat.getText() + "\nEnnyi egység maradt életben: " + tamadott.getDb());
            }
            kiirat.setText(kiirat.getText() + "\n-----------------------------");
        }
    }

    /**
     *
     * @param tamado
     * az az egység aki támadja az ellenfelét
     * @param tamadott
     * a megtámadott egység
     * @param img
     * a megtámadott egység képe
     *
     * sebzést az előre definiált képletek alapján kiszámoljuk
     * ha a sebzés nagyobb mint az egység össz életereje akkor az egység meghal
     * ha a sebzés kisebb mint az össz életereje akkor csak sebződik
     * egységeket veszít az adott(pl griff) sereg de még kinn marad a csatamezőn
     */

    public void megtamad (Egyseg tamado,Egyseg tamadott,ImageView img) {
        kiirat.setText(kiirat.getText()+"\nTámadás:");
        int random=(int) (Math.random() * 100) + 1;
        int duplaz; //crit
        if (tamado.getNev().contains("E")) {
            System.out.println("tamad ellen:"+random+" "+ellensegesHos.getSzerencse() * 5);
            if (random <= ellensegesHos.getSzerencse() * 5) duplaz = 2;
            else duplaz = 1;
        } else {
            System.out.println("tamad sajat:"+random+" "+hos.getSzerencse() * 5);
            if (random <= hos.getSzerencse() * 5) duplaz = 2;
            else duplaz = 1;
        }
        if (duplaz==2) kiirat.setText(kiirat.getText()+"\nKritikus találat!");
        int sebzes = (int) (Math.random() * (tamado.getSebzesig() - tamado.getSebzestol() ) + tamado.getSebzestol());
        if (reszletesKiiras)kiirat.setText(kiirat.getText()+"\n//kiindulási sebzés: " + sebzes);
        sebzes *= tamado.getDb();
        if (reszletesKiiras)kiirat.setText(kiirat.getText()+"\n//kiindulási * db: " + sebzes);
        if (tamado.getNev().contains("E")) sebzes = (int) (sebzes * (1+((double)ellensegesHos.getTamadas() / 10)));
        else sebzes = (int) (sebzes * (1+((double)hos.getTamadas() / 10)));
        if (reszletesKiiras)kiirat.setText(kiirat.getText()+"\n//sebzes + hős támadás pontaji: " + sebzes);
        if (tamado.getNev().contains("E")) sebzes = (int) (sebzes*(1- hos.getVedekezes() * (double)5 /100));
        else sebzes = (int) (sebzes*(1- ellensegesHos.getVedekezes() * (double)5 /100));
        if (reszletesKiiras)kiirat.setText(kiirat.getText()+"\n//sebzes - ellenséges hős védekezés pontjai: " + sebzes);
        sebzes=tamadott.sebzestCsokkent(sebzes);
        if (reszletesKiiras)kiirat.setText(kiirat.getText()+"\n//ha az adott egység sebzést csökkent mert harcos: " + sebzes);
        sebzes=sebzes*duplaz; //crit
        kiirat.setText(kiirat.getText()+"\nSebzés: " + sebzes);
        if (sebzes>tamadott.getElet()*tamadott.getDb()) {
            kiirat.setText(kiirat.getText()+"\nMeghalt");
            nyertesCsata=true;
            mozgat(img,9,11);
            ancPane.getChildren().remove(img);
            int removeImageId=0;
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
            egysegTorol(removeImageId);
            egysegListaIW.remove(removeImageId);
        }
        else {
            nyertesCsata=false;
            if (sebzes >= tamadott.getUtolsoEgysegElet()) {
                sebzes -= tamadott.getUtolsoEgysegElet();
                tamadott.setUtolsoEgysegElet(tamadott.getElet());
                tamadott.setDb(tamadott.getDb() - 1);
                kiirat.setText(kiirat.getText()+"\nMeghalt a sebesült (utolsó) egység");
            }
            else {
                tamadott.setUtolsoEgysegElet(tamadott.getUtolsoEgysegElet() - sebzes);
                kiirat.setText(kiirat.getText()+"\nCsak megsebesült az utolsó egység és ennyi élete maradt:" + tamadott.getUtolsoEgysegElet());
            }
            int meghaltEgyseg = sebzes / tamadott.getElet();
            tamadott.setDb(tamadott.getDb() - meghaltEgyseg);
            kiirat.setText(kiirat.getText()+"\nEnnyi egység maradt életben: " + tamadott.getDb());
        }
        kiirat.setText(kiirat.getText()+"\n-----------------------------");
    }

    /**
     *
     * @param img ez a mozgatni kívánt egység képe
     * @param i az i/y koordinátát jelöli
     * @param j a j/x koordinátát jelöli
     *
     * nincs visszatérési értéke
     *
     */

    public void mozgat(ImageView img,int i,int j) {
        img.setX(j*60+100);
        img.setY(i*45+100);
        megkeresIW(img).setJ(j);
        megkeresIW(img).setI(i);
    }

    public void kihelyez(Egyseg egyseg,RadioButton rad,int i, int j) {
        ImageView img=new ImageView(new Image("file:src/main/java/org/example/img/"+egyseg.getNev()+".png"));
        img.setFitWidth(60);
        img.setFitHeight(45);
        img.setX(j*60+100);
        img.setY(i*45+100);
        img.setPickOnBounds(true);
        img.setId(egyseg.getNev());
        img.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (egysegreVarazsol) {
                    case 0:
                        if (Objects.equals(egysegListaIW.get(k).getId(), img.getId())) {
                            kiirat.setText(kiirat.getText()+"\nrámkattintottál ezért várakozok ^^");
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                            break;
                        }

                        int vanAKozelbenEllenfel = 0;
                        if (megkeresIW(egysegListaIW.get(k)) == (ijaszE)) {
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                if (!(Objects.equals(megkeresIW(egysegListaIW.get(l)).getNev(), ijaszE.getNev())) && !megkeresIW(egysegListaIW.get(l)).getNev().contains("E") && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - ijaszE.getI()) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - ijaszE.getJ()) <= 1)
                                    vanAKozelbenEllenfel = 1;
                            }
                        }
                        if (megkeresIW(egysegListaIW.get(k)) == (alchemistE)) {
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                if (!(Objects.equals(megkeresIW(egysegListaIW.get(l)).getNev(), alchemistE.getNev())) && !megkeresIW(egysegListaIW.get(l)).getNev().contains("E") && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - alchemistE.getI()) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - alchemistE.getJ()) <= 1)
                                    vanAKozelbenEllenfel = 1;
                            }
                        }
                        if (vanAKozelbenEllenfel == 0 && (megkeresIW(egysegListaIW.get(k)) == (ijaszE) || megkeresIW(egysegListaIW.get(k)) == (alchemistE))) {
                            megtamad(megkeresIW(egysegListaIW.get(k)), egyseg, img);
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                        }
                        if (Math.abs(megkeresIW(egysegListaIW.get(k)).getJ() - (img.getX() - 100) / 60) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(k)).getI() - (img.getY() - 100) / 45) <= 1 && egysegListaIW.get(k).getId().charAt(egysegListaIW.get(k).getId().length() - 1) == 'E') {
                            megtamad(megkeresIW(egysegListaIW.get(k)), egyseg, img);
                            if (egyseg.isVisszaTamad()) {
                                visszaTamad(egyseg, megkeresIW(egysegListaIW.get(k)), egysegListaIW.get(k));
                                egyseg.setVisszaTamad(false);
                                egyseg.visszatamadReset();
                            }
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                        }
                        break;
                    case 1:
                        hos.megut(egyseg,img,ancPane);
                        egysegreVarazsol=0;
                        break;
                    case 2:
                        if (varazslat.getAr()==60) {
                            if (egysegListaIW.get(k).getId().contains("E")){
                                hos.varazsol(varazslat,egyseg,egysegLista,img,ancPane,floSorrend);
                                break;
                            }
                            else break;
                        }
                        if (varazslat.getManacost()==9) {
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                varazslatbanMeghalt=false;
                                if (Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - (img.getX() - 100) / 60) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - (img.getY() - 100) / 45) <= 1) {
                                    hos.varazsol(varazslat,megkeresIW(egysegListaIW.get(l)),egysegLista,egysegListaIW.get(l),ancPane,floSorrend);
                                }
                            }
                            if (varazslatbanMeghalt) {
                                if (k>=egysegListaIW.size()-1) {
                                    k=0;
                                    visszaTamadVisszaAllit();
                                    imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                    labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText())+1)+"");
                                    hos.setVarazsoltUtott(false);
                                }
                                else {
                                    k++;
                                    imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                    imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                }
                            }
                            break;
                        }
                        hos.varazsol(varazslat,egyseg,egysegLista,img,ancPane,floSorrend);
                        egysegreVarazsol=0;
                        break;
                }
            }
        });
        egyseg.setI(i);
        egyseg.setJ(j);
        floPane.getChildren().remove(rad);
        ancPane.getChildren().add(img);
        Pair<Integer,ImageView> pair = new Pair<Integer,ImageView>(egyseg.getKezdemenyezes(),img);
        egysegListaPair.add(pair);
        modValasztoSzamlalo++;
        rad.setSelected(false);
    }

    public void kihelyezEllenfel(Egyseg egyseg,int i, int j) {
        ImageView img=new ImageView(new Image("file:src/main/java/org/example/img/"+egyseg.getNev()+".png"));
        img.setFitWidth(60);
        img.setFitHeight(45);
        img.setX(j*60+100);
        img.setY(i*45+100);
        img.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        img.setId(egyseg.getNev()+"E");
        img.setPickOnBounds(true);
        img.setOnMouseClicked(new EventHandler<MouseEvent>() {




            @Override
            public void handle(MouseEvent event) {
                switch (egysegreVarazsol) {
                    case 0:
                        if (Objects.equals(egysegListaIW.get(k).getId(), img.getId())) {
                            kiirat.setText(kiirat.getText()+"\nrámkattintottál ezért várakozok ^^");
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                            break;
                        }
                        int vanAKozelbenEllenfel = 0;
                        if (megkeresIW(egysegListaIW.get(k)) == (ijasz)) {
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                if (!(Objects.equals(megkeresIW(egysegListaIW.get(l)).getNev(), ijasz.getNev())) && megkeresIW(egysegListaIW.get(l)).getNev().contains("E") && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - ijasz.getI()) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - ijasz.getJ()) <= 1)
                                    vanAKozelbenEllenfel = 1;
                            }
                        }
                        if (megkeresIW(egysegListaIW.get(k)) == (alchemist)) {
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                if (!(Objects.equals(megkeresIW(egysegListaIW.get(l)).getNev(), alchemist.getNev())) && megkeresIW(egysegListaIW.get(l)).getNev().contains("E") && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - alchemist.getI()) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - alchemist.getJ()) <= 1)
                                    vanAKozelbenEllenfel = 1;
                            }
                        }
                        if (vanAKozelbenEllenfel == 0 && (megkeresIW(egysegListaIW.get(k)) == (ijasz) || megkeresIW(egysegListaIW.get(k)) == (alchemist))) {
                            megtamad(megkeresIW(egysegListaIW.get(k)), egyseg, img);
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                        }
                        if (Math.abs(megkeresIW(egysegListaIW.get(k)).getJ() - (img.getX() - 100) / 60) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(k)).getI() - (img.getY() - 100) / 45) <= 1 && egysegListaIW.get(k).getId().charAt(egysegListaIW.get(k).getId().length() - 1) != 'E') {
                            megtamad(megkeresIW(egysegListaIW.get(k)), egyseg, img);
                            if (egyseg.isVisszaTamad()) {
                                visszaTamad(egyseg, megkeresIW(egysegListaIW.get(k)), egysegListaIW.get(k));
                                egyseg.setVisszaTamad(false);
                                egyseg.visszatamadReset();
                            }
                            if (k >= egysegListaIW.size() - 1) {
                                k = 0;
                                visszaTamadVisszaAllit();
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                hos.setVarazsoltUtott(false);
                            } else {
                                k++;
                                imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                            }
                        }
                        break;
                    case 1:
                        hos.megut(egyseg,img,ancPane);
                        egysegreVarazsol=0;
                        hos.setVarazsoltUtott(true);
                        break;
                    case 2:
                        if (varazslat.getAr()==60) {
                            varazslatbanMeghalt = false;
                            if (!egysegListaIW.get(k).getId().contains("E")) {
                                hos.varazsol(varazslat, egyseg, egysegLista, img, ancPane, floSorrend);
                                kiirat.setText(kiirat.getText() + "\n*csiribú~*~csiribáá*");
                                egysegreVarazsol = 0;
                                hos.setVarazsoltUtott(true);
                                if (varazslatbanMeghalt) {
                                    if (k >= egysegListaIW.size() - 1) {
                                        k = 0;
                                        visszaTamadVisszaAllit();
                                        imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                        labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText()) + 1) + "");
                                        hos.setVarazsoltUtott(false);
                                    } else {
                                        k++;
                                        imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                        imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                    }
                                }
                                break;
                            }
                            else break;
                        }
                        if (varazslat.getManacost()==9) {
                            varazslatbanMeghalt=false;
                            for (int l = 0; l < egysegListaIW.size(); l++) {
                                if (Math.abs(megkeresIW(egysegListaIW.get(l)).getJ() - (img.getX() - 100) / 60) <= 1 && Math.abs(megkeresIW(egysegListaIW.get(l)).getI() - (img.getY() - 100) / 45) <= 1) {
                                    hos.varazsol(varazslat,megkeresIW(egysegListaIW.get(l)),egysegLista,egysegListaIW.get(l),ancPane,floSorrend);
                                }
                            }
                            if (varazslatbanMeghalt) {
                                if (k>=egysegListaIW.size()-1) {
                                    k=0;
                                    visszaTamadVisszaAllit();
                                    imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                    labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText())+1)+"");
                                    hos.setVarazsoltUtott(false);
                                }
                                else {
                                    k++;
                                    imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                                    imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                                }
                            }
                            break;
                        }
                        hos.varazsol(varazslat,egyseg,egysegLista,img,ancPane,floSorrend);
                        kiirat.setText(kiirat.getText()+"\n*csiribú~*~csiribáá*");
                        egysegreVarazsol=0;
                        hos.setVarazsoltUtott(true);
                        break;
                }
            }




        });
        egyseg.setI(i);
        egyseg.setJ(j);
        ancPane.getChildren().add(img);
        Pair<Integer,ImageView> pair = new Pair<Integer,ImageView>(egyseg.getKezdemenyezes(),img);
        egysegListaPair.add(pair);
    }

    /**
     *
     * @param egysegKep
     * egy ImageView tipusu nem null objectet kap az osztály
     *
     * megvizsgálja sok if-en keresztül hogy a kép fix idje melyik egység nevével azonos.
     * ha megtalálta
     *
     * @return
     * vissza adja a képhez tartozó egységet
     * ha esetleg valami hiba folytán nem találna eggyezést akkor a foldmuvest adja vissza
     */
    public Egyseg megkeresIW(ImageView egysegKep) {
        if (Objects.equals(griff.getNev(), egysegKep.getId().toString())) return griff;
        else if (Objects.equals(alchemist.getNev(), egysegKep.getId().toString())) return alchemist;
        else if (Objects.equals(ijasz.getNev(), egysegKep.getId().toString())) return ijasz;
        else if (Objects.equals(harcos.getNev(), egysegKep.getId().toString())) return harcos;
        else if (Objects.equals(foldmuves.getNev(), egysegKep.getId().toString())) return foldmuves;

        else if (Objects.equals(griffE.getNev(), egysegKep.getId().toString())) return griffE;
        else if (Objects.equals(alchemistE.getNev(), egysegKep.getId().toString())) return alchemistE;
        else if (Objects.equals(ijaszE.getNev(), egysegKep.getId().toString())) return ijaszE;
        else if (Objects.equals(harcosE.getNev(), egysegKep.getId().toString())) return harcosE;
        else return foldmuvesE;
    }

    /**
     *
     * @param lista
     * egy párokból álló nem üres Arraylistát vár paraméterül
     * a párok egy egységnek a kezdeményezéséből
     * és az egységnek a képéből állnak.
     * azért van szükség arra hogy párban legyenek hogy a képeket
     * a kezdeményezés alapján sorba lehessen rendezni
     * a pair esetében egy key kezdeményezés és egy
     * value kép létezik
     * megkeressük a listában szereplő elemek között
     * a legnagyobb kezdeményezésű elemet
     * ehez hozzá adjuk az egységhez tartozó hős morálját
     * majd ezt egy ImageView-okat tartalmazó ArrayListvbe helyezzük
     * ezután a pair listából eltávolítjuk a maximális elempárt
     * hogy leközelebb a nálluk egyel kisebb elemet találjuk meg
     * nincs visszaa térési értéke a függvénynek
     */
    public void sorrendKialakitas (ArrayList<Pair<Integer,ImageView>> lista) {
        int p=0;
        boolean elsoCiklusVege=false;
        for (int i=0;i<lista.size();) {
            int max=0;
            int maxid=0;
            egysegLista=new ArrayList<>();
            boolean elsokor=true;
            for (int j=0;j<lista.size();j++) {
                if (elsokor) {
                    egysegLista.add(megkeresIW(lista.get(j).getValue()));
                    elsokor=false;
                }
                if (lista.get(j).getValue().getId().contains("E")) {
                    if (lista.get(j).getKey()+1 > max) {
                        max = lista.get(j).getKey()+1;
                        maxid=j;
                    }
                } else {
                    if (lista.get(j).getKey()+hos.getMoral() > max) {
                        max = lista.get(j).getKey()+hos.getMoral();
                        maxid=j;
                    }
                }
            }
            egysegListaIW.add(lista.get(maxid).getValue());
            ImageView kep = new ImageView(lista.get(maxid).getValue().getImage());
            kep.setFitHeight(50);
            kep.setFitWidth(50);
            kep.setId(megkeresIW(lista.get(maxid).getValue()).getNev());
            if (egysegListaIW.get(p).getId().contains("E")) kep.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            p++;
            floSorrend.getChildren().add(kep);
            lista.remove(maxid);
        }
    }

    public void kivalasztottGriff(ActionEvent e) {
        tileClicked(alchemist.getI(),alchemist.getJ());
    }
    public void kivalasztottAlchemist(ActionEvent e) {
        tileClicked(alchemist.getI(),alchemist.getJ());
    }
    public void kivalasztottIjasz(ActionEvent e) {
        tileClicked(ijasz.getI(),ijasz.getJ());
    }
    public void kivalasztottHarcos(ActionEvent e) {
        tileClicked(harcos.getI(),harcos.getJ());
    }
    public void kivalasztottFalusi(ActionEvent e) {
        tileClicked(foldmuves.getI(), foldmuves.getJ());
    }

    public void tileClicked(int i, int j) {
        switch (modValaszto) {
            case 0: // Egységek kihelyezése a mapra
                // ha mind az 5 gomb eltűnt -> modvalaszto 1
                if (modValasztoSzamlalo == egysegSzamlalo - 1 && j<2 && radSelected) {
                    kihelyezEllenfel(griffE,(int)(Math.random() * 3),10);
                    kihelyezEllenfel(harcosE,(int)(Math.random() * 3)+3,10);
                    kihelyezEllenfel(foldmuvesE,(int)(Math.random() * 3)+6,10);
                    kihelyezEllenfel(ijaszE,(int)(Math.random() * 5),11);
                    kihelyezEllenfel(alchemistE,(int)(Math.random() * 5)+5,11);
                    floSorrend.setOpacity(1);
                    ancAktualisEgyseg.setOpacity(1);

                    TextArea masolat = kiirat;
                    ancPane.getChildren().remove(kiirat);
                    ancPane.getChildren().add(masolat);

                    griffE.setNev(griffE.getNev()+"E");
                    alchemistE.setNev(alchemistE.getNev()+"E");
                    ijaszE.setNev(ijaszE.getNev()+"E");
                    harcosE.setNev(harcosE.getNev()+"E");
                    foldmuvesE.setNev(foldmuvesE.getNev()+"E");
                    modValaszto = 1;
                }




                if (radHarcos.isSelected() && j <= 1) {
                    kihelyez(harcos, radHarcos, i, j);
                } else if (radFoldmuves.isSelected() && j <= 1) {
                    kihelyez(foldmuves, radFoldmuves, i, j);
                } else if (radGriff.isSelected() && j <= 1) {
                    kihelyez(griff, radGriff, i, j);
                } else if (radAlchemist.isSelected() && j <= 1) {
                    kihelyez(alchemist, radAlchemist, i, j);
                } else if (radIjasz.isSelected() && j <= 1) {
                    kihelyez(ijasz, radIjasz, i, j);
                }
                break;



            case 1:
                if (eredmenyVizsgal()==1) { //nyertel
                    ImageView win = new ImageView(new Image("file:src/main/java/org/example/img/Win.png"));
                    ancPane.getChildren().add(win);
                } else if (eredmenyVizsgal()==2) { //vesztettél
                    ImageView lose = new ImageView(new Image("file:src/main/java/org/example/img/lose.png"));
                    ancPane.getChildren().add(lose);
                }

                if (sorrendkialakit) {
                    sorrendKialakitas(egysegListaPair);
                    sorrendkialakit=false;
                }
                if (k<egysegListaIW.size()) {
                    if (megkeresIW(egysegListaIW.get(k)) != null) {
                        if (Math.abs(megkeresIW(egysegListaIW.get(k)).getI() - i) <= megkeresIW(egysegListaIW.get(k)).getSebesseg() && Math.abs(megkeresIW(egysegListaIW.get(k)).getJ() - j) <= megkeresIW(egysegListaIW.get(k)).getSebesseg()) {
                            mozgat(egysegListaIW.get(k),i,j);
                        }
                        else k--;
                    }
                    if (k>=egysegListaIW.size()-1) {
                        k=0;
                        visszaTamadVisszaAllit();
                        imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                        labKorSzamlalo.setText((Integer.parseInt(labKorSzamlalo.getText())+1)+"");
                        hos.setVarazsoltUtott(false);
                    }
                    else {
                        k++;
                        imgAktualisegyseg.setImage(egysegListaIW.get(k).getImage());
                        imgAktualisegyseg.setNodeOrientation(egysegListaIW.get(k).getNodeOrientation());
                    }
                }
                break;
            case 3:
                Egyseg lathatatlanCelpont = new Foldmuves();
                lathatatlanCelpont.setJ(j);
                lathatatlanCelpont.setI(i);
                ImageView randomkep = new ImageView(new Image("file:src/main/java/org/example/img/Focim.png"));
                hos.varazsol(varazslat,lathatatlanCelpont,egysegLista,randomkep,ancPane,floSorrend);
                modValaszto=1;
                break;
        }
    }

    /**
     *
     * @param url
     * @param resourceBundle
     *
     * nincs visszatérési értéke
     *
     * ez a függvény akkor fut le amikor megnyitjuk a Harc.fxml-t
     * legenerálja a térképet
     * beállítja az ellenfelünk egységeit képességeit
     * fixálja hoogy mennyi volt a megvett egységek száma
     * kitörli a felesleges gombokat(ha nem vettünk egységet/varázslatot)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hos.setMana(hos.getTudas()*10);

        //ellenség beállítása 985 arany felhasználásával
        griffE.setDb(10);
        ijaszE.setDb(10);
        alchemistE.setDb(10);
        harcosE.setDb(10);
        foldmuvesE.setDb(10);
        ellensegesHos.setVedekezes(10);
        ellensegesHos.setTamadas(8);
        ellensegesHos.setSzerencse(1);
        ellensegesHos.setArany(1000);
        ellensegesHos.setMoral(1);
        ellensegesHos.setTuzlabda(false);
        ellensegesHos.setVillam(false);
        ellensegesHos.setRevive(false);

        //megvett mennyiség fixálása
        griff.setVettMennyiseg(griff.getDb());
        alchemist.setVettMennyiseg(alchemist.getDb());
        ijasz.setVettMennyiseg(ijasz.getDb());
        harcos.setVettMennyiseg(harcos.getDb());
        foldmuves.setVettMennyiseg(foldmuves.getDb());
        griffE.setVettMennyiseg(griffE.getDb());
        alchemistE.setVettMennyiseg(alchemistE.getDb());
        ijaszE.setVettMennyiseg(ijaszE.getDb());
        harcosE.setVettMennyiseg(harcosE.getDb());
        foldmuvesE.setVettMennyiseg(foldmuvesE.getDb());

        Image falu = new Image("file:src/main/java/org/example/img/Arnyek.png");
        ImageView[][] map = new ImageView[10][12];
        for(int i=0;i<10;i++) {
            for(int j=0;j<12;j++) {
                ImageView img = new ImageView(falu);
                img.setFitWidth(60);
                img.setFitHeight(45);
                img.setY(i * 45+100);
                img.setX(j * 60 +100);
                img.setPickOnBounds(true);

                final int row = i;
                final int column = j;
                img.setOnMouseClicked((MouseEvent e) -> {
                    tileClicked(row,column);
                });
                map[i][j] = img;
                ancPane.getChildren().add(img);

                if (ijasz.getDb()==0) {
                    floPane.getChildren().remove(radIjasz);
                }
                if (foldmuves.getDb()==0) {
                    floPane.getChildren().remove(radFoldmuves);
                }
                if (alchemist.getDb()==0) {
                    floPane.getChildren().remove(radAlchemist);
                }
                if (griff.getDb()==0) {
                    floPane.getChildren().remove(radGriff);
                }
                if (harcos.getDb()==0) {
                    floPane.getChildren().remove(radHarcos);
                }

                if (!hos.isTuzlabda()) floSpell.getChildren().remove(imgFireBall);
                if (!hos.isVillam()) floSpell.getChildren().remove(imgThunder);
                if (!hos.isRevive()) floSpell.getChildren().remove(imgRevive);
            }
        }
    }

    //Egység kihelyezés kiválasztáskor a többi opciót inaktívra állítja
    public void radFAktiv(ActionEvent actionEvent) {
        radIjasz.setSelected(false);
        radHarcos.setSelected(false);
        radAlchemist.setSelected(false);
        radGriff.setSelected(false);
        radSelected=true;
    }

    public void radIAktiv(ActionEvent actionEvent) {
        radFoldmuves.setSelected(false);
        radHarcos.setSelected(false);
        radAlchemist.setSelected(false);
        radGriff.setSelected(false);
        radSelected=true;
    }

    public void radHAktiv(ActionEvent actionEvent) {
        radIjasz.setSelected(false);
        radFoldmuves.setSelected(false);
        radAlchemist.setSelected(false);
        radGriff.setSelected(false);
        radSelected=true;
    }

    public void radAAktiv(ActionEvent actionEvent) {
        radIjasz.setSelected(false);
        radHarcos.setSelected(false);
        radFoldmuves.setSelected(false);
        radGriff.setSelected(false);
        radSelected=true;
    }

    public void radGAktiv(ActionEvent actionEvent) {
        radIjasz.setSelected(false);
        radHarcos.setSelected(false);
        radAlchemist.setSelected(false);
        radFoldmuves.setSelected(false);
        radSelected=true;
    }


    public void hosMegutKepesseg(MouseEvent event) {
        listafeltolt();
        if (!hos.isVarazsoltUtott() && !egysegListaIW.get(k).getId().contains("E")) {
            egysegreVarazsol=1;
            kiirat.setText(kiirat.getText()+"\nütés kiválasztva");
        }
    }

    public void hosReviveKepesseg(MouseEvent event) {
        listafeltolt();
        Varazslat revive = new Feltamasztas();
        varazslat=revive;
        if (hos.getMana()>=revive.getManacost() && !hos.isVarazsoltUtott()&& !egysegListaIW.get(k).getId().contains("E")) {
            egysegreVarazsol=2;
            kiirat.setText(kiirat.getText()+"\nrevive kiválasztva");
        }
    }

    public void hosThunderKepesseg(MouseEvent event) {
        listafeltolt();
        Varazslat villam = new Villam();
        varazslat=villam;
        if (hos.getMana()>=villam.getManacost() && !hos.isVarazsoltUtott() && !egysegListaIW.get(k).getId().contains("E")) {
            egysegreVarazsol=2;
            kiirat.setText(kiirat.getText()+"\nvillámlás kiválasztva");
        }
    }

    public void hosFireBallKepeseg(MouseEvent event) {
        listafeltolt();
        Varazslat tuzlabda = new Tuzlabda();
        varazslat=tuzlabda;
        if (hos.getMana()>=tuzlabda.getManacost() && !hos.isVarazsoltUtott() && !egysegListaIW.get(k).getId().contains("E")) {
            egysegreVarazsol=2;
            modValaszto=3;
            kiirat.setText(kiirat.getText()+"\ntűzlabda kiválasztva");
        }
    }

    public void txtMegjelenites(ActionEvent actionEvent) {
        if (kiiratBool) {
            kiirat.setPrefWidth(0);
            kiirat.setPrefHeight(0);
            kiiratBool=false;
        } else {
            kiirat.setPrefWidth(350);
            kiirat.setPrefHeight(206);
            kiiratBool=true;
        }
    }
}
