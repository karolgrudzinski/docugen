package com.grudzinski.docugen.wedding.model;

import com.grudzinski.docugen.base.models.FileObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wedding_pdf")
public class PdfFile extends FileObject {

    public PdfFile(byte[] data, String fileName) {
        super(data, fileName);
    }

    @OneToOne(mappedBy = "pdfFile")
    private WeddingCeremony wedding;
}
