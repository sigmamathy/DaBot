import myDiscordBot.DiscordBot;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws LoginException, IOException {
        // read the token from the resources
        String token = Files.readString(Path.of("res/token.txt"));
        DiscordBot.create(token).execute();
    }
}
