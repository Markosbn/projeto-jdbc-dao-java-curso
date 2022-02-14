package domain.dao.impl;

import db.DbException;
import domain.dao.DaoFactory;
import domain.dao.DepartamentoDao;
import domain.model.Departamento;
import org.postgresql.shaded.com.ongres.scram.common.stringprep.StringPreparation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartamentoDaoJDBC implements DepartamentoDao {

    private Connection conn;

    public DepartamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departamento obj) {

    }

    @Override
    public void update(Departamento obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Departamento findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * from department \n" +
                    "WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Departamento dep = instanciarDepartamento(rs);
                return dep;
            }else {
                return null;
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(rs.getInt("id"));
        dep.setNome(rs.getString("name"));
        return dep;
    }

    @Override
    public List<Departamento> findAll() {
        return null;
    }
}
