package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

/**
 * Représente le bouton pour activer l'aide à la résolution.
 */
public class HelpSwitch extends GridPane {

    Circle knob;
    Label lblDesc;

    boolean isOn;

    /**
     * Construit un bouton d'aide.
     */
    public HelpSwitch() {

        // Dimensionnement
        ColumnConstraints colThird = new ColumnConstraints();
        colThird.setPercentWidth(100d/3d);
        getColumnConstraints().addAll(colThird,colThird,colThird);
        setMaxHeight(30);
        setMaxWidth(95);
        setMinHeight(30);
        setMinWidth(95);
        
        // Bouton
        knob = new Circle(12.5);
        knob.setStyle("-fx-border-color: black;");
        knob.setFill(Color.GRAY);
        
        // Étiquette de description
        lblDesc = new Label("HELP OFF");
        lblDesc.setStyle(KropkiConstants.HELP_TEXT_STYLE);
        lblDesc.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(lblDesc, HPos.CENTER);
    }

    /**
     * Dessine l'interrupteur
     */
    public void paintSwitch() {
        getChildren().clear();
        
        if(isOn) 
        {
            add(knob, 2, 0);
            lblDesc.setText("HELP ON"); // Activée
            add(lblDesc, 0, 0, 2, 1);
            GridPane.setHalignment(lblDesc, HPos.CENTER);
            setStyle("-fx-background-radius: 30; -fx-background-color: #1CEA31; -fx-border-radius: 30;-fx-border-width:2; -fx-border-color: white;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 1);");
            knob.setFill(Color.valueOf("#1B9D28"));
        }
        else 
        {
            add(knob, 0, 0);
            lblDesc.setText("HELP OFF"); // Désactivée
            add(lblDesc, 1, 0);
            setStyle("-fx-background-radius: 30; -fx-background-color: #D6D6D6; -fx-border-radius: 30;-fx-border-width:2; -fx-border-color: white;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 1);");
            knob.setFill(Color.GRAY);
        }
    }

    /**
     * Définit la valeur de l'interrupteur
     * @param value la valeur de l'interrupteur
     */
    public void setValue(boolean value) {
        isOn = value; 
        paintSwitch(); 
    }

    /**
     * Renvoie la valeur de l'interrupteur
     * @return la valeur de l'interrupteur
     */
    public boolean getValue() {
        return isOn; 
    }
}