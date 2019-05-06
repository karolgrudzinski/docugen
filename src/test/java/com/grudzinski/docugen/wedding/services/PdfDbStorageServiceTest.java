package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.model.PdfFile;
import com.grudzinski.docugen.wedding.repositories.PdfDbStorageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PdfDbStorageServiceTest {

    @Mock
    PdfDbStorageRepository storageRepository;

    private PdfDbStorageService storageService;

    private byte[] exampleFileData = {0x01, 0x03, 0x40, 0x53, 0x65};
    private String exampleFileName = "example.txt";
    private UUID exampleFileUUID = UUID.fromString("00000000-1111-2222-3333-444444444444");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        storageService = new PdfDbStorageService(storageRepository);
    }

    @Test
    public void shouldSaveObject() {
    }

    @Test
    public void shouldSaveObjectByArguments() {
    }

    @Test
    public void shouldFindById() {
        PdfFile pdfFile = new PdfFile(exampleFileData, exampleFileName);
        pdfFile.setId(3L);

        when(storageRepository.findById(anyLong())).thenReturn(Optional.of(pdfFile));

        PdfFile pdfFileReturned = storageService.findById(3L);

        assertNotNull("Null PdfFile returned", pdfFileReturned);
        verify(storageRepository).findById(anyLong());
        verify(storageRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindById() {
        Optional<PdfFile> pdfFileOptional = Optional.empty();

        when(storageRepository.findById(anyLong())).thenReturn(pdfFileOptional);

        PdfFile pdfFileReturned = storageService.findById(2L);
    }

    @Test
    public void shouldFindByUuid() {
        PdfFile pdfFile = new PdfFile(exampleFileData, exampleFileName);
        pdfFile.setId(5L);

        when(storageRepository.findByUuid(any())).thenReturn(Optional.of(pdfFile));

        PdfFile pdfFileReturned = storageService.findByUuid(exampleFileUUID);

        assertEquals(pdfFile.getUuid(), pdfFileReturned.getUuid());
    }
}