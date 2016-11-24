package websocketmessages.androidrequests;

import classes.CField;
import classes.CLink;
import classes.CMapItem;
import classes.CPortal;
import jsoncoder.AJSONCoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ltonnet637 on 08/06/16.
 */
public class CWebsocketRequest implements Serializable {
    public final static class CWebsocketRequestCoder extends AJSONCoder<CWebsocketRequest> {}

    List<CField> mFields;
    List<CLink> mLinks;
    List<CPortal> mPortals;
    List<CMapItem> mMapItems;

    private Set<EWebsocketRequestType> mRequestType;


    public CWebsocketRequest() {
        mFields = new ArrayList<CField>();
        mLinks = new ArrayList<CLink>();
        mPortals = new ArrayList<CPortal>();
        mMapItems = new ArrayList<CMapItem>();

        mRequestType = new HashSet<EWebsocketRequestType>();
    }


    public List<CField> getFields() {
        return mFields;
    }

    public void setFields(List<CField> mFields) {
        this.mFields = mFields;
        mRequestType.add(EWebsocketRequestType.UPDATED_FIELDS);
    }

    public List<CLink> getLinks() {
        return mLinks;
    }

    public void setLinks(List<CLink> mLinks) {
        this.mLinks = mLinks;
        mRequestType.add(EWebsocketRequestType.UPDATED_LINKS);
    }

    public List<CPortal> getPortals() {
        return mPortals;
    }

    public void setPortals(List<CPortal> mPortals) {
        this.mPortals = mPortals;
        mRequestType.add(EWebsocketRequestType.UPDATED_PORTALS);
    }

    public List<CMapItem> getMapItems() {
        return mMapItems;
    }

    public void setMapItems(List<CMapItem> mMapItems) {
        this.mMapItems = mMapItems;
        mRequestType.add(EWebsocketRequestType.UPDATED_MAP_ITEMS);
    }

    public Set<EWebsocketRequestType> getRequestType() {
        return mRequestType;
    }

    public void setRequestType(Set<EWebsocketRequestType> mRequestType) {
        this.mRequestType = mRequestType;
    }

    public void addRequestType(EWebsocketRequestType pType) {
        mRequestType.add(pType);
    }
}
