package ru.arushanyan.test.ws;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Garushanyan on 16.11.2016.
 */

public class Search {
    //public static ArrayList<String> findWord(String word, XMLGregorianCalendar dateStart, XMLGregorianCalendar dateEnd)
    public static ArrayList<String> findWord(String word, ArrayList<PairOfDates> pairOfDates)
    {
        //File slog1= new File ("C:\\Users\\Garushanyan\\Desktop\\java\\Java Developer\\ws_server1.log02354");
        File slog1 = new File("C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\servers\\c1_s1\\logs\\c1_s1.log");
        ArrayList<String> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean flagC = false;
        GregorianCalendar gc = new GregorianCalendar();
        long tsStart = 0, tsEnd = 0;
        Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        Pattern ts = Pattern.compile("\\d{13}");
        long tstamp = 0;
        XMLGregorianCalendar dateStart = null, dateEnd = null;
        int i=0;

        for (PairOfDates elem : pairOfDates) {

            dateStart=elem.getDateFrom();
            dateEnd=elem.getDateTo();

            if (dateStart == null) {
                if (dateEnd == null) {
                    tsStart = 0;
                    tsEnd = System.currentTimeMillis();
                } else {
                    dateEnd = dateEnd.normalize();
                    gc = dateEnd.toGregorianCalendar();
                    tsEnd = gc.getTimeInMillis();
                }
            } else {
                if (dateEnd == null) {
                    tsEnd = System.currentTimeMillis();
                    dateStart = dateStart.normalize();
                    gc = dateStart.toGregorianCalendar();
                    tsStart = gc.getTimeInMillis();
                } else {
                    dateStart = dateStart.normalize();
                    gc = dateStart.toGregorianCalendar();
                    tsStart = gc.getTimeInMillis();
                    dateEnd = dateEnd.normalize();
                    gc = dateEnd.toGregorianCalendar();
                    tsEnd = gc.getTimeInMillis();
                }
            }


            try (LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(slog1)))) {
                String s;
                while ((s = lnr.readLine()) != null) {
                    Matcher m = p.matcher(s);

                    if ((s.startsWith("####")) && flagC) {
                        Matcher tsm = ts.matcher(sb);
                        if (tsm.find())
                            tstamp = Long.parseLong(tsm.group());
                        if ((tstamp >= tsStart) && (tstamp <= tsEnd)) {
                            results.add(sb.toString());
                            flagC = false;
                        }
                    }

                    if (s.startsWith("####"))
                        sb.setLength(0);
                    sb.append("<<< " + lnr.getLineNumber() + " >>> " + s + '\n');

                    if ((m.find())) {
                        Matcher tsm = ts.matcher(sb);
                        if (tsm.find())
                            tstamp = Long.parseLong(tsm.group());
                        if ((tstamp >= tsStart) && (tstamp <= tsEnd)) {
                            flagC = true;
                        }
                    }
                }
                Matcher m = p.matcher(sb);
                Matcher tsm = ts.matcher(sb);

                if ((m.find())) {
                    if (tsm.find())
                        tstamp = Long.parseLong(tsm.group());
                    if ((tstamp >= tsStart) && (tstamp <= tsEnd)) {
                        results.add(sb.toString());
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
                System.exit(0);
            }
        }
            return results;
    }
}




