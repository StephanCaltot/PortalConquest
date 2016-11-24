package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Screetts on 27/04/2016.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "CPlayer.findPlayerAll", query = "select player from CPlayer player"),
        @NamedQuery(name = CPlayer.PLAYER_BY_ID, query = "select player from CPlayer player where player.id = :Pid"),
        @NamedQuery(name = CPlayer.PLAYER_BY_EMAIL, query = "select player from CPlayer player where player.email = :Pemail"),
        @NamedQuery(name = CPlayer.PLAYER_BY_TEAM, query = "select player from CPlayer player where player.team.id = :Pid"),
        @NamedQuery(name = CPlayer.PLAYER_BY_STATUS, query = "select player from CPlayer player where player.latitude != 0")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CPlayer.class)
public class CPlayer implements Serializable {
    @TableGenerator(name = "playerGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="playerGenerator")
    @Column(name="Player_id")
    private int id;
    private String nickname;
    private int energy;
    @Column(unique=true)
    private String email;
    private int actionPoints;
    private double latitude ;
    private double longitude ;
    private int level;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Team")
    private CTeam team;


    public static final String PLAYER_BY_ALL = "CPlayer.findPlayerAll";
    public static final String PLAYER_BY_ID = "CPlayer.findPlayerById";
    public static final String PLAYER_BY_TEAM = "CPlayer.findPlayerByTeam";
    public static final String PLAYER_BY_STATUS = "CPlayer.findPlayerByStatus";
    public static final String PLAYER_BY_EMAIL = "CPlayer.findPlayerEmail";


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CPlayer() {}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public CTeam getTeam() {
        return team;
    }

    public void setTeam(CTeam team) {
        this.team = team;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void addActionPoints(int actionPoints){
        this.setActionPoints(this.getActionPoints()+actionPoints);
        if(this.getActionPoints()>= Math.pow(2, this.getLevel())*1000)
            if(this.getLevel() < 8){
                this.setActionPoints(this.getActionPoints()-(int)Math.pow(2, this.getLevel())*1000);
                this.setLevel(this.getLevel()+1);
            }
    }

    @Override
    public String toString() {
        return "CPlayer " +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", energy=" + energy +
                ", email='" + email + '\'' +
                ", actionPoints=" + actionPoints +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", level=" + level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CPlayer cPlayer = (CPlayer) o;

        return id == cPlayer.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
