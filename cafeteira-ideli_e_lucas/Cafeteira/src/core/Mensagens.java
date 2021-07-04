package core;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @version 1.0
 * @since 
 */

public final class Mensagens {

    public Mensagens() {
    }
    
    public static void mensagemTela(String msg)
    {
        showMessageDialog(null, msg);
    }
    
    public static void mandaEmailTecnico(String msg)
    {
        showMessageDialog(null, msg);
    }
}
