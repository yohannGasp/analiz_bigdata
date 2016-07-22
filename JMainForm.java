package cnvrvki;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JMainForm {
    JCheckBox jchkbox, jchkbox2;
    JButton jbtnAlpha;
    JButton jbtnBeta;
    JLabel  jlabel1;
    JMainForm() {
        JFrame jfrm = new JFrame("Swing Application");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(400, 200);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jbtnAlpha = new JButton("Ok");
        jbtnBeta = new JButton("Setting");
        jchkbox = new JCheckBox("2 алгоритм поиска");
        jchkbox2 = new JCheckBox("3 алгоритм поиска, дата + 1");
        jlabel1 = new JLabel("CPU Core: " + String.valueOf(Runtime.getRuntime().availableProcessors()));
        jbtnAlpha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Jsea().analiz();
            }
        });
        jbtnBeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        jfrm.add(jbtnAlpha);
        jfrm.add(jbtnBeta);
        jfrm.add(jchkbox);
        jfrm.add(jchkbox2);
        jfrm.add(jlabel1);
        //jfrm.pack();
        jfrm.setVisible(true);
    }
}
