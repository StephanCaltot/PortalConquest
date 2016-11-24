package fr.univtln.m1dapm.groupeb.portalconquest.primarygui;

import fr.univtln.m1dapm.groupeb.portalconquest.newgamegui.CNewGameGUI;
import fr.univtln.m1dapm.groupeb.portalconquest.newgamegui.CNewGameModel;
import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CBrowser;
import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CNewGameParameters;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

/**
 * Created by clemzux on 06/05/16.
 * Cette classe est le controleur de la vue de la fenetre principale : IHM serveur.
 */
public class CServerCtrl {


    //////// attributes ////////


    // MVC attributes
    private CServerGUI cServerGUI;
    private CNewGameGUI cNewGameGUI;

    // widgets from primarygui
    private MenuItem newGameButton, resumeGameButton, leaveServerNutton;
    private Button newLayerButton, deleteLayerButton;
    private ListView<CheckBox> checkBoxListView;
    private TextField newLayerTextField;
    private CheckBox playersCheckBox, portalsCheckBox, objetcsCheckBox;
    private CheckBox linksCheckBox, areaCheckBox;
    private Button zoomButton, zoomOutButton, upArrowButton, botArrowButton, leftArrowButton, rightArrowButton;
    private CBrowser mapBrowser;


    //////// builders ////////


    /**
     * Constructeur du controler de la vue principale.
     * Prend en parametre la vue principale.
     * Initialise les widgets.
     * Initialise les listeners des widgets.
     * Transmet l'instance active de la webview au modèle.
     * Transmet les parametres de connection au serveur au modèle.
     * Pour finir, signale au modèle qu'un thread qui ira chercher les informations via le serveur est pret a le faire.
     * @param pCServerGUI
     */
    public CServerCtrl(CServerGUI pCServerGUI){

        cServerGUI = pCServerGUI;
        cNewGameGUI = new CNewGameGUI();

        // on initialise les boutons provenant de la primarygui pour y attribuer un listener par la suite
        initWidgets();

        // on initialise tout les listeners
        initListeners();

        // on donne le cbrowser au modele et a la classe des paramètres de jeu
        CServerModel.getInstance().setBrowser(mapBrowser);

        // on initialise la connection a la base
        CServerModel.getInstance().initConnection();

        // on lance le thread qui ira chercher les infos dans la base
        CServerModel.getInstance().launchThread();
    }


    //////// methods ////////


    /**
     * Cette méthode permet d'initialiser tout les listeners reliés aux widgets.
     * Listener du bouton lancement de la fenetre de nouvelle partie.
     * Listener du bouton de reprise d'écoute du serveur.
     * Listener du bouton pour quitter l'application.
     * Listener du bouton d'ajout de nouveau calque.
     * Listener du bouton de suppression de calque.
     * Listener de la listView -> affiche le calque actif.
     * Listener des radio buttons.
     * Listener des boutons de gestion de la map.
     */
    void initListeners(){

        //listeners du menu

        newGameButton.setOnAction((ActionEvent e) -> {
            CNewGameModel.getInstance().setcNewGameGUI(cNewGameGUI);
            CServerModel.getInstance().setNewGameGuiVisible();
        });

        resumeGameButton.setOnAction((ActionEvent e) -> {
            CNewGameParameters.getInstance().launchGame();
        });

        leaveServerNutton.setOnAction(actionEvent -> Platform.exit());

        // buttons listeners

        // ajout d'un nouveau calque
        newLayerButton.setOnAction((ActionEvent e) -> {
            if (cServerGUI.getNewLayerTextField().getText().isEmpty()){
                CServerModel.getInstance().warning("Attention", "Nom non correct", "Vous tentez de créer un calque\nsans l'avoir nommé !");
            }
            else if (!newLayerTextField.getText().matches("[0-9|a-z|A-Z]*")){
                CServerModel.getInstance().warning("Attention", "Nom non correct", "Le nom du calque ne doit contenir\nque des chiffres ou des lettres !");
            }
            else
                CServerModel.getInstance().addLayerListView();
        });

        // suppresion d'un calque
        deleteLayerButton.setOnAction((ActionEvent e) -> {

            CServerModel.getInstance().removeLayerListView();
        });

        // listview listener

        checkBoxListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
            public void changed(ObservableValue<? extends CheckBox> a, CheckBox b, CheckBox c){
                CServerModel.getInstance().selectLayer();
            }
        });

        // radio button listeners

        playersCheckBox.setOnAction((ActionEvent e) -> {
            if (!cServerGUI.getIsEmpty())
                CServerModel.getInstance().setLayerPlayersSelected();
            else {
                alertNoLayerCreated();
                CServerModel.getInstance().resetAllCheckbox();
            }
        });

        portalsCheckBox.setOnAction((ActionEvent e) -> {
            if (!cServerGUI.getIsEmpty())
                CServerModel.getInstance().setLayerPortalsSelected();
            else {
                alertNoLayerCreated();
                CServerModel.getInstance().resetAllCheckbox();
            }
        });

        objetcsCheckBox.setOnAction((ActionEvent e) -> {
            if (!cServerGUI.getIsEmpty())
                CServerModel.getInstance().setLayerObjectsSelected();
            else {
                alertNoLayerCreated();
                CServerModel.getInstance().resetAllCheckbox();
            }
        });

        linksCheckBox.setOnAction((ActionEvent e) -> {
            if (!cServerGUI.getIsEmpty())
                CServerModel.getInstance().setLayerLinksSelected();
            else {
                alertNoLayerCreated();
                CServerModel.getInstance().resetAllCheckbox();
            }
        });

        areaCheckBox.setOnAction((ActionEvent e) -> {
            if (!cServerGUI.getIsEmpty())
                CServerModel.getInstance().setLayerFieldsSelected();
            else {
                alertNoLayerCreated();
                CServerModel.getInstance().resetAllCheckbox();
            }
        });

        // listeners boutons de la map

        zoomButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeZoomIn();
        });

        zoomOutButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeZoomOut();
        });

        upArrowButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeMoveUpLocation();
        });

        botArrowButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeMoveBotLocation();
        });

        leftArrowButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeMoveLeftLocation();
        });

        rightArrowButton.setOnAction((ActionEvent e) -> {
            CServerModel.getInstance().makeMoveRightLocation();
        });
    }

    /**
     * Cette méthode fait appel a une méthode contenue dans le modele qui permet d'afficher une fenetre de,
     * confirmation d'action.
     */
    void alertNoLayerCreated(){

        CServerModel.getInstance().warning("Attention", "Pas de calque trouvé", "Vous tentez de modifier un calque\n qui n'existe pas !");
    }

    /**
     * Cette méthode initialise les differents widgets pour éviter de les get quand le controler en a besoin.
     */
    void initWidgets(){

        // widget menu
        newGameButton = cServerGUI.getNewGameButton();
        resumeGameButton = cServerGUI.getResumeGameButton();
        leaveServerNutton = cServerGUI.getLeaveServerButton();

        // widget dans la fenetre
        newLayerButton = cServerGUI.getNewLayerButton();
        deleteLayerButton = cServerGUI.getDeleteLayerButton();
        checkBoxListView = cServerGUI.getLayerListView();
        playersCheckBox = cServerGUI.getPlayerCheckBox();
        portalsCheckBox = cServerGUI.getPortalCheckBox();
        objetcsCheckBox = cServerGUI.getItemCheckBox();
        linksCheckBox = cServerGUI.getLinkCheckBox();
        areaCheckBox = cServerGUI.getAreaCheckBox();

        // widget textfield
        newLayerTextField = cServerGUI.getNewLayerTextField();

        // widget CBrowser
        mapBrowser = cServerGUI.getMapBrowser();

        // widget boutons map
        zoomButton = cServerGUI.getZoomButton();
        zoomOutButton = cServerGUI.getZoomOutButton();
        upArrowButton = cServerGUI.getUpArrowButton();
        botArrowButton = cServerGUI.getBotArrowButton();
        leftArrowButton = cServerGUI.getLeftArrowButton();
        rightArrowButton = cServerGUI.getRightArrowButton();
    }
}
