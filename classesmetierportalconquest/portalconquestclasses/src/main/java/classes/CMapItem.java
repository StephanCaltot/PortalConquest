package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Screetts on 11/05/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CMapItem.MAP_ITEM_BY_ALL, query = "select item from CMapItem item"),
        @NamedQuery(name = CMapItem.MAP_ITEM_BY_ID, query = "select item from CMapItem item where item.id = :Pid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CMapItem.class)
public class CMapItem implements Serializable {
    @TableGenerator(name = "mapGenerator", allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "mapGenerator")
    @Column(name = "map_item_id")
    private int id;
    private boolean generated;
    private double latitude;
    private double longitude;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "item_id")
    private AItem item;


    public static final String MAP_ITEM_BY_ALL = "CMapItem.findMapItemAll";
    public static final String MAP_ITEM_BY_ID = "CMapItem.findMapItemById";


    public CMapItem() {
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AItem getItem() {
        return item;
    }

    public void setItem(AItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "CMapItem " +
                "id=" + id +
                ", generated=" + generated +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", item=" + item;
    }
}


