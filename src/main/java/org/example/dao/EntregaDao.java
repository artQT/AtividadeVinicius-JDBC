package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.enums.StatusEntrega;
import org.example.model.Entrega;
import org.example.model.Motorista;
import org.example.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public Entrega buscarEntrega(int id) throws  SQLException{
        String query = """
                SELECT * FROM entrega where id = ?
                """;

        PedidoDao pedidoDao = new PedidoDao();
        MotoristaDao motoristaDao = new MotoristaDao();
        Entrega entrega = null;


        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            ResultSet rs = prep.executeQuery();

            int idBd = 0;
            Pedido pedido;
            int idPedido = 0;
            Motorista motorista;
            int idMotorista = 0;
            LocalDate dataSaida;
            String dataEntrega;
            StatusEntrega statusEntrega;

            if(rs.next()){
                idBd = rs.getInt("id");
                idPedido = rs.getInt("pedido_id");
                idMotorista = rs.getInt("motorista_id");
                dataSaida = rs.getDate("data_saida").toLocalDate();
                dataEntrega = rs.getString("data_entrega");
                statusEntrega = StatusEntrega.valueOf(rs.getString("status"));

                pedido = pedidoDao.buscarPedido(idPedido);
                motorista = motoristaDao.buscarMotoristas(idMotorista);

                entrega = new Entrega(idBd, pedido, motorista, dataSaida, dataEntrega, statusEntrega);
            }
        }
        return entrega;
    }

    public void atualizarEntrega(Entrega entrega) throws SQLException{
        String query = """
                UPDATE entrega
                SET status = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)){

            prep.setString(1, entrega.getStatus().toString());
            prep.setInt(2, entrega.getId());

            prep.executeUpdate();
        }
    }
}
