/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.List;

/**
 *
 * @author iderli
 */

public class Tecnico {
private int senha;

    public Tecnico(int senha) {
        this.senha = senha;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }
    
    public boolean checkTecnico (int senha){
        if(senha == this.getSenha()){
            return true; 
        } 
        return false;
    }

}
