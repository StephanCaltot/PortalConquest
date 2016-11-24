package portalconquest.test;

import classes.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

/**
 * Created by Screetts on 09/06/2016.
 */

public class CReset {
    public static void main(String[] args) {
        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(JacksonJsonProvider.class);
        Client c = Client.create(cc);
        WebResource webResource = c.resource("http://localhost:9998/");

        CInventory inventory = new CInventory();
        CInventory inventory18 = new CInventory();
        CInventory inventory20 = new CInventory();

        CPlayer player = webResource.path("players/2").type(MediaType.APPLICATION_JSON).get(CPlayer.class);
        CResonator resonator = webResource.path("items/17").type(MediaType.APPLICATION_JSON).get(CResonator.class);
        CResonator resonator18 = webResource.path("items/18").type(MediaType.APPLICATION_JSON).get(CResonator.class);
        CResonator resonator20 = webResource.path("items/20").type(MediaType.APPLICATION_JSON).get(CResonator.class);

        inventory.setPlayer(player);
        inventory.setItem(resonator);
        inventory.setQuantity(10);

        inventory18.setPlayer(player);
        inventory18.setItem(resonator18);
        inventory18.setQuantity(10);

        inventory20.setPlayer(player);
        inventory20.setItem(resonator20);
        inventory20.setQuantity(10);

        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory);
        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory18);
        webResource.path("inventories").type(MediaType.APPLICATION_JSON).post(inventory20);

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
//
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
//
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
//
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
//
//        CResonator resonator1 = new CResonator();
//        resonator1.setLevel(1);
//
//        CResonator resonator2 = new CResonator();
//        resonator2.setLevel(2);
//
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
//
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
//
//        CVirus virus = new CVirus();
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(virus);

//
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
//        CPortal p1 = webResource.path("portals/4").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p2 = webResource.path("portals/5").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//        CPortal p3 = webResource.path("portals/6").type(MediaType.APPLICATION_JSON).get(CPortal.class);
//
//
//        CKey key = new CKey();
//        key.setPortal(p1);
//        CKey key1 = new CKey();
//        key1.setPortal(p2);
//        CKey key2 = new CKey();
//        key2.setPortal(p3);
//
//
//
//
//
///        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key1);
//        webResource.path("items").type(MediaType.APPLICATION_JSON).post(key2);
//
//        p1.setKey(key);
//        p1.setName("Portal1");
//        p2.setKey(key1);
//        p2.setName("Portal2");
//        p3.setKey(key2);
//        p3.setName("Portal3");

//        portal1.setKey(webResource.path("items/37").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal2.setKey(webResource.path("items/38").type(MediaType.APPLICATION_JSON).get(CKey.class));
//        portal3.setKey(webResource.path("items/39").type(MediaType.APPLICATION_JSON).get(CKey.class));

//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p1);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p2);
//        webResource.path("portals").type(MediaType.APPLICATION_JSON).post(p3);


    }
}
