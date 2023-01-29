package myDiscordBot;

import myDiscordBot.command.BotSystem;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public static final String creator_name = "Σmathy";
    public static final String born_date = "06/04/2021";
    private JDA jda;

    public static DiscordBot create(String token) throws LoginException {
        DiscordBot bot = new DiscordBot();
        bot.jda = JDABuilder.
                createDefault(token).
                enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_VOICE_STATES).
                build();
        return bot;
    }

    public void execute(){
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, "never gonna give you up"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new BotSystem());
    }
}
