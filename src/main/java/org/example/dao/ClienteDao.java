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
    
    public void cadastrarCliente(Cliente cliente) throws SQLException{
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

    public List<Cliente> listarCliente() throws SQLException{
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

    public Cliente buscarCliente(int id){
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

    public Cliente clienteMaiorVolume() throws SQLException{
        String query = """
                SELECT cli.id, cli.nome, cli.cpfCnpj, cli.endereco, cli.cidade, cli.estado
                FROM cliente AS cli
                INNER JOIN pedido ON cli.id = pedido.cliente_id
                INNER JOIN entrega ON pedido.id = entrega.pedido_id
                WHERE pedido.volume_m3 = (SELECT MAX(volume_m3) FROM pedido);
                """;

        Cliente cliente = null;
        int id = 0;
        String nome = null;
        String cpfCnpj = null;
        String endereco = null;
        String cidade = null;
        String estado = null;

        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)) {

            ResultSet rs = prep.executeQuery();

            if (rs.next()){
                id = rs.getInt("cli.id");
                nome = rs.getString("cli.nome");
                cpfCnpj = rs.getString("cli.cpfCnpj");
                endereco = rs.getString("cli.endereco");
                cidade = rs.getString("cli.cidade");
                estado = rs.getString("cli.estado");
            }
        }

        cliente = new Cliente(id, nome, cpfCnpj, endereco, cidade, estado);

        return cliente;
    }
}
