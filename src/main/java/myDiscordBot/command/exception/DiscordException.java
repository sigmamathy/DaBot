package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public abstract class DiscordException
{
    protected EmbedBuilder builder = new EmbedBuilder();

    // Constructor
    protected DiscordException(){
        builder.setColor(Color.RED);
    }

    // Edit the embed builder, by the child class
    public abstract void build(DiscordEvent e);

    // Send an error
    public final void send(DiscordEvent e){
        send(e, null);
    }

    // Send an error with an additional reason
    public final void send(DiscordEvent e, String reason) {
        build(e);
        if (reason != null)
            builder.addField("More Info: ", reason, false);
        e.textChannel.sendMessage(builder.build()).queue();
    }
}
