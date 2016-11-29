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
    public static ArrayList<String> findWord(String word, ArrayList<PairOfDates> pairOfDates, String location)
    {
        //File slog1= new File ("C:\\Users\\Garushanyan\\Desktop\\java\\Java Developer\\ws_server1.log02354");
        //File slog1 = new File("C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\servers\\c1_s1\\logs\\c1_s1.log");
        ArrayList<String> results = new ArrayList<>();
        StringBuilder blockBuilder = new StringBuilder();
        boolean flagC = false;
        long tsStart = 0, tsEnd;
        Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        Pattern ts = Pattern.compile("\\d{13}");
        long tstamp = 0;
        XMLGregorianCalendar dateStart, dateEnd;
        ArrayList<File> files;


        files = FileList.getFiles(location, word);


        for (File slog1:files) {
            for (PairOfDates elem : pairOfDates) {

                dateStart = elem.getDateFrom();
                dateEnd = elem.getDateTo();

                if (dateStart == null) {
                    if (dateEnd == null) {
                        tsStart = 0;
                        tsEnd = System.currentTimeMillis();
                    } else {
                        dateEnd = dateEnd.normalize();
                        tsEnd = dateEnd.toGregorianCalendar().getTimeInMillis();
                    }
                } else {
                    if (dateEnd == null) {
                        tsEnd = System.currentTimeMillis();
                        dateStart = dateStart.normalize();
                        tsStart = dateStart.toGregorianCalendar().getTimeInMillis();
                    } else {
                        dateStart = dateStart.normalize();
                        tsStart = dateStart.toGregorianCalendar().getTimeInMillis();
                        dateEnd = dateEnd.normalize();
                        tsEnd = dateEnd.toGregorianCalendar().getTimeInMillis();
                    }
                }

                try (LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(slog1)))) {
                    String s;
                    while ((s = lnr.readLine()) != null) {
                        Matcher m = p.matcher(s);

                        if ((s.startsWith("####")) && flagC) {
                            Matcher tsm = ts.matcher(blockBuilder);
                            if (tsm.find()) {
                                tstamp = Long.parseLong(tsm.group());
                            }
                            if ((tstamp >= tsStart) && (tstamp <= tsEnd) && !results.contains(blockBuilder.toString())) {
                                results.add(blockBuilder.toString());
                                flagC = false;
                            }
                        }

                        if (s.startsWith("####")) {
                            blockBuilder.setLength(0);
                        }
                        blockBuilder.append("<<< ").append(lnr.getLineNumber()).append(" >>> ").append(s).append('\n');

                        if (m.find()) {
                            Matcher tsm = ts.matcher(blockBuilder);
                            if (tsm.find())
                                tstamp = Long.parseLong(tsm.group());
                            if ((tstamp >= tsStart) && (tstamp <= tsEnd)) {
                                flagC = true;
                            }
                        }
                    }
                    Matcher m = p.matcher(blockBuilder);
                    Matcher tsm = ts.matcher(blockBuilder);

                    if (m.find()) {
                        if (tsm.find()) {
                            tstamp = Long.parseLong(tsm.group());
                        }
                        if ((tstamp >= tsStart) && (tstamp <= tsEnd) && !results.contains(blockBuilder.toString())) {
                            results.add(blockBuilder.toString());
                        }
                    }
                } catch (IOException ex) {
                    ex.getMessage();
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        }
        return results;
    }
}




