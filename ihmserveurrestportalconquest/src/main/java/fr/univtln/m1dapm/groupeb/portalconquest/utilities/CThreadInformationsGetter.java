package fr.univtln.m1dapm.groupeb.portalconquest.utilities;


import classes.*;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import fr.univtln.m1dapm.groupeb.portalconquest.primarygui.CServerModel;
import javafx.application.Platform;

import javax.ws.rs.core.MediaType;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by clemzux on 12/05/16.
 * Cette classe est un thread qui va chercher des informations de la BDD via le serveur.
 */
public class CThreadInformationsGetter implements Runnable {


    //////// attributes ////////


    // connections
    private WebResource connectionWR;

    // webview
    public CBrowser cBrowser;

    // teams
    CTeam blue, red;

    // joueurs par team
    List<CPlayer> redTeam, blueTeam;

    // liste des portails
    List<CPortal> neutralPortals, bluePortals, redPortals;

    // liste des items de la map
    List<CMapItem> itemsList = new ArrayList<>();

    // liste de champs par équipes
    List<CField> blueFields, redFields, fields;

    // liens par équipes
    List<CLink> blueLinks, redLinks;

    // booléens des calques a afficher
    private Boolean players = true, portals = true, items = true, links = true, areas = true;

    // bolléens des informations qui ont été rapatriées au moins  une fois
    private Boolean portalsGetted = false, fieldsGetted = false, linksGetted = false, playersBlueGetted = false;
    private Boolean playersRedGetted = false, itemsGetted = false;


    //////// builder ////////


    /**
     * Constructeur, prend en parametre la connection, transmise par le modele.
     * Initialise la WebView transmise par le modele.
     * Initialise les équipes pour les traitements ultérieurs.
     */
    public  CThreadInformationsGetter() {

        connectionWR = CServerModel.getInstance().getConnection();

        cBrowser = CServerModel.getInstance().getMapBrowser();
        cBrowser.getWebEngine().setJavaScriptEnabled(true);

        blue = new CTeam();
        red = new CTeam();

        blue.setId(1);
        blue.setName("bleu");
        red.setId(2);
        red.setName("rouge");
    }


    //////// methods ////////


    /**
     * Cette méthode est obligatoire quand on implante l'interface Runnable.
     * Veille au bon deroulement des étapes : rapatrier les informations, les ranger,
     * les dessiner sur la carte.
     */
    public void run(){

        Platform.setImplicitExit(false);

        System.out.println("partie en cours !\n");

        while (true){
            if (CNewGameParameters.getInstance().isGameLaunched()){

                // on determine avec les calques ce que l'on doit afficher ou pas
//                objectsToDisplay();

                // on va chercher les portails
                getPortalsREST(1);
                getPortalsREST(2);
                getPortalsREST(3);

                // on recupere les liens entre portails
                getLinksREST(1);
                getLinksREST(1);

                // on recupere les champs que forment les liens
                getFieldsREST(1);
                getFieldsREST(2);

                // on va chercher la liste de tout les joueurs et on les range par team
                getPlayersREST(1);
                getPlayersREST(2);

                // on recupere la liste de tout les items disposés sur la map
                getItemsREST();

                // on recupere les scores de chaque team
                getScore(1);
                getScore(2);

                // on dessine toutes les entitées
                drawAll();
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cette méthode analyse la liste de calques et détermine ce qui doit etre dessiné ou non.
     */
    private void objectsToDisplay() {

        List<CLayer> mLayers = CServerModel.getInstance().getcServerGUI().getcLayers();

        players = false;
        portals = false;
        items = false;
        links = false;
        areas = false;

        for (CLayer l : mLayers) {

            System.out.println(l.toString());

            if (l.getVisible() == true) {

                if (l.getPlayers() == true)
                    players = true;

                if (l.getPortals() == true)
                    portals = true;

                if (l.getObjects() == true)
                    items = true;

                if (l.getLinks() == true)
                    links = true;

                if (l.getAreas() == true)
                    areas = true;
            }
        }
    }

    private void getScore(int pTeam) {

        CTeam mTeam = connectionWR.path("teams/"+ pTeam).type(MediaType.APPLICATION_JSON).get(new GenericType<CTeam>(){});

        CServerModel.getInstance().setScore(pTeam, mTeam.getScore());
    }

    /**
     * Cette méthode retourne la liste des portails via le serveur.
     */
    private void getPortalsREST(int pTeam){

        List<CPortal> mPortalsList = new ArrayList<>();

        mPortalsList = connectionWR.path("teams/"+ pTeam +"/portals").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CPortal>>(){});

        if (pTeam == 1)
            bluePortals = mPortalsList;

        if (pTeam == 2)
            redPortals = mPortalsList;

        if (pTeam == 3)
            neutralPortals = mPortalsList;

        portalsGetted = true;
    }

    /**
     * Cette méthode retourne la liste des joueurs via le serveur.
     */
    private void getPlayersREST(int pTeam){

        List<CPlayer> playersList = new ArrayList<>();

        playersList =
                connectionWR.path("teams/"+ pTeam +"/players").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CPlayer>>(){});

        if (pTeam == 1) {
            blueTeam = playersList;
            playersBlueGetted = true;
        }
        else {
            redTeam = playersList;
            playersRedGetted = true;
        }
    }

    /**
     * Cette méthode retourne la liste des items via le serveur.
     */
    private void getItemsREST() {

        itemsList = connectionWR.path("mapitems").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CMapItem>>(){});
    }

    /**
     * Cette méthode retourne la liste des champs via le serveur.
     */
    private void getFieldsREST(int pTeam) {

        List<CField> mFieldsList = new ArrayList<>();

        mFieldsList = connectionWR.path("teams/"+ pTeam +"/fields").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CField>>(){});

        if (pTeam == 1)
            blueFields = mFieldsList;

        if (pTeam == 2)
            redFields = mFieldsList;

        fieldsGetted = true;
    }

    /**
     * Cette méthode retourne la liste des liens via le serveur.
     */
    private void getLinksREST(int pTeam) {

        List<CLink> linksList = new ArrayList<>();

        linksList = connectionWR.path("teams/"+ pTeam +"/links").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CLink>>(){});

        if (pTeam == 1)
            blueLinks = linksList;

        if (pTeam == 2)
            redLinks = linksList;
    }


    //////// drawers ////////


    /**
     * Cette méthode gère toutes les méthodes de dessin de toutes les entités visibles sur
     * la carte.
     * Elle va appeler successivement la méthode pour effacer tout les Markers, tout les dessins.
     */
    private void drawAll(){

        // on efface tout les markers de la map
        clearMarkers();

        // on dessine les joueurs
        if (players) {
            drawPlayers(1);
            drawPlayers(2);
        }

        // on dessine les portails
        if (portals) {
            drawPortals(1);
            drawPortals(2);
            drawPortals(3);
        }

        // on dessine les items de la map
        if (items)
            drawItems();

        if (areas) {
            drawFields(1);
            drawFields(2);
        }

        if (links) {
            drawLinks(1);
            drawLinks(2);
        }
    }

    /**
     * Cette méthode dessine tout les liens par équipe.
     * Prend en parametre un entier qui caractèrise un numero d'équipe.
     * @param pTeam
     */
    private void drawLinks(int pTeam) {

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        CPortal mPortalTemp1 = new CPortal();
                        CPortal mPortalTemp2 = new CPortal();

                        if (portalsGetted && linksGetted) {

                            for (int i = 0; i < blueLinks.size(); i++)
                                try {

                                    for (int j = 0; j < bluePortals.size(); j++)
                                        if (blueLinks.get(i).getFirstPortal().getId() == bluePortals.get(j).getId()) {

                                            mPortalTemp1.setLatitude(bluePortals.get(j).getLatitude());
                                            mPortalTemp1.setLongitude(bluePortals.get(j).getLongitude());

                                            blueLinks.get(i).getFirstPortal().setLatitude(bluePortals.get(j).getLatitude());
                                            blueLinks.get(i).getFirstPortal().setLongitude(bluePortals.get(j).getLongitude());
                                        }

                                    for (int j = 0; j < bluePortals.size(); j++)
                                        if (blueLinks.get(i).getSecondPortal().getId() == bluePortals.get(j).getId()) {

                                            mPortalTemp2.setLatitude(bluePortals.get(j).getLatitude());
                                            mPortalTemp2.setLongitude(bluePortals.get(j).getLongitude());

                                            blueLinks.get(i).getSecondPortal().setLatitude(bluePortals.get(j).getLatitude());
                                            blueLinks.get(i).getSecondPortal().setLongitude(bluePortals.get(j).getLongitude());
                                        }

                                    cBrowser.getWebEngine().executeScript("document.drawLink(" +
                                            mPortalTemp1.getLatitude() + "," +
                                            mPortalTemp1.getLongitude() + "," +
                                            mPortalTemp2.getLatitude() + "," +
                                            mPortalTemp2.getLongitude() + ", 1)");

                                } catch (Exception e) {
                                }

                            mPortalTemp1 = new CPortal();
                            mPortalTemp2 = new CPortal();

                            for (int i = 0; i < redLinks.size(); i++)
                                try {

                                    for (int j = 0; j < redPortals.size(); j++)
                                        if (redLinks.get(i).getFirstPortal().getId() == redPortals.get(j).getId()) {

                                            mPortalTemp1.setLatitude(redPortals.get(j).getLatitude());
                                            mPortalTemp1.setLongitude(redPortals.get(j).getLongitude());

                                            redLinks.get(i).getFirstPortal().setLatitude(redPortals.get(j).getLatitude());
                                            redLinks.get(i).getFirstPortal().setLongitude(redPortals.get(j).getLongitude());
                                        }

                                    for (int j = 0; j < redPortals.size(); j++)
                                        if (redLinks.get(i).getSecondPortal().getId() == redPortals.get(j).getId()) {

                                            mPortalTemp2.setLatitude(redPortals.get(j).getLatitude());
                                            mPortalTemp2.setLongitude(redPortals.get(j).getLongitude());

                                            redLinks.get(i).getSecondPortal().setLatitude(redPortals.get(j).getLatitude());
                                            redLinks.get(i).getSecondPortal().setLongitude(redPortals.get(j).getLongitude());
                                        }

                                    cBrowser.getWebEngine().executeScript("document.drawLink(" +
                                            mPortalTemp1.getLatitude() + "," +
                                            mPortalTemp1.getLongitude() + "," +
                                            mPortalTemp2.getLatitude() + "," +
                                            mPortalTemp2.getLongitude() + ", 2)");
                                } catch (Exception e) {
                                }
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Cette méthode dessine tout les champs par équipe.
     * Prend en parametre un entier qui caractèrise un numero d'équipe.
     * @param pTeam
     */
    private void drawFields(int pTeam) {

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        if (portalsGetted && fieldsGetted) {

                            List<CPortal> mPortalList;

                            if (pTeam == 1)
                                fields = blueFields;
                            else
                                fields = redFields;

                            if (pTeam == 1)
                                mPortalList = bluePortals;
                            else
                                mPortalList = redPortals;

                            for (CField f : fields) {

                                for (int i = 0; i < mPortalList.size(); i++) {

                                    if (f.getFirstPortal().getId() == mPortalList.get(i).getId()) {

                                        f.getFirstPortal().setLatitude(mPortalList.get(i).getLatitude());
                                        f.getFirstPortal().setLongitude(mPortalList.get(i).getLongitude());
                                    }
                                }

                                for (int i = 0; i < mPortalList.size(); i++) {

                                    if (f.getSecondPortal().getId() == mPortalList.get(i).getId()) {

                                        f.getSecondPortal().setLatitude(mPortalList.get(i).getLatitude());
                                        f.getSecondPortal().setLongitude(mPortalList.get(i).getLongitude());
                                    }
                                }

                                for (int i = 0; i < mPortalList.size(); i++) {

                                    if (f.getThirdPortal().getId() == mPortalList.get(i).getId()) {

                                        f.getThirdPortal().setLatitude(mPortalList.get(i).getLatitude());
                                        f.getThirdPortal().setLongitude(mPortalList.get(i).getLongitude());
                                    }
                                }

                                if (pTeam == 1)
                                    cBrowser.getWebEngine().executeScript("document.drawField(" +
                                            f.getFirstPortal().getLatitude() + "," +
                                            f.getFirstPortal().getLongitude() + "," +
                                            f.getSecondPortal().getLatitude() + "," +
                                            f.getSecondPortal().getLongitude() + "," +
                                            f.getThirdPortal().getLatitude() + "," +
                                            f.getThirdPortal().getLongitude()
                                            + ",1)");

                                if (pTeam == 2)
                                    cBrowser.getWebEngine().executeScript("document.drawField(" +
                                            f.getFirstPortal().getLatitude() + "," +
                                            f.getFirstPortal().getLongitude() + "," +
                                            f.getSecondPortal().getLatitude() + "," +
                                            f.getSecondPortal().getLongitude() + "," +
                                            f.getThirdPortal().getLatitude() + "," +
                                            f.getThirdPortal().getLongitude()
                                            + ",2)");
                            }
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Cette méthode dessine les items disposés sur la carte.
     */
    private void drawItems() {

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        if (itemsGetted)
                            for (int i=0; i<itemsList.size(); i++)
                                try {

                                    cBrowser.getWebEngine().executeScript("document.drawMapItem(" +
                                            itemsList.get(i).getLatitude() + "," +
                                            itemsList.get(i).getLongitude() + ")");
                                }catch (Exception e) {}
                    }
                });
            }
        }).start();
    }

    /**
     * Cette méthode dessine les portails par équipe (neutres compris).
     * Le parametre est un entier qui determine le numero de l'équipe.
     * @param pTeam
     */
    private void drawPortals(int pTeam) {

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        if (portalsGetted) {
                            if (pTeam == 1)
                                for (int i = 0; i < bluePortals.size(); i++)
                                    try {
                                        cBrowser.getWebEngine().executeScript("document.drawBluePortal(" +
                                                bluePortals.get(i).getLatitude() + "," +
                                                bluePortals.get(i).getLongitude() + ")");
                                    } catch (Exception e) {
                                    }

                            if (pTeam == 2)
                                for (int i = 0; i < redPortals.size(); i++)
                                    try {
                                        cBrowser.getWebEngine().executeScript("document.drawRedPortal(" +
                                                redPortals.get(i).getLatitude() + "," +
                                                redPortals.get(i).getLongitude() + ")");
                                    } catch (Exception e) {
                                    }

                            if (pTeam == 3)
                                for (int i = 0; i < neutralPortals.size(); i++)
                                    try {
                                        cBrowser.getWebEngine().executeScript("document.drawNeutralPortal(" +
                                                neutralPortals.get(i).getLatitude() + "," +
                                                neutralPortals.get(i).getLongitude() + ")");
                                    } catch (Exception e) {
                                    }
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * Cette méthode efface les marker en appelant la WebView qui elle meme fait appel
     * au WebEngine qui appele a son tour une fonction javascript.
     */
    private void clearMarkers() {

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        // on efface tous les markers de la map
                        try {
//                            cBrowser.getWebEngine().executeScript("document.clearPlayers()");
                            cBrowser.getWebEngine().executeScript("document.clearPortals()");
                            cBrowser.getWebEngine().executeScript("document.clearFields()");
                            cBrowser.getWebEngine().executeScript("document.clearLinks()");
                            cBrowser.getWebEngine().executeScript("document.clearItems()");
                        }catch (Exception e){}
                    }
                });
            }
        }).start();
    }

    /**
     * Cette méthode dessine tout les joueurs par équipe.
     * Le parametre est un entier qui determine le numero de l'équipe.
     * @param pTeamNumber
     */
    private void drawPlayers(int pTeamNumber){

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        if (playersBlueGetted && playersRedGetted) {

                            List<CPlayer> playersList;

                            if (pTeamNumber == 1)
                                playersList = blueTeam;
                            else
                                playersList = redTeam;

//                            cBrowser.getWebEngine().executeScript("document.clearPlayers()");

                            for (CPlayer lPlayer : playersList) {

                                try {
                                    System.out.println(lPlayer);
                                    cBrowser.getWebEngine().executeScript("document.drawPlayer(" +
                                            String.valueOf(lPlayer.getLatitude()) + "," +
                                            String.valueOf(lPlayer.getLongitude()) + ",'" +
                                            String.valueOf(pTeamNumber) + "')");
                                    System.out.println("salut2");
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
