package me.itsmas.whatsanalysis.analysis;

import me.itsmas.whatsanalysis.chat.Chat;

/**
 * Abstraction of a chat analysis task
 *
 * @param <T> The object type returned by the analysis
 */
public abstract class ChatAnalysis<T>
{
    /**
     * Executes the analysis
     * and returns the result
     *
     * @param chat The chat to analyse
     */
    public abstract T execute(Chat chat);
}
