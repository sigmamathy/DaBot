package myDiscordBot.command;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class MusicOnTrackEndEvent {

    public static MusicOnTrackEndEvent event;
    private DiscordEvent e;

    public void sendMessage(CharSequence text) {
        e.textChannel.sendMessage(text).queue();
    }
    public void sendMessage(MessageEmbed text) {
        e.textChannel.sendMessage(text).queue();
    }

    public static void createEvent(DiscordEvent e){
        if (event == null)
            event = new MusicOnTrackEndEvent();
        event.e = e;
    }
}
