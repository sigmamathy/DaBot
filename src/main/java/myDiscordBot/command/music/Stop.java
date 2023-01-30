package myDiscordBot.command.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.music.handle.GuildMusicManager;
import myDiscordBot.command.music.handle.PlayerManager;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.exception.NoVoiceChannelConnectedException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class Stop extends DiscordCommand {
    @Override
    public void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        VoiceChannel vc = e.member.getVoiceState().getChannel();
        if (vc == null) {
            new NoVoiceChannelConnectedException().send(e);
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.guild);
        if (musicManager.scheduler.audioPlayer.getPlayingTrack() != null) {
            if (musicManager.scheduler.audioPlayer.isPaused())
                musicManager.scheduler.audioPlayer.setPaused(false);
            musicManager.scheduler.audioPlayer.stopTrack();
            e.textChannel.sendMessage("Song is stopped").queue();
        }
        else e.textChannel.sendMessage("no song are playing").queue();
    }

    @Override
    public boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 1)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "stop";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
