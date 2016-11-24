package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@DiscriminatorValue(value = "turret")
public class CTurret extends AUpgrade {
    private int attackValue;
    private int range;


    public CTurret() {}

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "CTurret " +
                "attackValue=" + attackValue +
                ", range=" + range;
    }
}
