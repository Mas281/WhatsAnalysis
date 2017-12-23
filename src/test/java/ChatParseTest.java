import me.itsmas.whatsanalysis.WhatsAnalysis;
import me.itsmas.whatsanalysis.analysis.types.MemberMessageCountAnalysis;
import me.itsmas.whatsanalysis.analysis.types.MemberMessageCountAnalysis.MemberMessageCountResult;
import me.itsmas.whatsanalysis.analysis.types.WordFrequencyAnalysis;
import me.itsmas.whatsanalysis.analysis.types.WordFrequencyAnalysis.WordFrequencyResult;
import me.itsmas.whatsanalysis.chat.Chat;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.Assert.fail;

public class ChatParseTest
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

            // Total messages
            System.out.println("Parsed " + chat.getMessageCount() + " messages in " + time + "ms");
            System.out.println();

            // Messages by member
            MemberMessageCountResult msgCountResult = chat.executeAnalysis(new MemberMessageCountAnalysis());

            msgCountResult.getSorted().forEach((member, count) ->
                System.out.println(member.getName() + " - " + count + " messages")
            );
            System.out.println();

            // Word frequency (top 10)
            WordFrequencyResult wordFreqResult = chat.executeAnalysis(new WordFrequencyAnalysis(10));

            wordFreqResult.getSorted().forEach((position, data) ->
                System.out.println("#" + position + " - " + data.word + " (" + data.uses + ")")
            );
        }
        else
        {
            fail("Unable to parse chat");
        }
    }
}
