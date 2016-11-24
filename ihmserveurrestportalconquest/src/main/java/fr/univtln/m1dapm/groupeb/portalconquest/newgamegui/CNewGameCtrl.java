package fr.univtln.m1dapm.groupeb.portalconquest.newgamegui;

import fr.univtln.m1dapm.groupeb.portalconquest.utilities.CNewGameParameters;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by clemzux on 09/05/16.
 * Cette classe est le controleur de la fenetre de nouvelle partie.
 */
public class CNewGameCtrl {

    // MVC attributes
    private CNewGameGUI cNewGameGUI;
    private CNewGameModel cNewGameModel;

    // widgets from GUI
    private Button buttonOk, buttonCancel;
    private TextField textFieldPortal, textFieldNbItems;


    //////// builder ////////


    /**
     * Constructeur, prend la vue de la fenetre de nouvelle partie en parametre.
     * Lors de l'éxécution, ce constructeur initialise les widgets de la vue ainsi que les listeners.
     * @param pCNewGameGUI
     */
    public CNewGameCtrl (CNewGameGUI pCNewGameGUI){

        cNewGameGUI = pCNewGameGUI;

        // on transfere les widgets de la vue
        initWidgets();

        // on initialise les listeners des widgets
        initListeners();
    }


    //////// methods ////////


    /**
     * Cette méthode initialise les listeners des widgets de la vue.
     */
    private void initListeners(){

        // bouton ok
        buttonOk.setOnAction((ActionEvent e) -> {

            // on transmet le nb de portails et le nb d'objets a la classe parametre
            CNewGameParameters.getInstance().setPortalNb(Integer.valueOf(textFieldPortal.getText()));
            CNewGameParameters.getInstance().setNbItemsNb(Integer.valueOf(textFieldNbItems.getText()));
            // on crée les parametres de la partie
            CNewGameParameters.getInstance().gameSettings();
            // on lance la partie
            CNewGameParameters.getInstance().launchGame();
            CNewGameModel.getInstance().setGuiHidden();
        });

        // bouton annuler
        buttonCancel.setOnAction((ActionEvent e) -> {
            CNewGameModel.getInstance().setGuiHidden();
        });
    }

    /**
     * Cette méthode initialise touts les widgets de la vue au sein du controleur
     */
    private void initWidgets(){

        // buttons
        buttonOk = cNewGameGUI.getButtonOk();
        buttonCancel = cNewGameGUI.getButtonCancel();

        // textfields
        textFieldPortal = cNewGameGUI.getTextFieldPortal();
        textFieldNbItems = cNewGameGUI.getTextFieldNbItems();
    }
}
