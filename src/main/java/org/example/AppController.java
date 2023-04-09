package org.example;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.data.Egysegek.*;
import org.example.data.Hosok.Hos;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController {

    /**
     * Ez a főoldal tehát a primary.fxml file evzérléséért felelős
     *
     * ha a felhasználó elindítja a programot ezzel az oldallal fog találkozni
     * tartalmaz egy new game és egy quit gombot
     * new game - átviszi a felhasználót a nehézség válaszásra
     * quit - kilép a programból
     */
    public int szamlalo=1;
    public static Hos hos;
    public static Foldmuves foldmuves;
    public static Griff griff;
    public static Ijasz ijasz;
    public static Alchemist alchemist;
    public static Harcos harcos;
    @FXML
    Label tamadasSzamlalo;
    /*
     *
     *      Oldal váltó gombok
     *
     */


    @FXML
    public void butExitClicked() {
        int szam=20;

        System.out.println(szam-(int)(5/4));
        System.exit(0);
    }



    @FXML
    void butNehezVal(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NehezsegValaszto.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

    @FXML
    void butKarakterVal1(ActionEvent event) throws Exception{
        Hos hero= new Hos();
        hero.setArany(1300);
        hos=hero;
        foldmuves=new Foldmuves();
        harcos=new Harcos();
        ijasz=new Ijasz();
        alchemist=new Alchemist();
        griff = new Griff();
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("karakterval.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();

    }
    @FXML
    void butKarakterVal2(ActionEvent event) throws Exception{
        Hos hero= new Hos();
        hero.setArany(1000);
        hos=hero;
        foldmuves=new Foldmuves();
        harcos=new Harcos();
        ijasz=new Ijasz();
        alchemist=new Alchemist();
        griff = new Griff();
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("karakterval.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }
    @FXML
    void butKarakterVal3(ActionEvent event) throws Exception{
        Hos hero= new Hos();
        hero.setArany(700);
        hos=hero;
        foldmuves=new Foldmuves();
        harcos=new Harcos();
        ijasz=new Ijasz();
        alchemist=new Alchemist();
        griff = new Griff();
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("karakterval.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

}