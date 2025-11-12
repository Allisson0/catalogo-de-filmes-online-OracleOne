package br.com.alura.screenmatch.modelos;

public class Titulo implements Comparable<Titulo>{
    private String nome;
    private int anoDeLancamento;
    private boolean incluidoNoPlano;
    private double somaDasAvaliacoes;
    private int totalDeAvaliacoes;
    private int duracaoEmMinutos;

    public Titulo(String nome, int anoDeLancamento) {
        this.nome = nome;
        this.anoDeLancamento = anoDeLancamento;
    }

    public int getTotalDeAvaliacoes(){
        return totalDeAvaliacoes;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
    
    public void setAnoDeLancamento (int anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public int getAnoDeLancamento(){
        return this.anoDeLancamento;
    }

    public void setIncluidoNoPlano (boolean incluidoNoPlano) {
        this.incluidoNoPlano = incluidoNoPlano;
    }

    public void setDuracaoEmMinutos (int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public int getDuracaoEmMinutos (){
        return duracaoEmMinutos;
    }

    public void exibeFichaTecnica(){
        System.out.println("Nome do filme: " + nome);
        System.out.println("Ano de lançamento: " + anoDeLancamento);
    }

    public void avalia(double nota){
        somaDasAvaliacoes += nota;
        totalDeAvaliacoes++;
    }

    public double obterMedia(){
        return somaDasAvaliacoes / totalDeAvaliacoes;
    }

    @Override
    public int compareTo(Titulo outroTitulo){
        return this.getNome().compareTo(outroTitulo.getNome());
    }

}
