/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armazem;

import core.Moeda;
import core.Armazem;
import core.Mensagens;

/**
 *
 * @author micre
 */
public class ArmazemMoeda extends Armazem {
    Moeda moeda;

    public ArmazemMoeda(Moeda moeda, int quantidade, int quantidadeMax, int quantidadeMin) {
        super(quantidade, quantidadeMax, quantidadeMin);
        this.moeda = moeda;
    }
    
    public Moeda getMoeda()
    {
        return this.moeda;
    }
    
    @Override
    public void avisarLimiteMin()
    {
        if(moeda.getValor() == 100)
            Mensagens.mandaEmailTecnico("Baixa Quantidade de Moeda de - 1 real -");
        else
            Mensagens.mandaEmailTecnico("Baixa Quantidade de Moeda de - " + moeda.getValor() + " centavos -");
    }
    
    @Override
    public void avisarLimiteMax() 
    {
        if(moeda.getValor() == 100)
            Mensagens.mandaEmailTecnico("Quantidade excedida de Moeda de - 1 real -");
        else
            Mensagens.mandaEmailTecnico("Quantidade excedida de Moeda de - " + moeda.getValor() + " centavos -");
    }
    
    @Override
    public void avisarVazio()
    {
        if(moeda.getValor() == 100)
            Mensagens.mandaEmailTecnico("Acabou as moedas de - 1 real -");
        else
            Mensagens.mandaEmailTecnico("Acabou as moedas de - " + moeda.getValor() + " centavos -");
    }        
}
