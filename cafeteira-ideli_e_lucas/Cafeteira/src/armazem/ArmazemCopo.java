/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armazem;
import core.Armazem;
import core.Copo;
import core.Mensagens;

/**
 *
 * @author micre
 */
public class ArmazemCopo extends Armazem {
    Copo copo;
    
        
    public ArmazemCopo(Copo copo, int quantidade, int quantidadeMax, int quantidadeMin) {
        super(quantidade, quantidadeMax, quantidadeMin);
        this.copo = copo;
    }    
    
    public Copo getCopo()
    {
        return this.copo;
    }
    
    @Override
    public void avisarLimiteMin()
    {
        Mensagens.mandaEmailTecnico("Baixa Quantidade do Copo " + copo.getNome());
    }
    
    @Override
    public void avisarLimiteMax() 
    {
        Mensagens.mandaEmailTecnico("Quantidade excedeu ao Máximo de Copo " + copo.getNome());
    }
    
    @Override
    public void avisarVazio()
    {
        Mensagens.mandaEmailTecnico("Acabou!" + copo.getNome());
    }

}
