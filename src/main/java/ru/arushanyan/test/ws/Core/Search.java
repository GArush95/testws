package ru.arushanyan.test.ws.Core;

import ru.arushanyan.test.ws.Utils.Method.FileList;
import ru.arushanyan.test.ws.Utils.Data.PairOfDates;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Garushanyan on 16.11.2016.
 **/

public class Search {
    public static ArrayList<String> findWord(String word, ArrayList<PairOfDates> pairOfDates, String location)
    {
        ArrayList<String> results = new ArrayList<>();
        StringBuilder blockBuilder = new StringBuilder();
        boolean flagC = false;
        long tsStart, tsEnd;
        Pattern wordPattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
        Pattern tsPattern = Pattern.compile("\\d{13}");
        long tstamp = 0;
        XMLGregorianCalendar dateStart, dateEnd;
        ArrayList<File> files;


        files = FileList.getFiles(location, word);


        for (File slog1:files) {
            for (PairOfDates elem : pairOfDates) {

                dateStart = elem.getDateFrom();
                dateEnd = elem.getDateTo();

                if (dateStart == null) {
                    tsStart = 0;
                } else {
                    dateStart = dateStart.normalize();
                    tsStart = dateStart.toGregorianCalendar().getTimeInMillis();
                }

                if (dateEnd == null) {
                    tsEnd = System.currentTimeMillis();
                } else {
                    dateEnd = dateEnd.normalize();
                    tsEnd = dateEnd.toGregorianCalendar().getTimeInMillis();
                }


                try (LineNumberReader lnr = new LineNumberReader(new BufferedReader(new FileReader(slog1)))) {
                    String lnrString;
                    while ((lnrString = lnr.readLine()) != null) {
                        Matcher wordMatch = wordPattern.matcher(lnrString);

                        if ((lnrString.startsWith("####")) && flagC) {
                            Matcher tsm = tsPattern.matcher(blockBuilder);

                            if (tsm.find()) {
                                tstamp = Long.parseLong(tsm.group());
                            }

                            if ((tstamp >= tsStart) && (tstamp <= tsEnd) && !results.contains(blockBuilder.toString())) {
                                results.add(blockBuilder.toString());
                                flagC = false;
                            }
                        }

                        if (lnrString.startsWith("####")) {
                            blockBuilder.setLength(0);
                        }
                        blockBuilder.append("<<< ").append(lnr.getLineNumber()).append(" >>> ").append(lnrString).append('\n');

                        if (wordMatch.find()) {
                            Matcher tsm = tsPattern.matcher(blockBuilder);

                            if (tsm.find()) {
                                tstamp = Long.parseLong(tsm.group());
                            }

                            if ((tstamp >= tsStart) && (tstamp <= tsEnd)) {
                                flagC = true;
                            }
                        }
                    }
                    Matcher wordMatch = wordPattern.matcher(blockBuilder);
                    Matcher tsm = tsPattern.matcher(blockBuilder);

                    if (wordMatch.find()) {

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




