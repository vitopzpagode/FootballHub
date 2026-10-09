
package DAO;

import DTO.UsuarioDTO;
import Conexao.ConexaoMySQL;
import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    public boolean cadastrar(UsuarioDTO u) throws SQLException {
        String sql = "INSERT INTO usuarios (nome,email,senha,perfil,ativo) VALUES (?,?,?,?,?)";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u.getNome());
            p.setString(2, u.getEmail());
            p.setString(3, u.getSenha());
            p.setString(4, u.getPerfil() == null ? "COMUM" : u.getPerfil());
            p.setBoolean(5, u.isAtivo());
            return p.executeUpdate() > 0;
        }
    }

    public List<UsuarioDTO> listar() throws SQLException {
        List<UsuarioDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nome";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        }
        return lista;
    }

    public UsuarioDTO buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    public boolean atualizar(UsuarioDTO u) throws SQLException {
        String sql = "UPDATE usuarios SET nome=?,email=?,senha=?,perfil=?,ativo=? WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u.getNome());
            p.setString(2, u.getEmail());
            p.setString(3, u.getSenha());
            p.setString(4, u.getPerfil());
            p.setBoolean(5, u.isAtivo());
            p.setInt(6, u.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    public UsuarioDTO buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email=?";
        try (Connection c = ConexaoMySQL.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? mapear(r) : null;
            }
        }
    }

    private UsuarioDTO mapear(ResultSet r) throws SQLException {
        UsuarioDTO u = new UsuarioDTO();
        u.setId(r.getInt("id"));
        u.setNome(r.getString("nome"));
        u.setEmail(r.getString("email"));
        u.setSenha(r.getString("senha"));
        u.setPerfil(r.getString("perfil"));
        u.setAtivo(r.getBoolean("ativo"));
        u.setCriadoEm(r.getTimestamp("criado_em"));
        return u;
    }
}