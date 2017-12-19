package me.itsmas.whatsanalysis.util;

import me.itsmas.whatsanalysis.chat.ChatMember;
import me.itsmas.whatsanalysis.chat.ChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Builder for creating {@link ChatMessage} objects
 *
 * A builder is used here to ensure the
 * immutability of all {@link ChatMessage} objects
 */
final class MessageBuilder
{
    /**
     * The time the message was sent
     */
    private final Date time;

    /**
     * The message's sender
     */
    private final ChatMember member;

    /**
     * The lines in the message content
     */
    private final List<String> messageLines;

    /**
     * Whether the message has been built
     */
    private boolean built = false;

    MessageBuilder(Date time, ChatMember member)
    {
        this.time = time;
        this.member = member;
        this.messageLines = new ArrayList<>();
    }

    /**
     * Adds a line to the message
     *
     * @see #messageLines
     *
     * @param line The line
     */
    void addMessageLine(String line)
    {
        messageLines.add(line);
    }

    /**
     * Fetches whether the message has been built
     *
     * @see #built
     *
     * @return If the message has been built
     */
    boolean isBuilt()
    {
        return built;
    }

    /**
     * Builds a {@link ChatMessage} object from the builder
     *
     * @return The message
     */
    ChatMessage build()
    {
        if (!built)
        {
            built = true;

            ChatMessage message = new ChatMessage(time, member, String.join("\n", messageLines));
            member.addMessage(message);

            return message;
        }

        throw new IllegalArgumentException("ChatMessage already built");
    }
}
