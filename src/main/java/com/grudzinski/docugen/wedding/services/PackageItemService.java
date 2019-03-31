package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.wedding.model.PackageItem;

import java.util.Set;

public interface PackageItemService {
    Set<PackageItem> getPackageItems();
    PackageItem findById(Long id);
}
