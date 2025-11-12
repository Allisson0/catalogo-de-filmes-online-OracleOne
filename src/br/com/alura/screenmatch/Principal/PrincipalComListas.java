package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.modelos.Filme;
import br.com.alura.screenmatch.modelos.Serie;
import br.com.alura.screenmatch.modelos.Titulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrincipalComListas {
    public static void main(String[] args){
        Filme meuFilme = new Filme("O Poderoso Chefão", 1970);
        meuFilme.avalia(9);
        Filme outroFilme = new Filme("Avatar", 2023);
        outroFilme.avalia(6);
        var filmeDoPaulo = new Filme("Dogville", 2003);
        filmeDoPaulo.avalia(10);
        Serie lost = new Serie("Lost", 2000);
    
        
        // ArrayList<Titulo> lista = new ArrayList<>();
        List<Titulo> lista = new ArrayList<>();
        lista.add(filmeDoPaulo);
        lista.add(meuFilme);
        lista.add(outroFilme);
        lista.add(lost);

        for (Titulo item : lista) {

            System.out.println(item.getNome());

            if (item instanceof Filme filme && filme.getClassificacao() > 2){
                System.out.println("Classificação: "+filme.getClassificacao());
            }

        }


        ArrayList<String> buscaPorArtista = new ArrayList<>();
        buscaPorArtista.add("Paulo");
        buscaPorArtista.add("Adam Sandler");
        buscaPorArtista.add("Tom Holland");

        System.out.println(buscaPorArtista);

        Collections.sort(buscaPorArtista);
        System.out.println("Após a ordenação:");
        System.out.println(buscaPorArtista);

        System.out.println("\n\nLista antes da ordenação:");
        System.out.println(lista);

        Collections.sort(lista);

        System.out.println("\nLista depois da ordenação: ");
        System.out.println(lista);

        lista.sort(Comparator.comparing(Titulo::getAnoDeLancamento));

        System.out.println("Ordenando por ano: ");
        System.out.println(lista);
    }
}
