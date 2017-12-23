package me.itsmas.whatsanalysis.analysis.types;

import me.itsmas.whatsanalysis.analysis.ChatAnalysis;
import me.itsmas.whatsanalysis.analysis.types.MemberMessageCountAnalysis.MemberMessageCountResult;
import me.itsmas.whatsanalysis.chat.Chat;
import me.itsmas.whatsanalysis.chat.ChatMember;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Analysis type for finding the
 * proportion of chat messages
 * sent by each {@link ChatMember}
 *
 * If a {@link Comparator} is specified
 * in the constructor when creating an
 * instance of this analysis, the resulting
 * map will be sorted by message counts
 * descending
 */
public class MemberMessageCountAnalysis implements ChatAnalysis<MemberMessageCountResult>
{
    /**
     * The default comparator to use
     */
    private static final Comparator<Integer> DEFAULT_COMPARATOR = Comparator.reverseOrder();

    /**
     * The comparator for message counts
     */
    private final Comparator<Integer> comparator;

    /**
     * Constructor which  delegates to {@link #MemberMessageCountAnalysis(Comparator)}
     * using the default comparator
     *
     * @see #DEFAULT_COMPARATOR
     * @see #MemberMessageCountAnalysis(Comparator)
     */
    public MemberMessageCountAnalysis()
    {
        this(DEFAULT_COMPARATOR);
    }

    /**
     * Constructor taking a {@link Comparator<Integer>}
     * which will be used to sort message counts
     *
     * @param comparator The comparator
     */
    public MemberMessageCountAnalysis(Comparator<Integer> comparator)
    {
        this.comparator = comparator;
    }

    @Override
    public MemberMessageCountResult execute(Chat chat)
    {
        Map<ChatMember, Integer> sortedMap = chat.getMembers().stream()
            .sorted(Comparator.comparing(ChatMember::getMessageCount, comparator))
            .collect(Collectors.toMap(
                Function.identity(),
                ChatMember::getMessageCount,
                (v1, v2) -> v1,
                LinkedHashMap::new
            ));

        return new MemberMessageCountResult(sortedMap);
    }

    /**
     * The result of a {@link MemberMessageCountAnalysis}
     */
    public class MemberMessageCountResult
    {
        /**
         * The map of {@link ChatMember} objects
         * to their respective message count
         */
        private final Map<ChatMember, Integer> messageCounts;

        private MemberMessageCountResult(Map<ChatMember, Integer> messageCounts)
        {
            this.messageCounts = messageCounts;
        }

        /**
         * Fetches the message count of a {@link ChatMember}
         *
         * @param member The member
         *
         * @return The member's message count
         */
        public int getMessageCount(ChatMember member)
        {
            return messageCounts.get(member);
        }

        /**
         * Fetches the sorted map of {@link ChatMember}
         * objects to their respective message count
         *
         * @return Immutable sorted map of members to message counts
         */
        public Map<ChatMember, Integer> getSorted()
        {
            return Collections.unmodifiableMap(messageCounts);
        }
    }
}
