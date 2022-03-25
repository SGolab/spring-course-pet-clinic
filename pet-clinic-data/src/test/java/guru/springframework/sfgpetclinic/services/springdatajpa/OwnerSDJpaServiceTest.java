package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    Owner returnOwner;
    String LAST_NAME = "Smith";
    Long ID = 1L;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> preparedSet = new HashSet<>();
        preparedSet.add(Owner.builder().id(1L).build());
        preparedSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(preparedSet);

        Set<Owner> foundSet = ownerService.findAll();

        assertNotNull(foundSet);
        assertEquals(2, foundSet.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(ID)).thenReturn(Optional.ofNullable(returnOwner));

        Owner foundOwner = ownerService.findById(ID);

        assertNotNull(foundOwner);
        verify(ownerRepository, times(1)).findById(any());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(ID)).thenReturn(Optional.empty());

        Owner foundOwner = ownerService.findById(ID);

        assertNull(foundOwner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(ID).build();

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = ownerService.save(ownerToSave);

        assertNotNull(savedOwner);

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerService.delete(returnOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(ID);

        verify(ownerRepository, times(1)).deleteById(any());

    }

    @Test
    void findByLastName() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner smith = ownerService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }
}