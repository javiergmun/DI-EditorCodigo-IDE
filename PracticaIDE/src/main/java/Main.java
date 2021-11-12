//import com.formdev.flatlaf.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            //tengo el mismo codigo pero me lo lanza a un nuevo hilo con una vista independiente para la vista
            @Override
            public void run() {
                //new JFrame().setVisible(true)   ES IGUAL AL DE ABAJO YA QUE VENTANA extends JFRAME
                new Ventana().setVisible(true);
            }
        });
    }
}