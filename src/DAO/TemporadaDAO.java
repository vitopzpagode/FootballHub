
package DAO;

import DTO.TemporadaDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class TemporadaDAO {

    public boolean cadastrar(TemporadaDTO x) throws SQLException {
        String sql = "INSERT INTO temporadas (competicao_id,nome,data_inicio,data_fim,status) VALUES (?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, x.getCompeticaoId());
            p.setString(2, x.getNome());
            p.setDate(3, x.getDataInicio());
            p.setDate(4, x.getDataFim());
            p.setString(5, x.getStatus() == null ? "PLANEJADA" : x.getStatus());
            return p.executeUpdate() > 0;
        }
    }

    public List<TemporadaDTO> listar() throws SQLException {
        List<TemporadaDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM temporadas ORDER BY nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public TemporadaDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM temporadas WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(TemporadaDTO x) throws SQLException {
        String sql = "UPDATE temporadas SET competicao_id=?,nome=?,data_inicio=?,data_fim=?,status=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, x.getCompeticaoId());
            p.setString(2, x.getNome());
            p.setDate(3, x.getDataInicio());
            p.setDate(4, x.getDataFim());
            p.setString(5, x.getStatus());
            p.setInt(6, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM temporadas WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private TemporadaDTO mapear(ResultSet r) throws SQLException {
        TemporadaDTO x = new TemporadaDTO();
        x.setId(r.getInt("id"));
        x.setCompeticaoId(r.getInt("competicao_id"));
        x.setNome(r.getString("nome"));
        x.setDataInicio(r.getDate("data_inicio"));
        x.setDataFim(r.getDate("data_fim"));
        x.setStatus(r.getString("status"));
        return x;
    }
}