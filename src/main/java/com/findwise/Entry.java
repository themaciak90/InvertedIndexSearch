package com.findwise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Entry implements IndexEntry {
    @Setter
    @Getter
    String id;
    @Setter
    @Getter
    double score;
}
