package ru.alex.myBlog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ToString(of = "idusers")
@Entity
@NamedQueries({
    @NamedQuery(name = "Users.uniqLogin", query = "select COUNT(u) from UsersEntity u where u.idusers = :id_users"),
    @NamedQuery(name = "Users.uniqEmail", query = "select COUNT(u) from UsersEntity u where u.email = :email"),
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM UsersEntity u WHERE u.idusers = :name")
})
@Table(name = "users", schema = "myblog")
public class UsersEntity implements Serializable {

    @Id
    @Column(name = "id_users", length = 45, nullable = false)
    private String idusers;

    @Basic
    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Basic
    @Column(name = "pass", nullable = false, length = 255)
    private String pass;

    @Basic
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime createDate;

    
    public UsersEntity() {
    }

    public UsersEntity(String idusers, String name, String pass, String email, LocalDateTime createDate) {
        this.idusers = idusers;
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.createDate = createDate;
    }
       
}
