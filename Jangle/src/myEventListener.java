import music.MusicCommands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class myEventListener extends ListenerAdapter {

	public myEventListener() {

	}

	public void onMessageReceived(MessageReceivedEvent event) {
		// Makes it so that Jangle won't respond to other bots
		if (event.getAuthor().isBot()) {
			return;
		}

		// Setting up variables for sending messages
		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel();
		User author = event.getAuthor();

		MusicCommands musicCommands = new MusicCommands(message, content, channel, author, event);
		musicCommands.onInputReceived();
		Commands command = new Commands(message, content, channel, author, event);
		command.OnInputReceived();

	}
}
