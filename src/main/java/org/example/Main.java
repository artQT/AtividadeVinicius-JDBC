package org.example;

import org.example.dao.*;
import org.example.db.ConnectionFactory;
import org.example.enums.StatusEntrega;
import org.example.model.*;

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

    public static void inicio() {
        boolean a = true;

        while (a) {
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
                case 5:
                    criarHistorico();
                    break;
                case 6:
                    atualizarEntrega();
                    break;
                case 0:
                    a = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void cadastrarCliente() {
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
            clienteDao.cadastrarCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarMotorista() {
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
            motoristaDao.cadastrarMotorista(motorista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarPedido() {
        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> listaCliente = new ArrayList<>();
        try {
            listaCliente = clienteDao.listarCliente();

            for (Cliente c : listaCliente) {
                System.out.println("ID: " + c.getId());
                System.out.println("Nome: " + c.getNome());
                System.out.println("Cpf ou Cnpj: " + c.getCpfCnpj());
                System.out.println("Endereço: " + c.getEndereco());
                System.out.println("Cidade: " + c.getCidade());
                System.out.println("Estado: " + c.getEstado());
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Insira o id do cliente");
        int clienteID = INPUT.nextInt();

        Cliente cliente = clienteDao.buscarCliente(clienteID);

        System.out.println("Insira o volume");
        double volumeM3 = INPUT.nextDouble();
        System.out.println("Insira o peso em kg do pedido");
        double precoKG = INPUT.nextDouble();

        Pedido pedido = new Pedido(cliente, volumeM3, precoKG);
        PedidoDao pedidoDao = new PedidoDao();
        try {
            pedidoDao.criarPedido(pedido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarEntrega() {
        System.out.println("Insira o id do pedido");
        int pedidoId = INPUT.nextInt();

        PedidoDao pedidoDao = new PedidoDao();
        Pedido pedido = new Pedido(0, null, null, 0, 0, null);

        try {
            pedido = pedidoDao.buscarPedido(pedidoId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Insira o id do motorista");
        int motoristaId = INPUT.nextInt();
        INPUT.nextLine();

        MotoristaDao motoristaDao = new MotoristaDao();
        Motorista motorista = new Motorista(0,null, null, null, null);

        try {
            motorista = motoristaDao.buscarMotoristas(motoristaId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Insira a data que foi entregue o pedido");
        String dataEntrega = INPUT.nextLine();

        Entrega entrega = new Entrega(0, pedido, motorista, null, dataEntrega, null);
        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.cadastrarEntrega(entrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarHistorico() {
        System.out.println("Insira o id da entrega");
        int idEntrega = INPUT.nextInt();

        EntregaDao entregaDao = new EntregaDao();
        Entrega entrega = null;

        try {
            entrega = entregaDao.buscarEntrega(idEntrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Qual a data do envento?");
        String dataEvento = INPUT.nextLine();

        System.out.println("Insira uma descriçãopra o evento?");
        String descricao = INPUT.nextLine();

        HistoricoEntrega historicoEntrega = new HistoricoEntrega(entrega, dataEvento, descricao);
        HistoricoDao historicoDao = new HistoricoDao();

        try {
            historicoDao.criarHistorico(historicoEntrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarEntrega() {
        System.out.println("Insira o id que tu vai mudar: ");
        int id = INPUT.nextInt();

        System.out.println("""
                Qual o novo status:
                1- ENTREGUE
                2- ATRASADO
                """);
        int opcao = INPUT.nextInt();

        StatusEntrega statusEntrega = null;

        switch (opcao) {
            case 1:
                statusEntrega = StatusEntrega.ENTREGUE;
                break;
            case 2:
                statusEntrega = StatusEntrega.ATRASADA;
                break;
        }

        Entrega entrega = new Entrega(id, null, null, null, null, statusEntrega);
        EntregaDao entregaDao = new EntregaDao();

        try {
            entregaDao.atualizarEntrega(entrega);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
