package websocketmessages.serverresponses;

import classes.*;
import jsoncoder.AJSONCoder;
import websocketmessages.androidrequests.EWebsocketRequestType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ltonnet637 on 08/06/16.
 */

/**
 * Class of websocket response messages sent to WebSocket Server .
 */
public class CWebsocketResponse {
    public final static class CWebsocketResponseCoder extends AJSONCoder<CWebsocketResponse> {}

    List<CField> mFields;
    List<CLink> mLinks;
    List<CPortal> mPortals;
    List<CMapItem> mMapItems;

    Set<EWebsocketRequestType> mResponseType;


    public CWebsocketResponse() {
        mFields = new ArrayList<CField>();
        mLinks = new ArrayList<CLink>();
        mPortals = new ArrayList<CPortal>();
        mMapItems = new ArrayList<CMapItem>();

        mResponseType = new HashSet<EWebsocketRequestType>();
    }


    public CWebsocketResponse(Set<EWebsocketRequestType> pResponseType) {
        mFields = new ArrayList<CField>();
        mLinks = new ArrayList<CLink>();
        mPortals = new ArrayList<CPortal>();
        mMapItems = new ArrayList<CMapItem>();

        mResponseType = pResponseType;
    }


    public List<CField> getFields() {
        return mFields;
    }

    public void setFields(List<CField> mFields) {
        this.mFields = mFields;
        mResponseType.add(EWebsocketRequestType.UPDATED_FIELDS);
    }

    public List<CLink> getLinks() {
        return mLinks;
    }

    public void setLinks(List<CLink> mLinks) {
        this.mLinks = mLinks;
        mResponseType.add(EWebsocketRequestType.UPDATED_LINKS);
    }

    public List<CPortal> getPortals() {
        return mPortals;
    }

    public void setPortals(List<CPortal> mPortals) {
        this.mPortals = mPortals;
        mResponseType.add(EWebsocketRequestType.UPDATED_PORTALS);
    }

    public List<CMapItem> getMapItems() {
        return mMapItems;
    }

    public void setMapItems(List<CMapItem> pMapItems) { mMapItems = pMapItems; }

    public Set<EWebsocketRequestType> getResponseType() {
        return mResponseType;
    }

    public void addResponseType(EWebsocketRequestType pResponseType) {
        mResponseType.add(pResponseType);
    }
}
