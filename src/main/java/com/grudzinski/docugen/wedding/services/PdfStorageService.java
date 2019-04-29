package com.grudzinski.docugen.wedding.services;

import java.io.OutputStream;

public interface PdfStorageService {
    void save(OutputStream document, String fileName);
    
}
