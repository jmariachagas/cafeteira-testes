/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agregadores;
import core.Ingrediente;

/**
 *
 * @author micre
 */
public class IngredienteReceita {
    public Ingrediente ingrediente;
    public int quantidade;
    
    public IngredienteReceita(Ingrediente ingrediente, int quantidade)
    {
        this.ingrediente = ingrediente;
        this.setQuantidade(quantidade);
    }
    
    public Ingrediente getIngrediente()
    {
        return this.ingrediente;
    }
    
    private void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }
    
    public int getQuantidade()
    {
        return this.quantidade;
    }
}
