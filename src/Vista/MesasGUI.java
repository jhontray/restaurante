package Vista;

import javax.swing.*;

public class MesasGUI {


    private JPanel main;

    /*permite aceso en  en la interfa menugui
     */
    public JPanel getPanel(){
        return main;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mesasGUI");
        frame.setContentPane(new MesasGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


