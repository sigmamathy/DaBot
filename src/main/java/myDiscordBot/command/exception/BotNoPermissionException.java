package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class BotNoPermissionException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: BotNoPermissionException:");
        builder.setDescription("Action cannot be performed, I don't have permission to do this");
    }
}
