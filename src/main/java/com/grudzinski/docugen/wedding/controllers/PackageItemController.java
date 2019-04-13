package com.grudzinski.docugen.wedding.controllers;

import com.grudzinski.docugen.wedding.model.PackageItem;
import com.grudzinski.docugen.wedding.services.PackageItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/wedding/packageitem/")
public class PackageItemController {

    private final PackageItemService packageItemService;

    public PackageItemController(PackageItemService packageItemService) {
        this.packageItemService = packageItemService;
    }

    @RequestMapping({"/", "/index", "/list"})
    public String listPackageItems(Model model) {
        model.addAttribute("packageitems", packageItemService.getPackageItems());

        return "document/wedding/packageitem/list";
    }

    @RequestMapping({"/new"})
    public String newPackageItem(Model model) {
        model.addAttribute("packageitem", new PackageItem());

        return "document/wedding/packageitem/edit";
    }

    @RequestMapping({"/{id}/edit"})
    public String editPackageItem(@PathVariable String id, Model model) {
        PackageItem packageItem = packageItemService.findById(Long.valueOf(id));
        model.addAttribute("packageitem", packageItem);

        return "document/wedding/packageitem/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("packageitem") PackageItem packageItem,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "document/wedding/packageitem/edit";
        }

        PackageItem savedPackageItem = packageItemService.save(packageItem);

        return "redirect:/wedding/packageitem/";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteConfirm(@PathVariable String id, Model model) {
        PackageItem packageItem = packageItemService.findById(Long.valueOf(id));
        model.addAttribute("packageitem", packageItem);

        return "document/wedding/packageitem/confirmdelete";
    }

    @PostMapping({"/{id}/delete"})
    public String deletePackageItem(@Valid @ModelAttribute("packageitem") PackageItem packageItem,
                                    @PathVariable String id, Model model) {
        if (packageItem.getId().equals(Long.valueOf(id))) {
            packageItemService.deleteById(Long.valueOf(id));
        }

        return "redirect:/wedding/packageitem/";
    }
}
