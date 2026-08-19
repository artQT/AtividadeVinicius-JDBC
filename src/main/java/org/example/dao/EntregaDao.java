package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Entrega;
import org.example.model.Motorista;
import org.example.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDao {

    public void cadastrarEntrega(Entrega entrega) throws SQLException {
        String command = """
                INSERT INTO entrega
                (pedido_id, motorista_id, data_saida, data_entrega, status)
                VALUES
                (?,?,?,?,?)
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

            prep.setInt(1, entrega.getPedido().getId());
            prep.setInt(2, entrega.getMotorista().getId());
            prep.setObject(3, entrega.getDataSaida());
            prep.setString(4, entrega.getDataEntrega());
            prep.setString(5, entrega.getStatus().toString());

            prep.executeUpdate();
        }
    }
}
