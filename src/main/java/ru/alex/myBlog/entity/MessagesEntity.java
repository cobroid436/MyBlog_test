package ru.alex.myBlog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "messages", schema = "myblog")
public class MessagesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "text",nullable = false ,length = 255)
    private String text;

    @Basic
    @Column(name = "date",nullable = false )
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_users",referencedColumnName = "id_users",nullable = false)
    private UsersEntity idusers;

    @ManyToOne
    @JoinColumn(name = "id_articles",referencedColumnName = "id",nullable = false)
    private ArticlesEntity idarticles;

    public MessagesEntity() {
    }

    public MessagesEntity(String text, LocalDateTime date, UsersEntity idusers, ArticlesEntity idarticles) {
        this.text = text;
        this.date = date;
        this.idusers = idusers;
        this.idarticles = idarticles;
    }

}
