package messages;

import classes.CInventory;
import classes.CMapItem;
import classes.CPlayer;
import portalconquest.utils.CDistanceCalculator;

import static portalconquest.services.CInventoryServices.getInventory;
import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CMapItemServices.sCrudMapItems;

/**
 * Created by sorvats on 08/06/2016.
 */

/**
 * Action's Entity to pick item from map .
 */
public class CGetMapItemAction {
    private CPlayer mPlayer;
    private CMapItem mMapItem;

    public CPlayer getPlayer() {
        return mPlayer;
    }

    public CMapItem getMapItem() {
        return mMapItem;
    }

    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public void setMapItem(CMapItem mMapItem) {
        this.mMapItem = mMapItem;
    }


    /**
     * Update the player's inventory with one Mapitem took (and delete) from map.
     */
    public void pickUpMapItem(){
//        if(CDistanceCalculator.getINSTANCE().isMapItemInPlayerRange(mPlayer, mMapItem)) {
            CInventory mInventory;

            try {
                mInventory = getInventory(mPlayer.getId(), mMapItem.getItem().getId());
                mInventory.setQuantity(mInventory.getQuantity() + 1);
                mInventory.setPlayer(mPlayer);
                sCrudInventory.update(mInventory);
            } catch (Exception e) {
                mInventory = new CInventory();
                mInventory.setItem(mMapItem.getItem());
                mInventory.setQuantity(1);
                mInventory.setPlayer(mPlayer);
                sCrudInventory.create(mInventory);
            }
            sCrudMapItems.delete(CMapItem.class, this.getMapItem().getId());
        }
//    }
}
