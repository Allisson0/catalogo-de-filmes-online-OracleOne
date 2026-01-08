package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner input = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("API_OMDB_KEY");
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    private List<Serie> series = new ArrayList<>();

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
                4 - Buscar série por título
                5 - Buscar séries por ator
                6 - Buscar top 5 séries
                7 - Buscar séries por categoria
                
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
                    EpisodiosDaSerie();
                    break;
                case 3:
                    listarSerieBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
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

    //============ REALIZA A BUSCA DOS EPISÓDIOS DA SÉRIE NA WEB =================
    private void EpisodiosDaSerie(){

        listarSerieBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = input.nextLine();

        //Procura a série buscada no nosso banco de dados
//        Optional<Serie> serie = series.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();

        //Busca por meio do banco de dados diretamente por meio derived queries
        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        //Verifica se a série foi encontrada no banco de dados
        if (serie.isPresent()) {

            var serieEncontrada = serie.get();
            //Cria uma lista para armazenar as temporadas
            List<DadosTemporada> temporadas = new ArrayList<>();

            //Para cada temporada, guarda as informações na lista das temporadas da série
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var address = ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY;
                var json = consumoApi.obterDados(address);
                //Para cada temporada encontrada, transforma na classe dadosTemporada
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                //Adiciona a temporada na lista de temporadas
                temporadas.add(dadosTemporada);
            }

            //Retorna a lista de temporadas
            temporadas.forEach(System.out::println);

            //Para cada episódio das temporadas, trabalharemos no episódio e criaremos uma
            //instância de episódios, passando o número da temporada do episódio e o episódio
            //para a lista de episódios.
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            //t.numero() é o número da temporada e 'e' é a instância do record
                            //DadosEpisodio
                            .map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());

            //Para nossa lista de serieEncontrada, passamos então ela para o nosso banco
            //de dados. Assim, ele passará também os episódios da série.
            serieEncontrada.setEpisodios(episodios);

            //Salva no banco de dados as informações da série que foi encontrada
            //do banco de dados
            repositorio.save(serieEncontrada);
        } else{
            System.out.println("Série não encontrada.");
        }
    }

    //========= IMPRIME AS SÉRIES BUSCADAS =========
    private void listarSerieBuscadas(){
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    //========= BUSCA A SERIE PELO TITULO =========
    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = input.nextLine();

        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieBuscada.isPresent()){
            System.out.println("Dados da série: " + serieBuscada.get());
        } else{
            System.out.println("Série não encontrada!");
        }
    }

    //========== BUSCA SÉRIES PELO ATOR ========
    private void buscarSeriesPorAtor() {
        System.out.println("Qual o nome do ator para busca? ");
        var nomeDoAtor = input.nextLine();
        System.out.println("A partir de que avaliação? ");
        var avaliacao = input.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeDoAtor, avaliacao);

        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));

    }

    //========= BUSCA AS TOP 5 MELHORES SÉRIES ========
    private void buscarTop5Series(){
        List<Serie> seriesTop = repositorio.findTop5ByOrderByAvaliacaoDesc();

        seriesTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    //========= BUSCA AS SÉRIES POR CATEGORIA =========
    private void buscarSeriesPorCategoria(){
        System.out.println("Qual a categoria/gênero que irá pesquisar? ");
        var nomeGenero = input.nextLine();
        //Recebe o item e transforma no enum de categoria
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        //Procura por essa categoria no banco de dados e salva numa
        //lista de séries
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }
}