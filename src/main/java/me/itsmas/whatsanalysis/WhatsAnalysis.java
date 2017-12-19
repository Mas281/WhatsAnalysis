package me.itsmas.whatsanalysis;

import me.itsmas.whatsanalysis.chat.Chat;
import me.itsmas.whatsanalysis.util.UtilChat;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Main library class
 */
public final class WhatsAnalysis
{
    private WhatsAnalysis() {}

    /**
     * Attempts to parse a {@link Chat} from a {@link File}
     * Delegates to {@link UtilChat#parseChat(File)} and
     * wraps in a nullable {@link Optional<Chat>} object
     *
     * @see UtilChat#parseChat(File)
     *
     * @param file The file
     *
     * @return An optional of the resulting chat
     */
    public static Optional<Chat> parseChat(File file)
    {
        return Optional.ofNullable(UtilChat.parseChat(file));
    }

    /**
     * Attempts to parse a {@link Chat} from a
     * {@link File} asynchronously
     *
     * @see #parseChat(File)
     *
     * @param file The file
     * @return A {@link CompletableFuture} holding the result of the parse
     */
    public static CompletableFuture<Optional<Chat>> parseChatAsync(File file)
    {
        return CompletableFuture.supplyAsync(() -> parseChat(file));
    }
}
