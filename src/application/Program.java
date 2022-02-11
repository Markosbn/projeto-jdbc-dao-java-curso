package application;

import domain.dao.DaoFactory;
import domain.dao.VendedorDao;
import domain.dao.impl.VendedorDaoJDBC;
import domain.model.Departamento;
import domain.model.Vendedor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Program {

    public static void main (String [] args){

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        Vendedor test = vendedorDao.findById(3);

        Departamento dep = new Departamento(2, null);

        List<Vendedor> test2 = vendedorDao.findByDepartamento(dep);

        System.out.println("Teste por departamento");
        for(Vendedor x : test2){
            System.out.println(x);
        }

        System.out.println("Teste por id");
        System.out.println(test);
    }
}
