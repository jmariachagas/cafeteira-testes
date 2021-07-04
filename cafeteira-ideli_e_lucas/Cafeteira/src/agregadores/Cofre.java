/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agregadores;
import armazem.ArmazemMoeda;
import core.Moeda;
import java.util.ArrayList;


/**
 *
 * @author micre
 */
public class Cofre {
    public ArrayList<ArmazemMoeda> moedasInternas;
    
    public Cofre()
    {
        moedasInternas = new ArrayList<ArmazemMoeda>();
    }
    
    public void addGavetaMoeda(Moeda moeda, int quantidade, int quantidadeMax, int quantidadeMin)
    {
        moedasInternas.add(new ArmazemMoeda(moeda, quantidade, quantidadeMax, quantidadeMin));
    }
    
    public void mostrarDados()
    {
        for(int i = 0; i < moedasInternas.size()+i; i++)
        {
            System.out.println("Moeda : " + moedasInternas.get(i).getMoeda() +
                               " | Valor : " + moedasInternas.get(i).getMoeda().getValor() +
                               " | Quantidade Atual: " + moedasInternas.get(i).getQuantidade() +
                               " | Quantidade Máxima: " + moedasInternas.get(i).getQuantidadeMax() +
                               " | Quantidade Mínima: " + moedasInternas.get(i).getQuantidadeMin());
        }
    }
    
    public ArmazemMoeda procurarGavetaMoeda(int valor)
    {
        for(int i = 0; i < moedasInternas.size(); i++)
        {
            if(moedasInternas.get(i).getMoeda().getValor() == valor)
                return moedasInternas.get(valor);
        }
        return moedasInternas.get(0);
    }
    
    public ArrayList<Moeda> getMoedasSistema() {
        ArrayList<Moeda> tipoMoedas = new ArrayList<>();
        
        for(int i = 0; i < moedasInternas.size(); i++)
        {
            tipoMoedas.add(moedasInternas.get(i).getMoeda());
        }
        return tipoMoedas;
    }
}
