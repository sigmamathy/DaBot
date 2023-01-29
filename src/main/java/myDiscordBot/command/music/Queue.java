package myDiscordBot.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import myDiscordBot.command.exception.BadArgumentsException;
import utils.MathHelper;
import myDiscordBot.command.music.handle.GuildMusicManager;
import myDiscordBot.command.music.handle.PlayerManager;
import myDiscordBot.command.*;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public class Queue extends DiscordCommand {

    @Override
    protected void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        final GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(e.guild);
        //manager

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.MAGENTA);
        //embed builder

        String title = "Music Queue: (" + manager.scheduler.queue.size() + " tracks)";
        builder.setTitle(title);
        //builder title

        AudioTrack nowPlaying = manager.scheduler.audioPlayer.getPlayingTrack();
        boolean isPLaying = nowPlaying != null;
        //playing track

        StringBuilder string1 = new StringBuilder();
        if (isPLaying){
            string1.append(nowPlaying.getInfo().title).append("\n");
            string1.append("`-> Time left: ").append(MathHelper.getTime(nowPlaying.getDuration() - nowPlaying.getPosition())).append("`");
            builder.addField("**Now Playing:**", string1.toString(), false);
        }
        //now playing display

        StringBuilder string2 = new StringBuilder();
        if (!manager.scheduler.queue.isEmpty()) {
            int index = 0;
            for (AudioTrack track: manager.scheduler.queue) {
                index++;
                string2.append("\n----------------------------------------------------------");
                string2.append("\n**#").append(index).append(":**\n").append(track.getInfo().title).append("\n");
                string2.append("`-> Time left: ").append(MathHelper.getTime(track.getDuration())).append("`");
            }
        }
        else string2.append("\n").append("`no song here currently`");
        builder.addField("**Song List:**", string2.toString(), false);
        //song list display

        builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        e.textChannel.sendMessage(builder.build()).queue();
        //send message
    }

    @Override
    protected void errorHandle(DiscordEvent e) {
        if (e.args.length > 1){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "queue";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
