package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.model.PackageItem;
import com.grudzinski.docugen.wedding.repositories.PackageItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PackageItemServiceImpl implements PackageItemService {

    private final PackageItemRepository packageItemRepository;

    public PackageItemServiceImpl(PackageItemRepository packageItemRepository) {
        this.packageItemRepository = packageItemRepository;
    }

    @Override
    public List<PackageItem> getPackageItems() {
        List<PackageItem> packageItems = new ArrayList<>();
        packageItemRepository.findAll().forEach(packageItems::add);

        return packageItems;
    }

    @Override
    public PackageItem findById(Long id) {
        Optional<PackageItem> packageItemOptional;
        packageItemOptional = packageItemRepository.findById(id);
        if (!packageItemOptional.isPresent()) {
             throw new NotFoundException("PackageItem not found for Id:" + id);
        }

        return packageItemOptional.get();
    }

    @Override
    public PackageItem save(PackageItem packageItem) {
        PackageItem packageItemToSave;
        if (packageItem.getId() != null) {
            packageItemToSave = this.findById(packageItem.getId());
            if (packageItemToSave != null) {
                packageItemToSave.setName(packageItem.getName());
            }
        } else {
            packageItemToSave = packageItem;
        }
        PackageItem savedPackageItem = packageItemRepository.save(packageItemToSave);
        
        return savedPackageItem;
    }

    @Override
    public void deleteById(Long id) {
        packageItemRepository.deleteById(id);
    }
}
