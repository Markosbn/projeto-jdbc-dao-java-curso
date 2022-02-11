package domain.dao.impl;

import db.DB;
import db.DbException;
import domain.dao.VendedorDao;
import domain.model.Departamento;
import domain.model.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendedorDaoJDBC implements VendedorDao {

    //declarado conexão com o banco + construtor para estar disponivel
    private Connection conn;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vendedor obj) {

    }

    @Override
    public void update(Vendedor obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Vendedor findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            //select para fazer a busca por id
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            //faz a validação, a Proxima linha (1 pois a primeira é zero)
            //se for vazia retorna nulo, se não ira setar os valores para os objetos de cada um
            if (rs.next()){
                Departamento dep = new Departamento();
                dep.setId(rs.getInt("departmentid"));
                dep.setNome(rs.getString("depname"));
                Vendedor vendedor = new Vendedor();
                vendedor.setId(rs.getInt("id"));
                vendedor.setNome(rs.getString("name"));
                vendedor.setEmail(rs.getString("email"));
                vendedor.setDataAniversario(rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate());
                vendedor.setSalario(rs.getDouble("basesalary"));
                vendedor.setDepartamento(dep);
                return vendedor;
            }
            return null;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Vendedor> findAll() {
        return null;
    }
}
