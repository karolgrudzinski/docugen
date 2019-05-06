package com.grudzinski.docugen.base.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class FileObject extends BaseAuditableEntity {

    private final UUID uuid = UUID.randomUUID();

    @Lob
    private byte[] data;

    private String fileName;

    public FileObject(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }
}
