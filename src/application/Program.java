package application;

import domain.dao.DaoFactory;
import domain.dao.VendedorDao;
import domain.model.Departamento;
import domain.model.Vendedor;


import java.util.List;

public class Program {

    public static void main (String [] args){

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        Vendedor test = vendedorDao.findById(3);

        Departamento dep = new Departamento(2, null);

        List<Vendedor> test2 = vendedorDao.findByDepartamento(dep);

        List<Vendedor> test3 = vendedorDao.findAll();
        System.out.println("Teste por all");
        for(Vendedor x : test3){
            System.out.println(x);
        }

        System.out.println("Teste por departamento");
        for(Vendedor x : test2){
            System.out.println(x);
        }

        System.out.println("Teste por id");
        System.out.println(test);
    }
}
