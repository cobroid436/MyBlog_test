package ru.alex.myBlog.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity
@ToString(exclude = "fileData")
@Table(name = "attachment" , schema = "myblog")
public class AttachmentEntity implements Serializable {
// STORE IN "UTF-16"
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID")
  private Long id;


  @Basic
  @Column(name = "file_Name", length = 50,nullable = false)
  private String fileName;


  @Lob
  @Column(name = "file_Data",nullable = false)
  private byte[] fileData;

  @Basic
  @Column(name = "description", length = 255)
  private String description;

  @Basic
  @Column (name="date",nullable = false)
  private LocalDateTime date;

  @Basic
  @Column (name="ref_id")
  private Long refid;

    public AttachmentEntity() {
    }

    public AttachmentEntity(String fileName, byte[] fileData, String description, LocalDateTime date, Long refid) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.description = description;
        this.date = date;
        this.refid = refid;
    }
    
}
