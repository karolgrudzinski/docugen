package com.grudzinski.docugen.controllers;

import com.grudzinski.docugen.exceptions.NotFoundException;
import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.services.WeddingCeremonyRendererService;
import com.grudzinski.docugen.services.WeddingCeremonyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeddingCeremonyControllerTest {

    @Mock
    WeddingCeremonyService weddingCeremonyService;

    @Mock
    WeddingCeremonyRendererService weddingCeremonyRendererService;

    WeddingCeremonyController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new WeddingCeremonyController(weddingCeremonyService, weddingCeremonyRendererService);

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

    @Test
    public void testViewWedding() throws Exception {
        WeddingCeremony wedding = new WeddingCeremony();

        when(weddingCeremonyService.findById(anyLong())).thenReturn(wedding);
        mockMvc.perform(get("/wedding/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/view"))
                .andExpect(model().attributeExists("wedding"));
    }

    @Test
    public void testViewWeddingNotFound() throws Exception {
        when(weddingCeremonyService.findById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/wedding/2/view"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNewWeddingForm() throws Exception {
        mockMvc.perform(get("/wedding/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/edit"))
                .andExpect(model().attributeExists("wedding"));
    }

    @Test
    public void testEditWeddingForm() throws Exception {
        WeddingCeremony wedding = new WeddingCeremony();

        when(weddingCeremonyService.findById(anyLong())).thenReturn(wedding);
        mockMvc.perform(get("/wedding/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/edit"))
                .andExpect(model().attributeExists("wedding"));
    }

    @Test
    public void testPostNewWedding() throws Exception {
         WeddingCeremony wedding = new WeddingCeremony();
         wedding.setId(3L);

         when(weddingCeremonyService.save(any())).thenReturn(wedding);

         mockMvc.perform(post("/wedding/save")
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                 .param("id", "")
                 .param("customer.id", "1")
                 .param("performer", "Music Maestro"))
                 .andExpect(status().is3xxRedirection())
                 .andExpect(view().name("redirect:/wedding/3/view"));

    }

    @Test
    public void getWeddingPDF() {
    }
}