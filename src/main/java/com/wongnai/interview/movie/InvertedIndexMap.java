package com.wongnai.interview.movie;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class InvertedIndexMap {

    private HashMap<String, HashSet<Long>> map;

    public InvertedIndexMap(){
        map = new HashMap<>();
    }

    public void setMap(HashMap<String, HashSet<Long>> map) {
        this.map = map;
    }

    public HashMap<String, HashSet<Long>> getMap() {
        return map;
    }
}


