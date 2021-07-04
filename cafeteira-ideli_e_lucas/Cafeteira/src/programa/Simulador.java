package programa;

import agregadores.Cofre;
import agregadores.Dispensa;
import agregadores.IngredienteReceita;
import agregadores.LivroReceitas;
import agregadores.Receita;
import armazem.ArmazemCopo;
import armazem.ArmazemIngrediente;
import core.Copo;
import core.Ingrediente;
import core.Moeda;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.lang.System.exit;
import java.util.Scanner;

/**
 *
 * @author micre
 */
public class Simulador {
    public Scanner arquivoConfiguracao = null;
    public Cofre objCofre = null;
    public Dispensa objDispensa = null;
    public LivroReceitas objLivroReceitas = null;

    public Simulador(String arquivoInicial) throws FileNotFoundException
    {
        objCofre = new Cofre();
        objDispensa = new Dispensa();
        objLivroReceitas = new LivroReceitas();
        arquivoConfiguracao = null;
    }

    public void parserArquivo()
    {
        String checksun = null;                                                 // Verificador de TAGs
        String parse = null;                                                    // Verifica Inicio da TAG
        int contadorLinha = 0;                                                  // Linha Atualmente LIDA
        while (arquivoConfiguracao.hasNextLine()) 
        {
            String linha = arquivoConfiguracao.nextLine();
            contadorLinha++;
            if(linha.charAt(0) == '#')                                          // TAG
            {
                parse = linha.substring(1, 4);
                switch (parse) {
                    case "INI":
                        if(checksun != null)
                            setErro("MULTIPLAS ABERTURAS SEM FECHAMENTO NA LINHA " + contadorLinha + ">> " + linha);

                        checksun = linha.substring(5);
                        break;
                    case "FIM":
                        if(checksun == null)
                            setErro("FECHAMENTO SEM ABERTURA NA LINHA " + contadorLinha + ">> " + linha);
                        if(checksun.equals(linha.substring(5)))
                            checksun = null;
                        else
                            setErro("FECHAMENTO INCORRETO NA LINHA " + contadorLinha + " >> " + linha);

                        break;
                }
            }
            else // não é TAG e sim VALORES
            {
                String[] arrayValores = linha.split(";");
                switch(checksun) {
                    case "MOEDA":
                        if(arrayValores.length != 5)
                            setErro("PARÂMETROS INCORRETOS PRA CRIAR DE MOEDA: " + contadorLinha + " >> " + linha);
                        
                        geraGavetaMoeda(arrayValores, contadorLinha, linha);
                        break;
                    case "INGREDIENTE":
                        if(arrayValores.length != 5)
                            setErro("PARÂMETROS INCORRETOS PRA CRIAR O INGREDIENTE: " + contadorLinha + " >> " + linha);
                        
                        geraDispensaIngrediente(arrayValores, contadorLinha, linha);
                        break;
                    case "COPO":
                        if(arrayValores.length != 5)
                            setErro("PARÂMETROS INCORRETOS PRA CRIAR O COPO: " + contadorLinha + " >> " + linha);
                        
                        geraDispensaCopo(arrayValores, contadorLinha, linha);
                        break;
                    case "RECEITA":
                        if(arrayValores.length <= 4)
                            setErro("RECEITA FALTANDO INFORMAÇÕES OU INGREDIENTES: " + contadorLinha + " >> " + linha);
                        if(arrayValores.length % 2 != 0)
                            setErro("RECEITA COM PARÂMETROS INCORRETOS: " + contadorLinha + " >> " + linha);
                        
                        geraReceitaNoLivro(arrayValores, contadorLinha, linha);
                        break;
                    default:
                        setErro(" ERRO FATAL : " + contadorLinha + " >> " + linha);
                }
            }
        }
    }
    
    public void geraGavetaMoeda(String[] arrayValores, int contadorLinha, String linha)
    {
        String nome = arrayValores[0];
        int valor = Integer.parseInt(arrayValores[1]);
        int quantidade = Integer.parseInt(arrayValores[2]);
        int quantidadeMax = Integer.parseInt(arrayValores[3]);
        int quantidadeMin = Integer.parseInt(arrayValores[4]);

        if(objCofre.procurarGavetaMoeda(valor) == null)
            setErro("GAVETA JÁ CADASTRADA | CONFERIR CONFIGURAÇÃO : " + contadorLinha + " >> " + linha);

        objCofre.addGavetaMoeda(
                new Moeda(nome, valor),
                quantidade, 
                quantidadeMax, 
                quantidadeMin);
    }
    
    public void geraDispensaIngrediente(String[] arrayValores, int contadorLinha, String linha)
    {
        String nome = arrayValores[0];
        String unidade = arrayValores[1];
        int quantidade = Integer.parseInt(arrayValores[2]);
        int quantidadeMax = Integer.parseInt(arrayValores[3]);
        int quantidadeMin = Integer.parseInt(arrayValores[4]);

        if(objDispensa.procurarDispensaIngrediente(nome) == null)
            setErro("INGREDIENTE JÁ ENCONTRA-SE NA DISPENSA | CONFERIR CONFIGURAÇÃO : " + contadorLinha + " >> " + linha);

        objDispensa.addItemDispensaIngrediente(
                new Ingrediente(nome, unidade), 
                quantidade, 
                quantidadeMax, 
                quantidadeMin);
    }
    
    public void geraDispensaCopo(String[] arrayValores, int contadorLinha, String linha)
    {
        String nome = arrayValores[0];
        int volume = Integer.parseInt(arrayValores[1]);
        int quantidade = Integer.parseInt(arrayValores[2]);
        int quantidadeMax = Integer.parseInt(arrayValores[3]);
        int quantidadeMin = Integer.parseInt(arrayValores[4]);
        
        if(objDispensa.procurarDispensaCopo(nome) != null)
            setErro("TIPO DE COPO JÁ ENCONTRA-SE NA DISPENSA | CONFERIR CONFIGURAÇÃO : " + contadorLinha + " >> " + linha);
        
        objDispensa.addItemDispensaCopo(
                new Copo(nome, volume), 
                quantidade, 
                quantidadeMax, 
                quantidadeMin);
    }

    public void geraReceitaNoLivro(String[] arrayValores, int contadorLinha, String linha)
    {
        String nome = arrayValores[0];                                          // Separa o nome da Receita
        String copo = arrayValores[1];                                          // Separar o nome do Copo
        int preco = Integer.parseInt(arrayValores[2]);                          // Separar o preço da Receita
        String tipo = arrayValores[3];                                          // Separar o Tipo (Café | Achocolatado)

        ArmazemCopo objArmazemCopo = objDispensa.procurarDispensaCopo(copo);    // Verifico se há na dispensa o copo da receita
        if(objArmazemCopo == null)
            setErro("COPO INEXISTENTE NA DISPENSA PARA RECEITA: " + contadorLinha + " >> " + linha);
        
        Receita objReceita = objLivroReceitas.procuraReceitaLivro(nome);        // Verifico se ja não existe esta receita
        if(objReceita != null)
            setErro("RECEITA DUPLICADA: " + contadorLinha + " >> " + linha);
            
        objReceita = new Receita(nome, preco, tipo, objArmazemCopo.getCopo());
        for(int i = 4;i < arrayValores.length; i += 2)
        {
            String ingrediente = arrayValores[i];
            int quantidade = Integer.parseInt(arrayValores[i+1]);
            
            ArmazemIngrediente objIngrediente = objDispensa.procurarDispensaIngrediente(ingrediente);
            if(objIngrediente == null)
                setErro("INGREDIENTE da receita não existe na dispensa " + contadorLinha + " >> " + linha);
            
            objReceita.addIngredienteNaReceita(objIngrediente.getIngrediente(), quantidade);
        }
        objLivroReceitas.addReceitaNoLivroReceita(objReceita);
    }
    
    public void setErro(String msg)
    {
        this.setError(msg, -1);
    }
    
    public void setError(String msg, int codigo)
    {
        System.out.println(msg);
        exit(codigo);
    }

    public void setObjCofre(Cofre objCofre) {
        this.objCofre = objCofre;
    }

    public void setArquivoConfiguracao(Scanner arquivoConfiguracao) {
        this.arquivoConfiguracao = arquivoConfiguracao;
    }
}
