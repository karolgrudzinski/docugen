package com.grudzinski.docugen.controllers;

import com.grudzinski.docugen.services.WeddingCeremonyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/wedding")
public class WeddingCeremonyController {

    private final String TEMPLATE_PREFIX = "document/wedding/";

    private final WeddingCeremonyService weddingCeremonyService;

    public WeddingCeremonyController(WeddingCeremonyService weddingCeremonyService) {
        this.weddingCeremonyService = weddingCeremonyService;
    }

    @RequestMapping({"/", "/index"})
    public String listWeddings(Model model) {
        model.addAttribute("weddings", weddingCeremonyService.getWeddings());

//        return TEMPLATE_PREFIX + "list";
        return "document/wedding/list";
    }
}
