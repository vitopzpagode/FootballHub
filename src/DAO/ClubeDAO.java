
package DAO;

import DTO.ClubeDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class ClubeDAO {

    public boolean cadastrar(ClubeDTO x) throws SQLException {
        String sql = "INSERT INTO clubes (nome,sigla,cidade,pais,estadio,ano_fundacao) VALUES (?,?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, x.getNome());
            p.setString(2, x.getSigla());
            p.setString(3, x.getCidade());
            p.setString(4, x.getPais());
            p.setString(5, x.getEstadio());
            if (x.getAnoFundacao() == null) p.setNull(6, Types.SMALLINT);
            else p.setInt(6, x.getAnoFundacao());
            return p.executeUpdate() > 0;
        }
    }

    public List<ClubeDTO> listar() throws SQLException {
        List<ClubeDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM clubes ORDER BY nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public ClubeDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM clubes WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(ClubeDTO x) throws SQLException {
        String sql = "UPDATE clubes SET nome=?,sigla=?,cidade=?,pais=?,estadio=?,ano_fundacao=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, x.getNome());
            p.setString(2, x.getSigla());
            p.setString(3, x.getCidade());
            p.setString(4, x.getPais());
            p.setString(5, x.getEstadio());
            if (x.getAnoFundacao() == null) p.setNull(6, Types.SMALLINT);
            else p.setInt(6, x.getAnoFundacao());
            p.setInt(7, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM clubes WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private ClubeDTO mapear(ResultSet r) throws SQLException {
        ClubeDTO x = new ClubeDTO();
        x.setId(r.getInt("id"));
        x.setNome(r.getString("nome"));
        x.setSigla(r.getString("sigla"));
        x.setCidade(r.getString("cidade"));
        x.setPais(r.getString("pais"));
        x.setEstadio(r.getString("estadio"));
        int ano = r.getInt("ano_fundacao");
        x.setAnoFundacao(r.wasNull() ? null : ano);
        x.setCriadoEm(r.getTimestamp("criado_em"));
        return x;
    }
}
