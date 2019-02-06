package com.grudzinski.docugen.services;

import com.grudzinski.docugen.exceptions.NotFoundException;
import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.grudzinski.docugen.repository.WeddingCeremonyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class WeddingCeremonyServiceImplTest {

    @Mock
    WeddingCeremonyRepository weddingCeremonyRepository;

    private WeddingCeremonyServiceImpl weddingCeremonyService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weddingCeremonyService = new WeddingCeremonyServiceImpl(weddingCeremonyRepository);
    }

    @Test
    public void shouldFindById() throws Exception {
        WeddingCeremony weddingCeremony1 = new WeddingCeremony();
        weddingCeremony1.setId(1L);
        weddingCeremony1.setPerformer("Artist1");

        when(weddingCeremonyRepository.findById(anyLong())).thenReturn(java.util.Optional.of(weddingCeremony1));

        WeddingCeremony weddingReturned = weddingCeremonyService.findById(1L);

        assertNotNull("Null WeddingCeremony document returned", weddingReturned);
        verify(weddingCeremonyRepository, times(1)).findById(anyLong());
        verify(weddingCeremonyRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotFindById() throws Exception {
        Optional<WeddingCeremony> weddingCeremonyOptional = Optional.empty();

        when(weddingCeremonyRepository.findById(anyLong())).thenReturn(weddingCeremonyOptional);

        WeddingCeremony weddingCeremony = weddingCeremonyService.findById(1L);
    }

    @Test
    public void shouldReturnAllWeddings() {
        List<WeddingCeremony> weddings = new ArrayList<>();
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());
        weddings.add(new WeddingCeremony());

        when(weddingCeremonyRepository.findAll()).thenReturn(weddings);

        List<WeddingCeremony> weddingsReturned = weddingCeremonyService.getWeddings();
        assertEquals(3L, weddingsReturned.size());
        verify(weddingCeremonyRepository).findAll();
        verify(weddingCeremonyRepository, never()).findById(anyLong());
    }
}