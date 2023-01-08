package com.findwise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyFileReader {

    private static int id = 0;
    public static Map<String, String> readFile(String path) {
        BufferedReader reader;
        Map<String, String> documentMap = new HashMap<>();

        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while(line != null) {
                documentMap.put(String.valueOf(id), line);
                id++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documentMap;
    }


}
