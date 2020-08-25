package com.experiments.triewordsuggestion.Trie;

import java.util.HashMap;

public class Node {
    HashMap<Character,Node> children;
    boolean isCompleteWord;

    public Node(HashMap<Character, Node> children, boolean isCompleteWord) {
        this.children = children;
        this.isCompleteWord = isCompleteWord;
    }

    
}
