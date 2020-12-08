//package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class MainClass {
    public static void main(String[] args) {
        List<BufferedReader> readers = new ArrayList<>();
        List<PrintStream> print = new ArrayList<>();
        print.add(System.out);
        if(args.length == 0 || !args[0].equals("cat")){
            System.out.println("Error input");
            return;
        }
        int i = 1;
        for (i = 1; i < args.length && !args[i].equals(">"); i++)
        {
            if(args[i].equals("-"))
                readers.add(new BufferedReader(new InputStreamReader(System.in)));
            else {
                try {
                    readers.add(new BufferedReader(new FileReader(args[i]))); }
                catch (FileNotFoundException e) { e.printStackTrace();
                }
            }
        }
        if(i+1 < args.length) print.clear();
        for(int j = i+1; j< args.length;j++){
            try {
                print.add(new PrintStream(args[j]));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < readers.size(); j++) {
            String read="";
            while (true) {
                try {
                    if (!((read = readers.get(j).readLine()) != null && read.length() != 0))
                        break;
                }
                catch (IOException e) { e.printStackTrace();
                }
                for (int k = 0; k < print.size(); k++) {
                    print.get(k).println(read);
                }
            }
        }
    }
}