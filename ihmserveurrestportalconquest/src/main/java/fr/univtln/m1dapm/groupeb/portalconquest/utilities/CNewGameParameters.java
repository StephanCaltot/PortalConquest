package fr.univtln.m1dapm.groupeb.portalconquest.utilities;

import classes.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.maps.model.LatLng;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.application.Platform;
import javafx.scene.web.WebView;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clemzux on 10/05/16.
 * Cette classe est un singleton.
 * Son role est d'instancier une nouvelle partie en disposant des portails et des objets
 * sur la carte.
 */
public class CNewGameParameters {

    private static CNewGameParameters ourInstance = new CNewGameParameters();

    public static CNewGameParameters getInstance() {
        return ourInstance;
    }


    //////// attributes ////////


    // nombre de :
    private int portalNb, nbItemsNb;

    // coins de la map
    private CCoords topRightCorner, topLeftCorner, botRightCorner, botLeftCorner;

    // cette liste contient tout les portails
    private List<CPortal> cPortals;

    // liste des items disposés sur la map
    private List<CMapItem> mapItems;

    // webview
    private WebView browser;

    // si ce booleen est a true, on considère que la partie est lancée
    private boolean gameLaunched = false;

    // connection
    WebResource wr;


    //////// builder ////////


    /**
     * Constructeur du singleton, normal qu'il soit vide.
     */
    private CNewGameParameters(){}


    //////// methods ////////


    /**
     * Cette méthode sert juste a indiquer que le bouton "ok" a été cliqué dans la fenetre de nouvelle
     * partie.
     */
    public void launchGame(){

        gameLaunched = true;
    }

    /**
     * Cette méthode initialise les parametres de jeu.
     * Centre la vision de la carte a peu pres au centre de la fac de Toulon.
     * Initialise la connection au serveur (WebRessource).
     * Initialise les portails et leur position.
     * Ajoute les portails a la BDD via le serveur.
     * Initialise les itemset leur position.
     * Ajoute les items a la BDD via le serveur.
     */
    public void gameSettings(){

        // connection a la BDD
        ClientConfig mClientConfig = new DefaultClientConfig();
        mClientConfig.getClasses().add(JacksonJsonProvider.class);
        Client mClient = Client.create(mClientConfig);
//        wr = mClient.resource("http://212.194.18.118:9998/");
        wr = mClient.resource("http://192.168.1.11:9998/");

        // coordonnées du centre de la carte (fac de la garde)
        setBorders(43.13644, 6.01881);

        // on crée les portails de facon aléatoire, avec des slots vides
        setPortals();

        // on ajoute les portails a la base de données
        addPortalsToBDD();

        // on crée les items qui seront dispersés a travers la map
//        setItems();
//
//        // on place les items a grande distance les uns des autres
//        setMapItemCoords();
//
//        // on ajoute a la BDD les items précédement créés
//        addItemsToDBB();
    }

    /**
     * Cette méthode ajoute les items a la BDD via le serveur en utilisant la connection (WebRessource).
     */
    private void addItemsToDBB(){

        // on ajoute tout les portails un par un a la bdd
        for (int i=0; i<nbItemsNb; i++)
            wr.path("mapitems").type(MediaType.APPLICATION_JSON).post(mapItems.get(i));

        System.out.println("items correctement insérés");
    }

    /**
     * Cette méthode détermine aléatoirement un item qui sera disposé sur la carte.
     */
    private void setItems(){

        Double mRandItem;
        int mNbItem;
        mapItems = new ArrayList<>();

        for (int i=0; i<nbItemsNb; i++){

            mRandItem = Math.random();
            mRandItem *= 100;
            mNbItem = (int) (mRandItem % 7);

            switch (mNbItem){
                case 0:
                    setConsumable();
                    break;
                case 1:
                    setFreqHackBoost();
                    break;
                case 2:
                    setRangeBoost();
                    break;
                case 3:
                    setResonator();
                    break;
                case 4:
                    setShield();
                    break;
                case 5:
                    setWeapon();
                    break;
                case 6:
                    setTurret();
                    break;
            }
        }
    }

    /**
     * Cette méthode détermine des coordonnées aux objets, ils seront disposés a distance respectables
     * les uns des autres sur la carte.
     */
    private void setMapItemCoords() {

        LatLng mLatLng = generateCoords();
        mapItems.get(0).setLatitude(mLatLng.lat);
        mapItems.get(0).setLongitude(mLatLng.lng);

        for (int i=1; i<mapItems.size(); i++){

            mLatLng = generateCoords();

            while (distanceBetween(
                    new LatLng(mapItems.get(i-1).getLatitude(), mapItems.get(i-1).getLongitude()), mLatLng) < 200)
                mLatLng = generateCoords();

            mapItems.get(i).setLatitude(mLatLng.lat);
            mapItems.get(i).setLongitude(mLatLng.lng);
        }
    }

    /**
     * Cette méthode initialise une tourelle a disposer sur la carte.
     */
    private void setTurret() {

//        CTurret mTurret = wr.path("items");
//
//        CMapItems mMapItem = new CMapItems();
//        mMapItem.setItem(mTurret);
//        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise une arme a disposer sur la carte.
     */
    private void setWeapon(){

        CWeapon mWeapon = new CWeapon();
        mWeapon.setLevel(1);
        mWeapon.setAttackValue(10);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mWeapon);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise un bouclier a disposer sur la carte.
     */
    private void setShield(){

        CShield mShield = new CShield();
        mShield.setDefenseValue(10);
        mShield.setRarity(1);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mShield);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise un résonateur a disposer sur la carte.
     */
    private void setResonator(){

        CResonator mResonator = new CResonator();
        mResonator.setLevel(1);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mResonator);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise un boost de portée a disposer sur la carte.
     */
    private void setRangeBoost(){

        CRangeBoost mRangeBoost = new CRangeBoost();
        mRangeBoost.setRangeValue(15);
        mRangeBoost.setRarity(1);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mRangeBoost);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise une augmentation de frequence de piratage a disposer sur la carte.
     */
    private void setFreqHackBoost(){

        CFrequencyHackBoost mFrequencyHackBoost = new CFrequencyHackBoost();
        mFrequencyHackBoost.setRarity(1);
        mFrequencyHackBoost.setBoostValue(2);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mFrequencyHackBoost);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise un consommable a disposer sur la carte.
     */
    private void setConsumable(){

        CConsumable mConsumable = new CConsumable();
        mConsumable.setEnergyValue(100);
        mConsumable.setLevel(2);

        CMapItem mMapItem = new CMapItem();
        mMapItem.setItem(mConsumable);
        mapItems.add(mMapItem);
    }

    /**
     * Cette méthode initialise une tourelle a disposer sur la carte.
     * Permet de définir un rectangle qui correspond aux bords de la fac.
     * Ce rectangle a des dimensions d'environ 1000 x 500.
     */
    public void setBorders(double pLat, double pLng){

        topRightCorner = new CCoords(pLat + 0.002, pLng + 0.007);
        topLeftCorner = new CCoords(pLat + 0.002, pLng - 0.007);
        botRightCorner = new CCoords(pLat - 0.002, pLng + 0.007);
        botLeftCorner = new CCoords(pLat - 0.002, pLng - 0.007);
    }

    /**
     * Cette méthode initialise les portails pour qu'ils soient neutres.
     * Initialise leur cooordonnées au hasard et de facon a ce qu'ils soient tous a une
     * distance minimum.
     * Initialise aussi une clé a chaque portail.
     */
    private void setPortals(){

        // on recrée la team neutre
        CTeam neutral = new CTeam();
        neutral.setId(3);
        neutral.setName("neutre");
        neutral.setScore(100);

        // liste des portails qui seront ajoutés a la bdd
        cPortals = new ArrayList<CPortal>();
        cPortals.add(new CPortal());

        // on génere une nouvelle coordonnée
        LatLng latlng = generateCoords();

        // on attribue cette coordonnée au premier portail
        cPortals.get(0).setLatitude(latlng.lat);
        cPortals.get(0).setLongitude(latlng.lng);
        cPortals.get(0).setName("Portal1");
        cPortals.get(0).setTeam(neutral);
        cPortals.get(0).setKey(new CKey());

        for (int i = 0; i < portalNb; i++) {

            // on génere de nouvelles coordonnées
            latlng = generateCoords();

            // a partir d'ici on va vérifier que le nouveau portail est a ditance raisonnable du précédent
            if (i > 0){

                // tant que le nouveau portail est a moins de 50m du précédent on en genere un nouveau
                while (distanceBetween(
                        new LatLng(cPortals.get(i-1).getLatitude(), cPortals.get(i-1).getLongitude()),
                        new LatLng(latlng.lat, latlng.lng)) < 80){

                    latlng = generateCoords();
                }

                // une fois que la nouvelle coordonnée a été générée a distance raisonnable,
                // on l'ajoute a la liste des portails a ajouter dans la bdd
                // on génere un nouveau portail
                cPortals.add(new CPortal());
                cPortals.get(i).setLatitude(latlng.lat);
                cPortals.get(i).setLongitude(latlng.lng);
                cPortals.get(i).setName("Portal" + (i+1));
                cPortals.get(i).setTeam(neutral);
                cPortals.get(i).setKey(new CKey());
            }
        }

        // on attribue des slots vides aux portails

        CSlot slotTemp = new CSlot();

        for (CPortal p: cPortals)
            for (int i=0; i<8; i++) {
                slotTemp.setPosition(i);
                p.addSlot(slotTemp);
                slotTemp = new CSlot();
            }
    }

    /**
     * Cette methode génère des cooronnées aléatoire a partant du coin gauche inférieur
     * de la limite de la zone de jeu.
     * @return
     */
    private LatLng generateCoords(){

        double mRandLat = Math.random();
        double mRandLng = Math.random();
        // on génere des coordonnées de la dimension maximum au bord droit de la zone de jeu
        mRandLat *= 10000;
        mRandLng *= 10000;
        mRandLat %= 400;
        mRandLng %= 1400;
        // cette division permet d'avoir une précision plus élevée
        mRandLat /= 100000;
        mRandLng /= 100000;

        return new LatLng(botLeftCorner.getLat() + mRandLat, botLeftCorner.getLng() + mRandLng);
    }

    /**
     * Cette méthode calcule la distance (en m) entre deux coordonnées.
     * Les deux parametres donnés a la fonction sont sous forme de LatLng.
     * @param pL1
     * @param pL2
     * @return
     */
    public float distanceBetween(LatLng pL1, LatLng pL2){

        double mEarthRadius = 6371000; //meters
        double mLat = Math.toRadians(pL2.lat-pL1.lat);
        double mLng = Math.toRadians(pL2.lng-pL1.lng);
        double mDouble1 = Math.sin(mLat/2) * Math.sin(mLat/2) +
                   Math.cos(Math.toRadians(pL1.lat)) * Math.cos(Math.toRadians(pL2.lat)) *
                   Math.sin(mLng/2) * Math.sin(mLng/2);
        double mDouble2 = 2 * Math.atan2(Math.sqrt(mDouble1), Math.sqrt(1-mDouble1));

        return (float) (mEarthRadius * mDouble2);
    }

    /**
     * Cette méthode ajoute les portails a la BDD via le serveur en utilisant la méthode post,
     * cette méthode s'utilise avec une connection (WebRessource).
     */
    private void addPortalsToBDD(){

        resetBDD();

        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        // on ajoute tout les portails un par un a la bdd
                        for (int i = 0; i<cPortals.size(); i++)
                            wr.path("portals").type(MediaType.APPLICATION_JSON).post(cPortals.get(i));

                        System.out.println("portails correctement insérés");
                    }
                });
            }
        }).start();
    }

    private void resetBDD() {

        CTeam blueTeam = new CTeam();
        blueTeam.setId(1);
        blueTeam.setName("BlueTeam");
        CTeam redTeam = new CTeam();
        redTeam.setId(2);
        redTeam.setName("RedTeam");
        CTeam neutralTeam = new CTeam();
        neutralTeam.setId(3);
        neutralTeam.setName("NeutralTeam");

        wr.path("teams").type(MediaType.APPLICATION_JSON).post(blueTeam);
        wr.path("teams").type(MediaType.APPLICATION_JSON).post(redTeam);
        wr.path("teams").type(MediaType.APPLICATION_JSON).post(neutralTeam);


        CPlayer pl1 = new CPlayer();
        CPlayer pl2 = new CPlayer();
        CPlayer pl3 = new CPlayer();
        CPlayer pl4 = new CPlayer();
        CPlayer pl5 = new CPlayer();
        CPlayer pl6 = new CPlayer();

        pl1.setId(1);
        pl2.setId(2);
        pl3.setId(3);
        pl4.setId(4);
        pl5.setId(5);
        pl6.setId(6);

        pl1.setNickname("Stephane");
        pl2.setNickname("Stephan");
        pl3.setNickname("Jerome");
        pl4.setNickname("Loïs");
        pl5.setNickname("Tomy");
        pl6.setNickname("Clement");

        pl1.setEmail("stephane.grasselli@gmail.com");
        pl2.setEmail("caltots@gmail.com");
        pl3.setEmail("jerome.samson07@gmail.com");
        pl4.setEmail("tonnetlois@gmail.com");
        pl5.setEmail("azertp053@gmail.com");
        pl6.setEmail("farge.clement@gmail.com");

        pl1.setTeam(blueTeam);
        pl2.setTeam(blueTeam);
        pl3.setTeam(blueTeam);
        pl4.setTeam(redTeam);
        pl5.setTeam(redTeam);
        pl6.setTeam(redTeam);

        pl1.setLatitude(43.13744);
        pl1.setLongitude(6.01981);

        pl2.setLatitude(43.13644);
        pl2.setLongitude(6.01581);

        pl3.setLatitude(43.13744);
        pl3.setLongitude(6.01781);

        pl4.setLatitude(43.13544);
        pl4.setLongitude(6.01781);

        pl5.setLatitude(43.13622353083457);
        pl5.setLongitude(6.01803052313852);

        pl6.setLatitude(43.13644);
        pl6.setLongitude(6.01981);

        pl1.setLevel(1);
        pl2.setLevel(2);
        pl3.setLevel(4);
        pl4.setLevel(1);
        pl5.setLevel(5);
        pl6.setLevel(3);

        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl1);
        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl2);
        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl3);
        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl4);
        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl5);
        wr.path("players").type(MediaType.APPLICATION_JSON).post(pl6);

        CConsumable cConsumable1 = new CConsumable();
        cConsumable1.setLevel(1);
        cConsumable1.setEnergyValue(30);


        CConsumable cConsumable2 = new CConsumable();
        cConsumable2.setLevel(2);
        cConsumable2.setEnergyValue(40);


        CConsumable cConsumable3 = new CConsumable();
        cConsumable3.setLevel(3);
        cConsumable3.setEnergyValue(50);


        CConsumable cConsumable4 = new CConsumable();
        cConsumable4.setLevel(4);
        cConsumable4.setEnergyValue(60);


        CConsumable cConsumable5 = new CConsumable();
        cConsumable5.setLevel(5);
        cConsumable5.setEnergyValue(70);


        CConsumable cConsumable6 = new CConsumable();
        cConsumable6.setLevel(6);
        cConsumable6.setEnergyValue(80);


        CConsumable cConsumable7 = new CConsumable();
        cConsumable7.setLevel(7);
        cConsumable7.setEnergyValue(90);


        CConsumable cConsumable8 = new CConsumable();
        cConsumable8.setLevel(8);
        cConsumable8.setEnergyValue(100);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable3);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable4);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable5);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable6);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable7);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable8);


        CWeapon cWeapon1 = new CWeapon();
        cWeapon1.setLevel(1);
        cWeapon1.setAttackValue(10);


        CWeapon cWeapon2 = new CWeapon();
        cWeapon2.setLevel(2);
        cWeapon2.setAttackValue(20);


        CWeapon cWeapon3 = new CWeapon();
        cWeapon3.setLevel(3);
        cWeapon3.setAttackValue(30);


        CWeapon cWeapon4 = new CWeapon();
        cWeapon4.setLevel(4);
        cWeapon4.setAttackValue(40);


        CWeapon cWeapon5 = new CWeapon();
        cWeapon5.setLevel(5);
        cWeapon5.setAttackValue(50);


        CWeapon cWeapon6 = new CWeapon();
        cWeapon6.setLevel(6);
        cWeapon6.setAttackValue(60);


        CWeapon cWeapon7 = new CWeapon();
        cWeapon7.setLevel(7);
        cWeapon7.setAttackValue(70);


        CWeapon cWeapon8 = new CWeapon();
        cWeapon8.setLevel(8);
        cWeapon8.setAttackValue(100);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon3);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon4);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon5);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon6);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon7);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon8);


        CResonator resonator1 = new CResonator();
        resonator1.setLevel(1);

        CResonator resonator2 = new CResonator();
        resonator2.setLevel(2);

        CResonator resonator3 = new CResonator();
        resonator3.setLevel(3);

        CResonator resonator4 = new CResonator();
        resonator4.setLevel(4);

        CResonator resonator5 = new CResonator();
        resonator5.setLevel(5);

        CResonator resonator6 = new CResonator();
        resonator6.setLevel(6);

        CResonator resonator7 = new CResonator();
        resonator7.setLevel(7);

        CResonator resonator8 = new CResonator();
        resonator8.setLevel(8);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator3);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator4);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator5);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator6);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator7);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(resonator8);


        CFrequencyHackBoost fhb1 = new CFrequencyHackBoost();
        fhb1.setBoostValue(1);
        fhb1.setRarity(1);

        CFrequencyHackBoost fhb2 = new CFrequencyHackBoost();
        fhb2.setBoostValue(2);
        fhb2.setRarity(2);

        CFrequencyHackBoost fhb3 = new CFrequencyHackBoost();
        fhb3.setBoostValue(3);
        fhb3.setRarity(3);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(fhb1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(fhb2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(fhb3);



        CRangeBoost rangeboost1 = new CRangeBoost();
        rangeboost1.setRangeValue(20);
        rangeboost1.setRarity(1);

        CRangeBoost rangeboost2 = new CRangeBoost();
        rangeboost2.setRangeValue(40);
        rangeboost2.setRarity(2);

        CRangeBoost rangeboost3 = new CRangeBoost();
        rangeboost3.setRangeValue(60);
        rangeboost3.setRarity(3);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost3);


        CShield shield1 = new CShield();
        shield1.setDefenseValue(5);
        shield1.setRarity(1);

        CShield shield2 = new CShield();
        shield2.setDefenseValue(10);
        shield2.setRarity(2);

        CShield shield3 = new CShield();
        shield3.setDefenseValue(15);
        shield3.setRarity(3);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(shield1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(shield2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(shield3);


        CTurret turret1 = new CTurret();
        turret1.setAttackValue(5);
        turret1.setRarity(1);

        CTurret turret2 = new CTurret();
        turret2.setAttackValue(10);
        turret2.setRarity(2);

        CTurret turret3 = new CTurret();
        turret3.setAttackValue(15);
        turret3.setRarity(3);

        wr.path("items").type(MediaType.APPLICATION_JSON).post(turret1);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(turret2);
        wr.path("items").type(MediaType.APPLICATION_JSON).post(turret3);




//        CPortal portal1 = new CPortal();
//        CPortal portal2 = new CPortal();
//        CPortal portal3 = new CPortal();
//
//        portal1.setLevel(0);
//        portal2.setLevel(0);
//        portal3.setLevel(0);
//
//
//        portal1.setName("Portal1");
//        portal1.setName("Portal2");
//        portal1.setName("Portal3");
//
//        portal1.setLatitude(43.13830627889566);
//        portal1.setLongitude(6.024209619087428);
//
//        portal2.setLatitude(43.134850560876025);
//        portal2.setLongitude(6.0137197690145126);
//
//        portal3.setLatitude(43.13783146512867);
//        portal3.setLongitude(6.025353);
//
//
//
//        CPortal p1 = wr.path("portals/4").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p2 = wr.path("portals/5").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p3 = wr.path("portals/6").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//
//
//        CKey key = new CKey();
//        key.setPortal(p1);
//        CKey key1 = new CKey();
//        key1.setPortal(p2);
//        CKey key2 = new CKey();
//        key2.setPortal(p3);
//
//
//
//
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key2);
//
//        p1.setKey(key);
//        p1.setName("Portal1");
//        p2.setKey(key1);
//        p2.setName("Portal2");
//        p3.setKey(key2);
//        p3.setName("Portal3");

//        portal1.setKey(webResource.path("items/37").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal2.setKey(webResource.path("items/38").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal3.setKey(webResource.path("items/39").type(MediaType.APPLICATION_JSON).get(CKey.class));

//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p1);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p2);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p3);


    }


    //////// getters / setters ////////


    public void setPortalNb(int portalNb) { this.portalNb = portalNb; }

    public void setNbItemsNb(int nbItemsNb) { this.nbItemsNb = nbItemsNb; }

    public boolean isGameLaunched() { return gameLaunched; }


}
