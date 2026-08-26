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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;

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
            LocalDateTime dataSaida;
            String dataEntrega;
            StatusEntrega statusEntrega;

            if(rs.next()){
                idBd = rs.getInt("id");
                idPedido = rs.getInt("pedido_id");
                idMotorista = rs.getInt("motorista_id");
                dataSaida = rs.getTimestamp("data_saida").toLocalDateTime();
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

    public List<Entrega> listarEntregaClienteMotorista() throws SQLException{
        String query = """
                SELECT ent.id,
                       pedido_id,
                       motorista_id,
                       data_saida,
                       data_entrega,
                       ent.status
                FROM entrega AS ent INNER JOIN pedido ON
                ent.pedido_id = pedido.id
                WHERE motorista_id AND cliente_id IS NOT NULL
                """;

        List<Entrega> entregas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)) {

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                int pedidoId = rs.getInt("pedido_id");
                int motorista_id = rs.getInt("motorista_id");
                LocalDateTime dataSaida = rs.getTimestamp("data_saida").toLocalDateTime();
                String dataEntrega = rs.getString("data_entrega");
                StatusEntrega statusEntrega = StatusEntrega.valueOf(rs.getString("status"));

                PedidoDao pedidoDao = new PedidoDao();
                MotoristaDao motoristaDao = new MotoristaDao();

                Pedido pedido = pedidoDao.buscarPedido(pedidoId);
                Motorista motorista = motoristaDao.buscarMotoristas(motorista_id);

                Entrega entrega = new Entrega(id, pedido, motorista, dataSaida, dataEntrega, statusEntrega);
                entregas.add(entrega);
            }
        }
        return entregas;
    }

    public int numeroEntregaMoto() throws SQLException{
        String query = """
                SELECT COUNT(motorista_id) AS numero_de_motorista FROM entrega;
                """;

        int numeroEntrega = 0;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(query)){

            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                numeroEntrega = rs.getInt("numero_de_motorista");
            }
        }
        return numeroEntrega;
    }
}
