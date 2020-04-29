package ru.alex.myBlog.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "roles", schema = "myblog")
public class RolesEntity implements Serializable {


  @Id
  @Column(name = "id_role",length = 50, unique = true, nullable = false)
  private String idrole;

    public RolesEntity() {
    }

    public RolesEntity(String idrole) {
        this.idrole = idrole;
    }

}
