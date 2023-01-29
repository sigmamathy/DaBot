package myDiscordBot.command.Cpublic.music.handle;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private final ByteBuffer byteBuffer;
    private final MutableAudioFrame frame;

    public AudioPlayerSendHandler(AudioPlayer ap){
        audioPlayer = ap;
        byteBuffer = ByteBuffer.allocate(1024);
        frame = new MutableAudioFrame();
        frame.setBuffer(byteBuffer);
    }

    @Override
    public boolean canProvide() {
        return audioPlayer.provide(frame);
    }

    @Nullable @Override
    public ByteBuffer provide20MsAudio() {
        return byteBuffer.flip();
    }
    @Override
    public boolean isOpus(){
        return true;
    }
}
