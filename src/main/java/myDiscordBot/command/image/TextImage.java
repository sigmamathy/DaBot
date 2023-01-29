package myDiscordBot.command.image;

import utils.ColorHelper;
import utils.ImageHelper;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Objects;

public class TextImage extends DiscordCommand {

    static final int TEXT_MAXIMUM_LIMIT = 550;

    @Override
    public void handle(DiscordEvent e) {
        final String[] args = e.args;
        final TextChannel textChannel = e.textChannel;
        if (args.length == 2) {
            if (args[1].length() <= TEXT_MAXIMUM_LIMIT) {
                try {
                    textChannel.sendFile(Objects.requireNonNull(ImageHelper.imageAddString(
                            null, args[1], null, null))).queue();
                } catch (Exception ec) {
                    textChannel.sendMessage("Exception: too much word in a words (limit: 40)").queue();
                }
            } else new TextOutOfLimitException().send(e);
        }
        else if (args.length == 3) {
            if (args[1].length() <= TEXT_MAXIMUM_LIMIT) {
                Color t = ColorHelper.returnColor(args[2]);
                if (t == null) {
                    new BadArgumentsException().send(e);
                    return;
                }
                try {
                    textChannel.sendFile(Objects.requireNonNull(ImageHelper.imageAddString(null, args[1], t, null))).queue();
                } catch (Exception ec) {
                    textChannel.sendMessage("Exception: too much word in a words (limit: 40)").queue();
                }
            } else new TextOutOfLimitException().send(e);
        }
        else if (args.length == 4) {
            if (args[1].length() <= TEXT_MAXIMUM_LIMIT) {
                Color t = ColorHelper.returnColor(args[2]);
                Color bg = ColorHelper.returnColor(args[3]);
                if (t == null || bg == null) {
                    new BadArgumentsException().send(e);
                    return;
                }
                try {
                    textChannel.sendFile(Objects.requireNonNull(ImageHelper.imageAddString(null, args[1], t, bg))).queue();
                } catch (Exception ec) {
                    textChannel.sendMessage("Exception: too much word in a words (limit: 40)").queue();
                }
            } else new TextOutOfLimitException().send(e);
        }
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length < 2 || e.args.length > 4) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected String getName() {
        return "textimage";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
