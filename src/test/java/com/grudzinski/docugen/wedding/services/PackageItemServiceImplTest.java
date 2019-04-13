package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.wedding.model.PackageItem;
import com.grudzinski.docugen.wedding.repositories.PackageItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class PackageItemServiceImplTest {

    @Mock
    PackageItemRepository packageItemRepository;

    private PackageItemServiceImpl packageItemService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        packageItemService = new PackageItemServiceImpl(packageItemRepository);
    }

    @Test
    public void shouldReturnAllBundleItems() {
        Set<PackageItem> packageItems = new HashSet<>();
        packageItems.add(new PackageItem("Piano"));
        packageItems.add(new PackageItem("Violin"));
        packageItems.add(new PackageItem("String quartet"));

        when(packageItemRepository.findAll()).thenReturn(packageItems);

        List<PackageItem> packageItemsReturned = packageItemService.getPackageItems();
        assertEquals(3L, packageItemsReturned.size());
        verify(packageItemRepository).findAll();
        verify(packageItemRepository, never()).findById(anyLong());
    }

    @Test
    public void shouldFindById() {
        PackageItem packageItem = new PackageItem("Violin");
        packageItem.setId(3L);

        when(packageItemRepository.findById(anyLong())).thenReturn(Optional.of(packageItem));

        PackageItem packageItemReturned = packageItemService.findById(3L);

        assertNotNull("Null BundleItem returned", packageItemReturned);
        verify(packageItemRepository).findById(anyLong());
        verify(packageItemRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindById() {
        Optional<PackageItem> bundleItemOptional = Optional.empty();

        when(packageItemRepository.findById(anyLong())).thenReturn(bundleItemOptional);
        
        PackageItem packageItemReturned = packageItemService.findById(1L);
    }

    @Test
    public void shouldDeleteById() {
        Long idToDelete = 2L;
        packageItemService.deleteById(idToDelete);

        verify(packageItemRepository).deleteById(anyLong());
    }
}