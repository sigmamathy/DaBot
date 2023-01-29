package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class BadNumberException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: BadNumberException:");
        builder.setDescription("Format of number is invalid in the argument, use the correct format.");
    }
}
