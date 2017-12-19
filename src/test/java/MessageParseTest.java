import me.itsmas.whatsanalysis.WhatsAnalysis;
import me.itsmas.whatsanalysis.chat.Chat;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.fail;

public class MessageParseTest
{
    @Test
    public void parseTest()
    {
        long time = System.currentTimeMillis();

        Optional<Chat> optChat = WhatsAnalysis.parseChat(new File("chats", "group.txt"));

        time = System.currentTimeMillis() - time;

        if (optChat.isPresent())
        {
            Chat chat = optChat.get();

            System.out.println("Parsed " + chat.getMessageCount() + " messages in " + time + "ms");
            System.out.println();

            System.out.println("Chat Members:");
            chat.getMembers().stream()
                .sorted((m1, m2) -> Integer.compare(m2.getMessageCount(), m1.getMessageCount()))
                .map(member -> member.getName() + " - " + member.getMessageCount() + " messages")
                .forEach(System.out::println);

            /*
            System.out.println();

            System.out.println("Chat Messages:");
            chat.getMessages().forEach(msg -> System.out.println(msg.getDefaultFormat()));
            */
        }
        else
        {
            System.out.println("Unable to parse chat");
            fail();
        }
    }
}
