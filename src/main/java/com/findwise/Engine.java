package com.findwise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@AllArgsConstructor
public class Engine implements SearchEngine {
    @Getter
    @Setter
    private Map<String, Set<String>> wordToDocumentIds;
    @Getter
    @Setter
    private int numberOfDocuments;
    @Getter
    @Setter
    private Map<String, Map<String, Double>> sentenceTfMap;

    @Getter
    @Setter
    private Map<String, Map<String, Double>> sentenceTfIdfMap;

    public Engine(Map<String, Set<String>> wordToDocumentIds, int numberOfDocuments) {
        this.wordToDocumentIds = wordToDocumentIds;
        this.numberOfDocuments = numberOfDocuments;
        this.sentenceTfMap = new HashMap<>();
        this.sentenceTfIdfMap = new HashMap<>();
    }
    @Override
    public void indexDocument(String id, String content) {
       createInvertedIndex(id, content);
    }

    private void createInvertedIndex(String id, String content) {
        String[] tokens = content.trim().split("\\s+");
        HashSet<String> tokenSet = new HashSet<>(List.of(tokens));
        Map<String, Double> tfMap = new HashMap<>();
        Map<String, Double> tfIdfMap = new HashMap<>();
        for(String token : tokenSet) {
            int frequency = Collections.frequency(List.of(tokens), token);
            double tf = (double) frequency / (double) tokens.length;
            tfMap.put(token, tf);
            int tokenDocuments = wordToDocumentIds.get(token).size();
            double df = (double) numberOfDocuments / (double) tokenDocuments;
            double idf = Math.log10(df);
            double tfidf = tf * idf;
            tfIdfMap.put(token, tfidf);
        }
        this.sentenceTfMap.put(id, tfMap);
        this.sentenceTfIdfMap.put(id, tfIdfMap);
    }

    @Override
    public List<IndexEntry> search(String term) {
        List<IndexEntry> indexEntries = new ArrayList<>();
        Map<String, List<Double>> sentenceScoreMap = new HashMap<>();
        String[] tokenized = term.trim().split("\\s+");
        for(String token : tokenized) {
            Set<String> documentIds = wordToDocumentIds.get(token);
            for(String id : documentIds) {
                Map<String, Double> tfIdfMap = sentenceTfIdfMap.get(id);
                if(sentenceScoreMap.containsKey(id)) {
                    sentenceScoreMap.get(id).add(tfIdfMap.get(token));
                } else {
                    sentenceScoreMap.put(id, new ArrayList<>(Collections.singletonList(tfIdfMap.get(token))));
                }
            }
        }
        for(String id : sentenceScoreMap.keySet()) {
            double score = 0;
            for(Double tfidf : sentenceScoreMap.get(id)) {
                score += tfidf;
            }
            Entry entry = new Entry();
            entry.setId(id);
            entry.setScore(score / sentenceScoreMap.get(id).size());
            indexEntries.add(entry);
        }
        return indexEntries;
    }
}
