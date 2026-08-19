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
}
