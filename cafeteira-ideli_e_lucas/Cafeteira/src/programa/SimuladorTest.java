package programa;

import agregadores.Cofre;
import armazem.ArmazemMoeda;
import core.Moeda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SimuladorTest {

    private Simulador simulador;

    @BeforeEach
    void setUp() {
        try {
            simulador = new Simulador("");

            System.out.println("Inicio de testes.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Teste no Mutante 1 (Atributo - arquivoConfiguracao = null: NullPointerException")
    void parserArquivoNullAtributoArquivoConfiguracao() {

        Throwable exception = assertThrows(NullPointerException.class, () -> {
           simulador.parserArquivo();
        });

        int lineException = exception.getStackTrace()[0].getLineNumber();
        String msg = "O atributo 'arquivoConfiguracao' esta sendo acessado na linha '41' da classe Simulador";
        System.out.println("Mensagem - " + msg);
        assert lineException == 41 : msg;
    }

    @Test
    @DisplayName("Teste no Mutante 2 (Atributo - checksun = null: NullPointerException")
    void parserArquivoNullAtributoCheckSun() {

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            String arquivo = "teste.txt";
            Scanner scanner = new Scanner(arquivo);
            simulador.setArquivoConfiguracao(scanner);
            simulador.parserArquivo();
        });
        int lineException = exception.getStackTrace()[0].getLineNumber();
        String msg = "O atributo 'checksun' esta sendo acessado na linha '69' da classe Simulador";
        assert lineException == 69 : msg;
    }

    @Test
    void geraGavetaMoeda() {


    }

    @Test
    void geraDispensaIngrediente() {
    }

    @Test
    void geraDispensaCopo() {
    }

    @Test
    void geraReceitaNoLivro() {
    }

}