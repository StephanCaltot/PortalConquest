package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CSlot.CSLOT_BY_ALL, query = "select slot from CSlot slot"),
        @NamedQuery(name = CSlot.CSLOT_BY_ID, query = "select slot from CSlot slot where slot.id = :Pid"),
        @NamedQuery(name = CSlot.CSLOT_BY_PLAYER, query = "select slot from CSlot slot where slot.player.id = :Pplayerid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CSlot.class)
public class CSlot implements Serializable {
    @TableGenerator(name = "slotGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="slotGenerator")
    @Column(name = "Slot_id")
    private int id;
    private int energy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "item_id")
    private CResonator resonator;
    private int position;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Player")
    private CPlayer player;


    public static final String CSLOT_BY_ALL = "CSlot.findSlotAll";
    public static final String CSLOT_BY_ID = "CSlot.findSlotById";
    public static final String CSLOT_BY_PLAYER = "CSlot.findSlotByPlayer";


    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CSlot() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CResonator getResonator() {
        return resonator;
    }

    public void setResonator(CResonator resonator) {
        this.resonator = resonator;
    }

    public CPlayer getPlayer() {
        return this.player;
    }

    public void setPlayer(CPlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "CSlot " +
                "id=" + id +
                ", energy=" + energy +
                ", resonator=" + resonator +
                ", position=" + position +
                ", player=" + player;
    }
}
