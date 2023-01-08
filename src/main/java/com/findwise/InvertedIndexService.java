package com.findwise;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvertedIndexService {
    @Getter
    private Map<String, Set<String>> invertedIndexMap;

    public InvertedIndexService() {
        invertedIndexMap = new HashMap<>();
    }
    public void addToInvertedIndex(String id, String content) {
        String[] tokens = content.trim().split("\\s+");
        for(String token : tokens) {
            if(this.invertedIndexMap.containsKey(token)) {
                this.invertedIndexMap.get(token).add(id);
            } else {
                this.invertedIndexMap.put(token, Stream.of(id).collect(Collectors.toCollection(HashSet::new)));
            }
        }
    }

}
