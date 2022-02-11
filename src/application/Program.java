package application;

import domain.dao.DaoFactory;
import domain.dao.VendedorDao;
import domain.model.Departamento;
import domain.model.Vendedor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Program {

    public static void main (String [] args){

        Departamento test = new Departamento( 1 , "teste");

        Vendedor testV = new Vendedor(1, "vendedor", "vendedor@mail.com", LocalDate.parse("28/01/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")), 2000.00, test);

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println(test + "\n" + testV);
    }
}
