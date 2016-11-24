package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CPortal.PORTAL_BY_ALL, query = "select portal from CPortal portal"),
        @NamedQuery(name = CPortal.PORTAL_BY_ID, query = "select portal from CPortal portal where portal.id = :Pid"),
        @NamedQuery(name = CPortal.PORTAL_BY_TEAM, query = "select portal from CPortal portal where portal.team.id = :Pid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CPortal.class)
public class CPortal implements Serializable {
    @TableGenerator(name = "portalGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="portalGenerator")
    @Column(name="Portal_id")
    private int id;
    private int level;
    private String name;
    private double latitude;
    private double longitude;

    @OneToMany(cascade = CascadeType.REFRESH)
    private List<AUpgrade> upgradesList = new ArrayList<AUpgrade>(4);

    @OneToMany(cascade = CascadeType.REFRESH)
    private List<CSlot> slots = new ArrayList<CSlot>(8);

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Team")
    private CTeam team;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy="thirdPortal")
    @JoinColumn(name = "Field")
    private List<CField> fields = new ArrayList<CField>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy="secondPortal")
    @JoinColumn(name = "Link")
    private List<CLink> links = new ArrayList<CLink>();

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Key")
    private CKey key;



    public static final String PORTAL_BY_ALL = "CPortal.findPortalAll";
    public static final String PORTAL_BY_ID = "CPortal.findPortalById";
    public static final String PORTAL_BY_TEAM = "CPortal.findPortalByTeam";


    public CPortal() {}

    public CKey getKey() {
        return key;
    }

    public void setKey(CKey key) {
        this.key = key;
    }

    public List<CLink> getLinks() {
        return links;
    }

    public void setLinks(List<CLink> links) {
        this.links = links;
    }

    public void addLink(CLink link) {links.add(link);}

    public CTeam getTeam() {
        return team;
    }

    public void setTeam(CTeam team) {
        this.team = team;
    }

    public List<CField> getFields() {
        return fields;
    }

    public void setFields(List<CField> fields) {
        this.fields = fields;
    }

    public void addField(CField field) {fields.add(field);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<AUpgrade> getUpgradesList() {
        return upgradesList;
    }

    public void setUpgradesList(List<AUpgrade> upgradesList) {
        this.upgradesList = upgradesList;
    }

    public void addUpgrade(AUpgrade aUpgrade) {
        upgradesList.add(aUpgrade);
    }

    public List<CSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<CSlot> slots) {
        this.slots = slots;
    }

    public void addSlot(CSlot slot) {
        slots.add(slot);
    }

    public CSlot getSlotByPosition (int position){
        CSlot slotSelected = new CSlot();
        for (CSlot slot : slots){
            if (slot.getPosition() == position)
                slotSelected = slot;
        }
        return slotSelected;
    }


    @Override
    public String toString() {
        return "CPortal " +
                "id=" + id +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CPortal cPortal = (CPortal) o;

        return id == cPortal.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
