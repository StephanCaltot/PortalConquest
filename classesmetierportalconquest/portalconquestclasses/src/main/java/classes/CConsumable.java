package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CConsumable.BY_LEVEL, query = "select objet from CConsumable objet where objet.level = :Plevel"),
})
@DiscriminatorValue(value = "consumable")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = AItem.class)
public class CConsumable extends AItem {
    private int level;
    private int energyValue;

    public static final String BY_LEVEL = "CConsumable.findObjetByLevel";


    public CConsumable() {}

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(int energyValue) {
        this.energyValue = energyValue;
    }

    @Override
    public String toString() {
        return "CConsumable :" +
                "level=" + level +
                ", energyValue=" + energyValue;
    }
}
