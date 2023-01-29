package myDiscordBot.command.Cpublic.moderation;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.util.List;

public class Kick extends DiscordCommand {
    @Override
    public void handle(DiscordEvent e) {
        List<Member> members = e.message.getMentionedMembers();
        TextChannel channel = e.textChannel;
        if (members.size() == 0){
            new BadArgumentsException().send(e, "please mention a member");
            return;
        }
        if (members.get(0).getUser().equals(e.author)){
            new BotNoPermissionException().send(e, "don't kick yourself");
            return;
        }
        try {
            members.get(0).kick().queue();
        } catch (HierarchyException ec){
            new BotNoPermissionException().send(e);
            return;
        }
        channel.sendMessage(members.get(0).getUser().getName() + " has been kicked by " + e.author.getName()).queue();
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length != 2){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "kick";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
