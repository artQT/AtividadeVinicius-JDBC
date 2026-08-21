package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Entrega;
import org.example.model.HistoricoEntrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public HistoricoEntrega buscarHistorico(int id) throws SQLException{
        String command = """
                SELECT (id, entrega_id, data_evento, descricao)
                FROM historicoEntrega WHERE id = ?
                """;

        Entrega entrega = null;
        EntregaDao entregaDao = new EntregaDao();

        HistoricoEntrega historicoEntrega = null;

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(command)){

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            int idDb = 0;
            int idEntrega = 0;
            String dataEvento = null;
            String descricao = null;

            if (rs.next()){
                idDb = rs.getInt("id");
                idEntrega = rs.getInt("entrega_id");
                dataEvento = rs.getString("data_evento");
                descricao = rs.getString("descricao");

                entrega = entregaDao.buscarEntrega(idEntrega);
                historicoEntrega = new HistoricoEntrega(idDb, entrega, dataEvento, descricao);
            }
        }
        return historicoEntrega;
    }
}
