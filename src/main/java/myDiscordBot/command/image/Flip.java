package myDiscordBot.command.image;

import utils.ImageHelper;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class Flip extends DiscordCommand {

    @Override
    public void handle(DiscordEvent e) {
        if (e.message.getAttachments().size() == 1) {
            Message.Attachment att = e.message.getAttachments().get(0);
            if (!e.args[1].equalsIgnoreCase("horizontal") && !e.args[1].equalsIgnoreCase("vertical")) {
                new BadArgumentsException().send(e, "`args[2]` should be either `horizontal` or `vertical`");
                return;
            }
            try {
                File getImage = att.downloadToFile(
                        "res/get.png").get();
                File flipped = ImageHelper.imageFlip(getImage,
                        e.args[1].equalsIgnoreCase("horizontal") ? ImageHelper.FLIP_HORIZONTAL : ImageHelper.FLIP_VERTICAL);
                assert flipped != null;
                e.textChannel.sendFile(flipped).queue();
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            } catch (Exception ec){
                new BadAttachmentException().send(e);
            }

        }
        else new BadAttachmentException().send(e);
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length != 2) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "flip";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
