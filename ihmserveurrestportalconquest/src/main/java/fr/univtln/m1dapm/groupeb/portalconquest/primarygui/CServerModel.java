package fr.univtln.m1dapm.groupeb.portalconquest.primarygui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import fr.univtln.m1dapm.groupeb.portalconquest.newgamegui.CNewGameModel;
import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CBrowser;
import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CThreadInformationsGetter;
import javafx.scene.control.Alert;

/**
 * Created by clemzux on 06/05/16.
 * Cette classe est le modele (singleton) de la vue de la fenetre principale de l'application.
 */
public class CServerModel {
    private static CServerModel ourInstance = new CServerModel();

    public static CServerModel getInstance() {
        return ourInstance;
    }


    //////// attributes ////////


    // connection
    private WebResource connection;

    // webview
    private CBrowser mapBrowser;

    // MVC attributes
    private CServerGUI cServerGUI;


    //////// builder ////////


    /**
     * Constructeur d'un singleton, normal qu'il soit vide.
     */
    private CServerModel() {
    }


    //////// methods ////////


    /**
     * Cette méthode instancie un nouveau thread qui ira chercher cycliquement les données sur le serveur.
     */
    public void launchThread(){

        CThreadInformationsGetter mThread = new CThreadInformationsGetter();
        Thread mTh = new Thread(mThread);
        mTh.start();
    }

    /**
     * Cette méthode instancie une connection au serveur.
     */
    public void initConnection(){

        ClientConfig mClientConfig = new DefaultClientConfig();
        mClientConfig.getClasses().add(JacksonJsonProvider.class);
        Client mClient = Client.create(mClientConfig);
//        connection = mClient.resource("http://212.194.18.118:9998/");
//        connection = mClient.resource("http://10.21.199.198:9998/");
        connection = mClient.resource("http://192.168.1.11:9998/");
    }


    //////// GUI interraction ////////


    /**
     * Cette méthode appelle la classe Alert qui a pour but de faire apparaitre un message d'alerte,
     * la premier parametre est le titre de la fenetre,
     * le second parametre est le titre du message d'alerte,
     * le dernier parametre est le contenu du message d'alerte.
     * @param p1
     * @param p2
     * @param p3
     */
    public void warning(String p1, String p2, String p3){

        Alert mAlert = new Alert(Alert.AlertType.INFORMATION);
        mAlert.setTitle(p1);
        mAlert.setHeaderText(p2);
        mAlert.setContentText(p3);
        mAlert.showAndWait();
    }

    /**
     * Cette méthode indique a la vue qu'un nouveau calque a été créé, la vue mettra a jour la ListView.
     */
    public void addLayerListView() {

        cServerGUI.addLayerListView();
    }

    /**
     * Cette méthode indique a la vue qu'un calque a été supprimé, la vue mettra a jour la ListView.
     */
    public void removeLayerListView() {

        cServerGUI.removeLayerListView();
    }

    /**
     * Cette méthode indique a la vue qu'un calqué a été séléctionné, la vue mettra a jour un Label
     * qui affiche le nom du calque.
     */
    public void selectLayer() {

        cServerGUI.selectLayer();
    }

    /**
     * Cette méthode coche ou non la CheckBox des joueurs lorsqu'un calque a été séléctionné.
     */
    public void setLayerPlayersSelected () {

        cServerGUI.getcLayers().get(
                cServerGUI.getSelectedLayer()).setPlayers(cServerGUI.getPlayerCheckBox().isSelected());
    }

    /**
     * Cette méthode coche ou non la CheckBox des portails lorsqu'un calque a été séléctionné.
     */
    public void setLayerPortalsSelected () {

        cServerGUI.getcLayers().get(
                cServerGUI.getSelectedLayer()).setPortals(cServerGUI.getPortalCheckBox().isSelected());
    }

    /**
     * Cette méthode coche ou non la CheckBox des objets lorsqu'un calque a été séléctionné.
     */
    public void setLayerObjectsSelected () {

        cServerGUI.getcLayers().get(
                cServerGUI.getSelectedLayer()).setObjects(cServerGUI.getItemCheckBox().isSelected());
    }

    /**
     * Cette méthode coche ou non la CheckBox des liens lorsqu'un calque a été séléctionné.
     */
    public void setLayerLinksSelected () {

        cServerGUI.getcLayers().get(
                cServerGUI.getSelectedLayer()).setLinks(cServerGUI.getLinkCheckBox().isSelected());
    }

    /**
     * Cette méthode coche ou non la CheckBox des champs lorsqu'un calque a été séléctionné.
     */
    public void setLayerFieldsSelected () {

        cServerGUI.getcLayers().get(
                cServerGUI.getSelectedLayer()).setAreas(cServerGUI.getAreaCheckBox().isSelected());
    }

    /**
     * Cette méthode rend visible la vue de la nouvelle partie.
     */
    public void setNewGameGuiVisible() {

        CNewGameModel.getInstance().setGuiVisible();
    }

    /**
     * Cette fonction remet a l'état false toutes les CheckBox de visibilité des diffentes entitées.
     */
    public void resetAllCheckbox(){

        cServerGUI.getPlayerCheckBox().setSelected(false);
        cServerGUI.getPortalCheckBox().setSelected(false);
        cServerGUI.getItemCheckBox().setSelected(false);
        cServerGUI.getLinkCheckBox().setSelected(false);
        cServerGUI.getAreaCheckBox().setSelected(false);
    }

    /**
     * Cette méthode demande a la WebView de faire un zoom sur la map.
     */
    public void makeZoomIn() {

        mapBrowser.makeZoomIn();
    }

    /**
     * Cette méthode demande a la WebView de faire un dézoom sur la map.
     */
    public void makeZoomOut() {

        mapBrowser.makeZoomOut();
    }

    /**
     * Cette méthode demande a la WebView de faire un mouvement vers le haut sur la map.
     */
    public void makeMoveUpLocation() {

        mapBrowser.makeMoveUpLocation();
    }

    /**
     * Cette méthode demande a la WebView de faire un mouvement vers le bas sur la map.
     */
    public void makeMoveBotLocation() {

        mapBrowser.makeMoveBotLocation();
    }

    /**
     * Cette méthode demande a la WebView de faire un mouvement vers la gauche sur la map.
     */
    public void makeMoveLeftLocation() {

        mapBrowser.makeMoveLeftLocation();
    }

    /**
     * Cette méthode demande a la WebView de faire un mouvement vers la droite sur la map.
     */
    public void makeMoveRightLocation() {

        mapBrowser.makeMoveRightLocation();
    }

    public void setScore(int pTeam, int pScore) {

        if (pTeam == 1)
            cServerGUI.getBlueTeamScore().setText(String.valueOf(pScore));
        else
            cServerGUI.getRedTeamScore().setText(String.valueOf(pScore));
    }


    //////// getters / setters ////////


    public void setBrowser(CBrowser pCbrowser){ mapBrowser = pCbrowser; }

    public CBrowser getMapBrowser() { return mapBrowser; }

    public CServerGUI getcServerGUI() { return cServerGUI; }

    public void setcServerGUI(CServerGUI cServerGUI) { this.cServerGUI = cServerGUI; }

    public WebResource getConnection() { return connection; }
}
