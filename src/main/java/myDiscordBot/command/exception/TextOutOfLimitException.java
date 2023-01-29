package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class TextOutOfLimitException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: TextOutOfLimitException:");
        builder.setDescription("The text is too long, please reduce the length of text");
    }
}
