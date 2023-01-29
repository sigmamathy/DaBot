package myDiscordBot.command.Cpublic.music;

import myDiscordBot.command.Cpublic.music.handle.PlayerManager;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.exception.NoVoiceChannelConnectedException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.net.URI;
import java.net.URISyntaxException;

public class Add extends DiscordCommand {
    @Override
    protected void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        VoiceChannel vc = e.member.getVoiceState().getChannel();
        if (vc == null) {
            new NoVoiceChannelConnectedException().send(e);
            return;
        }
        String s = e.args[1];
        if (!isUrl(s))
            s = "ytsearch:" + s;

        PlayerManager.getInstance().loadAndPLay(e, s, false);
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
    protected void errorHandle(DiscordEvent e) {
        if (e.args.length != 2) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "add";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
