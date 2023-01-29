package myDiscordBot.command.music.handle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.*;
import utils.MathHelper;
import myDiscordBot.command.MusicOnTrackEndEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public final BlockingQueue<AudioTrack> queue;

    TrackScheduler(AudioPlayer player){
        audioPlayer = player;
        queue = new LinkedBlockingDeque<>();
    }

    public void queue(AudioTrack track){
        queue.offer(track);
        if (audioPlayer.getPlayingTrack() == null) {
            audioPlayer.startTrack(queue.poll(), true);
            final AudioTrack now = audioPlayer.getPlayingTrack();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.GREEN);
            builder.setTitle(":musical_note: **Now Playing:**");
            builder.setDescription("Title: `" + now.getInfo().title + "`\nAuthor: `" + now.getInfo().author + "`\n" +
                    "Length: `" + MathHelper.getTime(now.getDuration()) + "`\n" + now.getInfo().uri);
            MusicOnTrackEndEvent.event.sendMessage(builder.build());
        }
    }

    public void nextTrack(){
        audioPlayer.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
            final AudioTrack now = audioPlayer.getPlayingTrack();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.GREEN);
            builder.setTitle(":musical_note: **Now Playing:**");
            builder.setDescription("Title: `" + now.getInfo().title + "`\nAuthor: `" + now.getInfo().author + "`\n" +
                    "Length: `" + MathHelper.getTime(now.getDuration()) + "`\n" + now.getInfo().uri);
            MusicOnTrackEndEvent.event.sendMessage(builder.build());
        }
    }
}
