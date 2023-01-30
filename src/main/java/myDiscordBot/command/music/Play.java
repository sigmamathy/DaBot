package myDiscordBot.command.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.*;
import myDiscordBot.command.music.handle.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import java.net.URI;
import java.net.URISyntaxException;

public class Play extends DiscordCommand {
    @Override
    public void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        VoiceChannel vc = e.member.getVoiceState().getChannel();
        if (vc == null) {
            new NoVoiceChannelConnectedException().send(e);
            return;
        }
        AudioManager am = e.guild.getAudioManager();
        if (!e.guild.getSelfMember().getVoiceState().inVoiceChannel()) {
            am.openAudioConnection(vc);
            e.textChannel.sendMessage("Channel connected successfully").queue();
        }
        if (e.guild.getSelfMember().getVoiceState().isMuted()){
            new MutedException().send(e);
            return;
        }
        if (e.args.length == 1){
            var sc = PlayerManager.getInstance().getMusicManager(e.guild).scheduler;
            if (sc.audioPlayer.isPaused()) {
                sc.audioPlayer.setPaused(false);
                return;
            }
            if (!sc.queue.isEmpty())
            {
                try {
                    sc.queue(sc.queue.take());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                return;
            }
            e.textChannel.sendMessage("no song now").queue();
            return;
        }
        String s = e.args[1];
        if (!isUrl(s))
            s = "ytsearch:" + s;
        PlayerManager.getInstance().loadAndPLay(e, s, true);
    }

    private boolean isUrl(String s){
        try {
            new URI(s);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 2)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "play";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
