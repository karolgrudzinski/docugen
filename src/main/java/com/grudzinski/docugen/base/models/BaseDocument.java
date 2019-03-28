package com.grudzinski.docugen.base.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseDocument extends BaseEntity {

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "modification_time")
    private LocalDateTime lastModificationTime;

    @PrePersist
    void onPersist() {
        this.creationTime = LocalDateTime.now();
        this.lastModificationTime = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.lastModificationTime = LocalDateTime.now();
    }
}
