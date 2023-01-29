package myDiscordBot.command;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DiscordEvent
{
    public String[] args;
    public TextChannel textChannel;
    public Member member;
    public Message message;
    public Guild guild;
    public User author;

    public DiscordEvent(String[] args, GuildMessageReceivedEvent e)
    {
        this.args = args;
        textChannel = e.getChannel();
        message = e.getMessage();
        guild = e.getGuild();
        author = e.getAuthor();
        member = e.getMember();
    }
}
