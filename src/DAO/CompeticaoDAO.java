
package DAO;

import DTO.CompeticaoDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class CompeticaoDAO {

    public boolean cadastrar(CompeticaoDTO x) throws SQLException {
        String sql = "INSERT INTO competicoes (nome,tipo,pais,descricao) VALUES (?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, x.getNome());
            p.setString(2, x.getTipo() == null ? "LIGA" : x.getTipo());
            p.setString(3, x.getPais());
            p.setString(4, x.getDescricao());
            return p.executeUpdate() > 0;
        }
    }

    public List<CompeticaoDTO> listar() throws SQLException {
        List<CompeticaoDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM competicoes ORDER BY nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public CompeticaoDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM competicoes WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(CompeticaoDTO x) throws SQLException {
        String sql = "UPDATE competicoes SET nome=?,tipo=?,pais=?,descricao=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, x.getNome());
            p.setString(2, x.getTipo());
            p.setString(3, x.getPais());
            p.setString(4, x.getDescricao());
            p.setInt(5, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM competicoes WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private CompeticaoDTO mapear(ResultSet r) throws SQLException {
        CompeticaoDTO x = new CompeticaoDTO();
        x.setId(r.getInt("id"));
        x.setNome(r.getString("nome"));
        x.setTipo(r.getString("tipo"));
        x.setPais(r.getString("pais"));
        x.setDescricao(r.getString("descricao"));
        x.setCriadoEm(r.getTimestamp("criado_em"));
        return x;
    }
}
