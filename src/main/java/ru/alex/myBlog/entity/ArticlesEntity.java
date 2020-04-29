package ru.alex.myBlog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "articles", schema = "myblog")
public class ArticlesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Basic
    @Column(name = "title",nullable = false ,length = 45)
    private String title;

    @Basic
    @Column(name = "description",length = 500,nullable = false )
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_text",nullable = false,referencedColumnName = "id")
    private AttachmentEntity idtext;

    @Basic
    @Column(name = "date", nullable = false )
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "id_users", unique = true, nullable = false,referencedColumnName = "id_users")
    private UsersEntity idusers;

    public ArticlesEntity() {
    }

    public ArticlesEntity(String title, String description, AttachmentEntity idtext, LocalDate date, UsersEntity idusers) {
        this.title = title;
        this.description = description;
        this.idtext = idtext;
        this.date = date;
        this.idusers = idusers;
    }

}
