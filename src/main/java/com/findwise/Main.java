package com.findwise;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, String> documentMap = MyFileReader.readFile("C:\\Users\\MSI\\Desktop\\studia pjatk\\InvertedIndexSearch\\untitled\\src\\Documents.txt");
        InvertedIndexService indexService = new InvertedIndexService();
        for(String key : documentMap.keySet()) {
            indexService.addToInvertedIndex(key, documentMap.get(key));
        }
        Engine engine = new Engine(indexService.getInvertedIndexMap(), documentMap.size());
        for(String key : documentMap.keySet()) {
            engine.indexDocument(key, documentMap.get(key));
        }
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while(run) {
            System.out.println("Insert your search term:");
            String term = scanner.nextLine();
            List<IndexEntry> entries = engine.search(term);
            entries.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());
            System.out.println("Result");
            for(IndexEntry entry : entries) {
                System.out.println(entry.getId() + " " + documentMap.get(entry.getId()) + " " + entry.getScore());
            }
            System.out.println("Insert 1 to search another term.");
            System.out.println("Insert 2 to quit");
            String choice = scanner.nextLine();

            if(choice.equals("2")) {
                run = false;
            }
        }

    }
}