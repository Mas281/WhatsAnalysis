package me.itsmas.whatsanalysis.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data about a message
 */
public class ChatMessage
{
    /**
     * The {@link DateFormat} for displaying message times
     */
    private static final DateFormat dateDisplay = new SimpleDateFormat("dd/MM/yy HH:mm");

    /**
     * The default message display format
     */
    private static final String defaultFormat = "%s - %s: %s";

    /**
     * The time the message was sent
     */
    private final Date time;

    /**
     * The message's sender
     */
    private final ChatMember sender;

    /**
     * The content of the message
     */
    private final String content;

    public ChatMessage(Date time, ChatMember sender, String content)
    {
        this.time = time;
        this.sender = sender;
        this.content = content;
    }

    /**
     * Fetches the message time
     *
     * @see #time
     *
     * @return The message time
     */
    public Date getTime()
    {
        return time;
    }

    /**
     * Fetches the message's time displayed cleanly
     *
     * @return The time display
     */
    public String getTimeDisplay()
    {
        return dateDisplay.format(time);
    }

    /**
     * Fetches the message formatted
     * with the default format
     *
     * @see #defaultFormat
     *
     * @return The formatted message
     */
    public String getDefaultFormat()
    {
        return String.format(defaultFormat,
            getTimeDisplay(),
            getSender().getName(),
            getContent()
        );
    }

    /**
     * Determines whether the message
     * is a media message
     *
     * @return Whether the message is a media message
     */
    public boolean isMediaMessage()
    {
        return getContent().equals("<Media omitted>");
    }

    /**
     * Fetches the message's sender
     *
     * @see #sender
     *
     * @return The sender
     */
    public ChatMember getSender()
    {
        return sender;
    }

    /**
     * Fetches the message's content
     *
     * @see #content
     *
     * @return The content
     */
    public String getContent()
    {
        return content;
    }
}
