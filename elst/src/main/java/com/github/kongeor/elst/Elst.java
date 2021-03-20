package com.github.kongeor.elst;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Elst {

    public static String lowerStopAndStem(String word) {
        ElstGreekAnalyzer analyzer = new ElstGreekAnalyzer();

        TokenStream tokenStream = analyzer.tokenStream(null, word);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                return charTermAttribute.toString();
            }
        } catch(IOException e) {
            throw new IllegalArgumentException("Could not process: " + word, e);
        }
        return null;
    }

    public static List<String> lowerStopAndStemPhrase(String phrase) {
        return Arrays.stream(phrase.split("\\s+|_|-|:"))
                .map(Elst::lowerStopAndStem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
