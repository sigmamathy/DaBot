package myDiscordBot.command.Cpublic.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import utils.MathHelper;
import myDiscordBot.command.Cpublic.music.handle.*;
import myDiscordBot.command.*;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;

public class Skip extends DiscordCommand {
    @Override
    protected void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        VoiceChannel vc = e.member.getVoiceState().getChannel();
        if (vc == null) {
            new NoVoiceChannelConnectedException().send(e);
            return;
        }
        final GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(e.guild);
        if (manager.scheduler.queue.isEmpty()){
            e.textChannel.sendMessage("no song to skip").queue();
            return;
        }
        if (e.args.length == 1) {
            manager.scheduler.nextTrack();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.ORANGE);
            final AudioTrack track = manager.scheduler.audioPlayer.getPlayingTrack();
            builder.setTitle("Skip 1 song to:");
            builder.setDescription("Title: `" + track.getInfo().title + "`\nauthor: `" + track.getInfo().author + "`\n" +
                    "length: `" + MathHelper.getTime(track.getDuration()) + "`\n" + track.getInfo().uri);
            builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
            e.textChannel.sendMessage(builder.build()).queue();
            return;
        }
        try {
            if (!MathHelper.isInteger(e.args[1]) || Double.parseDouble(e.args[1]) < 1){
                new BadNumberException().send(e);
                return;
            }
        } catch (NumberFormatException exception){
            new BadArgumentsException().send(e, "Not a number, put numbers here");
            return;
        }
        if (Integer.parseInt(e.args[1]) > manager.scheduler.queue.size()){
            e.textChannel.sendMessage("no song to skip").queue();
            return;
        }
        for (int i = 0; i < Integer.parseInt(e.args[1]); i++){
            manager.scheduler.nextTrack();
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.ORANGE);
        final AudioTrack track = manager.scheduler.audioPlayer.getPlayingTrack();
        builder.setTitle("Skip " + Integer.parseInt(e.args[1]) + " song to:");
        builder.setDescription("Title: `" + track.getInfo().title + "`\nauthor: `" + track.getInfo().author + "`\n" +
                "length: `" + MathHelper.getTime(track.getDuration()) + "`\n" + track.getInfo().uri);
        builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        e.textChannel.sendMessage(builder.build()).queue();
    }

    @Override
    protected void errorHandle(DiscordEvent e) {
        if (e.args.length > 2){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "skip";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}