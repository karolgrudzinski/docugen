package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.WeddingCeremony;

import java.io.IOException;
import java.io.OutputStream;

public interface WeddingCeremonyRendererService {
    void generatePDF(WeddingCeremony weddingCeremony, OutputStream outputStream) throws IOException, Exception;
}
