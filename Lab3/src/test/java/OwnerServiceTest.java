import org.example.service.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.example.dto.OwnerDto;
import org.example.dto.mapper.OwnerMapping;
import org.example.model.Owner;
import org.example.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerMapping ownerMapping;

    @InjectMocks
    private OwnerService ownerService;

    private OwnerDto ownerDto;
    private Owner owner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ownerDto = new OwnerDto();
        ownerDto.setId(1);
        ownerDto.setName("Test Owner");
        ownerDto.setBirthday("01.01.2000");

        owner = new Owner();
        owner.setId(1);
        owner.setName("Test Owner");
        owner.setBirthday("01.01.2000");
    }

    @Test
    public void testSave() {
        when(ownerMapping.toEntity(ownerDto)).thenReturn(owner);
        when(ownerRepository.save(owner)).thenReturn(owner);
        when(ownerMapping.toDto(owner)).thenReturn(ownerDto);

        OwnerDto result = ownerService.save(ownerDto);

        assertEquals(ownerDto, result);
    }

    @Test
    public void testDeleteById() {
        ownerService.deleteById(1);
        verify(ownerRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteByEntity() {
        when(ownerMapping.toEntity(ownerDto)).thenReturn(owner);
        ownerService.deleteByEntity(ownerDto);
        verify(ownerRepository).delete(owner);
    }

    @Test
    public void testDeleteAll() {
        ownerService.deleteAll();
        verify(ownerRepository).deleteAll();
    }

    @Test
    public void testUpdate() {
        when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
        doNothing().when(ownerMapping).updateEntityFromDto(ownerDto, owner);
        when(ownerRepository.save(owner)).thenReturn(owner);
        when(ownerMapping.toDto(owner)).thenReturn(ownerDto);

        OwnerDto result = ownerService.update(ownerDto);

        assertEquals(ownerDto, result);
    }

    @Test
    public void testUpdateThrowsNotFound() {
        when(ownerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ownerService.update(ownerDto));
    }

    @Test
    public void testGetById() {
        when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
        when(ownerMapping.toDto(owner)).thenReturn(ownerDto);

        OwnerDto result = ownerService.getById(1);

        assertEquals(ownerDto, result);
    }

    @Test
    public void testGetByIdThrowsNotFound() {
        when(ownerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ownerService.getById(1));
    }

    @Test
    public void testGetAll() {
        List<Owner> owners = List.of(owner);
        List<OwnerDto> ownerDtos = List.of(ownerDto);

        when(ownerRepository.findAll()).thenReturn(owners);
        when(ownerMapping.toDto(owner)).thenReturn(ownerDto);

        List<OwnerDto> result = ownerService.getAll();
        assertEquals(ownerDtos, result);
    }
}

