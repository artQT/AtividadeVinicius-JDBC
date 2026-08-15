package org.example;

import org.example.dao.ClienteDao;
import org.example.dao.MotoristaDao;
import org.example.dao.PedidoDao;
import org.example.db.ConnectionFactory;
import org.example.model.Cliente;
import org.example.model.Motorista;
import org.example.model.Pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner INPUT = new Scanner(System.in);
    public static void main(String[] args) {
        inicio();
    }

    public static void inicio(){
        System.out.println("""
        1 - Cadastrar Cliente
        2 - Cadastrar Motorista
        3 - Criar Pedido
        4 - Atribuir Pedido a Motorista (Gerar Entrega)
        5 - Registrar Evento de Entrega (Histórico)
        6 - Atualizar Status da Entrega
        7 - Listar Todas as Entregas com Cliente e Motorista
        8 - Relatório: Total de Entregas por Motorista
        9 - Relatório: Clientes com Maior Volume Entregue
        10 - Relatório: Pedidos Pendentes por Estado
        11 - Relatório: Entregas Atrasadas por Cidade
        12 - Buscar Pedido por CPF/CNPJ do Cliente
        13 - Cancelar Pedido
        14 - Excluir Entrega (com validação)
        15 - Excluir Cliente (com verificação de dependência)
        16 - Excluir Motorista (com verificação de dependência)
        0 - Sair                
        """);
        int opcaoMenu = INPUT.nextInt();
        INPUT.nextLine();

        switch (opcaoMenu) {
            case 1:
                cadastrarCliente();
                break;
            case 2:
                cadastrarMotorista();
                break;
            case 3:
                criarPedido();
                break;
            case 4:
                criarEntrega();
                break;
            default:
                break;
        }
    }

    public static void cadastrarCliente(){
        System.out.println("Insira o nome");
        String nome = INPUT.nextLine();

        System.out.println("Insira o CPF ou CNPJ");
        String cpfCnpj = INPUT.nextLine();
        
        System.out.println("Insira o endereço");
        String endereco = INPUT.nextLine();
        
        System.out.println("Insira a cidade");
        String cidade = INPUT.nextLine();
        
        System.out.println("Insira o estado");
        String estado = INPUT.nextLine();

        Cliente cliente = new Cliente(nome, cpfCnpj, endereco, cidade, estado);
        ClienteDao clienteDao = new ClienteDao();

        try {
            clienteDao.cadastrar(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarMotorista(){
        System.out.println("Insira o nome");
        String nome = INPUT.nextLine();

        System.out.println("Insira a cnh");
        String cnh = INPUT.nextLine();
        
        System.out.println("Insira o veiculo");
        String veiculo = INPUT.nextLine();
        
        System.out.println("Insira a cidade base do veiculo");
        String cidadeBase = INPUT.nextLine();

        Motorista motorista = new Motorista(nome, cnh, veiculo, cidadeBase);
        MotoristaDao motoristaDao = new MotoristaDao();

        try {
            motoristaDao.cadastrar(motorista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarPedido(){
        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> listaCliente = new ArrayList<>();
        try {
            listaCliente = clienteDao.listar();

            for (Cliente c : listaCliente){
                System.out.println("ID: "+c.getId());
                System.out.println("Nome: " +c.getNome());
                System.out.println("Cpf ou Cnpj: " +c.getCpfCnpj());
                System.out.println("Endereço: " +c.getEndereco());
                System.out.println("Cidade: " +c.getCidade());
                System.out.println("Estado: " +c.getEstado());
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Insira o id do cliente");
        int clienteID = INPUT.nextInt();

        Cliente cliente = clienteDao.buscar(clienteID);

        System.out.println("Insira o volume");
        double volumeM3 = INPUT.nextDouble();
        
        System.out.println("Insira o peso em kg do pedido");
        double precoKG = INPUT.nextDouble();

        Pedido pedido = new Pedido(cliente, volumeM3, precoKG);
        PedidoDao pedidoDao = new PedidoDao();
        try {
            pedidoDao.criar(pedido, cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarEntrega(){
        System.out.println();
    }
}
