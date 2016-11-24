package classes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by Screetts on 28/04/2016.
 */
@NamedQueries({
        @NamedQuery(name = CWeapon.BY_LEVEL, query = "select objet from CWeapon objet where objet.level = :Plevel"),
})
@Entity
@DiscriminatorValue(value = "weapon")
public class CWeapon extends AItem {
    private int level;
    private int attackValue;


    public static final String BY_LEVEL = "CWeapon.findObjetByLevel";


    public CWeapon() {}

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    @Override
    public String toString() {
        return "CWeapon " +
                "level=" + level +
                ", attackValue=" + attackValue;
    }
}
