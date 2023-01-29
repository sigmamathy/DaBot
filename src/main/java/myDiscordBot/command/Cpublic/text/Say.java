package myDiscordBot.command.Cpublic.text;

import myDiscordBot.command.*;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Say extends DiscordCommand {

    private static final int textLimit = 1500;

    @Override
    public void handle(DiscordEvent e) {
        String[] args = e.args;
        TextChannel textChannel = e.textChannel;
        if (args.length == 2) {
            if (args[1].length() <= textLimit)
                textChannel.sendMessage(args[1]).queue();
            else new TextOutOfLimitException().send(e);
        }

        if (args.length == 3){
            if (args[1].length() <= textLimit) {
                if (args[2].equals("hide")){
                    e.message.delete().queue();
                    textChannel.sendMessage(args[1]).queue();
                }
                else if (args[2].equals("nohide")){
                    textChannel.sendMessage(args[1]).queue();
                }
                else new BadArgumentsException().send(e, "`args[2]` should be either `hide` or `nohide`");
            }
            else new TextOutOfLimitException().send(e);
        }
    }

    @Override
    protected void errorHandle(DiscordEvent e) {
        if (e.args.length < 2 || e.args.length > 3) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "say";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
