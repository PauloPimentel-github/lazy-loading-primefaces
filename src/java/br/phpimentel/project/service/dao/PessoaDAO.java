package br.phpimentel.project.service.dao;

import br.phpimentel.project.model.PessoaModel;
import br.phpimentel.project.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phpimentel
 */
public class PessoaDAO {
    
    public List<PessoaModel> listarPessoaFilter(int first, int pageSize, String sortField, boolean asc) {
        List<PessoaModel> listPessoaModel = new ArrayList<>();
        String order = " asc";
        
        if (sortField != null) {
            if (!asc) {
               order = " desc";     
            }
        }
        
        String sql = " SELECT id, nome, idade " +
                        " FROM ( SELECT id, nome, idade, row_number() over (order by " + sortField + order + ") rowRank " +
                     " FROM hr.pessoa )" +
                     " WHERE rowRank BETWEEN ? AND ? ";
        try {
            Connection connection = ConnectionFactory.getConnection();
            
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setInt(1, first);
            ps.setInt(2, pageSize);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Pessoa: " + rs.getString("nome"));
                PessoaModel pessoaModel = new PessoaModel();
                pessoaModel.setId(rs.getLong("id"));
                pessoaModel.setNome(rs.getString("nome"));
                pessoaModel.setIdade(rs.getInt("idade"));
                listPessoaModel.add(pessoaModel);
            }
            ConnectionFactory.closeConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPessoaModel;
    }
    
    public int getRowCount() {
        int total = 0;
        try {
            String sql = "select count(id)as total from hr.pessoa";
            Connection connection = ConnectionFactory.getConnection();
            
            PreparedStatement ps = connection.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
            ConnectionFactory.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
    public List<PessoaModel> getListPessoa() {
        List<PessoaModel> listPessoaModel = new ArrayList<>();
        String sql = "select * from hr.pessoa";
        Connection connection = ConnectionFactory.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                PessoaModel pessoaModel = new PessoaModel();
                pessoaModel.setId(rs.getLong("id"));
                pessoaModel.setNome(rs.getString("nome"));
                pessoaModel.setIdade(rs.getInt("idade"));
                listPessoaModel.add(pessoaModel);
            }
            ConnectionFactory.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listPessoaModel;
    }
    
    public void save(PessoaModel pessoaModel) {
        String sql = "insert into hr.pessoa(id, nome, idade)  values(?, ?, ?)";
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = connection.prepareCall(sql);
            ps.setLong(1, pessoaModel.getId());
            ps.setString(2,  pessoaModel.getNome());
            ps.setInt(3, pessoaModel.getIdade());
            ps.execute();
            ConnectionFactory.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
