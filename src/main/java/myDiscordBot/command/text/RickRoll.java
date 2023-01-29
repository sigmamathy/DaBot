package myDiscordBot.command.text;

import myDiscordBot.command.exception.BadArgumentsException;
import utils.NeverGonnaGiveYouUp;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class RickRoll extends DiscordCommand
{
    @Override
    public void handle(DiscordEvent e) {
        if (e.args.length == 1)
            e.textChannel.sendMessage(NeverGonnaGiveYouUp.rickroll).queue();
        if (e.args.length == 2)
            e.textChannel.sendMessage("to " + e.args[1] + ":\n" + NeverGonnaGiveYouUp.rickroll).queue();
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length > 3){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "rickroll";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
