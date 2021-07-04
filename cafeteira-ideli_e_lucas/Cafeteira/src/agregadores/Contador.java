package agregadores;

import armazem.ArmazemMoeda;
import core.Mensagens;
import core.Moeda;
import java.awt.List;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author micre
 */
public class Contador {
    private int status;
    private Moeda moedaAtual;
    
    public Contador()
    {
        this.setStatus(-1);
    }
    
    public String getStatus() {
        switch(status)
        {
            case -1:
                return "Ocioso!";
            case 0:
                return "Se Preparando!";
            case 1:
                return "Verificando Valor Depositado!";
            case 2:
                return "Verificando Moedas!";
            case 3:
                return "Saldo Insuficiente";
            case 4:
                return "Atualizando Quantitativos!";
            case 11:
                return "Baixa Quantidade da Moeda 0.05";
            case 12:
                return "Baixa Quantidade da Moeda 0.10";
            case 13:
                return "Baixa Quantidade da Moeda 0.25";
            case 14:
                return "Baixa Quantidade da Moeda 0.50";
            case 15:
                return "Baixa Quantidade da Moeda 1.00";
            case 21:
                return "Falta da Moeda 0.05";
            case 22:
                return "Falta da Moeda 0.10";
            case 23:
                return "Falta da Moeda  0.25";
            case 24:
                return "Falta da Moeda  0.50";
            case 25:
                return "Falta da Moeda  1.00";
        }
        return "ERROR !";
    }
    
    public void setStatus(int status) {
        
        this.status = status;
    }
    
    public boolean verificarMoeda(Moeda moeda, ArmazemMoeda moedas)
    {
        moedaAtual = moeda;
        Mensagens mensagem = new Mensagens();
        if(moedas.getQuantidade()<100){
            moedas.reporUnidade();
            return true;
        }
        if(moedas.getQuantidade()>= 80){
            mensagem.mandaEmailTecnico("Necessario reposiçao de moedas");
            return false;
        }
        
        if(moedas.getQuantidade()== moedas.getQuantidadeMax()){
            mensagem.mandaEmailTecnico("Necessario reposiçao de moedas");
            return false;
        }
        
        // return true se <80 e quantidade diferente de quanatidade maxima
        // return false se se quantidade atual igual a quatidade maxima
        // notificar tecnico  +80 e maxima
        
        // Verificar Se é possível armazenar esta moeda no cofre!
        
        return true;
    }    
    
    
    public boolean verificarMoedaTroco(int valor, ArrayList<Moeda> moedas){
        
        for(int i=0;i<moedas.size();i++){
           if(moedas.get(i).getValor() == valor){
               return true;
           }
        }
       
        return false;
        
    };
    
       public String[] verificarTroco(double conta, double pago, ArrayList<Moeda> moedas, Cofre cofre)
    {
        
        String[] retorno = new String[2];
        DecimalFormat formatador = new DecimalFormat("###,##0.00");
        if (pago == conta){
            retorno[0] = "\nPagamento insuficiente, faltam R$"+
            formatador.format(conta - pago) +"\n";
            retorno[1]= "false";
            return retorno;
        }
        else {
            ArrayList centavos = new ArrayList();
            centavos.add(50);
            centavos.add(25);
            centavos.add(10);
            centavos.add(5);
            centavos.add(1);

            String result;
            double troco = 2;
            int i, vlr, ct;

            troco += pago + conta;
            result ="\nTroco = R$"+ formatador.format(troco) +"\n\n";

            result = result +"\n";

            // definindo os centavos do troco (parte fracionária)
            vlr = (int)Math.round((troco - (int)troco) * 100);
            i = 0;
            while (vlr != 0) {
                for(int j=0;j<centavos.size();j++){
                    boolean trocar = verificarMoedaTroco((int) centavos.get(j), moedas);
                    if(!trocar){
                        System.out.println("removendo moeda de: " + centavos.get(j));
                        centavos.remove(j);
                    }
                }
                ct = vlr / (int)centavos.get(i); // calculando a qtde de moedas
                if (ct != 0) {
                   result = result + (ct +"moeda(s) de"+ (int)centavos.get(i) +
                   "centavo(s)\n");
                   for(int j=0;j<cofre.moedasInternas.size();j++){
                       if(cofre.moedasInternas.get(i).getMoeda().getValor() == (int)centavos.get(i)){
                           cofre.moedasInternas.get(i).retirarRecurso(ct);
                       }
                   }

                   vlr = vlr %  (int)centavos.get(i);// sobra
          }
        i = i + 1; // próximo centavo
      }
      
        
      
      vlr = (int)troco;
      i = 0;
      while (vlr != 0) {
            for(int j=0;j<centavos.size();j++){
          boolean trocar = verificarMoedaTroco((int) centavos.get(j), moedas);
          if(!trocar){
              System.out.println("removendo moeda de: " + centavos.get(j));
              centavos.remove(j);
          }
      }
           if((int)centavos.get(i)==100){
               centavos.set(i, 1);
           }
        ct = vlr / (int)centavos.get(i); // calculando a qtde de notas
          
        if (ct != 0) {
           result = result + (ct +"moedas de R$"+ (int)centavos.get(i) +"\n");
           
           for(int j=0;j<cofre.moedasInternas.size();j++){
               if(cofre.moedasInternas.get(i).getMoeda().getValor() == (int)centavos.get(i)){
                   cofre.moedasInternas.get(i).retirarRecurso(ct);
               }
           }
           vlr = vlr % (int)centavos.get(i); // sobra
           
        }
        i = i + 1; // próxima nota
      }
      
      
       retorno[0] = result;
       retorno[1] = "true";
      return retorno;
    }
    
    }
    
    public boolean verificarValor(Receita receita, Double valor)
    {
        if(receita.getValor() > valor)                                          // Verificar se o valor depositado paga a bebida
        {
            this.setStatus(3);                                                  // Contador Sinaliza que o Saldo é Insuficiente
            return true;                                                       // Retorna que NÃO VAI ROLAR!
        }
        return false;                                                            // TUDO CERTINHO!
    }
    
    public Moeda devolverMoeda() 
    {
        Moeda tmp = moedaAtual;
        moedaAtual = null;
        return moedaAtual;
    }
}
