package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException{
        Scanner input = new Scanner(System.in);
        System.out.println("Digite o filme a pesquisar:");
        String busca = input.nextLine();
        String endereco = "https://www.omdbapi.com/?t="+busca+"&apikey=ded4c2b8";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());


        String json = response.body();
        System.out.println(json);

//        Gson gson = new Gson();
        //Cria um Gson com concatenação de título de tipo no Gson, para
        //deixar em minusculo
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        //Cria um objeto do tipo record com base no arquivo Gson criado
        //Assim, atribuimos corretamente os valores ao objeto
        TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
        System.out.println(meuTituloOmdb);
        //Com base nesse objeto, criamos um objeto do tipo Titulo com
        //os valores obtidos dos atributos de meuTituloOmdb
        Titulo meuTitulo = new Titulo(meuTituloOmdb);
        System.out.println("Titulo já convertido: ");
        System.out.println(meuTitulo);
    }

}
