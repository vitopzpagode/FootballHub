
package DAO;

import DTO.EventoPartidaDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class EventoPartidaDAO {

    public boolean cadastrar(EventoPartidaDTO x) throws SQLException {
        String sql = "INSERT INTO eventos_partida (partida_id,clube_id,jogador_id,jogador_substituido_id,minuto,tipo,descricao) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            return p.executeUpdate() > 0;
        }
    }

    public List<EventoPartidaDTO> listar() throws SQLException {
        List<EventoPartidaDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM eventos_partida ORDER BY partida_id,minuto");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public EventoPartidaDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM eventos_partida WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(EventoPartidaDTO x) throws SQLException {
        String sql = "UPDATE eventos_partida SET partida_id=?,clube_id=?,jogador_id=?,jogador_substituido_id=?,minuto=?,tipo=?,descricao=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            p.setInt(8, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM eventos_partida WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private void definirParametros(PreparedStatement p, EventoPartidaDTO x) throws SQLException {
        p.setInt(1, x.getPartidaId());
        p.setInt(2, x.getClubeId());
        if (x.getJogadorId() == null) p.setNull(3, Types.INTEGER);
        else p.setInt(3, x.getJogadorId());
        if (x.getJogadorSubstituidoId() == null) p.setNull(4, Types.INTEGER);
        else p.setInt(4, x.getJogadorSubstituidoId());
        p.setInt(5, x.getMinuto());
        p.setString(6, x.getTipo());
        p.setString(7, x.getDescricao());
    }

    private EventoPartidaDTO mapear(ResultSet r) throws SQLException {
        EventoPartidaDTO x = new EventoPartidaDTO();
        x.setId(r.getInt("id"));
        x.setPartidaId(r.getInt("partida_id"));
        x.setClubeId(r.getInt("clube_id"));
        int jogador = r.getInt("jogador_id");
        x.setJogadorId(r.wasNull() ? null : jogador);
        int substituido = r.getInt("jogador_substituido_id");
        x.setJogadorSubstituidoId(r.wasNull() ? null : substituido);
        x.setMinuto(r.getInt("minuto"));
        x.setTipo(r.getString("tipo"));
        x.setDescricao(r.getString("descricao"));
        return x;
    }
}