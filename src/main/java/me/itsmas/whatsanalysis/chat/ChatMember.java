package me.itsmas.whatsanalysis.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data about a chat member
 */
public class ChatMember
{
    /**
     * The name of the member
     */
    private final String name;

    /**
     * The messages sent by the member
     */
    private final List<ChatMessage> messages;

    public ChatMember(String name)
    {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    /**
     * Fetches the name of the member
     *
     * @see #name
     *
     * @return The member's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Attaches a message to this member
     *
     * @see #messages
     *
     * @param message The message
     */
    public void addMessage(ChatMessage message)
    {
        messages.add(message);
    }

    /**
     * Fetches the member's messages
     *
     * @see #messages
     *
     * @return Immutable list of messages
     */
    public List<ChatMessage> getMessages()
    {
        return Collections.unmodifiableList(messages);
    }

    /**
     * Fetches the messages sent by the member
     *
     * @see #messages
     *
     * @return The message count
     */
    public int getMessageCount()
    {
        return getMessages().size();
    }
}
