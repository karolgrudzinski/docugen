package com.grudzinski.docugen.wedding.controllers;

import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import com.grudzinski.docugen.wedding.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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

    private final PackageItemService packageItemService;

    private final PdfStorageService pdfStorageService;

    public WeddingCeremonyController(WeddingCeremonyService weddingCeremonyService,
                                     WeddingCeremonyRendererService weddingCeremonyRendererService,
                                     PackageItemService packageItemService,
                                     PdfStorageService pdfStorageService) {
        this.weddingCeremonyService = weddingCeremonyService;
        this.weddingCeremonyRendererService = weddingCeremonyRendererService;
        this.packageItemService = packageItemService;
        this.pdfStorageService = pdfStorageService;
    }

    @RequestMapping({"/", "/index", "/list"})
    public String listWeddings(@SortDefault(value = "dateOfEvent", direction = Sort.Direction.ASC) Sort sort,
                               Model model) {

        model.addAttribute("weddings", weddingCeremonyService.getWeddingSummariesSorted(sort));

        Sort.Order order = sort.get().findFirst().get();
        model.addAttribute("sortProperty", order.getProperty());
        model.addAttribute("sortDesc", order.isDescending());

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
        model.addAttribute("allpackageitems", packageItemService.getPackageItems());

        return "document/wedding/edit";
    }

    @RequestMapping({"/{id}/edit"})
    public String editWedding(@PathVariable String id, Model model) {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));
        model.addAttribute("wedding", weddingCeremony);
        model.addAttribute("allpackageitems", packageItemService.getPackageItems());

        return "document/wedding/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("wedding") WeddingCeremony weddingCeremony,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            model.addAttribute("allpackageitems", packageItemService.getPackageItems());

            return "document/wedding/edit";
        }

        WeddingCeremony savedWeddingCeremony = weddingCeremonyService.save(weddingCeremony);

        return "redirect:/wedding/" + savedWeddingCeremony.getId() + "/view";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteConfirm(@PathVariable String id, Model model) {
        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(Long.valueOf(id));
        model.addAttribute("wedding", weddingCeremony);

        return "document/wedding/confirmdelete";
    }

    @PostMapping({"/{id}/delete"})
    public String deleteDocument(@Valid @ModelAttribute("wedding") WeddingCeremony weddingCeremony,
                                 @PathVariable String id, Model model) {
        if (weddingCeremony.getId().equals(Long.valueOf(id))) {
            weddingCeremonyService.deleteById(weddingCeremony.getId());
        }

        return "redirect:/";
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

//        pdfStorageService.save(outputStream, weddingCeremony.getDocumentShortName());

        outputStream.writeTo(response.getOutputStream());
        outputStream.flush();
        response.flushBuffer();
    }
}
