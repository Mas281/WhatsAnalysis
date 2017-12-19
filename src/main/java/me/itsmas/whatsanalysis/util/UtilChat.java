package me.itsmas.whatsanalysis.util;

import me.itsmas.whatsanalysis.chat.Chat;
import me.itsmas.whatsanalysis.chat.ChatMember;
import me.itsmas.whatsanalysis.chat.ChatMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * ChatMessage utilities
 */
public final class UtilChat
{
    private UtilChat() {}

    /**
     * Parses a {@link Chat} from a {@link File}
     *
     * @param file The file
     *
     * @return The chat
     */
    public static Chat parseChat(File file)
    {
        List<String> lines = UtilFile.readLines(file);

        if (lines == null)
        {
            return null;
        }

        Set<ChatMember> members = new HashSet<>();
        List<ChatMessage> messages = new ArrayList<>();

        MessageBuilder lastBuilder = null;

        for (String line : lines)
        {
            Optional<MessageBuilder> optBuilder = UtilMessage.parseMessageBuilder(line, members);

            // See UtilMessage#parseMessageBuilder()
            // for an explanation of this weird check
            if (optBuilder == null)
            {
                continue;
            }

            if (optBuilder.isPresent())
            {
                if (lastBuilder != null)
                {
                    messages.add(lastBuilder.build());
                }

                lastBuilder = optBuilder.get();
            }
            else if (lastBuilder != null)
            {

                // This line is a continuation of the previous message
                lastBuilder.addMessageLine(line);
            }
        }

        if (lastBuilder != null && !lastBuilder.isBuilt())
        {
            // Add the final message
            messages.add(lastBuilder.build());
        }

        return new Chat(members, messages);
    }
}
