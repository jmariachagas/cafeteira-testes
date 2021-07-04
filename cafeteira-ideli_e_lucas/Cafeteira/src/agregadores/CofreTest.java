package agregadores;

import core.Moeda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import programa.Simulador;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CofreTest {

    private Moeda moeda5;
    private Moeda moeda10;
    private Moeda moeda25;
    private Moeda moeda50;
    private Moeda moeda1;
    private Cofre objCofre;


    @BeforeEach
    void setUp() {

        objCofre = new Cofre();
        carregaMoedas(objCofre);
        System.out.println("Inicio de testes.");
    }

    @Test
    @DisplayName("Teste no Mutante 4 (Index(moedasInternas.size()+i) maior que a lista - for(int i = 0; i < moedasInternas.size()+i; i++) : IndexOutOfBoundsException")
    void mostrarDados() {

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            objCofre.mostrarDados();
        });

        String msg = "A lista de moedas possui o tamanho de 5 (moedas: 5, 10, 25, 50, 1BRL), contudo" +
                " o metodo esta tentando passar o index da lista maior que o tamanho";

        int lineException = exception.getStackTrace()[5].getLineNumber();

        assert lineException == 33 : msg;


    }

    @Test
    @DisplayName("Teste no Mutante 3 (Index(valor = valor da moeda) maior que a lista - return moedasInternas.get(valor) : IndexOutOfBoundsException")
    void procurarGavetaMoeda() {

        String[] teste = {"cinco", "5", "30", "100", "15"};
        int valor = Integer.parseInt(teste[1]);

        //tenta adicionar o mesmo tipo de moeda
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () ->{
            objCofre.procurarGavetaMoeda(valor);
        });

        String msg = "A lista de moedas possui o tamanho de 5 (moedas: 5, 10, 25, 50, 1BRL), contudo" +
                " o metodo esta tentando passar o index da lista maior que o tamanho";

        int lineException = exception.getStackTrace()[5].getLineNumber();

        assert lineException == 46 : msg;
    }

    private void carregaMoedas(Cofre cofre){

        moeda5 = new Moeda("cinco", 5);
        moeda10 = new Moeda("dez", 10);
        moeda25 = new Moeda("vinte", 25);
        moeda50 = new Moeda("cinquenta", 50);
        moeda1 = new Moeda("um", 100);

        cofre.addGavetaMoeda(moeda5, 30, 100, 15);
        cofre.addGavetaMoeda(moeda10, 30, 100, 15);
        cofre.addGavetaMoeda(moeda25, 30, 100, 15);
        cofre.addGavetaMoeda(moeda50, 30, 100, 15);
        cofre.addGavetaMoeda(moeda1, 30, 100, 15);
    }
}