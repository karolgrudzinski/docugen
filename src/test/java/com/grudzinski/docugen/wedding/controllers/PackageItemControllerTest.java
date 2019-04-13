package com.grudzinski.docugen.wedding.controllers;

import com.grudzinski.docugen.wedding.model.PackageItem;
import com.grudzinski.docugen.wedding.services.PackageItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PackageItemControllerTest {

    @Mock
    PackageItemService packageItemService;

    PackageItemController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new PackageItemController(packageItemService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListPackageItems() throws Exception {
        List<PackageItem> packageItems = new ArrayList<>();
        packageItems.add(new PackageItem("vocal"));
        packageItems.add(new PackageItem("piano"));
        packageItems.add(new PackageItem("violin"));

        when(packageItemService.getPackageItems()).thenReturn(packageItems);

        mockMvc.perform(get("/wedding/packageitem/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/packageitem/list"))
                .andExpect(model().attributeExists("packageitems"));

        verify(packageItemService).getPackageItems();
    }

    @Test
    public void testNewPackageItemForm() throws Exception {
        mockMvc.perform(get("/wedding/packageitem/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/packageitem/edit"))
                .andExpect(model().attributeExists("packageitem"));
    }

    @Test
    public void testEditPackageItemForm() throws Exception {
        PackageItem packageItem = new PackageItem("piano");

        when(packageItemService.findById(anyLong())).thenReturn(packageItem);

        mockMvc.perform(get("/wedding/packageitem/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/packageitem/edit"))
                .andExpect(model().attributeExists("packageitem"));
    }

    @Test
    public void testPostNewPackageItem() throws Exception {
        PackageItem packageItem = new PackageItem("piano");
        packageItem.setId(2L);

        when(packageItemService.save(any())).thenReturn(packageItem);

        mockMvc.perform(post("/wedding/packageitem/save")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "")
                    .param("name", "piano"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/wedding/packageitem/"));
    }

    @Test
    public void testDeleteConfirm() throws Exception {
        PackageItem packageItem = new PackageItem("violin");
        packageItem.setId(2L);

        when(packageItemService.findById(anyLong())).thenReturn(packageItem);

        mockMvc.perform(get("/wedding/packageitem/2/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("document/wedding/packageitem/confirmdelete"))
                .andExpect(model().attributeExists("packageitem"));
    }

    @Test
    public void testDeletePackageItem() throws Exception {
        mockMvc.perform(post("/wedding/packageitem/2/delete")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/wedding/packageitem/"));
    }

    @Test
    public void testDeletePackageItemParamAndFormMismatch() throws Exception {
        mockMvc.perform(post("/wedding/packageitem/2/delete")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/wedding/packageitem/"));

        verify(packageItemService, never()).deleteById(anyLong());
    }
}