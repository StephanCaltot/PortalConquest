package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/**
 * Created by Screetts on 11/05/2016.
 */

@Entity
@DiscriminatorValue(value = "key")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CKey.class)
public class CKey extends AItem {

    @OneToOne(fetch=FetchType.EAGER, mappedBy = "key",cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Portal")
    private CPortal portal;


    public static final String BY_ID = "CKey.findById";


    public CKey() {}

    public CPortal getPortal() {
        return portal;
    }

    public void setPortal(CPortal portal) {
        this.portal = portal;
    }

    @Override
    public String toString() {
        return "CKey " +
                "portal=" + portal;
    }
}
