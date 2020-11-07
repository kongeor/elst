package com.github.kongeor.elst;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.el.GreekLowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;

public final class ElstGreekAnalyzer extends StopwordAnalyzerBase {
    public static final String DEFAULT_STOPWORD_FILE = "stopwords.txt";

    public static final CharArraySet getDefaultStopSet() {
        return DefaultSetHolder.DEFAULT_SET;
    }

    public ElstGreekAnalyzer() {
        this(DefaultSetHolder.DEFAULT_SET);
    }

    public ElstGreekAnalyzer(CharArraySet stopwords) {
        super(stopwords);
    }

    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new GreekLowerCaseFilter(source);
        result = new StopFilter(result, this.stopwords);
        result = new ElstGreekStemTokenFilter(result);
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
                DEFAULT_SET = org.apache.lucene.analysis.el.GreekAnalyzer.loadStopwordSet(false, org.apache.lucene.analysis.el.GreekAnalyzer.class, "stopwords.txt", "#");
            } catch (IOException var1) {
                throw new RuntimeException("Unable to load default stopword set");
            }
        }
    }
}