package portalconquest.test;

import classes.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import messages.*;

import javax.ws.rs.core.MediaType;


/**
 * Created by Screetts on 01/05/2016.
 */

public class CTest {
    public static void main(String[] args) {
        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(JacksonJsonProvider.class);
        Client c = Client.create(cc);
        WebResource webResource = c.resource("http://localhost:9998/");


//        CPlayer pl2 = webResource.path("players/5").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
        CPlayer pl = webResource.path("players/2").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
        CResonator r1 = webResource.path("items/32").type(MediaType.APPLICATION_JSON).get(CResonator.class);

        CWeapon w1 = webResource.path("items/27").type(MediaType.APPLICATION_JSON).get(CWeapon.class);

        CKey key = webResource.path("items/2").type(MediaType.APPLICATION_JSON).get(CKey.class);
//
        CPortal p9 = webResource.path("portals/5").type(MediaType.APPLICATION_JSON).get(CPortal.class);

//        CTeam team = webResource.path("teams/1").type(MediaType.APPLICATION_JSON).get(CTeam.class);
//        team.setScore(3320);

//        CPortal p2 = webResource.path("portals/8").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p3 = webResource.path("portals/9").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//
//        CInventory inventory = webResource.path("inventories/2/6").type(MediaType.APPLICATION_JSON).get(CInventory.class);
//        CInventory inventory = new CInventory();
//        inventory.setItem(webResource.path("items/52").type(MediaType.APPLICATION_JSON).get(CVirus.class));
//        inventory.setPlayer(pl);
//        inventory.setQuantity(8);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory);


//        pl.setEnergy(200);
//        pl.setLevel(7);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl);

//        CInfectPortalAction cInfectPortalAction = new CInfectPortalAction();
//        cInfectPortalAction.setPortal(p9);
//        cInfectPortalAction.setInventory(inventory);

//        webResource.path("portals/virus").type(MediaType.APPLICATION_JSON).post(cInfectPortalAction);
//        System.out.println(key);
//        webResource.path("links/38").type(MediaType.APPLICATION_JSON).delete();
//

//        System.out.println(p1.getLinks().size() + " & " + p1.getFields().size());
//        System.out.println(p2.getLinks().size() + " & " + p2.getFields().size());
//        System.out.println(p3.getLinks().size() + " & " + p3.getFields().size());


//        CWebsocketResponse cWebsocketResponse = new CWebsocketResponse();
//        cWebsocketResponse.setFields(getFieldAll());
//        cWebsocketResponse.setLinks(getLinkAll());
//        cWebsocketResponse.setPortals(getPortalAll());
//        broadcastMessageToClients(cWebsocketResponse);


//        CDropItem cDropItem = new CDropItem();
//        cDropItem.setInventory(webResource.path("inventories/5/27").type(MediaType.APPLICATION_JSON).get(CInventory.class));
//        EntityTransaction transac = em.getTransaction();
//        transac.begin();
//        cDropItem.dropItem();
//        transac.commit();

        CSetLink cSetLink = new CSetLink();
        cSetLink.setPortal(p9);
        cSetLink.setKey(key);
        webResource.path("links").type(MediaType.APPLICATION_JSON).post(cSetLink);
//        System.out.println(portals2);
//        CMapItem mapItem = new CMapItem();
//        mapItem.setGenerated(false);
//        mapItem.setItem(key);
//        mapItem.setLatitude(43.13648);
//        mapItem.setLongitude(6.01588);
//        webResource.path("mapitems").type(MediaType.APPLICATION_JSON).post(mapItem);
//
//        CMapItem mapItem = webResource.path("mapitems/1").type(MediaType.APPLICATION_JSON).get(CMapItem.class);
//        CGetMapItemAction cGetMapItemAction = new CGetMapItemAction();
//        cGetMapItemAction.setMapItem(mapItem);
//        cGetMapItemAction.setPlayer(pl);
//
//        webResource.path("mapitems").type(MediaType.APPLICATION_JSON).put(cGetMapItemAction);


        CHackPortalAction cHackPortalAction = new CHackPortalAction();
        cHackPortalAction.setPortal(p9);
        cHackPortalAction.setPlayer(pl);
//        webResource.path("portals/hack").type(MediaType.APPLICATION_JSON).post(cHackPortalAction);


//        CSetLink se = new CSetLink();
//        se.setPortal(p9);
//        se.setKey(key);
//
//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(se);

//        System.out.println(p13.);
//        p13.addField());
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p13);


//        CSlot s7 = webResource.path("slots/6").type(MediaType.APPLICATION_JSON).get(CSlot.class);
//        CSlot s2 = webResource.path("slots/12").type(MediaType.APPLICATION_JSON).get(CSlot.class);



//        System.out.println(p2.getSlots());


//        CVirus virus = webResource.path("items/54").type(MediaType.APPLICATION_JSON).get(CVirus.class);


        CInventory i = new CInventory();
        i.setPlayer(pl);
        i.setQuantity(5);
        i.setItem(key);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(i);

//        CMapItem mapItem = new CMapItem();
//
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(i);

//        CAttackAction cAttackAction = new CAttackAction();
//        cAttackAction.setPlayer(pl);
//        cAttackAction.setPortal(p9);
//        cAttackAction.setInventoryOfWeapon(webResource.path("inventories/5/27").type(MediaType.APPLICATION_JSON).get(CInventory.class));
//
//        webResource.path("portals/attack").type(MediaType.APPLICATION_JSON).post(cAttackAction);

//        p2.setSlots(new ArrayList<CSlot>());
//
//        CSlot slot =new CSlot();
//        CSlot slot1 =new CSlot();
//        CSlot slot2=new CSlot();
//        CSlot slot3 =new CSlot();
//        CSlot slot4 =new CSlot();
//        CSlot slot5 =new CSlot();
//
//        slot.setEnergy(100);
//        slot1.setEnergy(50);
//        slot2.setEnergy(50);
//        slot3.setEnergy(50);
//        slot4.setEnergy(50);
//        slot5.setEnergy(50);
//
//        p2.addSlot(slot);
//        p2.addSlot(slot1);
//        p2.addSlot(slot2);
//        p2.addSlot(slot3);
//        p2.addSlot(slot4);
//        p2.addSlot(slot5);
//        p2.addSlot(s7);
//        p2.addSlot(s2);
//
//
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(p2);



//        System.out.println(pl.getLevel());
//        System.out.println(p2.getSlots());
//        CSlot slot = new CSlot();
//        CSlot slot1 = new CSlot();
//
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).post(slot);
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).post(slot1);

//        CSetUpgradeOnPortal cSetUpgradeOnPortal = new CSetUpgradeOnPortal();
//
//        cSetUpgradeOnPortal.setUpgrade(r1);
//        cSetUpgradeOnPortal.setPlayer(pl2);
//        cSetUpgradeOnPortal.setPortal(p2);
//
//        webResource.path("upgrades").type(MediaType.APPLICATION_JSON).post(cSetUpgradeOnPortal);

        CSetResonatorOnPortal cSetResonatorOnPortal = new CSetResonatorOnPortal();
        cSetResonatorOnPortal.setResonator(r1);
        cSetResonatorOnPortal.setPlayer(pl);
        cSetResonatorOnPortal.setPortal(p9);
        cSetResonatorOnPortal.setSlotPosition(0);
//
//        webResource.path("resonators").type(MediaType.APPLICATION_JSON).post(cSetResonatorOnPortal);





//
//        CSlot s1 = new CSlot();
//        CSlot s2 = new CSlot();
//
//        p2.addSlot(s1);
//
//        s1.setPlayer(pl);
//        s1.setResonator((CResonator) r1);
//
//        s2.setPlayer(pl2);
//        s2.setResonator((CResonator) r19);
//
//
//        p2.setSlots(new ArrayList<CSlot>());
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).post(s1);
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).post(s2);

//        p2.addSlot(new CSlot());
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).put(p2.getSlots().get(0));
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(p2);



//        CInventory inventory2 = new CInventory();
//        inventory2.setQuantity(50);
//        inventory2.setItem(w1);
//        inventory2.setPlayer(pl);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory2);
//
//
//        CInventory inventory2 = new CInventory();
//        inventory2.setQuantity(1);
//        inventory2.setItem(r19);
//        inventory2.setPlayer(pl2);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory2);

//        webResource.path("slots").type(MediaType.APPLICATION_JSON).post(new CSlot());
//        CKey k = webResource.path("items/37").type(MediaType.APPLICATION_JSON).get(CKey.class);
//



//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(se);
//        webResource.path("links/156").delete();




//        CField field = new CField();
//        field.setSurface(45);
//        webResource.path("fields").type(MediaType.APPLICATION_JSON).post(field);

//        CPortal p1 = webResource.path("portals/1").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p3 = webResource.path("portals/3").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//
//
//        CLink link = new CLink();
//        link.setFirstPortal(p1);
//        link.setSecondPortal(p2);
//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(link);
//
//        p2.addSlot(new CSlot());
//        CPortal portal = new CPortal();
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p2);

//        CSetResonatorOnPortal setResonator = new CSetResonatorOnPortal();
//        setResonator.setPlayer(pl);
//        setResonator.setPortal(p2);
//        setResonator.setSlot(p2.getSlots().get(0));
//        setResonator.setResonator(r1);
//        webResource.path("resonators").type(MediaType.APPLICATION_JSON).post(setResonator);
//
//        //////// teams ////////

//        CTeam blueTeam = new CTeam();
//        blueTeam.setId(1);
//        blueTeam.setName("BlueTeam");
//        CTeam redTeam = new CTeam();
//        redTeam.setId(2);
//        redTeam.setName("RedTeam");
//        CTeam neutralTeam = new CTeam();
//        neutralTeam.setId(3);
//        neutralTeam.setName("NeutralTeam");
//
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(blueTeam);
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(redTeam);
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(neutralTeam);
////
////         joueurs ////////
//
//        CPlayer pl1 = new CPlayer();
//        CPlayer pl2 = new CPlayer();
//        CPlayer pl3 = new CPlayer();
//        CPlayer pl4 = new CPlayer();
//        CPlayer pl5 = new CPlayer();
//        CPlayer pl6 = new CPlayer();
//
//        pl1.setId(1);
//        pl2.setId(2);
//        pl3.setId(3);
//        pl4.setId(4);
//        pl5.setId(5);
//        pl6.setId(6);
//
//        pl1.setNickname("Stephane");
//        pl2.setNickname("Stephan");
//        pl3.setNickname("Jerome");
//        pl4.setNickname("Loïs");
//        pl5.setNickname("Tomy");
//        pl6.setNickname("Clement");
//
//        pl1.setEmail("stephane.grasselli@gmail.com");
//        pl2.setEmail("caltots@gmail.com");
//        pl3.setEmail("jerome.samson07@gmail.com");
//        pl4.setEmail("tonnetlois@gmail.com");
//        pl5.setEmail("azertp053@gmail.com");
//        pl6.setEmail("farge.clement@gmail.com");
//
//        pl1.setTeam(blueTeam);
//        pl2.setTeam(blueTeam);
//        pl3.setTeam(blueTeam);
//        pl4.setTeam(redTeam);
//        pl5.setTeam(redTeam);
//        pl6.setTeam(redTeam);
//
//        pl1.setLatitude(43.13744);
//        pl1.setLongitude(6.01981);
//
//        pl2.setLatitude(43.13644);
//        pl2.setLongitude(6.01581);
//
//        pl3.setLatitude(43.13744);
//        pl3.setLongitude(6.01781);
//
//        pl4.setLatitude(43.13544);
//        pl4.setLongitude(6.01781);
//
//        pl5.setLatitude(43.13622353083457);
//        pl5.setLongitude(6.01803052313852);
//
//        pl6.setLatitude(43.13644);
//        pl6.setLongitude(6.01981);
//
//        pl1.setLevel(1);
//        pl2.setLevel(2);
//        pl3.setLevel(4);
//        pl4.setLevel(1);
//        pl5.setLevel(5);
//        pl6.setLevel(3);
//
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl1);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl2);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl3);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl4);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl5);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl6);



//        CPortal pp1 = new CPortal();
//        pp1.setId(11);
//        pp1.setName("Portal");
//        pp1.setLatitude(43.13644);
//        pp1.setLongitude(6.01881);
//        pp1.setTeam(neutralTeam);
//
//        CPortal pp2 = new CPortal();
//        pp2.setId(12);
//        pp2.setName("caVa");
//        pp2.setLatitude(43.13744);
//        pp2.setLongitude(6.01981);
//        pp2.setTeam(neutralTeam);
//
//        CPortal pp3 = new CPortal();
//        pp3.setId(13);
//        pp3.setName("etToi");
//        pp3.setLatitude(43.13544);
//        pp3.setLongitude(6.01781);
//        pp3.setTeam(neutralTeam);
////
//        CKey k = webResource.path("items/37").type(MediaType.APPLICATION_JSON).get(CKey.class);
//        CKey k2 = webResource.path("items/38").type(MediaType.APPLICATION_JSON).get(CKey.class);
//        CKey k3 = webResource.path("items/39").type(MediaType.APPLICATION_JSON).get(CKey.class);
//
//        k.setPortal(pp1);
//        k2.setPortal(pp2);
//        k3.setPortal(pp3);
//
//        pp1.setKey(k);
//        pp2.setKey(k2);
//        pp3.setKey(k3);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).put(k);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).put(k2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).put(k3);
//
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(pp1);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(pp2);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(pp3);
//
        // items ////////
//
//         Consumable
//        CConsumable cConsumable1 = new CConsumable();
//        cConsumable1.setLevel(1);
//        cConsumable1.setEnergyValue(10);
//
//
//        CConsumable cConsumable2 = new CConsumable();
//        cConsumable2.setLevel(2);
//        cConsumable2.setEnergyValue(20);
//
//
//        CConsumable cConsumable3 = new CConsumable();
//        cConsumable3.setLevel(3);
//        cConsumable3.setEnergyValue(30);
//
//
//        CConsumable cConsumable4 = new CConsumable();
//        cConsumable4.setLevel(4);
//        cConsumable4.setEnergyValue(40);
//
//
//        CConsumable cConsumable5 = new CConsumable();
//        cConsumable5.setLevel(5);
//        cConsumable5.setEnergyValue(50);
//
//
//        CConsumable cConsumable6 = new CConsumable();
//        cConsumable6.setLevel(6);
//        cConsumable6.setEnergyValue(60);
//
//
//        CConsumable cConsumable7 = new CConsumable();
//        cConsumable7.setLevel(7);
//        cConsumable7.setEnergyValue(70);
//
//
//        CConsumable cConsumable8 = new CConsumable();
//        cConsumable8.setLevel(8);
//        cConsumable8.setEnergyValue(100);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable3);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable4);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable5);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable6);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable7);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable8);
//

//         weapons

//        CWeapon cWeapon1 = new CWeapon();
//        cWeapon1.setLevel(1);
//        cWeapon1.setAttackValue(10);
//
//
//        CWeapon cWeapon2 = new CWeapon();
//        cWeapon2.setLevel(2);
//        cWeapon2.setAttackValue(20);
//
//
//        CWeapon cWeapon3 = new CWeapon();
//        cWeapon3.setLevel(3);
//        cWeapon3.setAttackValue(30);
//
//
//        CWeapon cWeapon4 = new CWeapon();
//        cWeapon4.setLevel(4);
//        cWeapon4.setAttackValue(40);
//
//
//        CWeapon cWeapon5 = new CWeapon();
//        cWeapon5.setLevel(5);
//        cWeapon5.setAttackValue(50);
//
//
//        CWeapon cWeapon6 = new CWeapon();
//        cWeapon6.setLevel(6);
//        cWeapon6.setAttackValue(60);
//
//
//        CWeapon cWeapon7 = new CWeapon();
//        cWeapon7.setLevel(7);
//        cWeapon7.setAttackValue(70);
//
//
//        CWeapon cWeapon8 = new CWeapon();
//        cWeapon8.setLevel(8);
//        cWeapon8.setAttackValue(100);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon3);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon4);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon5);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon6);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon7);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cWeapon8);
//
//         resonator
//
//        CResonator resonator1 = new CResonator();
//        resonator1.setLevel(1);
//
//        CResonator resonator2 = new CResonator();
//        resonator2.setLevel(2);

//        CResonator resonator3 = new CResonator();
//        resonator3.setLevel(3);
//
//        CResonator resonator4 = new CResonator();
//        resonator4.setLevel(4);
//
//        CResonator resonator5 = new CResonator();
//        resonator5.setLevel(5);
//
//        CResonator resonator6 = new CResonator();
//        resonator6.setLevel(6);
//
//        CResonator resonator7 = new CResonator();
//        resonator7.setLevel(7);
//
//        CResonator resonator8 = new CResonator();
//        resonator8.setLevel(8);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator3);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator4);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator5);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator6);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator7);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(resonator8);
//
//         frequency hack boost

//        CFrequencyHackBoost fhb1 = new CFrequencyHackBoost();
//        fhb1.setBoostValue(5);
//        fhb1.setRarity(1);
//
//        CFrequencyHackBoost fhb2 = new CFrequencyHackBoost();
//        fhb2.setBoostValue(10);
//        fhb2.setRarity(2);
//
//        CFrequencyHackBoost fhb3 = new CFrequencyHackBoost();
//        fhb3.setBoostValue(15);
//        fhb3.setRarity(3);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(fhb1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(fhb2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(fhb3);
//
//
////         range boost
//
//        CRangeBoost rangeboost1 = new CRangeBoost();
//        rangeboost1.setRangeValue(5);
//        rangeboost1.setRarity(1);
//
//        CRangeBoost rangeboost2 = new CRangeBoost();
//        rangeboost2.setRangeValue(10);
//        rangeboost2.setRarity(2);
//
//        CRangeBoost rangeboost3 = new CRangeBoost();
//        rangeboost3.setRangeValue(15);
//        rangeboost3.setRarity(3);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(rangeboost3);
//
//
//        CShield shield1 = new CShield();
//        shield1.setDefenseValue(5);
//        shield1.setRarity(1);
//
//        CShield shield2 = new CShield();
//        shield2.setDefenseValue(10);
//        shield2.setRarity(2);
//
//        CShield shield3 = new CShield();
//        shield3.setDefenseValue(15);
//        shield3.setRarity(3);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(shield1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(shield2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(shield3);
//
//
//        CTurret turret1 = new CTurret();
//        turret1.setAttackValue(5);
//        turret1.setRarity(1);
//
//        CTurret turret2 = new CTurret();
//        turret2.setAttackValue(10);
//        turret2.setRarity(2);
//
//        CTurret turret3 = new CTurret();
//        turret3.setAttackValue(15);
//        turret3.setRarity(3);
//
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(turret1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(turret2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(turret3);


//        CPortal portal1 = new CPortal();
//        CPortal portal2 = new CPortal();
//        CPortal portal3 = new CPortal();
//
//        portal1.setLevel(0);
//        portal2.setLevel(0);
//        portal3.setLevel(0);
//
//
//        portal1.setName("Portal1");
//        portal1.setName("Portal2");
//        portal1.setName("Portal3");
//
//        portal1.setLatitude(43.13830627889566);
//        portal1.setLongitude(6.024209619087428);
//
//        portal2.setLatitude(43.134850560876025);
//        portal2.setLongitude(6.0137197690145126);
//
//        portal3.setLatitude(43.13783146512867);
//        portal3.setLongitude(6.025353);
//
//
//        portal1.setKey(new CKey());//webResource.path("items/40").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal2.setKey(new CKey());//webResource.path("items/40").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal3.setKey(new CKey());//webResource.path("items/40").type(MediaType.APPLICATION_JSON).get(CKey.class));
//
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(portal1);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(portal2);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(portal3);
//
//         clés


        // inventaires ////////

//        CInventory inv1 = new CInventory();
//        inv1.setPlayer(pl3);
//        inv1.setItem();
//        inv1.setQuantity(1);
//
//        CInventory inv2 = new CInventory();
//        inv2.setPlayer(pl3);
//        inv2.setItem();
//        inv2.setQuantity(1);
//
//        CInventory inv3 = new CInventory();
//        inv3.setPlayer(pl3);
//        inv3.setItem();
//        inv3.setQuantity(1);

//        CInventory inv4 = new CInventory();
//        inv4.setPlayer(pl3);
//        inv4.setItem();
//        inv4.setQuantity(1);

//        CInventory inv5 = new CInventory();
//        inv5.setPlayer(pl3);
//        inv5.setItem();
//        inv5.setQuantity(1);

//         maps items
//        CTurret tur = new CTurret();
//        tur.setRange(20);
//        tur.setId(100);
//        tur.setRarity(2);
//        tur.setAttackValue(10);
//
//        CFrequencyHackBoost cfhb = new CFrequencyHackBoost();
//        cfhb.setBoostValue(2);
//        cfhb.setId(101);
//        cfhb.setRarity(1);

//
//        CMapItem m  = webResource.path("mapitems/2").type(MediaType.APPLICATION_JSON).get(CMapItem.class);
//        m.setLongitude(6.018815);
//        m.setLatitude(43.135692);
//
        //////// ajout BDD ////////

//         equipes
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(blueTeam);
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(redTeam);
//        webResource.path("teams").type(MediaType.APPLICATION_JSON).post(neutralTeam);

        // joueurs
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl1);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl2);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl3);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl4);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl5);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).post(pl6);
//
//         items
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable2);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable3);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable4);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable5);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable6);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable7);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(cConsumable8);
//
//         inventaire
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inv1);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inv2);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inv3);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inv4);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inv5);
//
//        // map items
//        webResource.path("mapitems").type(MediaType.APPLICATION_JSON).put(m);

//        // portails
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(pp1);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(pp2);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(pp3);
//
//        // portail test slots de tomy
//        CPortal portal = webResource.path("portals/11").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        portal.addResonator(res1);
//        portal.addResonator(res2);
//        portal.addResonator(res3);
//        portal.addResonator(res4);
//        portal.addResonator(res5);
//        portal.addResonator(res6);
//
//        portal.setTeam(blueTeam);
//
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(portal);
//
//        // mettre le portail 2 dans l'équipe bleu
//        CPortal portal = webResource.path("portals/2").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        portal.setTeam(blueTeam);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(portal);


//        // Fields
//        CPlayer p = webResource.path("players/3").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
//        CPortal p9 = webResource.path("portals/9").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p10 = webResource.path("portals/10").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p5 = webResource.path("portals/5").type(MediaType.APPLICATION_JSON).get(CPortal.class);

//       CTeam t = webResource.path("teams/2").type(MediaType.APPLICATION_JSON).get(CTeam.class);
//
//        p.setEnergy(8125470);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).put(p);

//        CPortal portal = webResource.path("portals/3").type(MediaType.APPLICATION_JSON).get(CPortal.class);

//        portal.getSlots().get(4).setEnergy(33);

//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(portal);
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).put(portal.getSlots().get(0));




//        CField field = new CField();
//        field.setFirstPortal(p3);
//        field.setSecondPortal(p5);
//        field.setThirdPortal(p10);
//        field.setTeam(t);
//
//        webResource.path("fields").type(MediaType.APPLICATION_JSON).post(field);

//
//        CLink l1 = new CLink();
//        l1.setFirstPortal(p3);
//        l1.setSecondPortal(p5);
//        l1.setId(8);
//
//
//        CLink l2 = new CLink();
//        l2.setFirstPortal(p5);
//        l2.setSecondPortal(p10);
//        l2.setId(11);

//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(l1);
//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(l2);


//        CLink l3 = new CLink();
//        l3.setFirstPortal(p6);
//        l3.setSecondPortal(p5);
//        l3.setId(10);

//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(l1);
//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(l2);
//        webResource.path("links").type(MediaType.APPLICATION_JSON).post(l3);
//
//        CLink l8 = webResource.path("links/8").type(MediaType.APPLICATION_JSON).get(CLink.class);
//        CLink l9 = webResource.path("links/9").type(MediaType.APPLICATION_JSON).get(CLink.class);
//        CPortal p3 = webResource.path("links/3").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CLink l10= webResource.path("links/10").type(MediaType.APPLICATION_JSON).get(CLink.class);

//        CKey k = webResource.path("items/49").type(MediaType.APPLICATION_JSON).get(CKey.class);

//        k.setPortal(p3);
//
//        CKey key = webResource.path("items/40").type(MediaType.APPLICATION_JSON).get(CKey.class);
//        CInventory inventory = webResource.path("inventories/3/40").type(MediaType.APPLICATION_JSON).get(CInventory.class);

//        key.setPortal(p9);
//        inventory.setQuantity(1);
//        inventory.setItem(key);
//        inventory.setPlayer(p);

//        p19.getSlots().get(3).setEnergy(99);
//        p19.getSlots().get(3).setResonator(resonator);
//        p19.getSlots().get(3).setPlayer(p);
//        p19.setName("Portal3");
//        p9.setName("ThePortal");
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(p9);

//        CPlayer p = webResource.path("players/1").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
//
//        p.setLevel(8);
//        p.setEnergy(998);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).put(p);


//        CInventory inventory2 = webResource.path("inventories/3/3").type(MediaType.APPLICATION_JSON).get(CInventory.class);
//        inventory2.setQuantity(2);
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).put(inventory2);

//        List links = new ArrayList();
//        links = webResource.path("teams/2/links").type(MediaType.APPLICATION_JSON).get(ArrayList.class);
//
//        System.out.println(links.get(0));
//        System.out.println(links.get(1));
//        System.out.println(links.get(2));

//        CResonator resonator = new CResonator();
//        CKey turret = webResource.path("items/37").type(MediaType.APPLICATION_JSON).get(CKey.class);
//        turret.setAttackValue(56);
//        resonator.setLevel(4);
//        CSlot slot = new CSlot();
//        slot.setPlayer(p);
//        slot.setResonator(resonator);
//        slot = webResource.path("slots/11").type(MediaType.APPLICATION_JSON).get(CSlot.class);
//        CPortal portal = webResource.path("portals/3").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        portal.getSlots().get(0).setEnergy(7);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(portal);
//        System.out.println(portal.getUpgradesList().get(0).toString());

        // inventory

//        CPlayer p = webResource.path("players/5").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
//        p.setLevel(2);
//        webResource.path("players").type(MediaType.APPLICATION_JSON).put(p);


//        CPortal t = webResource.path("portals/1").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        t.setLevel(48);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(t);




//        CInventory inventory = webResource.path("inventories/1/11").type(MediaType.APPLICATION_JSON).get(CInventory.class);
//
//
//        CInventory inventory = new CInventory();
//        inventory.setPlayer(p);
//        inventory.setItem(turret);
//        inventory.setQuantity(1);

//
//        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory);

//        CResonator reso = webResource.path("items/5").type(MediaType.APPLICATION_JSON).get(CResonator.class);
//        CSlot slot = webResource.path("slots/10").type(MediaType.APPLICATION_JSON).get(CSlot.class);
//        CPortal portal = webResource.path("portals/10").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//
//        CSlot slot = portal.getSlots().get(0);
//        slot.setPlayer(p);
//        slot.setResonator(reso);
//        slot.setEnergy(18);

//        portal.addSlot(slot);
//        portal.setLevel(14);
//        portal.setName("Portal");
//
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).put(portal);
//        webResource.path("slots").type(MediaType.APPLICATION_JSON).put(slot);


    }
}
