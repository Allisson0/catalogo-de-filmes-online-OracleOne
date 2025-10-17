import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner leitura = new Scanner(System.in);

        System.out.println("Digite  seu filme favorito: ");

        String filme = leitura.nextLine();

        System.out.println("Qual o ano de lançamento: ");

        int anoDeLancamento = leitura.nextInt();

        System.out.println("Insira sua avaliação: ");

        double avaliacao = leitura.nextDouble();

        System.out.println(String.format("Filme: %s \nAno de lançamento: %d\nAvaliacao: %.2f", filme, anoDeLancamento, avaliacao));

    }
}
