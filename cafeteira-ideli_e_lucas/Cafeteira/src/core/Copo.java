package core;

/**
 *
 * @author micre
 */
public class Copo {
    private String nome;
    private int volume;

    public Copo(String nome, int volume) {
        this.setNome(nome);
        this.setVolume(volume);
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    private void setVolume(int volume) {
        this.volume = volume;
    }

    public String getNome() {
        return nome;
    }

    public int getVolume() {
        return volume;
    }
}
