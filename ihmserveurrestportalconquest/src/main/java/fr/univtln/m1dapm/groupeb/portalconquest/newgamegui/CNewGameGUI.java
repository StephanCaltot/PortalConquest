package fr.univtln.m1dapm.groupeb.portalconquest.newgamegui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by clemzux on 09/05/16.
 * Cette classe est la vue de la fenetre de nouvelle partie.
 */
public class CNewGameGUI {


    //////// attributes ////////


    // MVC attributes
    private CNewGameCtrl cNewGameCtrl;

    // taille fenetre
    private int height, width;
    // window attributes
    private Stage windowStage;
    private Scene fullWindowScene;

    // Layouts
    private VBox vBoxFullWindow;
    private HBox hBoxButtons;
    private GridPane gameSettingsGridPane;

    // labels
    private Label labelPortal, labelNbItems;

    // textfield
    private TextField textFieldPortal, textFieldNbItems;

    // buttons
    private Button buttonOk, buttonCancel;


    //////// builder ////////


    /**
     * Constructeur de la vue de la fenetre de nouvelle partie
     * Il configure en premier la nouvelle fenetre
     * En second tout les layouts
     * En troisième tout les labels, textFields et boutons
     * En quatrieme tout les labels, textFields et boutons dans les layouts
     * En cinquieme, finalise la fenetre en assignant les layouts au stage
     * Et pour terminer, initialise une instance du controleur de la fenetre de nouvelle partie.
     */
    public CNewGameGUI() {

        // on configure la nouvelle window a afficher
        setWindow();

        // on configure toutes les box et on ajoute le titre a la fenetre
        setLayouts();

        // création des labels, textFields et boutons
        initLabelTextFieldButtons();

        // on place les labels, textfields, boutons dans le gridpane
        placeLabelTextFieldButtons();

        // on finalise l'affichage et on l'affiche
        windowFinalisation();

        // on envoie la vue au controleur
        cNewGameCtrl = new CNewGameCtrl(this);
    }


    //////// methods ////////


    /**
     * Cette méthode finalise la fentre avant de l'afficher.
     * Elle place tout les layouts a l'endroit ou ils doivent etre, les spéarateurs ...
     */
    private void windowFinalisation(){

        // on ajoute un titre a l'activité concernant la fenetre
        Label mLabelTitle = new Label("Choisissez le nombre de chaque objet :");
        vBoxFullWindow.getChildren().add(mLabelTitle);

        // on ajoute un separateur entre le titre et les champs
        Separator mSeparator1 = new Separator();
        mSeparator1.setOrientation(Orientation.HORIZONTAL);
        mSeparator1.setPrefWidth(width);
        vBoxFullWindow.getChildren().add(mSeparator1);

        // on ajoute le gridpane avec tout les champs dans la vbox qui contient la fenetre
        vBoxFullWindow.getChildren().add(gameSettingsGridPane);

        // on ajoute un separateur entre les champs et les boutons
        Separator mSeparator2 = new Separator();
        mSeparator2.setOrientation(Orientation.HORIZONTAL);
        mSeparator2.setPrefWidth(width);
        vBoxFullWindow.getChildren().add(mSeparator2);

        // on ajoute la hbox boutons a la grange box
        vBoxFullWindow.getChildren().add(hBoxButtons);

        //scene
        fullWindowScene = new Scene(vBoxFullWindow, height, width);
        windowStage.setScene(fullWindowScene);
    }

    /**
     * Cette méthode place les labels, textFields et boutons a leur place dans le gridPane.
     */
    private  void placeLabelTextFieldButtons(){

        // on les place de facon alignée dans un gridpane

        // portals
        GridPane.setRowIndex(labelPortal, 1);
        GridPane.setColumnIndex(labelPortal, 1);
        GridPane.setRowIndex(textFieldPortal, 1);
        GridPane.setColumnIndex(textFieldPortal, 2);
        // nbItem
        GridPane.setRowIndex(labelNbItems, 2);
        GridPane.setColumnIndex(labelNbItems, 1);
        GridPane.setRowIndex(textFieldNbItems, 2);
        GridPane.setColumnIndex(textFieldNbItems, 2);

        gameSettingsGridPane.getChildren().addAll(labelPortal, labelNbItems,
                textFieldPortal, textFieldNbItems);

        hBoxButtons.getChildren().addAll(buttonOk, buttonCancel);
    }

    /**
     * Cette méthode initialise les labels, textFields et boutons à une valeur correcte avant d'etre affichés.
     */
    private void initLabelTextFieldButtons(){

        // labels
        labelPortal = new Label("Portails :");
        labelNbItems = new Label("Nombre d'objets :");

        // textfield
        textFieldPortal = new TextField();
        textFieldPortal.setText("0");
        textFieldPortal.setMaxWidth(50);
        textFieldNbItems = new TextField();
        textFieldNbItems.setText("3");
        textFieldNbItems.setMaxWidth(50);

        // buttons
        buttonOk = new Button("Ok");
        buttonCancel = new Button("Annuler");
    }

    /**
     * Cette méthode initialise tout les layouts pour etre prets a recevoir des widgets.
     */
    private void setLayouts(){

        // vbox contenant toute la fenetre
        vBoxFullWindow = new VBox(20);
        vBoxFullWindow.setPadding(new Insets(10, 10, 0, 30));
        // gridpane qui coniendra les labels et les textview
        gameSettingsGridPane = new GridPane();
        // hbox qui contient les boutons ok et annuler
        hBoxButtons = new HBox(30);
        hBoxButtons.setPadding(new Insets(20, 0, 0, 50));
    }

    /**
     * Cette methode initialise une nouveau stage, initialise la taille, le titre ...
     */
    private void setWindow(){

        // théatre
        windowStage = new Stage();
        windowStage.setTitle("Paramètres nouvelle partie");

//        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//        int h = (int)dimension.getHeight();
//        int w  = (int)dimension.getWidth();

        // a enlever quand je programmerai avec un seul écran !!!
        int h, w;
        h = 400;
        w = 360;
        height = h;
        width = w;

        // on définit la taille du théatre (fenetre principale)
        windowStage.setWidth(w);
        windowStage.setHeight(h);
    }

    /**
     * Cette méthode rend la fenetre visible.
     */
    public void setVisible(){

        windowStage.show();
    }

    /**
     * Cette méthode rend la fenetre invisible .
     */
    public void setHiden(){

        windowStage.hide();
        resetTextFields();
    }

    /**
     * Cette méthode initialise les textFields avec des valeurs par defaut.
     * Nombre de portails = 0
     * Nombre d'objets sur la carte = 3
     */
    private void resetTextFields(){

        textFieldPortal.setText("0");
        textFieldNbItems.setText("3");
    }


    //////// getters / setters ////////


    /**
     * Cette méthode retourne le bouton de validation de la page.
     * @return Button
     */
    public Button getButtonOk() { return buttonOk; }

    /**
     * Cette méthode retourne le bouton d'annulation de nouvelle partie.
     * @return Button
     */
    public Button getButtonCancel() { return buttonCancel; }

    /**
     * Cette méthode retourne le textField du nombre de portails.
     * @return TextField
     */
    public TextField getTextFieldPortal() { return textFieldPortal; }

    /**
     * Cette méthode retourne le textField du nombre d'objets sur la carte
     * @return TextField
     */
    public TextField getTextFieldNbItems() { return textFieldNbItems; }
}
