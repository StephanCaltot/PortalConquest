package fr.univtln.m1dapm.groupeb.portalconquest.utilities;

/**
 * Created by clemzux on 05/05/16.
 * Cette ne s'instancie autant de fois qu'il y a de calques dans la vue principale.
 */
public class CLayer {


    //////// attributes ////////


    private String layerName;
    private Boolean players, portals, items, links, areas, visible;


    //////// constructors ////////


    /**
     * Constructeur de CLayer, prend en parametre le nom du calque.
     * Initialise la visibilité de toutes les entitées a false.
     * @param pName
     */
    public CLayer (String pName) {

        layerName = pName;

        players = false;
        portals = false;
        items = false;
        links = false;
        areas = false;

        visible = false;
    }


    //////// methods ////////


    @Override
    public String toString() {
        return "CLayer{" +
                "layerName='" + layerName + '\'' +
                ", players=" + players +
                ", portals=" + portals +
                ", items=" + items +
                ", links=" + links +
                ", areas=" + areas +
                ", visible=" + visible +
                '}';
    }

    public String getLayerName(){ return layerName; }

    public Boolean getPlayers() { return players; }

    public void setPlayers(Boolean players) { this.players = players; }

    public Boolean getPortals() { return portals; }

    public void setPortals(Boolean portals) { this.portals = portals; }

    public Boolean getObjects() { return items; }

    public void setObjects(Boolean objects) { this.items = objects; }

    public Boolean getLinks() { return links; }

    public void setLinks(Boolean links) { this.links = links; }

    public Boolean getAreas() { return areas; }

    public void setAreas(Boolean areas) { this.areas = areas; }

    public Boolean getVisible() { return visible; }

    public void setVisible(Boolean visible) { this.visible = visible; }
}
