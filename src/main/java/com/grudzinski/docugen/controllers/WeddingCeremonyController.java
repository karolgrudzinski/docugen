package com.grudzinski.docugen.controllers;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.services.WeddingCeremonyRendererService;
import com.grudzinski.docugen.services.WeddingCeremonyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @RequestMapping({"/", "/index", "/list"})
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

    @RequestMapping({"/new"})
    public String newWedding(Model model) {
        model.addAttribute("wedding", new WeddingCeremony());

        return "document/wedding/edit";
    }

    @RequestMapping({"/{id}/edit"})
    public String editWedding(@PathVariable String id, Model model) {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));
        model.addAttribute("wedding", weddingCeremony);

        return "document/wedding/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("wedding") WeddingCeremony weddingCeremony,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "document/wedding/edit";
        }

        WeddingCeremony savedWeddingCeremony = weddingCeremonyService.save(weddingCeremony);

        return "redirect:/wedding/" + savedWeddingCeremony.getId() + "/view";
    }

    @GetMapping({"/{id}/pdf"})
    public void getWeddingPDF(@PathVariable String id, HttpServletResponse response) throws Exception {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        OutputStream outputStream = response.getOutputStream();

        weddingCeremonyRendererService.generatePDF(weddingCeremony, outputStream);

//        log.debug(String.valueOf(outputStream.size()));

        //        response.setContentType(String.valueOf(MediaType.APPLICATION_PDF));
        response.setContentType("application/pdf");
        response.setContentLength(outputStream.size());
//        log.debug(String.format("attachment; filename='%s.pdf'", weddingCeremony.getDocumentShortName()));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", weddingCeremony.getDocumentShortName()));

        outputStream.writeTo(response.getOutputStream());
        outputStream.flush();
        response.flushBuffer();
    }


}
