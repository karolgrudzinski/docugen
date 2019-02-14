package com.grudzinski.docugen.controllers;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.services.WeddingCeremonyRendererService;
import com.grudzinski.docugen.services.WeddingCeremonyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

@Slf4j
@Controller
@RequestMapping("/wedding")
public class WeddingCeremonyController {

    private final String TEMPLATE_PREFIX = "document/wedding/";

    private final WeddingCeremonyService weddingCeremonyService;

    private final WeddingCeremonyRendererService weddingCeremonyRendererService;

    public WeddingCeremonyController(WeddingCeremonyService weddingCeremonyService, WeddingCeremonyRendererService weddingCeremonyRendererService) {
        this.weddingCeremonyService = weddingCeremonyService;
        this.weddingCeremonyRendererService = weddingCeremonyRendererService;
    }

    @RequestMapping({"/", "/index"})
    public String listWeddings(Model model) {
        model.addAttribute("weddings", weddingCeremonyService.getWeddings());

//        return TEMPLATE_PREFIX + "list";
        return "document/wedding/list";
    }

    @RequestMapping({"/{id}/view"})
    public String viewWedding(@PathVariable String id, Model model) {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));
        model.addAttribute("wedding", weddingCeremony);

        return "document/wedding/view";
    }

    @GetMapping({"/{id}/pdf"})
    public void getWeddingPDF(@PathVariable String id, HttpServletResponse response) throws Exception {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        weddingCeremonyRendererService.generatePDF(weddingCeremony, outputStream);


        response.setContentType("application/pdf");
        response.setContentLength(outputStream.size());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", weddingCeremony.getDocumentShortName()));

        outputStream.writeTo(response.getOutputStream());
        outputStream.flush();
        response.flushBuffer();
    }


}
