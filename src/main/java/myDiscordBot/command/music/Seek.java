package myDiscordBot.command.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.music.handle.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import utils.MathHelper;

public class Seek extends DiscordCommand {

    @Override
    protected void handle(DiscordEvent e) {
        var audioPlayer = PlayerManager.getInstance().getMusicManager(e.guild).scheduler.audioPlayer;
        if (audioPlayer.getPlayingTrack() == null) {
            e.textChannel.sendMessage("No song is playing right now.").queue();
            return;
        }
        long time = MathHelper.timeToLong(e.args[1]);
        if (time < 0) {
            new BadArgumentsException().send(e, "`args[1]` expects a time, it should be either `hh:mm:ss`, `mm:ss` or `ss`");
            return;
        }
        if (time > audioPlayer.getPlayingTrack().getDuration()) {
            new BadArgumentsException().send(e, "`Specified time position is longer than the video's duration, please be aware.");
            return;
        }
        var now = audioPlayer.getPlayingTrack();
        now.setPosition(time);
        StringBuilder sb = new StringBuilder("Current track position changed:\n");
        sb.append("Title: `").append(now.getInfo().title).append("`\n`");
        sb.append(MathHelper.getTime(now.getPosition())).append(" ");
        int x = Math.round((float)now.getPosition() / now.getDuration() * 20);
        for (int i = 0; i <= 20; i++)
            sb.append(i == x ? "o" : "-");
        sb.append(" ").append(MathHelper.getTime(now.getDuration()));
        sb.append(" (-").append(MathHelper.getTime(now.getDuration() - now.getPosition())).append(")`");
        e.textChannel.sendMessage(sb).queue();
    }

    @Override
    protected boolean errorHandle(DiscordEvent e) {
        if (e.args.length != 2)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "seek";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
