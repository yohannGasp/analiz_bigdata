/*
 * vki
 *
 * Akulov Evgeniy (c), 2016
 */
package cnvrvki;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Jsea {

    public static final String HD_OPER = "d:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\cft_hd_oper.txt";
    public static final String PROV1 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\01.txt";
    public static final String PROV2 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\02.txt";
    public static final String PROV3 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\03.txt";
    public static final String PROV4 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\04.txt";
    public static final String PROV5 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\05.txt";
    public static final String PROV6_1 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\0601.txt";
    public static final String PROV6 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\06.txt";
    public static final String PROV7 = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\prov\\0701.txt";
    public static final String FILE_RES = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\file_result.txt";
    public static final String FILE_ERR = "D:\\проекты\\выгрузка ЦФТ\\внутбух\\хоз_oper\\file_err.txt";

    public List<Provodki> lines = new ArrayList<>();

    /*
        add pr to arraylist
    */
    private void addList(BufferedReader buf) throws IOException {
        String line;
        String type1, debet, kredit, debet_full, kredit_full;
        Provodki p;
        int count = 0;
        while ((line = buf.readLine()) != null) {
            count++;
            if (count > 1) {
                p = new Provodki();
                p.vki = line.substring(0, 28);
                p.data = line.substring(53, 63);
                p.number = line.substring(68, 76).trim();
                p.sum = line.substring(580, 599).trim(); // 540, 557
                type1 = line.substring(920, 935).trim();
                debet = line.substring(107, 112).trim();
                kredit = line.substring(132, 137).trim();
                debet_full = line.substring(107, 127).trim();
                kredit_full = line.substring(132, 152).trim();
                p.debet = debet_full;
                p.kredit = kredit_full;
                //if (debet.equals("60311") || debet.equals("60312") || debet.equals("60323") || debet.equals("60301") || debet.equals("60322") || kredit.equals("60311") || kredit.equals("60312") || kredit.equals("60323") || kredit.equals("60301") ||kredit.equals("60322") ) {
                    if (debet.startsWith("603")) {
                        p.type = true; //оплата
                        p.acct = debet_full;
                    } else {
                        p.type = false; //поставка
                        p.acct = kredit_full;
                    }
                    lines.add(p);

            }
        }
        System.out.println(lines.size());
    }

    /*

    */
    public void analiz() {
        String date;
        String nomer;
        String Summa;
        String vki;
        String num_dog;
        String type_doc;
        String acct;
        Semaphore sem = new Semaphore(1);
        int sdf = 0;
        int Kol = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(HD_OPER), "Cp866"));
                BufferedReader p1 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV1), "Cp866"));
                BufferedReader p2 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV2), "Cp866"));
                BufferedReader p3 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV3), "Cp866"));
                BufferedReader p4 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV4), "Cp866"));
                BufferedReader p5 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV5), "Cp866"));
                BufferedReader p61 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV6_1), "Cp866"));
                BufferedReader p6 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV6), "Cp866"));
                BufferedReader p7 = new BufferedReader(new InputStreamReader(new FileInputStream(PROV7), "Cp866"));
                OutputStreamWriter f0 = new OutputStreamWriter(new FileOutputStream(FILE_RES), "Cp866");
                FileWriter fe = new FileWriter(FILE_ERR);) {

            System.out.println("START");
            f0.write("<H_VKI_DOG         ><H_DATE_BEGIN><H_KOD_OPER        ><H_SUMMA           ><H_VKI_DOC                 >END_HEAD\r\n");
            long beginT, endT;
            beginT = System.nanoTime();
            addList(p1);
            addList(p2);
            addList(p3);
            addList(p4);
            addList(p5);
            addList(p61);
            addList(p6);
            addList(p7);
            endT = System.nanoTime();
            System.out.println("Elapsed time: " + (endT - beginT) + " ns");
            /*
                        Operat promiss
             */
            System.out.println("OPERATIONS PROMISS");
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
                if (count > 1) {
                    date = line.substring(20, 30);
                    Formatter frm = new Formatter();
                    nomer = frm.format("%08d", Integer.parseInt(line.substring(74, 90).trim())).toString();
                    frm.close();
                    Summa = line.substring(54, 70).trim();
                    num_dog = line.substring(0, 20).trim();
                    type_doc = line.substring(34, 54).trim();
                    acct = line.substring(94, 114).trim();
                    String deb1 = line.substring(115, 135).trim();
                    String kred1 = line.substring(136, 156).trim();
                    Kol++;
                    /*
                              search in ArrayList
                     */
                    vki = "";
                    // find oplats
                    for (int i = 0; i < lines.size(); i++) {
                        if (lines.get(i).number.equals(nomer) & lines.get(i).data.equals(date) & lines.get(i).sum.equals(Summa)) {
                            vki = vki + lines.get(i).vki.substring(0,25);
                            sdf++;
                        }
                    }
                    // add one day
                    int year = Integer.parseInt(line.substring(26, 30));
                    int mon = Integer.parseInt(line.substring(23, 25));
                    int day = Integer.parseInt(line.substring(20, 22));
                    Calendar c = new GregorianCalendar(year,mon,day);
                    if (c.get(Calendar.DAY_OF_WEEK) == 2) {
                        c.add(Calendar.DAY_OF_YEAR, 3);
                        //System.out.println(date + " " + c.get(Calendar.DAY_OF_MONTH));
                    } else {
                        c.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    String day1 = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                    if (day1.length() == 1) day1 = "0" + day1;
                    String mon1 = String.valueOf(c.get(Calendar.MONTH));
                    if (mon1.length() == 1) mon1 = "0" + mon1;
                    String date_1 = day1 + "/" + mon1 + "/" + c.get(Calendar.YEAR);

                    //find with two algorithm
                    if (CnvrVKI.JF.jchkbox.isSelected()) {
                        if (vki.equals("")) {
                            if (type_doc.equals("ЗАК_УСЛУГ_БАНКУ")) {
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).data.equals(date) & lines.get(i).debet.equals(deb1) & lines.get(i).kredit.equals(kred1) & lines.get(i).sum.equals(Summa)) {
                                        vki = vki + lines.get(i).vki.substring(0,25);
                                          sdf++;
                                        /*if (!lines.get(i).acct.equals(acct)) {
                                            Formatter fmt1 = new Formatter();
                                            String str1 = fmt1.format("%-19s %-13s %-19s %-19s %s %s%n", num_dog, date, type_doc, Summa, acct, lines.get(i).acct).toString();
                                            fmt1.close();
                                            fe.write(str1);
                                        }*/
                                        break;
                                    }
                                }
                                /*if (type_doc.equals("БЕЗН_ОПЛ_МЕМ_ОРД")) {
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).type == true & lines.get(i).data.equals(date) & lines.get(i).acct.equals(acct)) {
                                        vki = vki + lines.get(i).vki;
                                        sdf++;
                                        //break;
                                    }
                                }*/
                            }
                        }
                    }
                    //find with three algorithm
                    if (CnvrVKI.JF.jchkbox2.isSelected()) {
                        if (vki.equals("")) {
                            if (type_doc.equals("ЗАК_УСЛУГ_БАНКУ")) {
                                for (int i = 0; i < lines.size(); i++) {
                                    if (lines.get(i).data.equals(date_1) & lines.get(i).debet.equals(deb1) & lines.get(i).kredit.equals(kred1) & lines.get(i).sum.equals(Summa)) {
                                        vki = vki + lines.get(i).vki.substring(0,25);
                                        sdf++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    Formatter fmt = new Formatter();
                    String str = fmt.format("%-19s %-13s %-19s %-19s %s%n", num_dog, date, type_doc, Summa, vki).toString();
                    fmt.close();
                    f0.write(str);
                }
            }
            f0.flush();
            fe.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("END");
        lines.clear();
        Runtime.getRuntime().gc();
        System.out.println("All count: " + Kol);
        System.out.println("search count: " + sdf);
        System.out.println("Percent: " + (float) sdf / Kol * 100);
    }
}
