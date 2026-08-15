package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    
    public void cadastrar(Cliente cliente) throws SQLException{
        String command = """
                INSERT INTO cliente 
                (nome, cpfCnpj,endereco,cidade,estado)
                VALUES 
                (?,?,?,?,?)
                """; 

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

                prep.setString(1, cliente.getNome());
                prep.setString(2, cliente.getCpfCnpj());
                prep.setString(3, cliente.getEndereco());
                prep.setString(4, cliente.getCidade());
                prep.setString(5, cliente.getEstado());

                prep.executeUpdate();
            }
    }

    public List<Cliente> listar() throws SQLException{
        String command = """
                SELECT * FROM
                cliente;
                """;
        
        List<Cliente> clientes = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(command)){

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpfCnpj = rs.getString("cpfCnpj");
                String endereco = rs.getString("endereco");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                
                Cliente cliente = new Cliente(id,nome, cpfCnpj, endereco, cidade, estado);
                clientes.add(cliente);
            }
        }
        
        return clientes;
    }

    public Cliente buscar(int id){
        String command = """
            SELECT * FROM cliente WHERE id = ?
            """;

        int novoId = 0;
        String nome = "";
        String cpfCnpj = "";
        String endereco = "";
        String cidade = "";
        String estado = "";

        try(Connection conn =  ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(command)){

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                novoId = rs.getInt("id");
                nome = rs.getString("nome");
                cpfCnpj = rs.getString("cpfCnpj");
                endereco = rs.getString("endereco");
                cidade = rs.getString("cidade");
                estado = rs.getString("estado");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return new Cliente(novoId, nome, cpfCnpj, endereco, cidade, estado);
    }
}
