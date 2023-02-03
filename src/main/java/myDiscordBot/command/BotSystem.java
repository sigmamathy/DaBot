package myDiscordBot.command;

import myDiscordBot.command.image.*;
import myDiscordBot.command.moderation.*;
import myDiscordBot.command.music.*;
import myDiscordBot.command.music.Queue;
import myDiscordBot.command.r8.*;
import myDiscordBot.command.text.*;
import net.dv8tion.jda.api.events.message.guild.*;
import net.dv8tion.jda.api.hooks.*;
import org.jetbrains.annotations.*;
import java.util.*;

public class BotSystem extends ListenerAdapter
{
    AutoRespond respond;

    public BotSystem() {
        respond = new AutoRespond();
    }

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return;
        DiscordEvent e = new DiscordEvent(return_args(event), event);
        if (e.args == null || e.args.length == 0) {
            respond.handleCopypasta(event.getMessage(), e.textChannel);
            return;
        }

        new Help().exe(e);
        new Say().exe(e);
        new RickRoll().exe(e);
        new Embed().exe(e);
        //text commands

        new TextImage().exe(e);
        new Flip().exe(e);
        //image commands

        new Join().exe(e);
        new Leave().exe(e);
        new Play().exe(e);
        new Stop().exe(e);
        new Queue().exe(e);
        new Add().exe(e);
        new Skip().exe(e);
        new NowPlaying().exe(e);
        new Pause().exe(e);
        new Seek().exe(e);
        //music commands

        new GaeRate().exe(e);
        new MonkeRate().exe(e);
        new SusRate().exe(e);
        new WaifuRate().exe(e);
        //r8 commands

        new Kick().exe(e);
        //moderation
    }

    // Integer pair
    private static class IntPair {
        int start, end;
        IntPair(int st) {
            start = st;
        }
    }

    // split message received into arguments, separated with spaces.
    private String[] return_args(GuildMessageReceivedEvent e)
    {
        String message = e.getMessage().getContentRaw();
        if (message == null || message.isEmpty()) return null;
        // arguments division indices
        var args_index = new ArrayList<IntPair>();
        char prev = '\0';
        boolean string_read = false;
        for (int i = 0; i < message.length(); prev = message.charAt(i++))
        {
            char c = message.charAt(i);
            if (c == '"') {
                if (!string_read) {
                    if (i + 1 == message.length())
                        return null;
                    args_index.add(new IntPair(i + 1));
                }
                string_read = !string_read;
            }
            if (string_read)
                continue;
            if (c == ' ') {
                if (prev == '\0' || prev == ' ')
                    continue;
                args_index.get(args_index.size() - 1).end = i - (prev == '"' ? 1 : 0);
            }
            else if (prev == ' ' || prev == '\0')
                args_index.add(new IntPair(i));
        }
        if (prev != ' ')
            args_index.get(args_index.size() - 1).end = message.length() - (prev == '"' ? 1 : 0);
        // verification
        if (!message.substring(args_index.get(0).start, args_index.get(0).end).equals("dabot"))
            return null;
        // result arguments
        var args = new String[args_index.size() - 1];
        for (int i = 0; i < args.length; i++)
            args[i] = message.substring(args_index.get(i+1).start, args_index.get(i+1).end);
        return args;
    }
}
