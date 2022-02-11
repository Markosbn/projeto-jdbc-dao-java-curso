package application;

import domain.dao.DaoFactory;
import domain.dao.VendedorDao;
import domain.model.Departamento;
import domain.model.Vendedor;


import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main (String [] args){

        Scanner sc = new Scanner(System.in);

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        Vendedor test = vendedorDao.findById(3);

        Departamento dep = new Departamento(2, null);

        List<Vendedor> test2 = vendedorDao.findByDepartamento(dep);

        List<Vendedor> test3 = vendedorDao.findAll();

        Vendedor novoVendedor = new Vendedor(null, "Marcos", "marcos@gmail.com", LocalDate.now(), 3000.00, dep);
        vendedorDao.insert(novoVendedor);
        System.out.println("-----------------INserido: " + novoVendedor.getId());


        System.out.println("----------------------Teste por all");
        for(Vendedor x : test3){
            System.out.println(x);
        }

        System.out.println("-----------Teste por departamento");
        for(Vendedor x : test2){
            System.out.println(x);
        }

        System.out.println("-----------Teste por id");
        System.out.println(test);

        System.out.println("-----------_Teste update");
        test = vendedorDao.findById(1);
        test.setNome("Marcos Schulz");
        vendedorDao.update(test);
        System.out.println("update finalizado");


        System.out.println("----------teste 6 delete");
        System.out.println("Informe o id a deletar: ");
        int delete = sc.nextInt();
        vendedorDao.deleteById(delete);
    }
}
