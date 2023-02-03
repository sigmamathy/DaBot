package myDiscordBot.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class AutoRespond {

    private static class Respond {
        String[] names;
        String content;
        Respond(String... names) {
            try {
                assert(names.length != 0);
                File f = new File("res/copypasta/" + names[0] + ".txt");
                this.names = names;
                content = Files.readString(Path.of(f.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final ArrayList<Respond> responds;

    public AutoRespond() {
        responds = new ArrayList<>();
        responds.add(new Respond("china", "ccp", "social credit", "winnie", "pooh", "xi jingping"));
        responds.add(new Respond("downvote"));
        responds.add(new Respond("i asked"));
        responds.add(new Respond("source", "sauce"));
        responds.add(new Respond("who asked"));
        responds.add(new Respond("taiwan"));
        responds.add(new Respond("ice cream", "\uD83C\uDF66", "zhong xina", "john cena"));
        responds.add(new Respond("among us", "amogus"));
        responds.add(new Respond("sus"));
        responds.add(new Respond("idk", "i don't know"));
        responds.add(new Respond("\uD83D\uDDFF"));
        responds.add(new Respond("\uD83E\uDD13"));
        responds.add(new Respond("no bitches"));
        responds.add(new Respond("\uD83D\uDC80"));
        responds.add(new Respond("bruh"));
    }

    public void handleCopypasta(Message content, TextChannel channel) {
        String str = content.getContentRaw().toLowerCase();
        for (Respond respond: responds) {
            for (String name: respond.names) {
                if (str.contains(name)) {
                    channel.sendMessage(respond.content).reference(content).queue();
                    return;
                }
            }
        }
    }

}
