package core;

/**
 *
 * @author micre
 */
public class Ingrediente {
    private String nome;
    private String unidade;

    public Ingrediente(String nome, String unidade) {
        this.setNome(nome);
        this.setUnidade(unidade);
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    private void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public String getUnidade() {
        return unidade;
    }
}
