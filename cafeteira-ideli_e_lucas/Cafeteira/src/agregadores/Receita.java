package agregadores;

import java.util.ArrayList;
import core.Copo;
/**
 *
 * @author micre
 */
import core.Ingrediente;
public class Receita {
    private String descricao;
    private int valor;
    private String tipo;
    private ArrayList<IngredienteReceita> ingredientesReceita;
    public Copo copo;
    private boolean status;

    public Receita(String descricao, int valor, String tipo, Copo copo)
    {
        this.copo = copo;
        this.setDescricao(descricao);
        this.setValor(valor);
        this.setTipo(tipo);        
        this.setStatus(true);
        this.ingredientesReceita = new ArrayList<IngredienteReceita>();
    }

    public String getDescricao() {
        return descricao;
    }
    
    public Receita getReceita() {
        return this;
    }

    public int getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }
    
    public boolean getStatus() {
        return status;
    }

    private void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private void setValor(int valor) {
        this.valor = valor;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addIngredienteNaReceita(Ingrediente ingrediente, int quantidade)
    {
        ingredientesReceita.add(new IngredienteReceita(ingrediente, quantidade));
    }
    
    public int totalIngredientes() {
        return ingredientesReceita.size();
    }
    
    public String tamanhoCopo() {
        return copo.getNome();
    }
    
    public int volumeCopo() {
        return copo.getVolume();
    }
    
    public ArrayList<IngredienteReceita> getIngredientes() {
        return ingredientesReceita;
    }
    
    public IngredienteReceita buscarIngredienteNaReceita(String nome)
    {
        for(int i = 0; i < ingredientesReceita.size(); i++)
        {
            if(ingredientesReceita.get(i).ingrediente.getNome().equals(nome))
                return ingredientesReceita.get(i);
        }
        return null;
    }
}
