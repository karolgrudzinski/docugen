package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.PackageItem;

import java.util.List;

public interface PackageItemService {
    List<PackageItem> getPackageItems();
    PackageItem findById(Long id);
    PackageItem save(PackageItem packageItem);
    void deleteById(Long id);
}
