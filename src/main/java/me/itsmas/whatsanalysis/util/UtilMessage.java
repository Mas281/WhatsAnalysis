package me.itsmas.whatsanalysis.util;

import me.itsmas.whatsanalysis.chat.ChatMember;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ChatMessage utilities
 */
public final class UtilMessage
{
    private UtilMessage() {}

    /**
     * The message pattern
     * Where the magic happens
     */
    private static final Pattern messagePattern = Pattern.compile(
        "(\\d{2}/\\d{2}/\\d{4}, \\d{2}:\\d{2}) - (.*?): (.*)"
        // Example message: 01/01/2020, 00:00 - Sam: Hello from the future
    );

    /**
     * The date format of a message
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

    /**
     * Attempts to parse a {@link MessageBuilder} object from a {@link String}
     *
     * @param line The line to parse
     *
     * @return An optional of the resulting message builder
     */
    public static Optional<MessageBuilder> parseMessageBuilder(String line, Set<ChatMember> members)
    {
        MessageBuilder builder = null;

        Matcher matcher = messagePattern.matcher(line);

        if (matcher.matches())
        {
            try
            {
                String time = matcher.group(1);
                Date date = dateFormat.parse(time);

                String senderName = matcher.group(2);

                if (shouldIgnore(senderName))
                {
                    // Prevents group titles containing
                    // colons from slipping through the system.
                    // See end of method for explanation
                    // on returning null here.
                    return null;
                }

                ChatMember sender = getMember(senderName, members);

                String content = matcher.group(3);

                builder = new MessageBuilder(date, sender);
                builder.addMessageLine(content);
            }
            // Invalid date format, will
            // never happen when using
            // a real, unmodified chat file.
            catch (ParseException ignored) {}
        }

        if (shouldIgnore(line))
        {
            // Prevents messages such as the
            // group subject being changed or
            // the group being initially created
            // from being counted as a message.

            // Returning null with an optional
            // here because we have three behaviours
            // that need to be handled:
            // - Present: New message parsed
            // - Not Present: Continuation of previous message
            // - Null: Not a message intended to be parsed
            // A better way of handling this can probably be found.
            return null;
        }

        return Optional.ofNullable(builder);
    }

    /**
     * Determines whether to ignore a message
     *
     * @see #ignorePhrases
     *
     * @param line The line
     *
     * @return Whether to ignore the message
     */
    private static boolean shouldIgnore(String line)
    {
        for (String ignorePhrase : ignorePhrases)
        {
            if (line.contains(ignorePhrase))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Phrases to ignore when parsing a message
     */
    private static final String[] ignorePhrases = new String[]
        {
            " created group \"",
            " changed the subject from \""
        };

    /**
     * Fetches a {@link ChatMember} from a set by name
     * Will create and add a new {@link ChatMember} object
     * if the specified member is not found
     *
     * @param name The name of the member
     * @param members The set of members
     *
     * @return The member
     */
    private static ChatMember getMember(String name, Set<ChatMember> members)
    {
        for (ChatMember member : members)
        {
            if (member.getName().equals(name))
            {
                return member;
            }
        }

        ChatMember member = new ChatMember(name);
        members.add(member);

        return member;
    }
}
