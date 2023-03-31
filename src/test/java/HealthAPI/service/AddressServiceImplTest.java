package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.model.Address;
import HealthAPI.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddressServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Test
    void testSaveAddress() {
        when(addressRepository.save(Mockito.<Address>any())).thenReturn(new Address());
        Address address = new Address();
        assertSame(address, addressServiceImpl.saveAddress(address));
        verify(addressRepository).save(Mockito.<Address>any());
    }

}