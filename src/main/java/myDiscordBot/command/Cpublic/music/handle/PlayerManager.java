package myDiscordBot.command.Cpublic.music.handle;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.*;
import utils.MathHelper;
import myDiscordBot.command.DiscordEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;
    PlayerManager(){
        musicManagers = new HashMap<>();
        audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }
    public GuildMusicManager getMusicManager(Guild guild){
        return musicManagers.computeIfAbsent(guild.getIdLong(), (guildID) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }
    public void loadAndPLay(DiscordEvent e, String url, boolean play){
        final GuildMusicManager musicManager = getMusicManager(e.textChannel.getGuild());
        final TextChannel channel = e.textChannel;
        audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if (play)
                    musicManager.scheduler.queue(track);
                else
                    musicManager.scheduler.queue.offer(track);
                channel.sendMessage(reply(e, track).build()).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                AudioTrack track = tracks.get(0);
                channel.sendMessage(reply(e, track).build()).queue();
                if (play)
                    musicManager.scheduler.queue(track);
                else
                    musicManager.scheduler.queue.offer(track);
            }

            @Override
            public void noMatches() {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("No result found");
                eb.setDescription("use other keyword try again");
                channel.sendMessage(eb.build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("Song loaded failed:");
                eb.setDescription(e.getMessage());
                channel.sendMessage(eb.build()).queue();
            }
        });
    }

    private EmbedBuilder reply(DiscordEvent e, AudioTrack track){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(new Color(0x40A6EE));
        builder.setTitle("Adding to queue:");
        builder.setDescription("Title: `" + track.getInfo().title + "`\nauthor: `" + track.getInfo().author + "`\n" +
                "length: `" + MathHelper.getTime(track.getDuration()) + "`\n" + track.getInfo().uri);
        builder.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        return builder;
    }

    public static PlayerManager getInstance(){
        if (INSTANCE == null)
            INSTANCE = new PlayerManager();
        return INSTANCE;
    }
}
