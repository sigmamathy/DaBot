package myDiscordBot.command.Cpublic.text;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Embed extends DiscordCommand {
    @Override
    public void handle(DiscordEvent e) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setTitle(e.args[1]);
        builder.setDescription(e.args[2]);
        builder.setFooter(e.args[3]);
        e.textChannel.sendMessage(builder.build()).queue();
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length != 4){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "embed";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
