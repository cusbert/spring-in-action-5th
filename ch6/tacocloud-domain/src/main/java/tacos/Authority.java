package tacos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "username",  referencedColumnName = "username", foreignKey = @ForeignKey(name = "fk_authorities_users"))
    private User user;

    @Column(name = "AUTHORITY", length = 100, nullable = false)
    private String authority;
}
