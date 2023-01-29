package myDiscordBot.command.Cprivate;

import myDiscordBot.command.DiscordEvent;
import net.dv8tion.jda.api.entities.Role;

import java.util.Objects;

public class PrivateCommand {

    public final static String PASSWORD_1 = "dQw4w9WgXcQ.s", PASSWORD_2 = "dQw4w9WgXcQ.hw";

    public void exe(DiscordEvent e){
        if (!e.guild.getName().equals("my server")) return;
        if (!e.textChannel.getName().equalsIgnoreCase("secret")) return;
        Role role1 = e.guild.getRolesByName("sfw", true).get(0);
        Role role2 = e.guild.getRolesByName("You know the rules and so do I", true).get(0);
        if (!e.args[0].equalsIgnoreCase("?" + "login")){
            if (e.args.length == 2){
                if (e.args[1].equals(PASSWORD_1)) {
                    e.guild.addRoleToMember(Objects.requireNonNull(e.member), role1).queue();
                }
                if (e.args[1].equals(PASSWORD_2)) {
                    e.guild.addRoleToMember(Objects.requireNonNull(e.member), role2).queue();
                }
            }
        }
        if (!e.args[0].equalsIgnoreCase("?" + "logout")){
            e.guild.removeRoleFromMember(Objects.requireNonNull(e.member), role1).queue();
            e.guild.removeRoleFromMember(Objects.requireNonNull(e.member), role2).queue();
        }
        e.textChannel.delete().queue();
    }
}
