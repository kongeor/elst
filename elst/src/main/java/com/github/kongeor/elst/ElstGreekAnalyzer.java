package com.github.kongeor.elst;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.el.GreekLowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;

public final class ElstGreekAnalyzer extends StopwordAnalyzerBase {
    // https://github.com/stopwords-iso/stopwords-el
    public static final String DEFAULT_STOPWORD_FILE = "/stopwords-el.txt";

    private final boolean stop;
    private final boolean stem;

    public ElstGreekAnalyzer() {
        this(true, true);
    }

    public ElstGreekAnalyzer(boolean stop, boolean stem) {
        this(DefaultSetHolder.DEFAULT_SET, stop, stem);
    }

    public ElstGreekAnalyzer(CharArraySet stopwords, boolean stop, boolean stem) {
        super(stopwords);
        this.stop = stop;
        this.stem = stem;
    }

    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new GreekLowerCaseFilter(source);
        if (stop) {
            result = new StopFilter(result, this.stopwords);
        }
        if (stem) {
            result = new ElstGreekStemTokenFilter(result);
        }
        return new TokenStreamComponents(source, result);
    }

    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new GreekLowerCaseFilter(in);
    }

    private static class DefaultSetHolder {
        private static final CharArraySet DEFAULT_SET;

        private DefaultSetHolder() {
        }

        static {
            try {
                DEFAULT_SET = GreekAnalyzer.loadStopwordSet(false, ElstGreekAnalyzer.class, DEFAULT_STOPWORD_FILE, "#");
            } catch (IOException var1) {
                throw new RuntimeException("Unable to load default stopword set");
            }
        }
    }
}