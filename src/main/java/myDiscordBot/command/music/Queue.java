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
    protected void handle(DiscordEvent e)
    {
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
        boolean isPlaying = nowPlaying != null;
        //playing track

        StringBuilder string1 = new StringBuilder();
        if (isPlaying){
            string1.append("Title: `").append(nowPlaying.getInfo().title).append("`\n");
            string1.append("Author: `").append(nowPlaying.getInfo().author).append("`\n`");
            string1.append(MathHelper.getTime(nowPlaying.getPosition())).append(" ");
            int x = Math.round((float)nowPlaying.getPosition() / nowPlaying.getDuration() * 20);
            for (int i = 0; i <= 20; i++)
                string1.append(i == x ? "o" : "-");
            string1.append(" ").append(MathHelper.getTime(nowPlaying.getDuration()));
            string1.append(" (-").append(MathHelper.getTime(nowPlaying.getDuration() - nowPlaying.getPosition())).append(")`\n");
            string1.append(nowPlaying.getInfo().uri);
            builder.addField("**Now Playing:**", string1.toString(), false);
        }
        //now playing display

        StringBuilder string2 = new StringBuilder();
        if (!manager.scheduler.queue.isEmpty()) {
            int index = 0;
            string2.append("```md");
            for (AudioTrack track: manager.scheduler.queue) {
                index++;
                string2.append("\n\n").append(index).append(". ").append(track.getInfo().title).append("\n");
                string2.append("    Length: ").append(MathHelper.getTime(track.getDuration()));
            }
            string2.append("```");
        }
        else string2.append("\n").append("`no song here currently`");
        builder.addField("**Song List:**", string2.toString(), false);
        //song list display

        builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        e.textChannel.sendMessage(builder.build()).queue();
        //send message
    }

    @Override
    protected boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 1)
            return new BadArgumentsException().send(e);
        return true;
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
