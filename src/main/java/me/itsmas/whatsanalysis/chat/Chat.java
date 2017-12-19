package me.itsmas.whatsanalysis.chat;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Class holding data about a chat
 */
public class Chat
{
    /**
     * The chat members
     */
    private final Set<ChatMember> members;

    /**
     * The chat messages
     */
    private final List<ChatMessage> messages;

    public Chat(Set<ChatMember> members, List<ChatMessage> messages)
    {
        this.members = members;
        this.messages = messages;
    }

    /**
     * Fetches the chat members
     *
     * @see #members
     *
     * @return Immutable set of members
     */
    public Set<ChatMember> getMembers()
    {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Fetches the chat messages ordered by time sent
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
     * Fetches the amount of messages in the chat
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
