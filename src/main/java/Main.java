import myDiscordBot.DiscordBot;
import javax.security.auth.login.LoginException;
public class Main {
    public static void main(String[] args) throws LoginException {
        DiscordBot.create().execute();
    }
}
