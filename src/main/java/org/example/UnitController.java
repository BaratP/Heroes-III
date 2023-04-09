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
import org.example.data.Egysegek.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.AppController.*;
import static org.example.HarcController.egysegSzamlalo;

public class UnitController implements Initializable {

    /**
     * Ez az osztály felelős az egységek megvásárlásáért és a Vasarlas.fxml vezérléséért
     *
     * ezen az oldalon találhatóak az egységek
     * minden egységhez tartozik 2 gomb.
     * az egyik segítségével 1
     * a másikkal 10 egységet tudunk venni
     *
     * le vannak kezelve olyan esetre is hogyha nem lenne elég aranyunk
     * a megadottt mennyiségű egység megvásárolására
     *
     * az osztály tartalmaz "oldal léptetési" funkciókat
     */


    public boolean falusitVett=false,griffetVett=false,ijasztVett=false,alchemistVett=false,harcostVett=false;


    @FXML
    Label farmerSzamlalo;

    @FXML
    Label griffSzamlalo;

    @FXML
    Label harcosSzamlalo;

    @FXML
    Label alchemistSzamlalo;

    @FXML
    Label ijaszSzamlalo;

    @FXML
    Label aktualisAranyU;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aktualisAranyU.setText("Gold: "+hos.getArany());
        farmerSzamlalo.setText(foldmuves.getDb()+"");
        griffSzamlalo.setText(griff.getDb()+"");
        ijaszSzamlalo.setText(ijasz.getDb()+"");
        alchemistSzamlalo.setText(alchemist.getDb()+"");
        harcosSzamlalo.setText(harcos.getDb()+"");

    }

    @FXML
    void butPrimary(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("primary.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

    @FXML
    public void butKarakterVal(ActionEvent event) throws IOException {
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("karakterval.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }


    @FXML
    void butVasarlas(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Vasarlas.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

    @FXML
    void butSpell(ActionEvent event) throws Exception{
        Parent newGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Spell.fxml")));
        Scene newGameScene = new Scene(newGame);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(newGameScene);
        window.show();
    }

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
    //TODO Hibakezelés
    public void butFalusiVasarol(ActionEvent actionEvent) {
        if(foldmuves.getAr()<=hos.getArany()) {
            foldmuves.setDb(foldmuves.getDb() + 1);
            farmerSzamlalo.setText(foldmuves.getDb() + "");
            hos.setArany(hos.getArany() - foldmuves.getAr());
            aktualisAranyU.setText("Gold: " + hos.getArany());
            foldmuves.setVettMennyiseg(foldmuves.getDb());
            if (foldmuves.getDb()==1) {
                falusitVett=true;
                egysegSzamlalo++;
            }
        }
    }

    public void butGriffVasarol(ActionEvent actionEvent) {
        if(griff.getAr()<=hos.getArany()) {
            griff.setDb(griff.getDb()+1);
            griffSzamlalo.setText(griff.getDb()+"");
            hos.setArany(hos.getArany()-griff.getAr());
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            griff.setVettMennyiseg(griff.getDb());
            if (griff.getDb()==1) {
                griffetVett=true;
                egysegSzamlalo++;
            }
        }
    }

    public void butIjaszVasarol(ActionEvent actionEvent) {
        if(ijasz.getAr()<=hos.getArany()) {
            ijasz.setDb(ijasz.getDb()+1);
            ijaszSzamlalo.setText(ijasz.getDb()+"");
            hos.setArany(hos.getArany()-ijasz.getAr());
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            ijasz.setVettMennyiseg(ijasz.getDb());
            if (ijasz.getDb()==1) {
                ijasztVett=true;
                egysegSzamlalo++;
            }
        }
    }

    public void butAlcVasarol(ActionEvent actionEvent) {
        if(alchemist.getAr()<=hos.getArany()) {
            alchemist.setDb(alchemist.getDb()+1);
            alchemistSzamlalo.setText(alchemist.getDb()+"");
            hos.setArany(hos.getArany()-alchemist.getAr());
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            alchemist.setVettMennyiseg(alchemist.getDb());
            if (alchemist.getDb()==1) {
                alchemistVett=true;
                egysegSzamlalo++;
            }
        }
    }

    public void butHarcosVasarol(ActionEvent actionEvent) {
        if(harcos.getAr()<=hos.getArany()) {
            harcos.setDb(harcos.getDb()+1);
            harcosSzamlalo.setText(harcos.getDb()+"");
            hos.setArany(hos.getArany()-harcos.getAr());
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            harcos.setVettMennyiseg(harcos.getDb());
            if (harcos.getDb()==1) {
                harcostVett=true;
                egysegSzamlalo++;
            }
        }

    }

    public void butGriffVasarol10(ActionEvent actionEvent) {
        if(griff.getAr()*10<=hos.getArany()) {
            griff.setDb(griff.getDb()+10);
            griffSzamlalo.setText(griff.getDb()+"");
            hos.setArany(hos.getArany()-griff.getAr()*10);
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            griff.setVettMennyiseg(griff.getDb());
            if (griff.getDb()==10 && !griffetVett) egysegSzamlalo++;
        }
    }

    public void butIjaszVasarol10(ActionEvent actionEvent) {
        if(ijasz.getAr()*10<=hos.getArany()) {
            ijasz.setDb(ijasz.getDb()+10);
            ijaszSzamlalo.setText(ijasz.getDb()+"");
            hos.setArany(hos.getArany()-ijasz.getAr()*10);
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            ijasz.setVettMennyiseg(ijasz.getDb());
            if (ijasz.getDb()==10 && !ijasztVett) egysegSzamlalo++;
        }
    }

    public void butAlcVasarol10(ActionEvent actionEvent) {
        if(alchemist.getAr()*10<=hos.getArany()) {
            alchemist.setDb(alchemist.getDb()+10);
            alchemistSzamlalo.setText(alchemist.getDb()+"");
            hos.setArany(hos.getArany()-alchemist.getAr()*10);
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            alchemist.setVettMennyiseg(ijasz.getDb());
            if (alchemist.getDb()==10 && !alchemistVett) egysegSzamlalo++;
        }
    }

    public void butHarcosVasarol10(ActionEvent actionEvent) {
        if(harcos.getAr()*10<=hos.getArany()) {
            harcos.setDb(harcos.getDb()+10);
            harcosSzamlalo.setText(harcos.getDb()+"");
            hos.setArany(hos.getArany()-harcos.getAr()*10);
            aktualisAranyU.setText("Gold: "+ hos.getArany());
            harcos.setVettMennyiseg(ijasz.getDb());
            if (harcos.getDb()==10 && !harcostVett) egysegSzamlalo++;
        }
    }

    public void butFalusiVasarol10(ActionEvent actionEvent) {
        if(foldmuves.getAr()*10<=hos.getArany()) {
            foldmuves.setDb(foldmuves.getDb() + 10);
            farmerSzamlalo.setText(foldmuves.getDb() + "");
            hos.setArany(hos.getArany() - foldmuves.getAr()*10);
            aktualisAranyU.setText("Gold: " + hos.getArany());
            foldmuves.setVettMennyiseg(ijasz.getDb());
            if (foldmuves.getDb()==10 && !falusitVett) egysegSzamlalo++;
        }
    }
}
