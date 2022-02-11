package domain.dao;

import domain.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

    public static VendedorDao createVendedorDao(){
        return new VendedorDaoJDBC();
    }
}
