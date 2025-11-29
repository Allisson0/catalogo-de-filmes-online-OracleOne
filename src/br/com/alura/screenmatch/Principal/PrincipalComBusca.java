package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException{
        Scanner input = new Scanner(System.in);
        List<Titulo> titulos = new ArrayList<>();
        String busca = "";

        //Gson gson = new Gson();
        //Cria um Gson com concatenação de título de tipo no Gson, para
        //deixar em minusculo
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while(!busca.equals("sair")) {
            System.out.println("Digite o filme a pesquisar:");
            busca = input.nextLine();

            if(busca.equalsIgnoreCase("sair")){
                break;
            }

            //Cria o enderaçamento da API
            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=ded4c2b8";

            //try para verificação de erros e tratamento se houver
            try {
                //Cria um cliente para receber a requisição da API
                HttpClient client = HttpClient.newHttpClient();
                //Cria uma requisição de API do endereço criado anteriormente
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                //Armazena o resultado Json da API através do Cliente em uma HttpResponse
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                //Salva a resposta do chamado da API
                String json = response.body();
                System.out.println(json);


                //Cria um objeto do tipo record com base no arquivo Gson criado
                //Assim, atribuimos corretamente os valores ao objeto
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(meuTituloOmdb);

                //Com base nesse objeto, criamos um objeto do tipo Titulo com
                //os valores obtidos dos atributos de meuTituloOmdb
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Titulo já convertido: ");
                System.out.println(meuTitulo);

                titulos.add(meuTitulo);


                //File arquivo = new File("C:\\meuArquivo.txt"); referência/cria
                //um arquivo .txt no sistema
                //FileWriter referencia um documento .txt com o nome abaixo
                //FileWriter escrita = new FileWriter("filmes.txt");
                //.write escreve algo no documento
                //escrita.write(meuTitulo.toString());
                //.close define que foi fechado o processo
                //escrita.close();


            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro.");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereço.");
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(titulos);

        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
        System.out.println("O programa finalizou corretamente.");
    }

}
