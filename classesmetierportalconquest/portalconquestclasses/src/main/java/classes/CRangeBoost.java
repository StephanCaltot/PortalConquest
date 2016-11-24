package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@DiscriminatorValue(value = "rangeboost")
public class CRangeBoost extends AUpgrade {
    private int rangeValue;



    public CRangeBoost() {}

    public int getRangeValue() {
        return rangeValue;
    }

    public void setRangeValue(int rangeValue) {
        this.rangeValue = rangeValue;
    }

    @Override
    public String toString() {
        return "CRangeBoost " +
                "rangeValue=" + rangeValue;
    }
}
