package myDiscordBot.command.music;

import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.MusicOnTrackEndEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import myDiscordBot.command.exception.NoVoiceChannelConnectedException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join extends DiscordCommand {
    @Override
    public void handle(DiscordEvent e) {
        MusicOnTrackEndEvent.createEvent(e);
        VoiceChannel vc = e.member.getVoiceState().getChannel();
        if (vc == null) {
            new NoVoiceChannelConnectedException().send(e);
            return;
        }
        AudioManager am = e.guild.getAudioManager();
        am.openAudioConnection(vc);
        e.textChannel.sendMessage("Channel connected successfully").queue();
    }

    @Override
    public boolean errorHandle(DiscordEvent e) {
        if (e.args.length > 1)
            return new BadArgumentsException().send(e);
        return true;
    }

    @Override
    protected String getName() {
        return "join";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
