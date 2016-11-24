package messages;

import classes.CInventory;
import classes.CMapItem;

import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CMapItemServices.sCrudMapItems;

/**
 * Created by Informatique on 09/06/2016.
 */

/**
 * Action's Entity to Droping Items
 */
public class CDropItem {
    private CInventory mInventory;

    public CInventory getInventory() {
        return mInventory;
    }

    public void setInventory(CInventory mInventory) {
        this.mInventory = mInventory;
    }

    /**
     * Put the player's item on the map as MapItem .
     */
    public void dropItem(){

        if(mInventory.getQuantity() > 1)
            mInventory.setQuantity(mInventory.getQuantity() - 1);
        else
            mInventory.setQuantity(0);

        CMapItem mMap = new CMapItem();

        mMap.setItem(mInventory.getItem());
        mMap.setGenerated(false);
        mMap.setLatitude(mInventory.getPlayer().getLatitude());
        mMap.setLongitude(mInventory.getPlayer().getLongitude());

        sCrudMapItems.create(mMap);
        sCrudInventory.update(mInventory);
    }
}
