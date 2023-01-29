package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class MutedException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: MutedException:");
        builder.setDescription("Bot has been muted, please unmute it and try again.");
    }
}
