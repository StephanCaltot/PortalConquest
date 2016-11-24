package classes;

import java.io.Serializable;

/**
 * Created by Screetts on 03/05/2016.
 */

public class CInventoryId implements Serializable {

    private int player;
    private int item;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CInventoryId that = (CInventoryId) o;

        if (player != that.player) return false;
        return item == that.item;}


    @Override
    public int hashCode() {
        int result = player;
        result = 31 * result + item;
        return result;}
}
