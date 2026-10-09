
package DAO;

import DTO.JogadorDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class JogadorDAO {

    public boolean cadastrar(JogadorDTO x) throws SQLException {
        String sql = "INSERT INTO jogadores (clube_id,nome,data_nascimento,nacionalidade,posicao,numero_camisa,altura,valor_mercado,status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            return p.executeUpdate() > 0;
        }
    }

    public List<JogadorDTO> listar() throws SQLException {
        List<JogadorDTO> lista = new ArrayList<>();
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM jogadores ORDER BY nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public JogadorDTO buscarPorId(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("SELECT * FROM jogadores WHERE id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(JogadorDTO x) throws SQLException {
        String sql = "UPDATE jogadores SET clube_id=?,nome=?,data_nascimento=?,nacionalidade=?,posicao=?,numero_camisa=?,altura=?,valor_mercado=?,status=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            definirParametros(p, x);
            p.setInt(10, x.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement("DELETE FROM jogadores WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    private void definirParametros(PreparedStatement p, JogadorDTO x) throws SQLException {
        if (x.getClubeId() == null) p.setNull(1, Types.INTEGER);
        else p.setInt(1, x.getClubeId());
        p.setString(2, x.getNome());
        if (x.getDataNascimento() == null) p.setNull(3, Types.DATE);
        else p.setDate(3, x.getDataNascimento());
        p.setString(4, x.getNacionalidade());
        p.setString(5, x.getPosicao());
        if (x.getNumeroCamisa() == null) p.setNull(6, Types.INTEGER);
        else p.setInt(6, x.getNumeroCamisa());
        if (x.getAltura() == null) p.setNull(7, Types.DECIMAL);
        else p.setBigDecimal(7, x.getAltura());
        p.setBigDecimal(8, x.getValorMercado() == null
                ? new java.math.BigDecimal("0.00") : x.getValorMercado());
        p.setString(9, x.getStatus() == null ? "ATIVO" : x.getStatus());
    }

    private JogadorDTO mapear(ResultSet r) throws SQLException {
        JogadorDTO x = new JogadorDTO();
        x.setId(r.getInt("id"));
        int clube = r.getInt("clube_id");
        x.setClubeId(r.wasNull() ? null : clube);
        x.setNome(r.getString("nome"));
        x.setDataNascimento(r.getDate("data_nascimento"));
        x.setNacionalidade(r.getString("nacionalidade"));
        x.setPosicao(r.getString("posicao"));
        int camisa = r.getInt("numero_camisa");
        x.setNumeroCamisa(r.wasNull() ? null : camisa);
        x.setAltura(r.getBigDecimal("altura"));
        x.setValorMercado(r.getBigDecimal("valor_mercado"));
        x.setStatus(r.getString("status"));
        x.setCriadoEm(r.getTimestamp("criado_em"));
        return x;
    }
}