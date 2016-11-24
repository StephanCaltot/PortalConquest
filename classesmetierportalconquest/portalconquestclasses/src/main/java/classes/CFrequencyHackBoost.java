package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@DiscriminatorValue(value = "frequencyhackboost")
public class CFrequencyHackBoost extends AUpgrade {
    private int boostValue;



    public CFrequencyHackBoost() {}

    public int getBoostValue() {
        return boostValue;
    }

    public void setBoostValue(int boostValue) {
        this.boostValue = boostValue;
    }

    @Override
    public String toString() {
        return "CFrequencyHackBoost " +
                "boostValue=" + boostValue;
    }
}
