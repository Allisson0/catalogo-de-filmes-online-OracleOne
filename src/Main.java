import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        //Inicialização das variaveis:
        Scanner input = new Scanner(System.in);

        String nome = "Allisson Silva";
        String tipoDeConta = "Corrente";
        double saldoAtual = 759.5;

        int escolha = 0;

        double valor = 0.0;

        String extratoInicial ="""
                ****************************************
                Dados iniciais do cliente: 

                Nome:\t\t\t %s
                Tipo conta:\t\t %s
                Saldo inicial:\t\t %.2f 
                ****************************************
                """.formatted(nome, tipoDeConta, saldoAtual);

        String operacoes = """
                    
                Operações:

                1 - Consultar saldo
                2 - Receber valor
                3 - Transferir valor
                4 - Sair

                Digite a opção desejada: """;


        //Abertura do programa

        System.out.println(extratoInicial);

        while (escolha != 4) {
            
            System.out.println(operacoes);
            escolha = input.nextInt();

            switch (escolha) {

                case 1:
                    System.out.println(String.format("Saldo atual: %.2f", saldoAtual));
                    break;

                case 2:
                    System.out.println("Insira o valor a ser recebido: ");
                    valor = input.nextDouble();
                    saldoAtual+=valor;
                    System.out.println("Valor recebido com sucesso! ");
                    System.out.println(String.format("Saldo atualizado: %.2f", saldoAtual));
                    break;

                case 3:
                    System.out.println("Insira o valor a ser transferido: ");
                    valor = input.nextDouble();

                    if (valor <= saldoAtual) {
                        saldoAtual-=valor;
                        System.out.println("Valor transferido com sucesso! ");
                        System.out.println(String.format("Saldo atualizado: %.2f", saldoAtual));
                    } else{
                        System.out.println("Valor superior ao saldo atual. ");
                    }
                    break;

                case 4:
                    System.out.println("Saindo... ");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }
}