package com.dux.software.app_dux.duxsoftware.controller.equipo_controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dux.software.app_dux.duxsoftware.model.equipo_model.Equipo;
import com.dux.software.app_dux.duxsoftware.service.equipo_service.EquipoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/equipos")
@Tag(name = "End-Point de equipos", 
description = "Aqui tenemos los end-point para trabajar con el listado de equipos que tenemos en la base de datos")
public class EquipoController {

	@Autowired
    private EquipoService equipoService;

	@Operation(
		    summary = "Obtener todos los equipos",
		    description = "Devuelve una lista de todos los equipos disponibles en la base de datos.",
		    tags = {"Equipos"},
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Lista de equipos recuperada correctamente",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = Equipo.class)
		                
		            )
		        )
		    }
		)
    @GetMapping
    public List<Equipo> obtenerTodosLosEquipos() {
        return equipoService.getAllEquipos();
    }

	@Operation(
		    summary = "Obtener un equipo por ID",
		    description = "Devuelve un equipo específico según el ID proporcionado.",
		    tags = {"Equipos"},
		    parameters = {
		        @Parameter(
		            name = "id",
		            description = "ID del equipo a obtener",
		            required = true,
		            in = ParameterIn.PATH,
		            schema = @Schema(type = "integer", example = "1")
		        )
		    },
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Equipo encontrado",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = Equipo.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Equipo no encontrado",
		            content = @Content(
		                mediaType = "application/json"
		            )
		        )
		    }
		)
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Long id) {
        Optional<Equipo> equipo = equipoService.getEquipoById(id);
        return equipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

	@Operation(
		    summary = "Buscar equipos por nombre",
		    description = "Permite buscar equipos en la base de datos por su nombre.",
		    tags = {"Equipos"},
		    parameters = {
		        @Parameter(
		            name = "nombre",
		            description = "Nombre del equipo a buscar",
		            required = true,
		            in = ParameterIn.QUERY,
		            schema = @Schema(type = "string", example = "Barcelona")
		        )
		    },
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Equipos encontrados",
		            content = @Content(
		                mediaType = "application/json",
		                    schema = @Schema(implementation = Equipo.class)
		                
		            )
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "No se encontraron equipos con el nombre proporcionado",
		            content = @Content(
		                mediaType = "application/json"
		            )
		        )
		    }
		)
    @GetMapping("/buscar")
    public List<Equipo> buscarEquipos(@RequestParam String nombre) {
        return equipoService.buscarEquiposPorNombre(nombre);
    }

    @Operation(
    	    summary = "Crear un nuevo equipo",
    	    description = "Crea un nuevo equipo en la base de datos.",
    	    tags = {"Equipos"},
    	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
    	        description = "Objeto equipo que contiene la información del nuevo equipo",
    	        required = true,
    	        content = @Content(
    	            mediaType = "application/json",
    	            schema = @Schema(implementation = Equipo.class)
    	        )
    	    ),
    	    responses = {
    	        @ApiResponse(
    	            responseCode = "201",
    	            description = "Equipo creado correctamente",
    	            content = @Content(
    	                mediaType = "application/json",
    	                schema = @Schema(implementation = Equipo.class)
    	            )
    	        ),
    	        @ApiResponse(
    	            responseCode = "400",
    	            description = "Solicitud inválida",
    	            content = @Content(
    	                mediaType = "application/json"
    	            )
    	        )
    	    }
    	)
    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        Equipo nuevoEquipo = equipoService.crearEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
    }

    @Operation(
    	    summary = "Actualizar un equipo",
    	    description = "Actualiza la información de un equipo existente.",
    	    tags = {"Equipos"},
    	    parameters = {
    	        @Parameter(
    	            name = "id",
    	            description = "ID del equipo a actualizar",
    	            required = true,
    	            in = ParameterIn.PATH,
    	            schema = @Schema(type = "integer", example = "1")
    	        )
    	    },
    	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
    	        description = "Objeto equipo con la nueva información",
    	        required = true,
    	        content = @Content(
    	            mediaType = "application/json",
    	            schema = @Schema(implementation = Equipo.class)
    	        )
    	    ),
    	    responses = {
    	        @ApiResponse(
    	            responseCode = "200",
    	            description = "Equipo actualizado correctamente",
    	            content = @Content(
    	                mediaType = "application/json",
    	                schema = @Schema(implementation = Equipo.class)
    	            )
    	        ),
    	        @ApiResponse(
    	            responseCode = "404",
    	            description = "Equipo no encontrado",
    	            content = @Content(
    	                mediaType = "application/json"
    	            )
    	        )
    	    }
    	)
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        Equipo equipoActualizado = equipoService.actualizarEquipo(id, equipo);
        return equipoActualizado != null ? ResponseEntity.ok(equipoActualizado)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
    	    summary = "Eliminar un equipo",
    	    description = "Elimina un equipo de la base de datos.",
    	    tags = {"Equipos"},
    	    parameters = {
    	        @Parameter(
    	            name = "id",
    	            description = "ID del equipo a eliminar",
    	            required = true,
    	            in = ParameterIn.PATH,
    	            schema = @Schema(type = "integer", example = "1")
    	        )
    	    },
    	    responses = {
    	        @ApiResponse(
    	            responseCode = "204",
    	            description = "Equipo eliminado correctamente"
    	        ),
    	        @ApiResponse(
    	            responseCode = "404",
    	            description = "Equipo no encontrado",
    	            content = @Content(
    	                mediaType = "application/json"
    	            )
    	        )
    	    }
    	)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        boolean eliminado = equipoService.eliminarEquipo(id);
        return eliminado ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
