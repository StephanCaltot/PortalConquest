package classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Screetts on 28/04/2016.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = AItem.ITEM_BY_ALL, query = "select item from AItem item"),
        @NamedQuery(name = AItem.ITEM_BY_ID, query = "select item from AItem item where item.id = :Pid")
})
@DiscriminatorColumn(name = "item_type")
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, scope = AItem.class)
public abstract class AItem implements Serializable{
    @TableGenerator(name = "itemGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="itemGenerator")
    @Column(name="item_id")
    private int id ;


    public static final String ITEM_BY_ALL = "AItem.findItemAll";
    public static final String ITEM_BY_ID = "AItem.findItemById";


    public AItem() {}

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AItem aItem = (AItem) o;

        return id == aItem.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
