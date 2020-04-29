package ru.alex.myBlog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "groupuser_has_articles", schema = "myblog")
public class GroupuserHasArticlesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role",referencedColumnName = "id_role",nullable = false )
    private RolesEntity roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_articles",referencedColumnName = "id",nullable = false)
    private ArticlesEntity idarticles;

    public GroupuserHasArticlesEntity() {
    }

    public GroupuserHasArticlesEntity(RolesEntity roles, ArticlesEntity idarticles) {
        this.roles = roles;
        this.idarticles = idarticles;
    }
}
