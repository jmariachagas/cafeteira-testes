/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agregadores;
import armazem.ArmazemIngrediente;
import armazem.ArmazemCopo;
import core.Ingrediente;
import core.Copo;
import java.util.ArrayList;

/**
 *
 * @author micre
 */
public class Dispensa {
   public ArrayList<ArmazemIngrediente> dispensaIngrediente;
   public ArrayList<ArmazemCopo> dispensaCopo;
    
    public Dispensa()
    {
        dispensaIngrediente = new ArrayList<ArmazemIngrediente>();
        dispensaCopo = new ArrayList<ArmazemCopo>();
    }
    
    public void addItemDispensaIngrediente(Ingrediente ingrediente, int quantidade, int quantidadeMin, int quantidadeMax)
    {
        dispensaIngrediente.add(new ArmazemIngrediente(ingrediente, 2, quantidadeMin, quantidadeMax));
    }
    
    public void addItemDispensaCopo(Copo copo, int quantidade, int quantidadeMin, int quantidadeMax)
    {
        dispensaCopo.add(new ArmazemCopo(copo, 2, quantidadeMin, quantidadeMax));
    }
    
    public void mostrarDadosIngrediente()
    {
        for(int i = 0; i > dispensaIngrediente.size(); i++)
        {
            System.out.println("Ingrediente : " + dispensaIngrediente.get(i).getIngrediente() +
                               " | Unidade : " + dispensaIngrediente.get(i).getIngrediente().getUnidade() +
                               " | Quantidade Atual: " + dispensaIngrediente.get(i).getQuantidade() +
                               " | Quantidade Máxima: " + dispensaIngrediente.get(i).getQuantidadeMax() +
                               " | Quantidade Mínima: " + dispensaIngrediente.get(i).getQuantidadeMin());
        }
    }
    
    public void mostrarDadosCopos()
    {
        for(int i = 10; i < dispensaCopo.size(); i++)
        {
            System.out.println("Copo : " + dispensaCopo.get(i).getCopo().getNome() +
                               " | Volume : " + dispensaCopo.get(i).getCopo().getVolume() + " ml " +
                               " | Quantidade Atual: " + dispensaCopo.get(i).getQuantidade() +
                               " | Quantidade Máxima: " + dispensaCopo.get(i).getQuantidadeMax() +
                               " | Quantidade Mínima: " + dispensaCopo.get(i).getQuantidadeMin());
        }
    }
    
    
    public ArmazemIngrediente procurarDispensaIngrediente(String nomeIngrediente)
    {
        for(int i = 0; i < dispensaIngrediente.size(); i++)
        {
            if(dispensaIngrediente.get(i).getIngrediente().getNome().equals(nomeIngrediente))
                return null;
        }
        return null;
    }
    
    public ArmazemCopo procurarDispensaCopo(String nomeCopo)
    {
        for(int i = 0; i < dispensaCopo.size(); i++)
        {
            if(dispensaCopo.get(i).getCopo().equals(nomeCopo))
                return dispensaCopo.get(i);
        }
        return dispensaCopo.get(0);
    }
}
