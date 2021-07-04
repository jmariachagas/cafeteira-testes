package programa;
import agregadores.Barista;
import agregadores.Contador;
import agregadores.Receita;
import core.Mensagens;
import core.Moeda;
import java.io.FileNotFoundException;

/**
 *
 * @author micre
 */
public class Maquina extends Simulador {
    public Barista barista;
    public Contador contador;
    public Moeda moedaAtual;
    
    public Maquina(String text) throws FileNotFoundException
    {
        super(text);
        parserArquivo();
        
        objDispensa.mostrarDadosCopos();
        objDispensa.mostrarDadosIngrediente();
        this.contador = new Contador();
        barista = new Barista(objLivroReceitas, objDispensa);

    }
    
    public void prepararBebida(String bebida, int quantidadeAcucar)
    {
        Receita objReceita = objLivroReceitas.procuraReceitaLivro(bebida);
        
        if(!barista.prepararBebida(objReceita, quantidadeAcucar))
            Mensagens.mensagemTela("# ERRO NO PREPARO #");

        // APENAS PARA DEBUG
        objDispensa.mostrarDadosCopos();
        objDispensa.mostrarDadosIngrediente();
    }
    
}
