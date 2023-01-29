package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class BadArgumentsException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: BadArgumentsException:");
        builder.setDescription("Invalid arguments, please check if there is a typo or type `dabot help` for further information");
    }
}
