package org.example.dao;

import org.example.db.ConnectionFactory;
import org.example.model.Motorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDao {
    
    public void cadastrar(Motorista motorista) throws SQLException{
        String command = """
                INSERT INTO motorista
                (nome, cnh, veiculo, cidade_base)
                VALUES
                (?,?,?,?)
                """; 

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement prep = conn.prepareStatement(command)){

                prep.setString(1, motorista.getNome());
                prep.setString(2, motorista.getCnh());
                prep.setString(3, motorista.getVeiculo());
                prep.setString(4, motorista.getCidadeBase());

                prep.executeUpdate();
            }
    }

    public List<Motorista> listarMotorista() throws  SQLException{
        String query =  """
                Select id, nome from motorista
                """;

        List<Motorista> motoristas = new ArrayList<>();

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement prep = conn.prepareStatement(query)){

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                Motorista motorista = new Motorista(id, nome, null, null, null);
                motoristas.add(motorista);
            }
        }
        return motoristas;
    }

    public Motorista buscarMotoristas(int id){
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
        return new Motorista(idNovo, nome, cnh, veiculo, cidadeBase);
    }
}
