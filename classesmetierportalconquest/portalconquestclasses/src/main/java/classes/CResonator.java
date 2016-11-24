package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = CResonator.BY_LEVEL, query = "select objet from CResonator objet where objet.level = :Plevel"),
})
@DiscriminatorValue(value = "resonator")
public class CResonator extends AItem {
    private int level;

    public static final String BY_LEVEL = "CResonator.findObjetByLevel";


    public CResonator() {}

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "CResonator " +
                "level=" + level;
    }
}
