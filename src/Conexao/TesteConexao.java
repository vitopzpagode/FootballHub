
package Conexao;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = ConexaoMySQL.conectar()) {

            System.out.println("Conexao realizada com sucesso!");
            System.out.println("Banco conectado: " + conexao.getCatalog());

        } catch (SQLException erro) {

            System.out.println("Erro ao conectar com o MySQL:");
            erro.printStackTrace();

        }
    }
}