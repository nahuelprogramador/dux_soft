package com.dux.software.app_dux.duxsoftware.service.equipo_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dux.software.app_dux.duxsoftware.model.equipo_model.Equipo;
import com.dux.software.app_dux.duxsoftware.repository.equipo_repository.EquipoRepository;

@Service
public class EquipoService {

	@Autowired
	private EquipoRepository equipoRepository;

	public List<Equipo> getAllEquipos() {
		return equipoRepository.findAll();
	}

	public Optional<Equipo> getEquipoById(Long id) {
		return equipoRepository.findById(id);
	}

	public List<Equipo> buscarEquiposPorNombre(String nombre) {
		return equipoRepository.findByNombre(nombre);
	}

	public Equipo crearEquipo(Equipo equipo) {
		return equipoRepository.save(equipo);
	}

	public Equipo actualizarEquipo(Long id, Equipo equipoActualizado) {
		if (equipoRepository.existsById(id)) {
			equipoActualizado.setId(id);
			return equipoRepository.save(equipoActualizado);
		}
		return null;
	}

	public boolean eliminarEquipo(Long id) {
		if (equipoRepository.existsById(id)) {
			equipoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
