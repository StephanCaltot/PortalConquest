package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by scaltot904 on 22/02/16.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = CTeam.TEAM_BY_ALL, query = "select team from CTeam team"),
        @NamedQuery(name = CTeam.TEAM_BY_ID, query = "select team from CTeam team where team.id = :Pid")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = CTeam.class)
public class CTeam implements Serializable {
    @TableGenerator(name = "teamGenerator",allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="teamGenerator")
    @Id
    @Column(name="team_id")
    private int id;
    private String name;
    private int score ;


    public static final String TEAM_BY_ALL = "CTeam.findTeamAll";
    public static final String TEAM_BY_ID = "CTeam.findTeamByName";


    public CTeam() {}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CTeam(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CTeam " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score;
    }
}
