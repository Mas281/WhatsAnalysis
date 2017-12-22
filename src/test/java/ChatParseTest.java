import me.itsmas.whatsanalysis.WhatsAnalysis;
import me.itsmas.whatsanalysis.analysis.types.MemberMessageCountAnalysis;
import me.itsmas.whatsanalysis.analysis.types.MemberMessageCountAnalysis.MemberMessageCountResult;
import me.itsmas.whatsanalysis.chat.Chat;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.fail;

public class ChatParseTest
{
    private Chat chat;

    @Test
    public void parseTest()
    {
        long time = System.currentTimeMillis();

        Optional<Chat> optChat = WhatsAnalysis.parseChat(new File("chats", "group.txt"));

        time = System.currentTimeMillis() - time;

        if (optChat.isPresent())
        {
            chat = optChat.get();

            System.out.println("Parsed " + chat.getMessageCount() + " messages in " + time + "ms");
            System.out.println();

            // Creates sorted map of member count message counts in descending order
            MemberMessageCountResult msgCountResult = chat.executeAnalysis(new MemberMessageCountAnalysis());

            msgCountResult.getSorted().forEach((member, count) ->
                System.out.println(member.getName() + " - " + count + " messages")
            );
        }
        else
        {
            fail("Unable to parse chat");
        }
    }
}
