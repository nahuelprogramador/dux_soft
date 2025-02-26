package com.dux.software.app_dux.duxsoftware.repository.equipo_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dux.software.app_dux.duxsoftware.model.equipo_model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

	List<Equipo> findByNombre(String nombre);
}
