package ru.alex.myBlog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NamedQueries({
    @NamedQuery(name="Groupuser.findByUser",query = "SELECT gp.roles FROM GroupuserEntity gp WHERE gp.users.idusers = :user")
})
@Table(name = "groupuser", schema = "myblog")
public class GroupuserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role",referencedColumnName = "id_role",nullable = false)
    private RolesEntity roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_users",referencedColumnName = "id_users",nullable = false )
    private UsersEntity users;

    public GroupuserEntity() {
    }

    public GroupuserEntity(RolesEntity roles, UsersEntity users) {
        this.roles = roles;
        this.users = users;
    }

    
}
