package myDiscordBot.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import utils.MathHelper;
import myDiscordBot.command.music.handle.*;
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
        if (manager.scheduler.audioPlayer.isPaused())
            manager.scheduler.audioPlayer.setPaused(false);
        if (e.args.length == 1) {
            manager.scheduler.nextTrack();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.ORANGE);
            final AudioTrack track = manager.scheduler.audioPlayer.getPlayingTrack();
            builder.setTitle("Skip 1 song to:");
            builder.setDescription("Title: `" + track.getInfo().title + "`\nAuthor: `" + track.getInfo().author + "`\n" +
                    "Length: `" + MathHelper.getTime(track.getDuration()) + "`\n" + track.getInfo().uri);
            builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
            e.textChannel.sendMessage(builder.build()).queue();
            return;
        }
        try {
            int times = Integer.parseInt(e.args[1]);
            if (times > manager.scheduler.queue.size()){
                e.textChannel.sendMessage("no song to skip").queue();
                return;
            }
            for (int i = 0; i < times; i++)
                manager.scheduler.nextTrack();
        } catch (NumberFormatException exception){
            new BadArgumentsException().send(e, "`args[1]` expects a natural number.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.ORANGE);
        final AudioTrack track = manager.scheduler.audioPlayer.getPlayingTrack();
        builder.setTitle("Skip " + Integer.parseInt(e.args[1]) + " song to:");
        builder.setDescription("Title: `" + track.getInfo().title + "`\nAuthor: `" + track.getInfo().author + "`\n" +
                "Length: `" + MathHelper.getTime(track.getDuration()) + "`\n" + track.getInfo().uri);
        builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        e.textChannel.sendMessage(builder.build()).queue();
    }

    @Override
    protected boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 2)
            return new BadArgumentsException().send(e);
        return true;
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
