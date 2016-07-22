/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnvrvki;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ****************************************************************
 * // A thread of 1
******************************************************************
 */
public class Thread1 implements Runnable {

    String name;
    Semaphore sem;
    public static final String PROV1 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\01.txt";

    String line;
    int count;
    int kl;
    String type1;
    Provodki p;
    Thread t;

    Thread1(Semaphore s, String n) {
        sem = s;
        name = n;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        System.out.println("Starting " + name);
        try (BufferedReader p1 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV1), "Cp866"))) {
            count = 0;
            while ((line = p1.readLine()) != null) {
                count++;
                if (count > 1) {
                    p = new Provodki();
                    p.vki = line.substring(0, 28);
                    p.data = line.substring(53, 63);
                    p.number = line.substring(68, 76).trim();
                    p.sum = line.substring(540, 557).trim();
                    type1 = line.substring(920, 935).trim();
                    //if (type1.equals("БЕЗН_ПЛ_ПОРУЧ")) {
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Shared.lines.add(p);
                    sem.release();
                    //}
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Stop " + name);
    }
}
