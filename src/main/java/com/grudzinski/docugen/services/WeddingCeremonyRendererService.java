package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;

import java.io.IOException;
import java.io.OutputStream;

public interface WeddingCeremonyRendererService {
    void generatePDF(WeddingCeremony weddingCeremony, OutputStream outputStream) throws IOException, Exception;
}
