package myDiscordBot.command.music;

import myDiscordBot.command.music.handle.GuildMusicManager;
import myDiscordBot.command.music.handle.PlayerManager;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import net.dv8tion.jda.api.EmbedBuilder;

public class Leave extends DiscordCommand {

    @Override
    protected void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        e.guild.getAudioManager().closeAudioConnection();
        e.textChannel.sendMessage("Channel disconnected successfully").queue();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.guild);
        if (musicManager.scheduler.audioPlayer.getPlayingTrack() != null)
            musicManager.scheduler.audioPlayer.stopTrack();
    }

    @Override
    protected void errorHandle(DiscordEvent e) {
        if (e.args.length > 1) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "leave";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
