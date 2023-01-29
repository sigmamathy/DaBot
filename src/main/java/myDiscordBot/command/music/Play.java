package myDiscordBot.command.music;

import myDiscordBot.command.music.handle.GuildMusicManager;
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
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.guild);
            if (!musicManager.scheduler.queue.isEmpty()){
                try {
                    musicManager.scheduler.queue(musicManager.scheduler.queue.take());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                return;
            }
            e.textChannel.sendMessage("no song now").queue();
            return;
        }
        String s = e.args[1];
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++){
            if (sb.charAt(i) == ' ')
                sb.deleteCharAt(i);
        }
        s = sb.toString();
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
    public void errorHandle(DiscordEvent e) {
        if (e.args.length > 2) {
            new BadArgumentsException().send(e);
            error();
        }
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
