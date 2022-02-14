package domain.dao.impl;

import db.DB;
import db.DbException;
import domain.dao.DaoFactory;
import domain.dao.DepartamentoDao;
import domain.model.Departamento;
import org.postgresql.shaded.com.ongres.scram.common.stringprep.StringPreparation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDaoJDBC implements DepartamentoDao {

    private Connection conn;

    public DepartamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departamento obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO department \n" +
                    "(Name)\n" +
                    "VALUES \n" +
                    "(?)",  Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

            int linhasAfetadas = st.executeUpdate();

            if(linhasAfetadas > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }else {
                    throw new DbException("Unexpectec error! Nenhuma linha alterada");
                }
                DB.closeResultSet(rs);
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Departamento obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE department " +
                    "SET Name = ?" +
                    "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM department " +
                    "WHERE Id = ?");
            st.setInt(1, id);

            int linhasAfetadas = st.executeUpdate();

            if(linhasAfetadas == 0){
                throw new DbException("Erro: ID não existe");
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
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
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * from department \n" +
                    "order by name ");

            rs = st.executeQuery();

            List<Departamento> departamentoList = new ArrayList<>();
            while (rs.next()) {
                Departamento dep = instanciarDepartamento(rs);
                departamentoList.add(dep);
            }
            return departamentoList;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
