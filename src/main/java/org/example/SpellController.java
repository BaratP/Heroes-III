package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.AppController.hos;
import static org.example.HarcController.egysegSzamlalo;

public class SpellController implements Initializable {

    /**
     * Ez az osztály felelős a képességek vásárlásáért és a Spell.fxml vezérléséért
     *
     * tartalmaz 3 képességet
     *
     * ezt a 3 képességet egy-egy gomb segítségével lehet megvásárolni
     * ha megvásárolta 1x többször már nem lehet
     *
     * az osztály tartalmaz "oldal léptetési" funkciókat
     */

    @FXML
    Label aktualisAranyS;

    /**
     *
     * @param url
     * @param resourceBundle
     *
     * initialize függvény felülírásával az oldal betöltésekor leforduló
     * feladatokat tudjuk bővíteni.
     * Hogy oldalváltások közben az arany mennyiséget helyesen jelezze
     * beállítjuk az oldalon található Label tartalmát
     * az aktuális arany tartalmára
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aktualisAranyS.setText("Gold: "+hos.getArany());
    }

    /**
     *
     * @param event
     * ha interakcióba lépünk az adott elemmel akkor egy event egy esemény történik
     * lehet akár billentyű parancs kattintás húzás kattintás
     * @throws Exception
     * ennek köszönhetően le van kezelve az az eset ha a Parent load nem működne megfelelően
     *
     * a Parent adattagnak megadjuk az fxml file.t ahova szerenénk léptetni majd az oldalt
     * létrehozunk egy új scene-t a Parent alapján
     * létrehozunk egy új ablakot megvizsgáljuk hogy az event honnan jött melyik
     * sceneből-melyik ablakból és azt állítjuk be neki
     * majd az ablaknak beállítjuk a Parent-et
     * és láthatóvá tesszük az oldalt
     */
    @FXML
    void butPrimary(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("primary.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }
    /**
     *
     * @param event
     * ha interakcióba lépünk az adott elemmel akkor egy event egy esemény történik
     * lehet akár billentyű parancs kattintás húzás kattintás
     * @throws Exception
     * ennek köszönhetően le van kezelve az az eset ha a Parent load nem működne megfelelően
     *
     * a Parent adattagnak megadjuk az fxml file.t ahova szerenénk léptetni majd az oldalt
     * létrehozunk egy új scene-t a Parent alapján
     * létrehozunk egy új ablakot megvizsgáljuk hogy az event honnan jött melyik
     * sceneből-melyik ablakból és azt állítjuk be neki
     * majd az ablaknak beállítjuk a Parent-et
     * és láthatóvá tesszük az oldalt
     */
    @FXML
    public void butKarakterVal(ActionEvent event) throws IOException {
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("karakterval.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

    /**
     *
     * @param event
     * ha interakcióba lépünk az adott elemmel akkor egy event egy esemény történik
     * lehet akár billentyű parancs kattintás húzás kattintás
     * @throws Exception
     * ennek köszönhetően le van kezelve az az eset ha a Parent load nem működne megfelelően
     *
     * a Parent adattagnak megadjuk az fxml file.t ahova szerenénk léptetni majd az oldalt
     * létrehozunk egy új scene-t a Parent alapján
     * létrehozunk egy új ablakot megvizsgáljuk hogy az event honnan jött melyik
     * sceneből-melyik ablakból és azt állítjuk be neki
     * majd az ablaknak beállítjuk a Parent-et
     * és láthatóvá tesszük az oldalt
     */
    @FXML
    void butVasarlas(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Vasarlas.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }
    /**
     * Ha legalább egy egységet vettünk akkor tovább mehetünk a Harc.fxml file-ra
     *
     * @param event
     * ha interakcióba lépünk az adott elemmel akkor egy event egy esemény történik
     * lehet akár billentyű parancs kattintás húzás kattintás
     * @throws Exception
     * ennek köszönhetően le van kezelve az az eset ha a Parent load nem működne megfelelően
     *
     * a Parent adattagnak megadjuk az fxml file.t ahova szerenénk léptetni majd az oldalt
     * létrehozunk egy új scene-t a Parent alapján
     * létrehozunk egy új ablakot megvizsgáljuk hogy az event honnan jött melyik
     * sceneből-melyik ablakból és azt állítjuk be neki
     * majd az ablaknak beállítjuk a Parent-et
     * és láthatóvá tesszük az oldalt
     */
    @FXML
    void butHarc(ActionEvent event) throws Exception{
        if (egysegSzamlalo>0) {
            Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Harc.fxml")));
            Scene newGameScene = new Scene(newGame);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newGameScene);
            window.show();
        }
    }

    public void butVillamlasVasarlas(ActionEvent actionEvent) {
        if (hos.getArany()>=60 && !hos.isVillam()) {
            hos.setVillam(true);
            hos.setArany(hos.getArany()-60);
            aktualisAranyS.setText("Gold: "+ hos.getArany());
        }
    }

    public void butFeltamasztasVasarlas(ActionEvent actionEvent) {
        if (hos.getArany()>=120 && !hos.isRevive()) {
            hos.setRevive(true);
            hos.setArany(hos.getArany()-120);
            aktualisAranyS.setText("Gold: "+ hos.getArany());
        }
    }

    public void butTuzlabdaVasarlas(ActionEvent actionEvent) {
        if (hos.getArany()>=120 && !hos.isTuzlabda()) {
            hos.setTuzlabda(true);
            hos.setArany(hos.getArany()-120);
            aktualisAranyS.setText("Gold: "+ hos.getArany());
        }
    }
}
