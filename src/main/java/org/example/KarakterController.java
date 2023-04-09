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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.AppController.hos;
import static org.example.HarcController.egysegSzamlalo;

public class KarakterController implements Initializable {

    /**
     * Ez az osztály felelős a hősünk fejlesztéséért és a karakterval.fxml vezérléséért
     *
     * lehetőségünk van elegendő arany függvényében tulajdonságokat
     * fejleszteni a hősünknek
     *
     * minden fejlesztés növeli a következő fejlesztés árát 10%-al
     *
     * ezen felül itt lehet megtekinteni az ellenfél felszerelését amivel
     * szmbe fog szállni velünk.
     *
     * az osztály tartalmaz "oldal léptetési" funkciókat
     */
    public boolean latszik =false;

    @FXML
    AnchorPane ancEllenfel;

    @FXML
    Label tamadasSzamlalo;

    @FXML
    Label tudasSzamlalo;

    @FXML
    Label moralSzamlalo;

    @FXML
    Label szerencseSzamlalo;

    @FXML
    Label vedekezesSzamlalo;

    @FXML
    Label varazseroSzamlalo;

    @FXML
    Label aktualisArany;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aktualisArany.setText("Gold: "+hos.getArany());
        varazseroSzamlalo.setText(hos.getVarazsero()+"");
        vedekezesSzamlalo.setText(hos.getVedekezes()+"");
        szerencseSzamlalo.setText(hos.getSzerencse()+"");
        moralSzamlalo.setText(hos.getMoral()+"");
        tudasSzamlalo.setText(hos.getTudas()+"");
        tamadasSzamlalo.setText((hos.getTamadas())+"");
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


    public void butTamadasVasarlas(ActionEvent actionEvent) {
        if (hos.getTamadas()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setTamadas(hos.getTamadas() + 1);
            tamadasSzamlalo.setText(hos.getTamadas() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butTudasVasarlas(ActionEvent actionEvent) {
        if (hos.getTudas()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setTudas(hos.getTudas() + 1);
            tudasSzamlalo.setText(hos.getTudas() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butMoralVasarlas(ActionEvent actionEvent) {
        if (hos.getMoral()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setMoral(hos.getMoral() + 1);
            moralSzamlalo.setText(hos.getMoral() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butSzerencseVasarlas(ActionEvent actionEvent) {
        if (hos.getSzerencse()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setSzerencse(hos.getSzerencse() + 1);
            szerencseSzamlalo.setText(hos.getSzerencse() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butVedekezesVasarlas(ActionEvent actionEvent) {
        if (hos.getVedekezes()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setVedekezes(hos.getVedekezes() + 1);
            vedekezesSzamlalo.setText(hos.getVedekezes() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butVarazseroVasarlas(ActionEvent actionEvent) {
        if (hos.getVarazsero()<10 && hos.getArany()>=hos.getStatCost()) {
            hos.setVarazsero(hos.getVarazsero() + 1);
            varazseroSzamlalo.setText(hos.getVarazsero() + "");
            hos.setArany(hos.getArany()-hos.getStatCost());
            aktualisArany.setText("Gold: "+ hos.getArany());
            hos.setStatCost(hos.getStatCost()+(int) Math.ceil(hos.getStatCost() * 0.1));
        }
    }

    public void butEllefel(ActionEvent actionEvent) {
        if (!latszik)
        {
            ancEllenfel.setOpacity(1);
            latszik=true;
        }
        else
        {
            ancEllenfel.setOpacity(0);
            latszik=false;
        }
    }
}
