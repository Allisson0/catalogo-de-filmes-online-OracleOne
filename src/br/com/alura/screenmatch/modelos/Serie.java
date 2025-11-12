package br.com.alura.screenmatch.modelos;

public class Serie extends Titulo{
    private int temporadas;
    private boolean ativa;
    private int epsPorTemporada;
    private int minutosPorEps;

    public Serie(String nome, int anoDeLancamento){
        super(nome, anoDeLancamento);
    }

    public int getTemporadas() {
        return temporadas;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public int getEpsPorTemporada() {
        return epsPorTemporada;
    }

    public int getMinutosPorEps() {
        return minutosPorEps;
    }

    @Override
    public int getDuracaoEmMinutos(){
        return temporadas * epsPorTemporada * minutosPorEps;
    }

    public void setTemporada(int temporada) {
        this.temporadas = temporada;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setEpsPorTemporada(int epsPorTemporada) {
        this.epsPorTemporada = epsPorTemporada;
    }

    public void setMinutosPorEps(int minutosPorEps) {
        this.minutosPorEps = minutosPorEps;
    }

    @Override
    public String toString(){
        return "Série: " + this.getNome() + " (" + this.getAnoDeLancamento() + ")";
    }

}
