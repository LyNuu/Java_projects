import jakarta.persistence.EntityNotFoundException;
import org.example.dto.CatDto;
import org.example.dto.mapper.CatMapping;
import org.example.model.Cat;
import org.example.model.color.Color;
import org.example.repository.CatRepository;
import org.example.service.CatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CatServiceTest {

    @Mock
    private CatRepository catRepository;

    @Mock
    private CatMapping catMapping;

    @InjectMocks
    private CatService catService;

    private CatDto catDto;
    private Cat cat;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        catDto = new CatDto();
        catDto.setId(1);
        catDto.setName("Whiskers");
        catDto.setBirthday("01.01.2020");
        catDto.setBreed("Siamese");
        catDto.setColor(Color.BLACK);

        cat = new Cat();
        cat.setId(1);
        cat.setName("Whiskers");
        cat.setBirthday("01.01.2020");
        cat.setBreed("Siamese");
        cat.setColor(Color.BLACK);
    }

    @Test
    public void testSave() {
        when(catMapping.toEntity(catDto)).thenReturn(cat);
        when(catRepository.save(cat)).thenReturn(cat);
        when(catMapping.toDto(cat)).thenReturn(catDto);

        CatDto result = catService.save(catDto);

        assertEquals(catDto, result);
    }

    @Test
    public void testDeleteById() {
        catService.deleteById(1);
        verify(catRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteByEntity() {
        when(catMapping.toEntity(catDto)).thenReturn(cat);
        catService.deleteByEntity(catDto);
        verify(catRepository).delete(cat);
    }

    @Test
    public void testDeleteAll() {
        catService.deleteAll();
        verify(catRepository).deleteAll();
    }

    @Test
    public void testUpdate() {
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));
        doNothing().when(catMapping).updateEntityFromDto(catDto, cat);
        when(catRepository.save(cat)).thenReturn(cat);
        when(catMapping.toDto(cat)).thenReturn(catDto);

        CatDto result = catService.update(catDto);

        assertEquals(catDto, result);
    }

    @Test
    public void testUpdateThrowsNotFound() {
        when(catRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> catService.update(catDto));
    }

    @Test
    public void testGetById() {
        when(catRepository.findById(1)).thenReturn(Optional.of(cat));
        when(catMapping.toDto(cat)).thenReturn(catDto);

        CatDto result = catService.getById(1);

        assertEquals(catDto, result);
    }

    @Test
    public void testGetByIdThrowsNotFound() {
        when(catRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> catService.getById(1));
    }

    @Test
    public void testGetAll() {
        List<Cat> cats = List.of(cat);
        List<CatDto> catDtos = List.of(catDto);

        when(catRepository.findAll()).thenReturn(cats);
        when(catMapping.toDto(cat)).thenReturn(catDto);

        List<CatDto> result = catService.getAll();
        assertEquals(catDtos, result);
    }
}
