package com.grudzinski.docugen.controllers;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.services.WeddingCeremonyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeddingCeremonyControllerTest {

    @Mock
    WeddingCeremonyService weddingCeremonyService;

    WeddingCeremonyController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new WeddingCeremonyController(weddingCeremonyService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListWeddings() throws Exception {
        List<WeddingCeremony> weddings = new ArrayList<>();
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());

        when(weddingCeremonyService.getWeddings()).thenReturn(weddings);

        mockMvc.perform(get("/wedding/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/list"))
                .andExpect(model().attributeExists("weddings"));

        verify(weddingCeremonyService).getWeddings();
    }
}