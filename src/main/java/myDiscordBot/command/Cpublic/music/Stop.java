package myDiscordBot.command.Cpublic.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.Cpublic.music.handle.GuildMusicManager;
import myDiscordBot.command.Cpublic.music.handle.PlayerManager;
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
            musicManager.scheduler.audioPlayer.stopTrack();
            e.textChannel.sendMessage("Song is stopped").queue();
        }
        else e.textChannel.sendMessage("no song are playing").queue();
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length > 1){
            new BadArgumentsException().send(e);
            error();
        }
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
