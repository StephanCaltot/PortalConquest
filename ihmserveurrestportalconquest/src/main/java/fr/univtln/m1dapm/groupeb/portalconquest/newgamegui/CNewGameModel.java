package fr.univtln.m1dapm.groupeb.portalconquest.newgamegui;

/**
 * Created by clemzux on 09/05/16.
 * Cette classe est le modele de la vue de la nouvelle partie.
 */
public class CNewGameModel {
    private static CNewGameModel ourInstance = new CNewGameModel();

    public static CNewGameModel getInstance() {
        return ourInstance;
    }

    /**
     * Constructeur de CNewGameModel.
     * Vu que le modele est un singleton, il ne fait rien de particulier.
     */
    private CNewGameModel() {
    }


    //////// attributes ////////

    // MVC attributes
    private CNewGameGUI cNewGameGUI;


    //////// methods ////////


    /**
     * Cette méthode rend la vue de nouvelle partie visible.
     */
    public void setGuiVisible() {

        cNewGameGUI.setVisible();
    }

    /**
     * Cette méthode rend la vue de la nouvelle partie invisible.
     */
    public void setGuiHidden() {

        cNewGameGUI.setHiden();
    }


    //////// getters / setters ////////


    /**
     * Cette méthode initialise la vue a l'interieur du modèle.
     * @param cNewGameGUI
     */
    public void setcNewGameGUI(CNewGameGUI cNewGameGUI) { this.cNewGameGUI = cNewGameGUI; }
}
