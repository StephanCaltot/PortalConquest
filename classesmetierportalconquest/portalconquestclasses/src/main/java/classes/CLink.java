package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Screetts on 10/05/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CLink.BY_ALL, query = "select link from CLink link"),
        @NamedQuery(name = CLink.BY_ID, query = "select link from CLink link where link.id = :Plinkid"),
        @NamedQuery(name = CLink.BY_TEAM, query = "select link from CLink link where link.firstPortal.team.id = :Pteamid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CLink.class)
public class CLink implements Serializable{
    @TableGenerator(name = "linkGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="linkGenerator")
    @Column(name = "Link_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "firstPortal")
    private CPortal firstPortal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "secondPortal")
    private CPortal secondPortal;


    public static final String BY_TEAM = "CLink.findByTeam";
    public static final String BY_ID = "CLink.findById";
    public static final String BY_ALL = "CLink.findByAll";




    public CLink() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<CPortal> getAllPortals (){
        List<CPortal> portals = new ArrayList<CPortal>();
        if(firstPortal != null)
            portals.add(firstPortal);
        if(secondPortal != null)
            portals.add(secondPortal);
        return portals;
    }

    @Override
    public String toString() {
        return "CLink " +
                "id=" + id +
                ", firstPortal=" + firstPortal +
                ", secondPortal=" + secondPortal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CLink cLink = (CLink) o;

        return id == cLink.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

}
