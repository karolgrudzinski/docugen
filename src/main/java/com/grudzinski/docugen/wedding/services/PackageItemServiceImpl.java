package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.model.PackageItem;
import com.grudzinski.docugen.wedding.repositories.PackageItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PackageItemServiceImpl implements PackageItemService {

    private final PackageItemRepository packageItemRepository;

    public PackageItemServiceImpl(PackageItemRepository packageItemRepository) {
        this.packageItemRepository = packageItemRepository;
    }

    @Override
    public Set<PackageItem> getPackageItems() {
        Set<PackageItem> packageItems = new HashSet<>();
        packageItemRepository.findAll().forEach(packageItems::add);

        return packageItems;
    }

    @Override
    public PackageItem findById(Long id) {
        Optional<PackageItem> bundleItemOptional;
        bundleItemOptional = packageItemRepository.findById(id);
        if (!bundleItemOptional.isPresent()) {
             throw new NotFoundException("BundleItem not found for Id:" + id);
        }

        return bundleItemOptional.get();
    }
}
