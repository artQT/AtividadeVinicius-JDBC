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
    
    public void criar(Pedido pedido) throws SQLException{
         String command = """
                INSERT INTO pedido
                (cliente_id, data_pedido, volume_m3, peso_kg, status)
                VALUES
                (?,?,?,?,?)
                """; 

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

                prep.setInt(1, pedido.getCliente().getId());
                prep.setObject(2, pedido.getDataPedido());
                prep.setDouble(3, pedido.getVolumeM3());
                prep.setDouble(4, pedido.getPesoKG());
                prep.setString(5, pedido.getStatus().toString());

                prep.executeUpdate();
            }
    }

    public Pedido buscarPedido(int id) throws SQLException{
        String query = """
                SELECT * FROM pedido WHERE id = ?
                """;

        Pedido pedido = null;
        Cliente cliente = null;

        int idNovo = 0;
        int idCliente = 0;
        LocalDate data = null;
        double volumeM3 = 0;
        double pesoKG = 0;
        StatusPedido status = null;

        ClienteDao clienteDao = new ClienteDao();

        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)){

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            if (rs.next()){
                idNovo = rs.getInt("id");
                idCliente = rs.getInt("cliente_id");
                data = rs.getDate("data_evento").toLocalDate();
                volumeM3 = rs.getDouble("volume_m3");
                pesoKG = rs.getDouble("peso_kg");
                status = StatusPedido.valueOf(rs.getString("status"));

                cliente = clienteDao.buscar(idCliente);
                pedido = new Pedido(idNovo,cliente, data, volumeM3, pesoKG, status);
            }
        }
        return pedido;
    }
}
