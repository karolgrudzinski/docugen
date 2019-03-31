package com.grudzinski.docugen.base.controllers;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.controllers.WeddingCeremonyController;
import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import com.grudzinski.docugen.wedding.services.PackageItemService;
import com.grudzinski.docugen.wedding.services.WeddingCeremonyRendererService;
import com.grudzinski.docugen.wedding.services.WeddingCeremonyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeddingCeremonyControllerTest {

    @Mock
    WeddingCeremonyService weddingCeremonyService;

    @Mock
    WeddingCeremonyRendererService weddingCeremonyRendererService;

    @Mock
    PackageItemService packageItemService;

    WeddingCeremonyController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new WeddingCeremonyController(weddingCeremonyService, weddingCeremonyRendererService, packageItemService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new SortHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testListWeddings() throws Exception {
        List<WeddingCeremony> weddings = new ArrayList<>();
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());

        when(weddingCeremonyService.getWeddingsSorted(any())).thenReturn(weddings);

        mockMvc.perform(get("/wedding/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/list"))
                .andExpect(model().attributeExists("weddings"));

        verify(weddingCeremonyService).getWeddingsSorted(any());
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
                .andExpect(model().attributeExists("wedding"))
                .andExpect(model().attributeExists("allpackageitems"));
    }

    @Test
    public void testEditWeddingForm() throws Exception {
        WeddingCeremony wedding = new WeddingCeremony();

        when(weddingCeremonyService.findById(anyLong())).thenReturn(wedding);
        mockMvc.perform(get("/wedding/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/edit"))
                .andExpect(model().attributeExists("wedding"))
                .andExpect(model().attributeExists("allpackageitems"));
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

    @Test
    public void testDeleteConfirm() throws Exception {
        WeddingCeremony wedding = new WeddingCeremony();
        wedding.setId(3L);

        when(weddingCeremonyService.findById(anyLong())).thenReturn(wedding);

        mockMvc.perform(get("/wedding/3/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/confirmdelete"))
                .andExpect(model().attributeExists("wedding"));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        mockMvc.perform(post("/wedding/3/delete")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(weddingCeremonyService).deleteById(anyLong());
    }

    @Test
    public void testDeleteDocumentParamAndFormMismatch() throws Exception {
        mockMvc.perform(post("/wedding/3/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(weddingCeremonyService, never()).deleteById(anyLong());
    }
}