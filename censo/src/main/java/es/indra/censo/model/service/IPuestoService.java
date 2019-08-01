package es.indra.censo.model.service;

import java.util.List;

import es.indra.censo.model.Puesto;

public interface IPuestoService {

	public List<Puesto> findAll();

	public void save(Puesto puesto);

	public void deletePuesto(Integer id);

	public Puesto findPuestoById(Integer id);

}
