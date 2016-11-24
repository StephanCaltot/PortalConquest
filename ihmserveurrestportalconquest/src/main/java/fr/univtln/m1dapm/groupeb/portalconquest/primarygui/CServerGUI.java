package fr.univtln.m1dapm.groupeb.portalconquest.primarygui;

import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CBrowser;
import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CLayer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clemzux on 09/05/16.
 * Cette classe est la classe, qui permet a l'application de se lancer.
 * Elle initialise tout les parametres nécéssaires (stage, widgets ...).
 */
public class CServerGUI extends Application {


    //////// attributes ////////


    private Stage windowStage;
    private Scene fullWindowScene;
    int height, width;

    // menu attributes
    private MenuBar menuBar;
    private Menu menuFile, menuEdition, menuHelp;
    private MenuItem newGameButton, resumeGameButton, leaveServerButton;

    // label attributes
    private Label currentLayerLabel;
    private Label blueTeamLabel, redTeamLabel, blueTeamScore, redTeamScore;

    // button attributes
    private Button newLayerButton;
    private Button deleteLayerButton;

    // textfield attributes
    private TextField newLayerTextField;

    // listview attributes
    private ListView<CheckBox> layerListView;
    private ObservableList<CheckBox> radioButtons;
    private List<CLayer> cLayers;
    private int selectedLayer;
    private Boolean isEmpty;

    // radiobutton attributes
    private CheckBox playerCheckBox, portalCheckBox, itemCheckBox;
    private CheckBox linkCheckBox, areaCheckBox;

    // webview
    private CBrowser mapBrowser;

    // map buttons
    private Button zoomButton, zoomOutButton, upArrowButton, botArrowButton, leftArrowButton, rightArrowButton;

    // boxes
    private VBox vBoxFullWindow, vBoxLeftZone, vBoxCalques, vBoxObjects, vBoxColumn1, vBoxColumn2, vBoxMapButtons;
    private HBox hBoxLeftZoneMap, hBoxNewLayerZone, hBoxRadioButton;

    // MVC attributes
    private CServerCtrl cServerCtrl;


    //////// methods ////////


    /**
     * Cette methode est celle qui se lance en premier lorsqu'une classe hérite de Application.
     * Initialise la vue dans le modele.
     * Créée la fenetre.
     * Initialise tout les layouts.
     * Créée tout les widgets : Labels, TextView, WebView ...
     * Range les widgets dans les layouts puis dans le stage.
     * Initialise le controleur.
     * @param pPrimaryStage
     */
    @Override
    public void start(Stage pPrimaryStage) {

        CServerModel.getInstance().setcServerGUI(this);

        // le théatre
        pPrimaryStage = new Stage();
        windowStage = pPrimaryStage;
        //la liste des calques
        cLayers = new ArrayList<>();
        isEmpty = true;

        // appel de la methode qui va se charger de créer la fenetre
        createWindow();

        // appel de la méthodes qui va initialiser toutes les boxes
        initBoxes();

        // on crée le menu en haut de la fenetre
        createMenu();

        // on crée la zone de gestion des calques
        createLayerArea();

        // on crée la zone de gestion des objets
        createObjectsArea();

        // cette méthode va créer la zone d'affichage de la map et range la map avec son separateur dans la fenetre
        createMapBrowser();

        // cette méthode va créer les boutons qui se trouvent sur la droite de la map
        createMapPanel();

        // on fini d'organiser la fenetre : rangement de la box principale dans la scene principale, icone du programme ...
        windowFinalisation();

        // on envoie la gui au controler
        cServerCtrl = new CServerCtrl(this);
    }

    /**
     * Cette méthode initialise la fenetre : taille, titre.
     */
    void createWindow(){

        windowStage.setTitle("Server PortalConquest");

//        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//        int h = (int)dimension.getHeight();
//        int w  = (int)dimension.getWidth();

        // a enlever quand je programmerai avec un seul écran !!!
        int h, w;
        h = 625;
        w = 1315;
        height = h;
        width = w;

        // on définit la taille du théatre (fenetre principale)
        windowStage.setWidth(w);
        windowStage.setHeight(h);
    }

    /**
     * Cette méthode initialise tout les layouts.
     */
    void initBoxes(){

        // placement vertical du menu et de tout le reste en dessous = racine
        vBoxFullWindow = new VBox();

        // placement horizontal de la zone de gauche et de la map
        hBoxLeftZoneMap = new HBox();

        // on crée la zone de gauche (menu, gestion calques, gestion objets)
        vBoxLeftZone = new VBox();
        vBoxLeftZone.setPadding(new Insets(0, 10, 0, 10));

        // on crée la zone de gestion des calques
        vBoxCalques = new VBox(10);

        // on crée la zone de gestion des nouveaux calques
        hBoxNewLayerZone = new HBox(10);

        // on crée la zone d'affichage des objets
        vBoxObjects = new VBox(10);
        vBoxObjects.setPadding(new Insets(0, 0, 0, 10));

        // on crée une HBox pour que les deux colonnes de radio button soient l'une a coté de l'autre
        hBoxRadioButton = new HBox(20);
        hBoxRadioButton.setPadding(new Insets(20, 0, 0, 0));

        //on crée les differentes colonnes pour un alignement propre des radio button
        vBoxColumn1 = new VBox(20);
        vBoxColumn2 = new VBox(20);

        // on crée la vbox qui contiendra les boutons de gestion la map
        vBoxMapButtons = new VBox(20);
    }

    /**
     * Cette méthode sert juste a créer la barre de menu en haut de la fenetre.
     */
    void createMenu(){

        // on commence par le menu placé en haut
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(windowStage.widthProperty());
        menuFile = new javafx.scene.control.Menu("Fichier");
        newGameButton = new MenuItem("Nouvelle partie");
        resumeGameButton = new MenuItem("Reprendre partie");
        leaveServerButton = new MenuItem("Quitter");
        menuFile.getItems().addAll(newGameButton, resumeGameButton, leaveServerButton);
        menuEdition = new javafx.scene.control.Menu("Edition");
        menuHelp = new javafx.scene.control.Menu("Help");
        // ajout de chaque parties du menu dans la barre
        menuBar.getMenus().addAll(menuFile, menuEdition, menuHelp);
        menuBar.setStyle("-fx-background-color: darkgray");
        // on range le menu dans la zone fullwindow en premier (= en haut)
        vBoxFullWindow.getChildren().add(menuBar);
    }

    /**
     * Cette méthode assemble tout les morceaux : layouts et widgets.
     * Pour former la zone de création de calques.
     */
    void createLayerArea(){

        // nom de la zone
        Label mLayerNameZoneLabel = new Label("- Gestion des calques");
        mLayerNameZoneLabel.setStyle("-fx-font-weight: bold");
        mLayerNameZoneLabel.setPadding(new Insets(20, 0, 10, 20));
        // on fabrique un separator
        Separator mSeparator1 = new Separator();
        mSeparator1.setMaxWidth(width);
        // on affiche le titre de la zone
        vBoxCalques.getChildren().addAll(mLayerNameZoneLabel, mSeparator1);

        Label lNewLayerLabel = new Label("Nom du calque :");
        lNewLayerLabel.setPadding(new Insets(0, 0, 0, 10));
        newLayerTextField = new TextField();
        newLayerButton = new Button("Ajouter");
        // on ajoute tout les éléments nécéssaires a l'ajout d'un nouveau calque
        hBoxNewLayerZone.getChildren().addAll(lNewLayerLabel, newLayerTextField, newLayerButton);
        // on ajoute la zone des nouveaux calques a la zone des calques
        vBoxCalques.getChildren().add(hBoxNewLayerZone);

        // label affichage calques
        Label mShowLayersLabel = new Label("Afficher calques :");
        mShowLayersLabel.setPadding(new Insets(5, 0, 5, 10));
        // on affiche le label
        vBoxCalques.getChildren().add(mShowLayersLabel);

        // on crée une liste des calques a cocher (visibles ou non)
        layerListView = new ListView<CheckBox>();
        layerListView.setPrefSize(50, 130);
        layerListView.setEditable(true);

        // on remplit la listeview de boutons radio
        radioButtons = FXCollections.observableArrayList();
        // on range la liste de radiobouton dans la listview
        layerListView.setItems(radioButtons);

        // on configure le bouton de suppression de calques
        deleteLayerButton = new Button("Supprimer");
        // on ajoute la listview a la zone des calques et le bouton de suppression de calques
        vBoxCalques.getChildren().addAll(layerListView, deleteLayerButton);

        // separateur de la zone de calques et des objets
        Separator mSeparator2 = new Separator();
        mSeparator2.setMaxWidth(width);
        mSeparator2 .setPadding(new Insets(10, 0, 10, 0));

        // on range la zone de gestion des calques dans la zone de gauche
        vBoxLeftZone.getChildren().addAll(vBoxCalques, mSeparator2);
    }

    /**
     * Cette méthode assemble tout les morceaux : layouts et widgets.
     * Pour former la zone de gestion de l'affichage des differentes entitées.
     */
    void createObjectsArea(){

        // label de gestion des objets
        Label mObjectLabel = new Label("- Affichage des objets");
        mObjectLabel.setStyle("-fx-font-weight: bold");
        mObjectLabel.setPadding(new Insets(10, 0, 10, 20));
        // on ajoute le label a la zone de gauche
        vBoxLeftZone.getChildren().add(mObjectLabel);

        // on separe le titre de la zone des cases a cocher
        Separator mSeparator3 = new Separator();
        mSeparator3.setMaxWidth(width);
        mSeparator3 .setPadding(new Insets(10, 0, 0, 0));
        // on ajoute le separator a la zone de gauche
        vBoxLeftZone.getChildren().add(mSeparator3);

        // ce label sert a afficher le calque courant
        currentLayerLabel = new Label("Calque séléctionné : (aucun)");
        currentLayerLabel.setPadding(new Insets(10, 0, 0, 10));
        // on ajoute le label sous le separateur
        vBoxLeftZone.getChildren().add(currentLayerLabel);

        // on crée les différentes cases a cocher qui servent de filtre a l'affichage
        playerCheckBox = new CheckBox("Joueurs");
        portalCheckBox = new CheckBox("Portails");
        itemCheckBox = new CheckBox("Objets");
        areaCheckBox = new CheckBox("Champs");
        linkCheckBox = new CheckBox("Liens");

        vBoxColumn1.getChildren().addAll(playerCheckBox, itemCheckBox, areaCheckBox);
        vBoxColumn2.getChildren().addAll(portalCheckBox, linkCheckBox);
        hBoxRadioButton.getChildren().addAll(vBoxColumn1, vBoxColumn2);

        // on range toutes les lignes de radio button dans la zone de gestion des objets
        vBoxObjects.getChildren().addAll(hBoxRadioButton);

        // on range la zone de gestion des objets dans la zone de gauche
        vBoxLeftZone.getChildren().add(vBoxObjects);
    }

    /**
     * Cette méthode créé la zone dans laquelle sera contenue la WebView.
     */
    void createMapBrowser(){

        // on ajoute la zone de gauche et la map dans le meme affichage horizontal
        Separator mSepZoneGaucheMap = new Separator();
        mSepZoneGaucheMap.setOrientation(Orientation.VERTICAL);
        mSepZoneGaucheMap.setMaxWidth(height);
        mSepZoneGaucheMap.setPadding(new Insets(0, 10, 0, 0));
        // on crée la map placée tout a droite de l'écran
        mapBrowser = new CBrowser(height, width);
        hBoxLeftZoneMap.getChildren().addAll(vBoxLeftZone, mSepZoneGaucheMap,  mapBrowser);

        // on ajoute la zone d'affichage horizontal sous le menu
        vBoxFullWindow.getChildren().add(hBoxLeftZoneMap);
    }

    /**
     * Cette méthode créé la zone ou se trouvent les boutons de gestion de la map (directions et zoom).
     */
    void createMapPanel(){

        // on definit les boutons
        zoomButton = new Button();
        zoomOutButton = new Button();
        upArrowButton = new Button();
        botArrowButton = new Button();
        leftArrowButton = new Button();
        rightArrowButton = new Button();
        // on définit les labels
        blueTeamLabel = new Label("Equipe bleue :");
        redTeamLabel = new Label("Equipe rouge : ");
        blueTeamScore = new Label("0");
        blueTeamScore.setPadding(new Insets(0, 0, 0, 40));
        redTeamScore = new Label("0");
        redTeamScore.setPadding(new Insets(0, 0, 0, 40));

        // on range toutes les images dans des variables
        Image mZoomImage, mZoomOutImage, mUpImage, mBotImage, mRightImage, mLeftImage;
        mZoomImage = new Image(String.valueOf(getClass().getClassLoader().getResource("zoom-in.png")));
        mZoomOutImage = new Image(String.valueOf(getClass().getClassLoader().getResource("zoom-out.png")));
        mUpImage = new Image(String.valueOf(getClass().getClassLoader().getResource("up.png")));
        mBotImage = new Image(String.valueOf(getClass().getClassLoader().getResource("bot.png")));
        mLeftImage = new Image(String.valueOf(getClass().getClassLoader().getResource("left.png")));
        mRightImage = new Image(String.valueOf(getClass().getClassLoader().getResource("right.png")));

        // on leur attribue leur icone
        zoomButton.setGraphic(new ImageView(mZoomImage));
        zoomOutButton.setGraphic(new ImageView(mZoomOutImage));
        upArrowButton.setGraphic(new ImageView(mUpImage));
        botArrowButton.setGraphic(new ImageView(mBotImage));
        leftArrowButton.setGraphic(new ImageView(mLeftImage));
        rightArrowButton.setGraphic(new ImageView(mRightImage));

        // on range les boutons dans la box horizontale et on les centre
        VBox mVBoxMargin = new VBox(20);
        mVBoxMargin.setPadding(new Insets(50, 0, 0, 34));
        mVBoxMargin.getChildren().addAll(zoomButton, zoomOutButton);
        vBoxMapButtons.getChildren().add(mVBoxMargin);

        // on créé un gridpane pour range les fleches de de facon commune
        GridPane mGridPane = new GridPane();
        GridPane.setRowIndex(upArrowButton, 1);
        GridPane.setColumnIndex(upArrowButton, 2);
        GridPane.setRowIndex(leftArrowButton, 2);
        GridPane.setColumnIndex(leftArrowButton, 1);
        GridPane.setRowIndex(rightArrowButton, 2);
        GridPane.setColumnIndex(rightArrowButton, 3);
        GridPane.setRowIndex(botArrowButton, 3);
        GridPane.setColumnIndex(botArrowButton, 2);

        mGridPane.getChildren().addAll(upArrowButton, leftArrowButton, rightArrowButton, botArrowButton);

        vBoxMapButtons.getChildren().addAll(mGridPane, blueTeamLabel, blueTeamScore, redTeamLabel, redTeamScore);

        // on range la box verticale contenant les boutons dans la hbox générale de la fenetre
        hBoxLeftZoneMap.getChildren().add(vBoxMapButtons);
    }

    /**
     * Cette méthode attribue le layout qui englobe toute la fenetre au stage.
     * Affiche ensuite la fenetre.
     */
    void windowFinalisation(){

        // on ajoute le gestionaire d'affichage racine a la scène
        fullWindowScene = new Scene(vBoxFullWindow, height, width);
        // on ajoute la scène au théatre
        windowStage.setScene(fullWindowScene);
        // on attribue une icone au programme (affiché dans la barre des taches)
        setIcon(windowStage);
        // sans oublier d'afficher la page :D
        windowStage.show();
    }

    /**
     * Cette méthode attribue une icone a la fenetre (visible dans la barre des taches).
     * @param pStage
     */
    void setIcon(Stage pStage){

        pStage.getIcons().setAll(new Image(getClass().getClassLoader().getResource("icon.png").toExternalForm()));
    }

    /**
     * Cette méthode est utilisée par le controleur pour ajouter un calque dans la liste de calques
     * et l'affiche dans la liste des calques de la vue.
     */
    public void addLayerListView(){

        radioButtons.add(new CheckBox(newLayerTextField.getText()));
        layerListView.refresh();
        cLayers.add(new CLayer(newLayerTextField.getText()));
        // on efface le contenu du champ de nouveau calque
        newLayerTextField.setText("");
        // cette variable sert a vérifier s'il y a au moins 1 élément dans la listview
        isEmpty = false;
    }

    /**
     * Cette méthode est utilisée par le controleur pour supprimer un calque de la liste de calque
     * de la vue.
     */
    public void removeLayerListView(){

        try {
            if (radioButtons.size() > 0) {
                //on efface le bouton radio de la listview
                radioButtons.remove(selectedLayer);
                System.out.println("salut");
                // on rafraichit la vue
                layerListView.refresh();
                System.out.println("salut");
                // on efface le calque de la liste de calques
                cLayers.remove(selectedLayer);
                System.out.println("salut");
                // on met le calque séléctionné a 0
                selectedLayer = 0;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attention");
                alert.setHeaderText("Liste vide");
                alert.setContentText("Vous essayez de supprimer \nun élément qui n'existe pas" +
                        "\nou vous n'avez pas séléctionné de calque !");
                alert.showAndWait();
                // on indique qu'il n'y a plus d'éléments dans la listview
                isEmpty = true;
            }
        }
        catch (Exception e){}
    }

    /**
     * Cette méthode est utilisée par le controleur et affiche le calque séléctionnée dans un label
     * de la vue.
     */
    public void selectLayer (){

        //on indique a la classe quel calque est séléctionné et on affiche sur la GUI lequel est séléctionné
        selectedLayer = layerListView.getSelectionModel().getSelectedIndex();
        currentLayerLabel.setText(layerListView.getSelectionModel().getSelectedItem().getText());

        // on indique s'il est visible ou non
        cLayers.get(layerListView.getSelectionModel().getSelectedIndex()).setVisible(
                layerListView.getSelectionModel().getSelectedItem().isSelected());

        // on affiche quelles checkbox sont cochées
        playerCheckBox.setSelected(cLayers.get(selectedLayer).getPlayers());
        portalCheckBox.setSelected(cLayers.get(selectedLayer).getPortals());
        itemCheckBox.setSelected(cLayers.get(selectedLayer).getObjects());
        linkCheckBox.setSelected(cLayers.get(selectedLayer).getLinks());
        areaCheckBox.setSelected(cLayers.get(selectedLayer).getAreas());
    }

    /**
     * Cette méthode lance l'application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    //////// getters / setters ////////


    public Button getNewLayerButton() { return newLayerButton; }

    public Button getDeleteLayerButton() { return deleteLayerButton; }

    public ListView<CheckBox> getLayerListView() { return layerListView; }

    public CheckBox getPlayerCheckBox() { return playerCheckBox; }

    public CheckBox getPortalCheckBox() { return portalCheckBox; }

    public CheckBox getItemCheckBox() { return itemCheckBox; }

    public CheckBox getLinkCheckBox() { return linkCheckBox; }

    public CheckBox getAreaCheckBox() { return areaCheckBox; }

    public TextField getNewLayerTextField() { return newLayerTextField; }

    public int getSelectedLayer() { return selectedLayer; }

    public List<CLayer> getcLayers() { return cLayers; }

    public Boolean getIsEmpty() { return isEmpty; }

    public MenuItem getNewGameButton() { return newGameButton; }

    public MenuItem getResumeGameButton() { return resumeGameButton; }

    public MenuItem getLeaveServerButton() { return leaveServerButton; }

    public Button getZoomButton() { return zoomButton; }

    public Button getZoomOutButton() { return zoomOutButton; }

    public Button getUpArrowButton() { return upArrowButton; }

    public Button getBotArrowButton() { return botArrowButton; }

    public Button getLeftArrowButton() { return leftArrowButton; }

    public Button getRightArrowButton() { return rightArrowButton; }

    public CBrowser getMapBrowser() { return mapBrowser; }

    public Label getBlueTeamScore() { return blueTeamScore; }

    public Label getRedTeamScore() { return redTeamScore; }
}