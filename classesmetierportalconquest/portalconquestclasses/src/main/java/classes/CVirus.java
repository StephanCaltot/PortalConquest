package classes;

import javax.persistence.*;

/**
 * Created by Screetts on 08/06/2016.
 */

@Entity
@DiscriminatorValue(value = "virus")
public class CVirus extends AItem{
    @TableGenerator(name = "linkGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="linkGenerator")
    @Column(name = "Link_id")
    private int id;
}
