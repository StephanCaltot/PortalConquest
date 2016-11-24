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
        @NamedQuery(name = CField.FIELD_ALL, query = "select field from CField field"),
        @NamedQuery(name = CField.FIELD_BY_ID, query = "select field from CField field where field.id = :Pid"),
        @NamedQuery(name = CField.FIELD_BY_TEAM, query = "select field from CField field where field.team.id = :Pid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CField.class)
public class CField implements Serializable {
    @TableGenerator(name = "fieldGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="fieldGenerator")
    @Column(name="Field_id")
    private int id;
    private double surface;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "firstPortal")
    private CPortal firstPortal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "secondPortal")
    private CPortal secondPortal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "thirdPortal")
    private CPortal thirdPortal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team")
    private CTeam team;


    public static final String FIELD_ALL = "CField.findFieldAll";
    public static final String FIELD_BY_ID = "CField.findFieldById";
    public static final String FIELD_BY_TEAM = "CField.findFieldByTeam";


    public CField() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CTeam getTeam() {
        return team;
    }

    public void setTeam(CTeam team) {
        this.team = team;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public CPortal getFirstPortal() {
        return firstPortal;
    }

    public void setFirstPortal(CPortal firstPortal) {
        this.firstPortal = firstPortal;
    }

    public CPortal getSecondPortal() {
        return secondPortal;
    }

    public void setSecondPortal(CPortal secondPortal) {
        this.secondPortal = secondPortal;
    }

    public CPortal getThirdPortal() {
        return thirdPortal;
    }

    public void setThirdPortal(CPortal thirdPortal) {
        this.thirdPortal = thirdPortal;
    }

    public List<CPortal> getAllPortals (){
        List<CPortal> portals = new ArrayList<CPortal>();
        portals.add(firstPortal);
        portals.add(secondPortal);
        portals.add(thirdPortal);
        return portals;
    }

    @Override
    public String toString() {
        return "CField :" +
                "id=" + id +
                ", surface=" + surface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CField cField = (CField) o;

        return id == cField.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
