
package DAO;

import DTO.PartidaDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class PartidaDAO {

    public boolean cadastrar(PartidaDTO x) throws SQLException {
        String sql = "INSERT INTO partidas (temporada_id,clube_mandante_id,clube_visitante_id,data_hora,estadio,gols_mandante,gols_visitante,status,rodada) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            return p.executeUpdate() > 0;
        }
    }

    public List<PartidaDTO> listar() throws SQLException {
        List<PartidaDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM partidas ORDER BY data_hora");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public PartidaDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM partidas WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(PartidaDTO x) throws SQLException {
        String sql = "UPDATE partidas SET temporada_id=?,clube_mandante_id=?,clube_visitante_id=?,data_hora=?,estadio=?,gols_mandante=?,gols_visitante=?,status=?,rodada=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            p.setInt(10, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM partidas WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private void definirParametros(PreparedStatement p, PartidaDTO x) throws SQLException {
        p.setInt(1, x.getTemporadaId());
        p.setInt(2, x.getClubeMandanteId());
        p.setInt(3, x.getClubeVisitanteId());
        p.setTimestamp(4, x.getDataHora());
        p.setString(5, x.getEstadio());
        p.setInt(6, x.getGolsMandante());
        p.setInt(7, x.getGolsVisitante());
        p.setString(8, x.getStatus() == null ? "AGENDADA" : x.getStatus());
        if (x.getRodada() == null) p.setNull(9, Types.INTEGER);
        else p.setInt(9, x.getRodada());
    }

    private PartidaDTO mapear(ResultSet r) throws SQLException {
        PartidaDTO x = new PartidaDTO();
        x.setId(r.getInt("id"));
        x.setTemporadaId(r.getInt("temporada_id"));
        x.setClubeMandanteId(r.getInt("clube_mandante_id"));
        x.setClubeVisitanteId(r.getInt("clube_visitante_id"));
        x.setDataHora(r.getTimestamp("data_hora"));
        x.setEstadio(r.getString("estadio"));
        x.setGolsMandante(r.getInt("gols_mandante"));
        x.setGolsVisitante(r.getInt("gols_visitante"));
        x.setStatus(r.getString("status"));
        int rodada = r.getInt("rodada");
        x.setRodada(r.wasNull() ? null : rodada);
        return x;
    }
}