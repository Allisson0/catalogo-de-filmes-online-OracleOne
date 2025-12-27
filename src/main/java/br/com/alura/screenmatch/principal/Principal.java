package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner input = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ded4c2b8";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal (SerieRepository repositorio){
        this.repositorio = repositorio;
    }

    //=============== MENU PRINCIPAL =============
    public void exibeMenu(){
        var opcao = -1;
        var menu = """
                
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas
                
                0 - Sair""";

        //Menu principal da aplicação
        while (opcao != 0){
            System.out.println(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodiosSerie();
                    break;
                case 3:
                    listarSerieBuscadas();
                    break;
                default:
                    System.out.println("Saindo");
                    break;
            }
        }
    }

    //============ BUSCA E ADICIONA A SERIE NA LISTA DE SÉRIES ===========
    private void buscarSerieWeb(){
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    //============ REALIZA A BUSCA DA SÉRIE NA WEB ===============
    private DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da série para buscar: ");
        var choose = input.nextLine();
        String address = ENDERECO + choose.replace(" ", "+") + API_KEY;
        //Obtem da api o json responsável pela série
        var request = consumoApi.obterDados(address);
        //Dada ao json, transforma na classe DadosSerie e retorna
        return conversor.obterDados(request, DadosSerie.class);
    }

    //========== BUSCA OS EPISÓDIOS DA SÉRIE ==========
    private void buscarEpisodiosSerie(){
        List<DadosTemporada> dadosTemporada = getEpisodiosSerie();
        dadosTemporada.forEach(System.out::println);
    }

    //============ REALIZA A BUSCA DOS EPISÓDIOS DA SÉRIE NA WEB =================
    private List<DadosTemporada> getEpisodiosSerie(){
        System.out.println("Digite o nome da série para buscar: ");
        var choose = input.nextLine();
        String address = ENDERECO + choose.replace(" ", "+") + API_KEY;
        //Busca o resultado da API
        var json = consumoApi.obterDados(address);
        //Converte em um tipo de serie
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);

        //Cria uma lista para armazenar as temporadas
        List<DadosTemporada> temporadas = new ArrayList<>();

        //Para cada temporada, guarda as informações na lista das temporadas da série
        for (int i = 1; i <= serie.totalTemporadas(); i++){
            address = ENDERECO + choose.replace(" ", "+") + "&season=" + i + API_KEY;
            json = consumoApi.obterDados(address);
            //Para cada temporada encontrada, transforma na classe dadosTemporada
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            //Adiciona a temporada na lista de temporadas
            temporadas.add(dadosTemporada);
        }

        //Retorna a lista de temporadas
        return temporadas;
    }

    //========= IMPRIME AS SÉRIES BUSCADAS =========
    private void listarSerieBuscadas(){

        List<Serie> series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}