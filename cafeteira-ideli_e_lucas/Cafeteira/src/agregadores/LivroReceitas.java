package agregadores;
import java.util.ArrayList;
import core.Copo;
/**
 *
 * @author micre
 */
public class LivroReceitas {
   public ArrayList<Receita> livroReceitas;
   
   public LivroReceitas()
   {
       livroReceitas = new ArrayList<Receita>();
   }
   
   public void addReceitaNoLivroReceita(String descricao, int valor, String tipo, Copo copo)
   {
       livroReceitas.add(new Receita(descricao, valor, tipo, copo));
       //System.out.println("Descrição : " + descricao + " | Valor: " + valor + " | Tipo: " + tipo + " | Copo (Nome): " + copo.getNome());
       //livroReceitas.add(new Receita(descricao, valor, tipo, copo));
   }
   
   public void addReceitaNoLivroReceita(Receita receita)
   {
       livroReceitas.add(receita);
   }
   
   public Receita procuraReceitaLivro(String descricao)
   {
        for(int i = 0; i < livroReceitas.size(); i++)
        {
            if(livroReceitas.get(i).getReceita().getDescricao().equals(descricao))
                return livroReceitas.get(i).getReceita();
        }
        return null;   
   }
   
   public void mostrarDados()
   {
        for(int i = 0; i < livroReceitas.size(); i++)
        {
            System.out.println("Receita - " + livroReceitas.get(i).getDescricao() + " | Preço: " + livroReceitas.get(i).getValor());
            for(int y = 0; y < livroReceitas.get(i).getIngredientes().size(); y++)
            {
                System.out.println(":: Ingrediente - " + livroReceitas.get(i).getIngredientes().get(y).ingrediente.getNome() + " (" + livroReceitas.get(i).getIngredientes().get(y).quantidade + ")" );
            }
        }
   }
}
