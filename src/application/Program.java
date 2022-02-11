package application;

import domain.dao.DaoFactory;
import domain.dao.VendedorDao;
import domain.dao.impl.VendedorDaoJDBC;
import domain.model.Departamento;
import domain.model.Vendedor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Program {

    public static void main (String [] args){

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        Vendedor test = vendedorDao.findById(3);


        System.out.println(test);
    }
}
