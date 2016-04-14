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
            	      "very", "high", "severe","i","have", "feel","feeling","having",
            	      "a",
            	      "able",
            	      "about",
            	      "above",
            	      "abst",
            	      "accordance",
            	      "according",
            	      "accordingly",
            	      "across",
            	      "act",
            	      "actually",
            	      "added",
            	      "adj",
            	      "affected",
            	      "affecting",
            	      "affects",
            	      "after",
            	      "afterwards",
            	      "again",
            	      "against",
            	      "ah",
            	      "all",
            	      "almost",
            	      "alone",
            	      "along",
            	      "already",
            	      "also",
            	      "although",
            	      "always",
            	      "am",
            	      "among",
            	      "amongst",
            	      "an",
            	      "and",
            	      "announce",
            	      "another",
            	      "any",
            	      "anybody",
            	      "anyhow",
            	      "anymore",
            	      "anyone",
            	      "anything",
            	      "anyway",
            	      "anyways",
            	      "anywhere",
            	      "apparently",
            	      "approximately",
            	      "are",
            	      "aren",
            	      "arent",
            	      "arise",
            	      "around",
            	      "as",
            	      "aside",
            	      "ask",
            	      "asking",
            	      "at",
            	      "auth",
            	      "available",
            	      "away",
            	      "awfully",
            	      "b",
            	      "back",
            	      "be",
            	      "became",
            	      "because",
            	      "become",
            	      "becomes",
            	      "becoming",
            	      "been",
            	      "before",
            	      "beforehand",
            	      "begin",
            	      "beginning",
            	      "beginnings",
            	      "begins",
            	      "behind",
            	      "being",
            	      "believe",
            	      "below",
            	      "beside",
            	      "besides",
            	      "between",
            	      "beyond",
            	      "biol",
            	      "both",
            	      "brief",
            	      "briefly",
            	      "but",
            	      "by",
            	      "c",
            	      "ca",
            	      "came",
            	      "can",
            	      "cannot",
            	      "can't",
            	      "cause",
            	      "causes",
            	      "certain",
            	      "certainly",
            	      "co",
            	      "com",
            	      "come",
            	      "comes",
            	      "contain",
            	      "containing",
            	      "contains",
            	      "could",
            	      "couldnt",
            	      "d",
            	      "date",
            	      "did",
            	      "didn't",
            	      "different",
            	      "do",
            	      "does",
            	      "doesn't",
            	      "doing",
            	      "done",
            	      "don't",
            	      "down",
            	      "downwards",
            	      "due",
            	      "during",
            	      "e",
            	      "each",
            	      "ed",
            	      "edu",
            	      "effect",
            	      "eg",
            	      "eight",
            	      "eighty",
            	      "either",
            	      "else",
            	      "elsewhere",
            	      "end",
            	      "ending",
            	      "enough",
            	      "especially",
            	      "et",
            	      "et-al",
            	      "etc",
            	      "even",
            	      "ever",
            	      "every",
            	      "everybody",
            	      "everyone",
            	      "everything",
            	      "everywhere",
            	      "ex",
            	      "except",
            	      "f",
            	      "far",
            	      "few",
            	      "ff",
            	      "fifth",
            	      "first",
            	      "five",
            	      "fix",
            	      "followed",
            	      "following",
            	      "follows",
            	      "for",
            	      "former",
            	      "formerly",
            	      "forth",
            	      "found",
            	      "four",
            	      "from",
            	      "further",
            	      "furthermore",
            	      "g",
            	      "gave",
            	      "get",
            	      "gets",
            	      "getting",
            	      "give",
            	      "given",
            	      "gives",
            	      "giving",
            	      "go",
            	      "goes",
            	      "gone",
            	      "got",
            	      "gotten",
            	      "h",
            	      "had",
            	      "happens",
            	      "hardly",
            	      "has",
            	      "hasn't",
            	      "have",
            	      "haven't",
            	      "having",
            	      "he",
            	      "hed",
            	      "hence",
            	      "her",
            	      "here",
            	      "hereafter",
            	      "hereby",
            	      "herein",
            	      "heres",
            	      "hereupon",
            	      "hers",
            	      "herself",
            	      "hes",
            	      "hi",
            	      "hid",
            	      "him",
            	      "himself",
            	      "his",
            	      "hither",
            	      "home",
            	      "how",
            	      "howbeit",
            	      "however",
            	      "hundred",
            	      "i",
            	      "id",
            	      "ie",
            	      "if",
            	      "i'll",
            	      "im",
            	      "immediate",
            	      "immediately",
            	      "importance",
            	      "important",
            	      "in",
            	      "inc",
            	      "indeed",
            	      "index",
            	      "information",
            	      "instead",
            	      "into",
            	      "invention",
            	      "inward",
            	      "is",
            	      "isn't",
            	      "it",
            	      "itd",
            	      "it'll",
            	      "its",
            	      "itself",
            	      "i've",
            	      "j",
            	      "just",
            	      "k",
            	      "keep	keeps",
            	      "kept",
            	      "kg",
            	      "km",
            	      "know",
            	      "known",
            	      "knows",
            	      "l",
            	      "largely",
            	      "last",
            	      "lately",
            	      "later",
            	      "latter",
            	      "latterly",
            	      "least",
            	      "less",
            	      "lest",
            	      "let",
            	      "lets",
            	      "like",
            	      "liked",
            	      "likely",
            	      "line",
            	      "little",
            	      "'ll",
            	      "look",
            	      "looking",
            	      "looks",
            	      "ltd",
            	      "m",
            	      "made",
            	      "mainly",
            	      "make",
            	      "makes",
            	      "many",
            	      "may",
            	      "maybe",
            	      "me",
            	      "mean",
            	      "means",
            	      "meantime",
            	      "meanwhile",
            	      "merely",
            	      "mg",
            	      "might",
            	      "million",
            	      "miss",
            	      "ml",
            	      "more",
            	      "moreover",
            	      "most",
            	      "mostly",
            	      "mr",
            	      "mrs",
            	      "much",
            	      "mug",
            	      "must",
            	      "my",
            	      "myself",
            	      "n",
            	      "na",
            	      "name",
            	      "namely",
            	      "nay",
            	      "nd",
            	      "near",
            	      "nearly",
            	      "necessarily",
            	      "necessary",
            	      "need",
            	      "needs",
            	      "neither",
            	      "never",
            	      "nevertheless",
            	      "new",
            	      "next",
            	      "nine",
            	      "ninety",
            	      "no",
            	      "nobody",
            	      "non",
            	      "none",
            	      "nonetheless",
            	      "noone",
            	      "nor",
            	      "normally",
            	      "nos",
            	      "not",
            	      "noted",
            	      "nothing",
            	      "now",
            	      "nowhere",
            	      "o",
            	      "obtain",
            	      "obtained",
            	      "obviously",
            	      "of",
            	      "off",
            	      "often",
            	      "oh",
            	      "ok",
            	      "okay",
            	      "old",
            	      "omitted",
            	      "on",
            	      "once",
            	      "one",
            	      "ones",
            	      "only",
            	      "onto",
            	      "or",
            	      "ord",
            	      "other",
            	      "others",
            	      "otherwise",
            	      "ought",
            	      "our",
            	      "ours",
            	      "ourselves",
            	      "out",
            	      "outside",
            	      "over",
            	      "overall",
            	      "owing",
            	      "own",
            	      "p",
            	      "page",
            	      "pages",
            	      "part",
            	      "particular",
            	      "particularly",
            	      "past",
            	      "per",
            	      "perhaps",
            	      "placed",
            	      "please",
            	      "plus",
            	      "poorly",
            	      "possible",
            	      "possibly",
            	      "potentially",
            	      "pp",
            	      "predominantly",
            	      "present",
            	      "previously",
            	      "primarily",
            	      "probably",
            	      "promptly",
            	      "proud",
            	      "provides",
            	      "put",
            	      "q",
            	      "que",
            	      "quickly",
            	      "quite",
            	      "qv",
            	      "r",
            	      "ran",
            	      "rather",
            	      "rd",
            	      "re",
            	      "readily",
            	      "really",
            	      "recent",
            	      "recently",
            	      "ref",
            	      "refs",
            	      "regarding",
            	      "regardless",
            	      "regards",
            	      "related",
            	      "relatively",
            	      "research",
            	      "respectively",
            	      "resulted",
            	      "resulting",
            	      "results",
            	      "right",
            	      "run",
            	      "s",
            	      "said",
            	      "same",
            	      "saw",
            	      "say",
            	      "saying",
            	      "says",
            	      "sec",
            	      "section",
            	      "see",
            	      "seeing",
            	      "seem",
            	      "seemed",
            	      "seeming",
            	      "seems",
            	      "seen",
            	      "self",
            	      "selves",
            	      "sent",
            	      "seven",
            	      "several",
            	      "shall",
            	      "she",
            	      "shed",
            	      "she'll",
            	      "shes",
            	      "should",
            	      "shouldn't",
            	      "show",
            	      "showed",
            	      "shown",
            	      "showns",
            	      "shows",
            	      "significant",
            	      "significantly",
            	      "similar",
            	      "similarly",
            	      "since",
            	      "six",
            	      "slightly",
            	      "so",
            	      "some",
            	      "somebody",
            	      "somehow",
            	      "someone",
            	      "somethan",
            	      "something",
            	      "sometime",
            	      "sometimes",
            	      "somewhat",
            	      "somewhere",
            	      "soon",
            	      "sorry",
            	      "specifically",
            	      "specified",
            	      "specify",
            	      "specifying",
            	      "still",
            	      "stop",
            	      "strongly",
            	      "sub",
            	      "substantially",
            	      "successfully",
            	      "such",
            	      "sufficiently",
            	      "suggest",
            	      "sup",
            	      "sure	t",
            	      "take",
            	      "taken",
            	      "taking",
            	      "tell",
            	      "tends",
            	      "th",
            	      "than",
            	      "thank",
            	      "thanks",
            	      "thanx",
            	      "that",
            	      "that'll",
            	      "thats",
            	      "that've",
            	      "the",
            	      "their",
            	      "theirs",
            	      "them",
            	      "themselves",
            	      "then",
            	      "thence",
            	      "there",
            	      "thereafter",
            	      "thereby",
            	      "thered",
            	      "therefore",
            	      "therein",
            	      "there'll",
            	      "thereof",
            	      "therere",
            	      "theres",
            	      "thereto",
            	      "thereupon",
            	      "there've",
            	      "these",
            	      "they",
            	      "theyd",
            	      "they'll",
            	      "theyre",
            	      "they've",
            	      "think",
            	      "this",
            	      "those",
            	      "thou",
            	      "though",
            	      "thoughh",
            	      "thousand",
            	      "throug",
            	      "through",
            	      "throughout",
            	      "thru",
            	      "thus",
            	      "til",
            	      "tip",
            	      "to",
            	      "together",
            	      "too",
            	      "took",
            	      "toward",
            	      "towards",
            	      "tried",
            	      "tries",
            	      "truly",
            	      "try",
            	      "trying",
            	      "ts",
            	      "twice",
            	      "two",
            	      "u",
            	      "un",
            	      "under",
            	      "unfortunately",
            	      "unless",
            	      "unlike",
            	      "unlikely",
            	      "until",
            	      "unto",
            	      "up",
            	      "upon",
            	      "ups",
            	      "us",
            	      "use",
            	      "used",
            	      "useful",
            	      "usefully",
            	      "usefulness",
            	      "uses",
            	      "using",
            	      "usually",
            	      "v",
            	      "value",
            	      "various",
            	      "'ve",
            	      "very",
            	      "via",
            	      "viz",
            	      "vol",
            	      "vols",
            	      "vs",
            	      "w",
            	      "want",
            	      "wants",
            	      "was",
            	      "wasnt",
            	      "way",
            	      "we",
            	      "wed",
            	      "welcome",
            	      "we'll",
            	      "went",
            	      "were",
            	      "werent",
            	      "we've",
            	      "what",
            	      "whatever",
            	      "what'll",
            	      "whats",
            	      "when",
            	      "whence",
            	      "whenever",
            	      "where",
            	      "whereafter",
            	      "whereas",
            	      "whereby",
            	      "wherein",
            	      "wheres",
            	      "whereupon",
            	      "wherever",
            	      "whether",
            	      "which",
            	      "while",
            	      "whim",
            	      "whither",
            	      "who",
            	      "whod",
            	      "whoever",
            	      "whole",
            	      "who'll",
            	      "whom",
            	      "whomever",
            	      "whos",
            	      "whose",
            	      "why",
            	      "widely",
            	      "willing",
            	      "wish",
            	      "with",
            	      "within",
            	      "without",
            	      "wont",
            	      "words",
            	      "world",
            	      "would",
            	      "wouldnt",
            	      "www",
            	      "x",
            	      "y",
            	      "yes",
            	      "yet",
            	      "you",
            	      "youd",
            	      "you'll",
            	      "your",
            	      "youre",
            	      "yours",
            	      "yourself",
            	      "yourselves",
            	      "you've",
            	      "z",
            	      "zero");
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