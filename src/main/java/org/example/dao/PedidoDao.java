package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.enums.StatusPedido;
import org.example.model.Cliente;
import org.example.model.Pedido;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {
    
    public void criar(Pedido pedido, Cliente cliente) throws SQLException{
         String command = """
                INSERT INTO pedido
                (cliente_id, data_pedido, volume_m3, peso_kg, status)
                VALUES
                (?,?,?,?,?)
                """; 

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

                prep.setInt(1, cliente.getId());
                prep.setObject(2, pedido.getDataPedido());
                prep.setDouble(3, pedido.getVolumeM3());
                prep.setDouble(4, pedido.getPesoKG());
                prep.setString(5, pedido.getStatus().toString());

                prep.executeUpdate();
            }
    }

    /*arrumar pedido dao
        e descobrir como fazer aquilo ali
     */

    public List<Pedido> listarPedido()throws SQLException{
        String query =  """
                Select id, nome from motorista
                """;

        List<Pedido> pedidos = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)){

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                Cliente cliente = new Cliente();
                LocalDate date = LocalDate.now();
                double volumeM3 = rs.getDouble("volume_m3");
                double pesoKg = rs.getDouble("peso_kg");
                StatusPedido status = StatusPedido.PENDENTE;

                Pedido pedido = new Pedido(id,null,date,volumeM3,pesoKg,status);
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public Pedido buscarPedido(int id){
        String query = """
                Select * from motorista where id = ?
                """;

        int idNovo = 0;
        String nome = "";
        String cnh = "";
        String veiculo = "";
        String cidadeBase = "";

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)){

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            if (rs.next()){
                idNovo = rs.getInt("id");
                nome = rs.getString("nome");
                cnh = rs.getString("cnh");
                veiculo = rs.getString("veiculo");
                cidadeBase = rs.getString("cidade base");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new pedido(idNovo, nome, cnh, veiculo, cidadeBase);
    }
}
