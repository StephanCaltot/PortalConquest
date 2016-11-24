package fr.univtln.m1dapm.groupeb.portalconquest.utilities;

/**
 * Created by clemzux on 11/05/16.
 * Cette classe sert juste a stocker des coordonnées sous forme de Double (latitude, longitude).
 */
public class CCoords {

    // attributes

    double lat, lng;

    // builder

    /**
     * Constructeur de la classe CCords, prend en parametre une latitude et une longitude.
     * @param pLat
     * @param pLng
     */
    public CCoords(double pLat, double pLng){
        lat = pLat;
        lng = pLng;
    }

    // getters / setters

    /**
     * Cette méthode retourne une latitude sous forme de Double.
     * @return
     */
    public double getLat() { return lat; }

    /**
     * Cette méthode retourne une longitude sous forme de Double
     * @return
     */
    public double getLng() { return lng; }
}
