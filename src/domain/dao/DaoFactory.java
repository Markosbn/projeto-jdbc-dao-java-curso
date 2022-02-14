package domain.dao;

import db.DB;
import domain.dao.impl.DepartamentoDaoJDBC;
import domain.dao.impl.VendedorDaoJDBC;

public class DaoFactory {


    public static VendedorDao createVendedorDao(){
        //passado o metodo de conexão para a classe jdbc
        return new VendedorDaoJDBC(DB.getConnection());
    }

    public static DepartamentoDao createDepartamentoDao(){
        return new DepartamentoDaoJDBC(DB.getConnection());
    }
}
