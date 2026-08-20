package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.HistoricoEntrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoDao {

    public void criarHistorico(HistoricoEntrega historicoEntrega) throws SQLException {
        String command = """
                INSERT INTO historicoEntrega
                (entrega_id, data_evento, descricao)
                VALUES
                (?,?,?)
                """;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

            prep.setInt(1, historicoEntrega.getEntrega().getId());
            prep.setString(2, historicoEntrega.getDataEvento());
            prep.setString(3, historicoEntrega.getDescricao());

            prep.executeUpdate();
        }
    }

    public HistoricoEntrega
}
