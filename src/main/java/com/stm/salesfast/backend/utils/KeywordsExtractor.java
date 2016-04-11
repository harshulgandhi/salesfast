package com.stm.salesfast.backend.utils;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Keywords extractor functionality handler
 */
public class KeywordsExtractor {

    /**
     * Get list of keywords with stem form, frequency rank, and terms dictionary
     *
     * @param fullText
     * @return List<CardKeyword>, which contains keywords cards
     * @throws IOException
     */
    public static List<CardKeyword> getKeywordsList(String fullText) throws IOException {

        TokenStream tokenStream = null;

        try {
            // treat the dashed words, don't let separate them during the processing
            fullText = fullText.replaceAll("-+", "-0");

            // replace any punctuation char but apostrophes and dashes with a space
            fullText = fullText.replaceAll("[\\p{Punct}&&[^'-]]+", " ");

            // replace most common English contractions
            fullText = fullText.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");

            StandardTokenizer stdToken = new StandardTokenizer();
            stdToken.setReader(new StringReader(fullText));
            
            /* Making custom list of stop words
             * */
            final List<String> stopWords = Arrays.asList(
            	      "a", "an", "and", "are", "as", "at", "be", "but", "by",
            	      "for", "if", "in", "into", "is", "it",
            	      "no", "not", "of", "on", "or", "such",
            	      "that", "the", "their", "then", "there", "these",
            	      "they", "this", "to", "was", "will", "with", "extreme",
            	      "very", "high", "severe"
            	    );
            final CharArraySet stopSet = new CharArraySet(stopWords, false);
            final CharArraySet ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
            
            tokenStream = new StopFilter(new ASCIIFoldingFilter(new ClassicFilter(new LowerCaseFilter(stdToken))), ENGLISH_STOP_WORDS_SET);//(CharArraySet) EnglishAnalyzer.getDefaultStopSet());
            tokenStream.reset();

            List<CardKeyword> cardKeywords = new LinkedList<>();

            CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);

            while (tokenStream.incrementToken()) {

                String term = token.toString();
                String stem = getStemForm(term);

                if (stem != null) {
                    CardKeyword cardKeyword = find(cardKeywords, new CardKeyword(stem.replaceAll("-0", "-")));
                    // treat the dashed words back, let look them pretty
                    cardKeyword.add(term.replaceAll("-0", "-"));
                }
            }

            // reverse sort by frequency
            Collections.sort(cardKeywords);

            return cardKeywords;
        } finally {
            if (tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get stem form of the term
     *
     * @param term
     * @return String, which contains the stemmed form of the term
     * @throws IOException
     */
    private static String getStemForm(String term) throws IOException {

        TokenStream tokenStream = null;

        try {
            StandardTokenizer stdToken = new StandardTokenizer();
            stdToken.setReader(new StringReader(term));

            tokenStream = new PorterStemFilter(stdToken);
            tokenStream.reset();

            // eliminate duplicate tokens by adding them to a set
            Set<String> stems = new HashSet<>();

            CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);

            while (tokenStream.incrementToken()) {
                stems.add(token.toString());
            }

            // if stem form was not found or more than 2 stems have been found, return null
            if (stems.size() != 1) {
                return null;
            }

            String stem = stems.iterator().next();

            // if the stem form has non-alphanumerical chars, return null
            if (!stem.matches("[a-zA-Z0-9-]+")) {
                return null;
            }

            return stem;
        } finally {
            if (tokenStream != null) {
                try {
                    tokenStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Find sample in collection
     *
     * @param collection
     * @param sample
     * @param <T>
     * @return <T> T, which contains the found object within collection if exists, otherwise the initially searched object
     */
    private static <T> T find(Collection<T> collection, T sample) {

        for (T element : collection) {
            if (element.equals(sample)) {
                return element;
            }
        }

        collection.add(sample);

        return sample;
    }
    
   /*public static void main(String[] args) throws IOException {
		System.out.println("Analysing...");
		String text = "Skin problems pain in stomach stomach-ache sore throat Itching Nausea Boyels Side Nausea effect 2 Itching itching Serious side effect Stomachache Nausea Head ache Body Ache Head ache Sore throat";
		text = text.replaceAll("-+", "");
		text = text.toLowerCase().replaceAll(" ache","ache");
		
		List<CardKeyword> keywordsList = KeywordsExtractor.getKeywordsList(text);
		for(CardKeyword eachList : keywordsList){
			System.out.println(eachList.getStem()+" [ "+eachList.getFrequency()+" ] ==> "+eachList.getTerms());
		}
		
		text = text.replaceAll("-+", "");
		System.out.println("Replacing - test \n"+text);
	}*/
    
}