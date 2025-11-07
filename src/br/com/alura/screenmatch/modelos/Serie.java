package br.com.alura.screenmatch.modelos;

public class Serie extends Titulo{
    private int temporada;
    private boolean ativa;
    private int epsPorTemporada;
    private int minutosPorEps;

    public int getTemporada() {
        return temporada;
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

    public void setTemporada(int temporada) {
        this.temporada = temporada;
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


}
