package myDiscordBot.command.exception;

import myDiscordBot.command.DiscordEvent;

public class NoVoiceChannelConnectedException extends DiscordException {
    @Override
    public void build(DiscordEvent e) {
        builder.setTitle(":x: NoVoiceChannelConnectedException:");
        builder.setDescription("No voice channel are connected currently, connected a voice channel and try again.");
    }
}
