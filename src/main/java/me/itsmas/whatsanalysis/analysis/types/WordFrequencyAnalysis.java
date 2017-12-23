package me.itsmas.whatsanalysis.analysis.types;

import me.itsmas.whatsanalysis.analysis.ChatAnalysis;
import me.itsmas.whatsanalysis.analysis.types.WordFrequencyAnalysis.WordFrequencyResult;
import me.itsmas.whatsanalysis.chat.Chat;
import me.itsmas.whatsanalysis.chat.ChatMessage;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Analysis type for top X
 * used words in a chat
 */
public class WordFrequencyAnalysis implements ChatAnalysis<WordFrequencyResult>
{
    /**
     * The default size to limit the result to
     */
    private static final int DEFAULT_LIMIT = 1000;

    /**
     * The amount of entries to
     * limit the resulting map to
     */
    private final int limit;

    /**
     * Constructor which delegates to {@link #WordFrequencyAnalysis(int)}
     * using the default limit
     *
     * @see #DEFAULT_LIMIT
     * @see #WordFrequencyAnalysis(int)
     */
    public WordFrequencyAnalysis()
    {
        this(DEFAULT_LIMIT);
    }

    /**
     * Constructor taking an integer by
     * which to limit the resulting
     * map size to
     *
     * @param limit The limit
     */
    public WordFrequencyAnalysis(int limit)
    {
        this.limit = limit;
    }

    @Override
    public WordFrequencyResult execute(Chat chat)
    {
        Map<String, Integer> wordFrequency = new HashMap<>();

        chat.getMessages().stream()
            .filter(msg -> !msg.isMediaMessage())
            .forEach(msg -> handleMessage(msg, wordFrequency));

        return new WordFrequencyResult(sortMap(wordFrequency));
    }

    /**
     * Sorts a {@link Map} of strings to
     * integers in descending order
     *
     * @param topWords The map to sort
     *
     * @return Map of positions to {@link WordFrequencyData} objects
     */
    private Map<Integer, WordFrequencyData> sortMap(Map<String, Integer> topWords)
    {
        AtomicInteger atomicInt = new AtomicInteger();

        return topWords.entrySet().stream()
            .sorted(Comparator.comparing(Entry::getValue, Comparator.reverseOrder()))
            .limit(limit)
            .collect(Collectors.toMap(
                entry -> atomicInt.incrementAndGet(),
                entry -> new WordFrequencyData(entry.getKey(), entry.getValue()),
                (v1, v2) -> v1,
                LinkedHashMap::new
            ));
    }

    /**
     * Handles a chat message by
     * adding its words to the map
     *
     * @param message The message
     * @param topWords The top words map
     */
    private void handleMessage(ChatMessage message, Map<String, Integer> topWords)
    {
        String[] split = message.getContent().toLowerCase().split("[ \n]");

        for (String word : split)
        {
            String normalized = normalizeWord(word);

            if (isWord(normalized))
            {
                topWords.put(normalized, topWords.getOrDefault(normalized, 0) + 1);
            }
        }
    }

    /**
     * determines whether a normalized string is a word
     *
     * @param normalized The normalized string
     *
     * @return Whether the string is a word
     */
    private boolean isWord(String normalized)
    {
        return !normalized.isEmpty() && normalized.matches("[a-zA-Z']+");
    }

    /**
     * Normalises a string
     *
     * Will strip all non letter and apostrophe
     * characters from the start and end
     * of a string. Checks should be performed
     * after this to make sure the resulting
     * string contains only characters
     *
     * @param word The word to normalize
     *
     * @return The normalized string
     */
    private String normalizeWord(String word)
    {
        return word.replaceAll("^[^a-zA-Z']+|[^a-zA-Z']+$", "");
    }

    /**
     * The result of a {@link WordFrequencyAnalysis}
     */
    public class WordFrequencyResult
    {
        /**
         * Map of positions to {@link WordFrequencyData} objects
         */
        private Map<Integer, WordFrequencyData> topWords;

        private WordFrequencyResult(Map<Integer, WordFrequencyData> topWords)
        {
            this.topWords = topWords;
        }

        /**
         * Fetches the most used word
         * at a certain position
         *
         * @param position The position
         *
         * @return The {@link WordFrequencyData} at the given position
         */
        public WordFrequencyData wordAt(int position)
        {
            return topWords.get(position);
        }

        /**
         * Fetches the sorted map of integer
         * positions to their related {@link WordFrequencyData}
         * objects
         *
         * @return Immutable sorted map of positions to frequency data
         */
        public Map<Integer, WordFrequencyData> getSorted()
        {
            return Collections.unmodifiableMap(topWords);
        }
    }

    /**
     * Data about the frequency of a word
     */
    public class WordFrequencyData
    {
        /**
         * The word
         */
        public final String word;

        /**
         * The word's total uses
         */
        public final int uses;

        private WordFrequencyData(String word, int uses)
        {
            this.word = word;
            this.uses = uses;
        }
    }
}
