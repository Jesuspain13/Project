package es.indra.censo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.indra.censo.model.errores.excel.ColumnaExcel;

public interface ICamposExcelDao extends JpaRepository<ColumnaExcel, Integer> {

}
