package fr.univtln.m1dapm.groupeb.portalconquest.utilities;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

/**
 * Created by clemzux on 04/05/16.
 * Cette classe hérite de région, elle integre une WebView.
 * Une instance de cette classe peut etre intégrée dans un layout.
 */

public class CBrowser extends Region {

    //////// attributes ////////


    private WebView webView;
    private WebEngine webEngine;


    //////// builders ////////


    /**
     * Constructeur d'une instance de CBrowser, prend en parametre la taille (hauteur x largeur).
     * @param pHeight
     * @param pWidth
     */
    public CBrowser(int pHeight, int pWidth){

        // on crée une zone d'affichage de page web
        webView = new WebView();
        // on définit sa taille
        webView.setMaxSize(pWidth*0.7, pHeight);
        // on récupère le moteur d'affichage
        webEngine = webView.getEngine();

        // on active javascript (obligatoire pour gérer la google map)
        webEngine.setJavaScriptEnabled(true);

        // on renseigne le moteur de l'url sur laquelle il doit se baser
        final URL mUrlGoogleMaps = getClass().getClassLoader().getResource("GoogleMapsV3.html");
        webEngine.load(mUrlGoogleMaps.toExternalForm());
        getChildren().add(webView);
    }


    //////// methods ////////


    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un zoom.
     */
    public void makeZoomIn(){

        webEngine.executeScript("document.zoomIn()");
    }

    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un dézoom.
     */
    public void makeZoomOut(){

        webEngine.executeScript("document.zoomOut()");
    }

    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un déplacement vers le haut.
     */
    public void makeMoveUpLocation(){

        webEngine.executeScript("document.moveUp()");
    }

    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un déplacement vers le bas.
     */
    public void makeMoveBotLocation(){

        webEngine.executeScript("document.moveBot()");
    }

    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un déplacement vers la gauche.
     */
    public void makeMoveLeftLocation(){

        webEngine.executeScript("document.moveLeft()");
    }

    /**
     * Cette méthode demande au WebEngine de la WebView d'éxécuter une fonction en javascript
     * qui permet d'effectuer un déplacement vers la droite.
     */
    public void makeMoveRightLocation(){

        webEngine.executeScript("document.moveRight()");
    }


    //////// getters / setters ////////


    public WebEngine getWebEngine() { return webEngine; }
}

