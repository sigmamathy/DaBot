package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class BadAttachmentException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: BadAttachmentException:");
        builder.setDescription("Either missing attachment or required type mismatch, type `dabot help` for further information.");
    }
}
