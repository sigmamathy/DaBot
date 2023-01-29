package myDiscordBot.command.Cpublic.text;

import myDiscordBot.DiscordBot;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.exception.BadArgumentsException;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public class Help extends DiscordCommand {

    private final EmbedBuilder help = new EmbedBuilder();

    public Help(){
        help.setColor(new Color(0x40A6EE));
        help.setTitle("Σmathy DaBot Commands Help List");
        help.setDescription("created by `" + DiscordBot.creator_name + "` in `" + DiscordBot.born_date + "`");
        help.addField("How it works:", """
                           ```fix
                           Don't ask, it just works.
                           ```
                           """, false);
        help.addField("Commands List", """
                           ```md
                           #Text commands:
                           help
                           say (content) [hide/nohide]
                           rickroll [name]
                           embed (title) (desc) (footer)
                           
                           #Image commands:
                           textimage [foreground] [background]
                           flip : (horizontal/vertical) {image}
                           
                           #r8 commands:
                           monkerate [name]
                           gaerate [name]
                           waifurate [name]
                           susrate [name]
                           
                           #Music commands:
                           join
                           leave
                           add (name/url)
                           play [name/url]
                           stop
                           queue
                           skip [number]
                           
                           #Moderation
                           kick [user]
                           ```
                           """,false);
    }

    @Override
    public void handle(DiscordEvent e) {
        help.setFooter("Summoned by " + e.author.getName(), e.author.getAvatarUrl());
        e.textChannel.sendMessage(help.build()).queue();
    }

    @Override
    public void errorHandle(DiscordEvent e) {
        if (e.args.length != 1) {
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    public String getName(){
        return "help";
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
