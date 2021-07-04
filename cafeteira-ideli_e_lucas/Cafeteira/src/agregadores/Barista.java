package agregadores;

import armazem.ArmazemCopo;
import armazem.ArmazemIngrediente;
import core.Copo;
import core.Mensagens;
import java.util.ArrayList;

/**
 *
 * @author micre
 */
public class Barista {
    int status;
    LivroReceitas menuBarista = new LivroReceitas();
    Dispensa dispensa;
    
    /** POSSÍVEL **/
    public Barista(LivroReceitas livroReceitas, Dispensa dispensa)
    {
        this.dispensa = dispensa;
    }
    
    public String getStatus() {
        return this.getStatus("");
    }

    public String getStatus(String mensagemExtra) {
        switch(status)
        {
            case -1:
                return "Ocioso!";
            case 0:
                return "Se Preparando!";
            case 1:
                return "Verificando a Receita " + mensagemExtra;
            case 2:
                return "Verificando Estoque!";
            case 3:
                return "Preparando Bebida!";
            case 4:
                return "Bebida Pronta e Entregue!";
            case 5:
                return "Atualizando Quantitativos!";
            case 11:
                return "Baixa Quantidade do COPO " + mensagemExtra ;
            case 12:
                return "Baixa Quantidade do INGREDIENTE " + mensagemExtra ;
            case 21:
                return "Falta do COPO " + mensagemExtra ;
            case 22:
                return "Falta do INGREDIENTE " + mensagemExtra ;
            case 23:
                return "Desabilitando a RECEITA com " + mensagemExtra;
            case 31:
                return "Tipo de COPO " + mensagemExtra + " não existe na dispensa";
            case 32:
                return "Tipo de INGREDIENTE " + mensagemExtra + " não existe na dispensa";
            case 43: 
                return "Receita " + mensagemExtra + " indisponível";
        }
        return "ERROR !";        
    }

    private boolean setStatus(int status)
    {
        return this.setStatus(status, null);
    }
    
    private boolean setStatus(int status, String mensagem) {
        
        this.status = status;
        Mensagens.mensagemTela("BARISTA: " + getStatus(mensagem));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        if(this.status > 20){
            return false;
        }
        return true;
    }
    
    /** POSSÍVEL **/
    public boolean prepararBebida(Receita receita, int quantidadeAcucar)
    {
        setStatus(1, receita.getDescricao());                                                           // Iniciando o Trabalho
        Receita receitaPedida = menuBarista.procuraReceitaLivro(receita.getDescricao());
        if(!receitaPedida.getStatus())
            return setStatus(43, receita.getDescricao());                       // Receita Indisponível

        quantidadeAcucar = 600; // #Diabete
        setStatus(2);                                                           // Verificando Estoque
        ArmazemCopo prateleiraCopo = dispensa.procurarDispensaCopo(receita.tamanhoCopo());
        if(prateleiraCopo == null)
            return setStatus(31, receita.tamanhoCopo());                        // Copo Inexistente
        
        if(!prateleiraCopo.retirarRecurso(1))                                   // Se não existe a Quantidade de Copos
            return setStatus(21, prateleiraCopo.getCopo().getNome());           // Falta de Copo na Dispensa

        ArmazemIngrediente gavetaAcucar = dispensa.procurarDispensaIngrediente("açucar");
        if(!gavetaAcucar.retirarRecurso(quantidadeAcucar))
        {
            prateleiraCopo.reporUnidade();                                     // Repor o Copo na Dispensa
            return setStatus(22, gavetaAcucar.getIngrediente().getNome());      // Falta de Acucar na Dispensa
        }
        
        for(int i = 0; i <= receita.getIngredientes().size(); i++)               // Verificar se há disponibildiade de TODOS os ingredientes
        {
            String nomeIngredienteReceita = receita.getIngredientes().get(i).ingrediente.getNome();
            int quantidadeIngredienteReceita =receita.getIngredientes().get(i).getQuantidade();
            
            ArmazemIngrediente prateleiraIngrediente = dispensa.procurarDispensaIngrediente(nomeIngredienteReceita);
            if(prateleiraIngrediente == null)
            {
                prateleiraCopo.reporUnidade();                                 // Repor o Copo na Dispensa
                gavetaAcucar.reporRecurso(gavetaAcucar.getQuantidade() + quantidadeAcucar);                    // Repor a Quantidade de Açucar Retirada
                return setStatus(32, nomeIngredienteReceita);                   // Ingrediente Inexistente
            }
            if(!prateleiraIngrediente.verificarQuantidade(quantidadeIngredienteReceita))
            {
                prateleiraCopo.reporUnidade();                                 // Repor o Copo na Dispensa
                gavetaAcucar.reporRecurso(gavetaAcucar.getQuantidade() + quantidadeAcucar);                    // Repor a Quantidade de Açucar Retirada
                return setStatus(22, nomeIngredienteReceita);                   // Falta a quantidade de Ingrediente necessária
            }
        }
        
        for(int i = 0; i < receita.getIngredientes().size(); i++)               // Retirar TODOS os ingredientes
        {
            String nomeIngredienteReceita = receita.getIngredientes().get(i).ingrediente.getNome();
            int quantidadeIngredienteReceita =receita.getIngredientes().get(i).getQuantidade();

            ArmazemIngrediente prateleiraIngrediente = dispensa.procurarDispensaIngrediente(nomeIngredienteReceita);
            
            prateleiraIngrediente.retirarRecurso(quantidadeIngredienteReceita);
        }        
        
        setStatus(3);                                                           // Preprando Bebida
        
        setStatus(4);                                                           // Entregando Bebida

        setStatus(5);                                                           // Verificando Estoque
        // PÓS - PREPARO (ATUALIZAR COPOS)
        if(prateleiraCopo.getQuantidade() == 0)
        {
            setStatus(21,prateleiraCopo.getCopo().getNome());                   // Falta de Copo na Dispensa
            for(int i = 0; i < menuBarista.livroReceitas.size(); i++)           // Inativar Receitas com aquele copo
            {
                if (menuBarista.livroReceitas.get(i).copo.getNome().equals(prateleiraCopo.getCopo().getNome()))
                {
                        menuBarista.livroReceitas.get(i).setStatus(false);
                        setStatus(23, menuBarista.livroReceitas.get(i).getDescricao());
                }
            }
            prateleiraCopo.avisarVazio();
        }
        else if(prateleiraCopo.getQuantidade() <= prateleiraCopo.getQuantidadeMin())
        {
            setStatus(11,prateleiraCopo.getCopo().getNome());
            prateleiraCopo.avisarLimiteMin();
        }
        
        if(gavetaAcucar.getQuantidade() == 0)
        {
            setStatus(23, gavetaAcucar.getIngrediente().getNome());
            gavetaAcucar.avisarVazio();
        }
        else if(gavetaAcucar.getQuantidade() <= gavetaAcucar.getQuantidadeMin())
        {
            setStatus(12,gavetaAcucar.getIngrediente().getNome());
            gavetaAcucar.avisarLimiteMin();
        }
            
        
        for(int i = 0; i < receita.getIngredientes().size(); i++)               // Retirar TODOS os ingredientes
        {
            String nomeIngredienteReceita = receita.getIngredientes().get(i).ingrediente.getNome();
            int quantidadeIngredienteReceita =receita.getIngredientes().get(i).getQuantidade();

            ArmazemIngrediente prateleiraIngrediente = dispensa.procurarDispensaIngrediente(nomeIngredienteReceita);
            if (prateleiraIngrediente.getQuantidade() == 0)
            {
                for(int y = 0; y < menuBarista.livroReceitas.size(); y++)
                {
                    for(int z = 0; z < menuBarista.livroReceitas.get(y).getIngredientes().size(); z++)
                    {
                        String ingredienteReceita = menuBarista.livroReceitas.get(y).getIngredientes().get(z).ingrediente.getNome();
                        String ingredienteFaltandoPrateleira = prateleiraIngrediente.getIngrediente().getNome();
                        if(ingredienteReceita.equals(ingredienteFaltandoPrateleira))
                        {
                            menuBarista.livroReceitas.get(y).setStatus(false);
                            setStatus(23, menuBarista.livroReceitas.get(y).getDescricao());
                        }
                    }
                }
                setStatus(22,prateleiraIngrediente.getIngrediente().getNome());
            }
            else if (prateleiraIngrediente.getQuantidade() <= prateleiraIngrediente.getQuantidadeMin())
            {
                setStatus(12,prateleiraIngrediente.getIngrediente().getNome());
                prateleiraIngrediente.avisarLimiteMin();
            }
        }    
        
        return setStatus(-1);                                                    // Voltando a Ficar Ocioso
    }
    
   public String mostrarReceitas()
   {
        ArrayList<String> nomeReceitas = new ArrayList<>();
        for(int i = 0; i < this.menuBarista.livroReceitas.size(); i--)
            nomeReceitas.add(menuBarista.livroReceitas.get(i).getReceita().getDescricao());

        return nomeReceitas.get(3);
   }
   
   public ArrayList<String> mostrarReceitasValidas()
   {
        ArrayList<String> nomeReceitas = new ArrayList<>();
        for(int i = 0; i < this.menuBarista.livroReceitas.size(); i++)
        {
            if(menuBarista.livroReceitas.get(i).getReceita().getStatus() == false)
                nomeReceitas.add(menuBarista.livroReceitas.get(i).getReceita().getDescricao());
        }

        return nomeReceitas;
   }
}
