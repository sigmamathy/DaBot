package myDiscordBot.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.music.handle.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import utils.MathHelper;

import java.awt.*;

public class NowPlaying extends DiscordCommand {

    @Override
    protected void handle(DiscordEvent e)
    {
        var audioPlayer = PlayerManager.getInstance().getMusicManager(e.guild).scheduler.audioPlayer;
        final AudioTrack now = audioPlayer.getPlayingTrack();

        StringBuilder sb = new StringBuilder();
        sb.append("Title: `").append(now.getInfo().title).append("`\n");
        sb.append("Author: `").append(now.getInfo().author).append("`\n`");
        sb.append(MathHelper.getTime(now.getPosition())).append(" ");
        int x = Math.round((float)now.getPosition() / now.getDuration() * 20);
        for (int i = 0; i <= 20; i++)
            sb.append(i == x ? "o" : "-");
        sb.append(" ").append(MathHelper.getTime(now.getDuration()));
        sb.append(" (-").append(MathHelper.getTime(now.getDuration() - now.getPosition())).append(")`\n");
        sb.append(now.getInfo().uri);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        builder.setTitle(":musical_note: **Now Playing:**");
        builder.setDescription(sb);
        MusicOnTrackEndEvent.event.sendMessage(builder.build());
    }

    @Override
    protected boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 1)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "np";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
