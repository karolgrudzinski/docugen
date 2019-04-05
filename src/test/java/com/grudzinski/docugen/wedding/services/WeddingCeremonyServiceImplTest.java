package com.grudzinski.docugen.wedding.services;

import com.grudzinski.docugen.base.exceptions.NotFoundException;
import com.grudzinski.docugen.customer.models.Customer;
import com.grudzinski.docugen.customer.repositories.CustomerRepository;
import com.grudzinski.docugen.wedding.model.WeddingCeremony;
import com.grudzinski.docugen.wedding.repositories.WeddingCeremonyRepository;
import com.grudzinski.docugen.wedding.repositories.WeddingCeremonySummaryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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

    @Mock
    CustomerRepository customerRepository;

    @Mock
    WeddingCeremonySummaryRepository weddingCeremonySummaryRepository;

    private WeddingCeremonyServiceImpl weddingCeremonyService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weddingCeremonyService = new WeddingCeremonyServiceImpl(weddingCeremonyRepository, customerRepository, weddingCeremonySummaryRepository);
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

    @Test
    public void shouldReturnProposedShortName() {
        WeddingCeremony weddingCeremony = new WeddingCeremony();
        weddingCeremony.setDateOfEvent(LocalDate.of(2019,02, 03));
        Customer customer = new Customer();
        weddingCeremony.setCustomer(customer);

        weddingCeremony.setPlaceOfEvent("Szczecin");
        customer.setName("Alexander Greatest");
        assertEquals("20190203-AlexaGreat-Szczec", weddingCeremonyService.getProposedShortName(weddingCeremony));

        weddingCeremony.setPlaceOfEvent("Ełk");
        assertEquals("20190203-AlexaGreat-Ełk", weddingCeremonyService.getProposedShortName(weddingCeremony));

        customer.setName("Joe Doe");
        assertEquals("20190203-JoeDoe-Ełk", weddingCeremonyService.getProposedShortName(weddingCeremony));

        customer.setName("");
        assertEquals("20190203--Ełk", weddingCeremonyService.getProposedShortName(weddingCeremony));

        weddingCeremony.setPlaceOfEvent("");
        assertEquals("20190203--", weddingCeremonyService.getProposedShortName(weddingCeremony));

        assertEquals("", weddingCeremonyService.getProposedShortName(new WeddingCeremony()));
    }

    @Test
    public void shouldDeleteById() {
        Long idToDelete = 2L;
        weddingCeremonyService.deleteById(idToDelete);

        verify(weddingCeremonyRepository).deleteById(anyLong());
    }
}