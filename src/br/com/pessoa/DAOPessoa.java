package br.com.pessoa;

import br.com.Utils.Functions;
import br.com.softtalk.SoftTalk;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPessoa {

    public int inserirPessoa(Pessoa pessoa) {
        try {
            String sql = "INSERT INTO pessoa (IDSETOR, NOME) "
                    + "VALUES ("
                    + pessoa.getIdsetor() + ",'"
                    + pessoa.getNome() + "')";
            
            PreparedStatement pstm;
            pstm = SoftTalk.conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.execute();
            
            ResultSet rs = pstm.getGeneratedKeys();
            rs.next();
            
            return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
            return Functions.FAILURE;
        }
    }

    public int atualizarPessoa(Pessoa pessoa) {
        try {
            String sql = "UPDATE INTO pessoa (IDSETOR, NOME) "
                    + "VALUES ("
                    + pessoa.getIdsetor() + ",'"
                    + pessoa.getNome() + "')"
                    + "WHERE IDPESSOA = " + pessoa.getIdpessoa() + ";";
            PreparedStatement pstm;

            pstm = SoftTalk.conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.execute();
            ResultSet rs = pstm.getGeneratedKeys();
            rs.next();

            return Functions.SUCCESS;
        } catch (SQLException ex) {
            Logger.getLogger(DAOPessoa.class.getName()).log(Level.SEVERE, null, ex);
            return Functions.FAILURE;
        }
    }

    public List<Pessoa> listarPessoas() throws SQLException {
        Pessoa pessoa;
        List<Pessoa> lista = new ArrayList();
        
        String sql = "SELECT * FROM pessoa;";
        Statement stm = SoftTalk.conexao.createStatement();
        
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            pessoa = new Pessoa();
            pessoa.setIdsetor(rs.getInt("IdSetor"));
            pessoa.setNome(rs.getString("Nome"));
            lista.add(pessoa);
        }
        return lista;
    }

    public Pessoa listaPessoa(int idPessoa) throws SQLException {
        Pessoa pessoa;
        String sql = "SELECT * FROM pessoa WHERE idpessoa = " + Integer.toString(idPessoa) + ";";
        
        Statement stm = SoftTalk.conexao.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        
        pessoa = new Pessoa();
        
        if (rs.next()) {
            pessoa.setIdsetor(rs.getInt("IdSetor"));
            pessoa.setNome(rs.getString("Nome"));
        }
        
        return pessoa;
    }
}