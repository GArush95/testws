
package ru.arushanyan.test.ws.Utils.Method;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Garushanyan on 25.11.2016.
 **/
public class FileList {
    public static ArrayList<File> getFiles(String location, String word) {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        ArrayList<File> filelist = new ArrayList<>();



        switch (location) {
            case "domain": {

                try {
                    proc = rt.exec("cmd /c findstr /I /M /S /c:" + word + " C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\c*.log*");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String line;
                if (proc != null) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                        while ((line = in.readLine()) != null) {
                            filelist.add(new File(line));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "cluster": {

                try {
                    proc = rt.exec("cmd /c findstr /I /M /S /c:" + word + " C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\servers\\c*.log*");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String line;
                if (proc != null) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                        while ((line = in.readLine()) != null) {
                            filelist.add(new File(line));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "server1": {
                try {
                    proc = rt.exec("cmd /c findstr /I /M /S /c:" + word + " C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\servers\\c1_s1\\c*.log*");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String line;
                if (proc != null) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                        while ((line = in.readLine()) != null) {
                            filelist.add(new File(line));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            default: {
                try {
                    proc = rt.exec("cmd /c findstr /I /M /S /c:" + word + " C:\\oracle\\Middleware\\Oracle_Home\\user_projects\\domains\\base_domain3\\servers\\c1_s2\\c*.log*");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String line;
                if (proc != null) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                        while ((line = in.readLine()) != null) {
                            filelist.add(new File(line));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        return filelist;
    }
}