package br.com.alura.screenmatch.Principal;
import br.com.alura.screenmatch.calculos.CalculadoraDeTempo;
import br.com.alura.screenmatch.calculos.Recomendacao;
import br.com.alura.screenmatch.modelos.Episodio;
import br.com.alura.screenmatch.modelos.Filme;
import br.com.alura.screenmatch.modelos.Serie;
import java.util.ArrayList;

public class Principal {
    public static void main(String[] args){
        Filme meuFilme = new Filme("O Poderoso Chefão", 1970);
        // meuFilme.setNome("O Poderoso Chefão");
        // meuFilme.setAnoDeLancamento(1970);
        meuFilme.setDuracaoEmMinutos(180);

        meuFilme.exibeFichaTecnica();
        meuFilme.avalia(5);
        meuFilme.avalia(10);
        meuFilme.avalia(8);
        System.out.println(meuFilme.obterMedia());
        System.out.println(meuFilme.getTotalDeAvaliacoes());

        Serie lost = new Serie("Lost", 2000);

        // lost.setNome("Lost");
        // lost.setAnoDeLancamento(2000);
        lost.exibeFichaTecnica();
        lost.setTemporada(10);
        lost.setEpsPorTemporada(10);
        lost.setMinutosPorEps(50);
        System.out.println("Duração da série em minutos: " + lost.getDuracaoEmMinutos());

        Filme outroFilme = new Filme("Avatar", 2023);
        // outroFilme.setNome("Avatar");
        // outroFilme.setAnoDeLancamento(2023);
        outroFilme.setDuracaoEmMinutos(200);

        CalculadoraDeTempo calculadora = new CalculadoraDeTempo();

        calculadora.inclui(meuFilme);
        calculadora.inclui(lost);
        System.out.println(calculadora.getTempoTotal());

        Recomendacao filtro = new Recomendacao();
        filtro.filtra(meuFilme);

        Episodio episodio = new Episodio();
        episodio.setNumero(1);
        episodio.setSerie(lost);
        episodio.setTotalVisualizacoes(300);

        filtro.filtra(episodio);
        
        var filmeDoPaulo = new Filme("Dogville", 2003);
        // Filme filmeDoPaulo = new Filme();
        // filmeDoPaulo.setNome("Dogville");
        // filmeDoPaulo.setAnoDeLancamento(2003);
        filmeDoPaulo.setDuracaoEmMinutos(200);
        filmeDoPaulo.avalia(10);

        ArrayList<Filme> listaDeFilmes = new ArrayList<>();
        listaDeFilmes.add(filmeDoPaulo);
        listaDeFilmes.add(meuFilme);
        listaDeFilmes.add(outroFilme);
        System.out.println("Tamanho da lista: " + listaDeFilmes.size());
        System.out.println("Primeiro Filme: "+listaDeFilmes.get(0).getNome());
        System.out.println(listaDeFilmes);

    }
}
