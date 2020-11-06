package gr.cons.elst;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.util.Arrays;

public class Elst {

    public static String lowerStopAndStep(String word) {
        ElstGreekAnalyzer analyzer = new ElstGreekAnalyzer();

        TokenStream tokenStream = analyzer.tokenStream(null, word);
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
//            int startOffset = offsetAttribute.startOffset();
//            int endOffset = offsetAttribute.endOffset();
                return charTermAttribute.toString();
            }
        } catch(IOException e) {
            throw new IllegalArgumentException("Could not process: " + word, e);
        }
        return word;
    }
}
