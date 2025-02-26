package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dux.software.app_dux.duxsoftware.repository.equipo_repository.EquipoRepository;
import com.dux.software.app_dux.duxsoftware.service.equipo_service.EquipoService;
import com.dux.software.app_dux.AppDuxApplication;
import com.dux.software.app_dux.duxsoftware.model.equipo_model.*;

@SpringBootTest(classes = AppDuxApplication.class)
public class EquipoServiceTest {
 
	 @Autowired
	 private EquipoService equipoService; 
	
	@MockBean
    private EquipoRepository equipoRepository;
	
    @Test
    public void testGetAllEquipos() {
   
    	List<Equipo> equipos = Arrays.asList(new Equipo(1L, "Real Madrid", "La Liga", "España"),
                new Equipo(2L, "FC Barcelona", "La Liga", "España"));
    	
    	 Mockito.when(equipoRepository.findAll()).thenReturn(equipos);
    	 
         List<Equipo> result = equipoService.getAllEquipos();
         assertNotNull(result);
         assertEquals(2, result.size()); 
         assertEquals("Real Madrid", result.get(0).getNombre()); 
         System.out.println("Equipos obtenidos: " + equipos);
         Mockito.verify(equipoRepository, Mockito.times(1)).findAll();
    }
    
    @Test
    public void testGetEquipoById() {
      
        Equipo equipo = new Equipo(1L, "Real Madrid", "La Liga", "España");

        
        Mockito.when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

      
        Optional<Equipo> result = equipoService.getEquipoById(1L);
        assertTrue(result.isPresent());
        assertEquals("Real Madrid", result.get().getNombre());
    }
    

    @Test
    public void testBuscarEquiposPorNombre() {
        
        List<Equipo> equipos = Arrays.asList(
            new Equipo(1L, "Real Madrid", "La Liga", "España"),
            new Equipo(2L, "FC Barcelona", "La Liga", "España")
        );

       
        Mockito.when(equipoRepository.findByNombre("Real")).thenReturn(equipos);

       
        List<Equipo> result = equipoService.buscarEquiposPorNombre("Real");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getNombre().contains("Real")));
    }

    @Test
    public void testCrearEquipo() {
        Equipo nuevoEquipo = new Equipo(null, "Nuevo Equipo FC", "Nueva Liga", "Nuevo País");
        Equipo equipoGuardado = new Equipo(3L, "Nuevo Equipo FC", "Nueva Liga", "Nuevo País");

      
        Mockito.when(equipoRepository.save(nuevoEquipo)).thenReturn(equipoGuardado);

      
        Equipo result = equipoService.crearEquipo(nuevoEquipo);
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Nuevo Equipo FC", result.getNombre());
    }

    @Test
    public void testEliminarEquipo() {
    
        Equipo equipo = new Equipo(1L, "Real Madrid", "La Liga", "España");

        Mockito.when(equipoRepository.existsById(1L)).thenReturn(true);
      
        Mockito.doNothing().when(equipoRepository).deleteById(1L);

        equipoService.eliminarEquipo(1L);
        Mockito.verify(equipoRepository, Mockito.times(1)).deleteById(1L);
    }
    
	
}
