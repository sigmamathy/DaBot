package myDiscordBot.command.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.music.handle.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class Pause extends DiscordCommand {

    @Override
    protected void handle(DiscordEvent e)
    {
        var audioPlayer = PlayerManager.getInstance().getMusicManager(e.guild).scheduler.audioPlayer;
        if (audioPlayer.getPlayingTrack() == null) {
            e.textChannel.sendMessage("No song is playing right now.").queue();
            return;
        }
        if (audioPlayer.isPaused()) {
            e.textChannel.sendMessage("Song is already paused").queue();
            return;
        }
        audioPlayer.setPaused(true);
        e.textChannel.sendMessage("Song is paused.").queue();
    }

    @Override
    protected boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 1)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "pause";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
