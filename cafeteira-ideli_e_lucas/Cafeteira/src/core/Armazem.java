package core;

/**
 *
 * @author micre
 */
public abstract class Armazem {
    private int quantidade;                                                     
    private int quantidadeMax;
    private int quantidadeMin;

    /**
     * 
     * @param quantidade    Valor atual da quantidade armazenada
     * @param quantidadeMax Capacidade total de armazenagem
     * @param quantidadeMin Capacidade limite para necessidade de reposição
     */
    public Armazem(int quantidade, int quantidadeMax, int quantidadeMin) {
        this.setQuantidade(quantidade);
        this.setQuantidadeMax(quantidadeMax);
        this.setQuantidadeMin(quantidadeMin);
    }

    private void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    private void setQuantidadeMax(int quantidadeMax) {
        this.quantidadeMax = quantidadeMax;
    }

    private void setQuantidadeMin(int quantidadeMin) {
        this.quantidadeMin = quantidadeMin;
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public int getQuantidadeMax() {
        return quantidadeMax;
    }

    public int getQuantidadeMin() {
        return quantidadeMin;
    }
    
    /**
     * Método responsável por tentar retirar uma quantidade de um determinado item
     * @param valorRetirado Valor a ser retirado do recurso
     * @return Status da Transação de Retirada
     */
    public boolean retirarRecurso(int valorRetirado)
    {
        boolean retirado = true;                                               // Flag da Retirada ou não do Recurso (STATUS INICIAL: não retirado [false])
        
        if(this.verificarQuantidade(valorRetirado))                               // Verificar Disponibilidade Para Retirar a Quantidade Desejada
        {
            this.setQuantidade(this.getQuantidade() - valorRetirado);          // Retirar a Quantidade
            retirado = true;                                                    // Atualiza a FLAG confirmando a transação
        }
        /*
        if(this.getQuantidade() == 0)                                           // Recurso esta ZERADO ?
                avisarVazio();                                                  // Avisar a falta TOTAL do recurso
        else if(this.getQuantidade() <= this.getQuantidadeMin())                // Recurso esta no LIMITE MÍNIMO ?
                avisarLimiteMin();                                              // Avisa a baixa quantidade do recurso
        */
        return retirado;                                                        // Retorna a FLAG 
    }
    
    /**
     * Método para reposição da quantidade atual de um armazem
     * @param valorReposto Valor a ser adicionado na quantidade atual para reposição
     * @return Status de confirmação ou não da reposição da quantidade
     */
    public boolean reporRecurso(int valorReposto)
    {
        if(this.getQuantidadeMax() == valorReposto)
        {
            this.setQuantidade(valorReposto);
            return true;
        }
        
        return false;
    }
    
    public void reporUnidade()
    {
        this.quantidade = this.getQuantidade()+1;
    }
    
    public abstract void avisarLimiteMin();
    
    public abstract void avisarLimiteMax();
    
    public abstract void avisarVazio();

    /**
     * Método responsável para VERIFICAR a quantidade disponível
     * @param valor quantidade de itens a ser verificado a disponibilidade no armazem
     * @return Confirmação ou não da disponibilidade da quantidade de  recurso no armazem
     */
    public boolean verificarQuantidade(int valor)
    {
        if(this.getQuantidade() >= valor)
            return true;
        return false;
    }
}
