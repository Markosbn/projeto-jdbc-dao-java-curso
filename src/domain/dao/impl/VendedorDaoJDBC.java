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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                //chamado a função auxiliar para instanciar.
                Departamento dep = instanciarDepartamento(rs);
                Vendedor vendedor = instanciarVendedor(rs, dep);
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

    //alternado toda a instanciação para uma função auxiliar
    private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(rs.getInt("id"));
        vendedor.setNome(rs.getString("name"));
        vendedor.setEmail(rs.getString("email"));
        vendedor.setDataAniversario(rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate());
        vendedor.setSalario(rs.getDouble("basesalary"));
        vendedor.setDepartamento(dep);
        return vendedor;
    }
    //alternado toda a instanciação para uma função auxiliar
    private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(rs.getInt("departmentid"));
        dep.setNome(rs.getString("depname"));
        return dep;
    }

    @Override
    public List<Vendedor> findAll() {
        return null;
    }

    @Override
    public List<Vendedor> findByDepartamento(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name");
            st.setInt(1,departamento.getId());
            rs = st.executeQuery();

            //lista para receber os vendedores do select
            List<Vendedor> vendedorList = new ArrayList<>();
            //MAP criado para fazer a validação e não ter a duplicidade do departamento.
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()){

                //aqui o map receber o id buscado
                Departamento dep = map.get(rs.getInt("departmentid"));

                //se for nulo estancia o departamento, se ja existir a chave ele ignora e
                // instancia o departamento que ja existe no vendedor
                if (dep == null){
                    dep = instanciarDepartamento(rs);
                    map.put(rs.getInt("departmentid"), dep);
                }

                Vendedor vendedor = instanciarVendedor(rs, dep);
                vendedorList.add(vendedor);
            }
            return vendedorList;
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
