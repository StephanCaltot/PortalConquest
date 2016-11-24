package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = AUpgrade.UPGRADE_BY_ALL, query = "select up from AUpgrade up"),
        @NamedQuery(name = AUpgrade.UPGRADE_BY_ID, query = "select up from AUpgrade up where up.id = :Pid")
})
@DiscriminatorValue(value = "upgrade")
public abstract class AUpgrade extends AItem {
    private int rarity;


    public static final String UPGRADE_BY_ALL = "AUpgrade.findUpgradeByAll";
    public static final String UPGRADE_BY_ID = "AUpgrade.findUpgradeById";


    public AUpgrade() {}

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
