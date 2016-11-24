package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Screetts on 02/05/2016.
 */
@NamedQueries({
        @NamedQuery(name = CInventory.BY_PLAYER, query = "select inventory from CInventory inventory where inventory.player.id = :Pplayerid"),
        @NamedQuery(name = CInventory.BY_ID, query = "select inventory from CInventory inventory where inventory.player.id = :Pplayerid and inventory.item.id = :Pitemid")
})
@Entity
@IdClass(CInventoryId.class)
@Table(name = "inventory")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CInventory.class)
public class CInventory implements Serializable {

    @Column(name = "quantity")
    private int quantity;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id")
    private CPlayer player;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id")
    protected AItem item;


    public static final String BY_PLAYER = "CInventory.findByPlayer";
    public static final String BY_ID = "CInventory.findById";
    public static final String DELETE = "CInventory.delete";


    public CInventory() {}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CPlayer getPlayer() {
        return player;
    }

    public void setPlayer(CPlayer player) {
        this.player = player;
    }

    public AItem getItem() {
        return item;
    }

    public void setItem(AItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "CInventory " +
                "quantity=" + quantity +
                ", player=" + player +
                ", item=" + item;
    }
}
