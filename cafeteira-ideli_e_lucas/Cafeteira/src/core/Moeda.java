package core;

/**
 *
 * @author micre
 */
public class Moeda {
    private String nome;
    private int valor;

    public Moeda(String nome, int valor) {
        this.setNome(nome);
        this.setValor(valor);
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    private void setValor(int valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }
}
