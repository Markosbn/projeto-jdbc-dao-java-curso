package domain.dao;

import domain.model.Departamento;
import domain.model.Vendedor;

import java.util.List;

public interface VendedorDao {

    void insert (Vendedor obj);
    void update(Vendedor obj);
    void deleteById(Integer id);
    Vendedor findById(Integer id);
    List<Vendedor> findAll();
    List<Vendedor> findByDepartamento(Departamento departamento);


}
