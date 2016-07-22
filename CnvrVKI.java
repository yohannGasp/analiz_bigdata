package cnvrvki;

import javax.swing.*;

public class CnvrVKI {

    public static JMainForm JF;

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JF = new JMainForm();
            }
        });
    }
}
