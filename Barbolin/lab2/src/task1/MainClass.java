//package com.company;
import java.io.*;
import java.util.*;
public class MainClass {
    public static void main(String[] args) {
        try(FileReader file = new FileReader(args[0])) {
            BufferedReader reader=new BufferedReader(file);
            String str=reader.readLine();
            String words[];
            Map<String, Integer> map=new HashMap<String, Integer>();
            while(str!=null){
                words=str.split("\\s+");
                for (String word:words)
                    if (map.containsKey(word.toLowerCase()) == false) {
                        System.out.print(word + " ");
                        map.put(word.toLowerCase(), 1);
                    }
                System.out.println();
                str=reader.readLine();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}