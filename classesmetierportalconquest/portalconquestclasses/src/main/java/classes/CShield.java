package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@DiscriminatorValue(value = "shield")
public class CShield extends AUpgrade {
    private int defenseValue;



    public CShield() {}

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    @Override
    public String toString() {
        return "CShield " +
                "defenseValue=" + defenseValue;
    }
}
