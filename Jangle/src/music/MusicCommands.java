package music;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.SearchListResponse;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import utility.UtilityFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MusicCommands {
	/*
	 * A lot of variables are being declared here. Currently working to see if I can
	 * clean this up, I imagine a lot of these do not need to be global variables
	 * throughout the class.
	 */
	String content, prefix, command, voice_channel_name;
	MessageChannel channel;
	User author;
	Message message;
	MessageReceivedEvent event;
	VoiceChannel user_vc;
	AudioManager audioManager;
	ConnectionStatus status;
	PlayerManager manager;
	public static TextChannel jangle_channel;
	AudioPlayer DJ;
	TrackScheduler queue;
	public static ArrayList<String> song_queue = new ArrayList<String>();

	/*
	 * Constructor for the MusicCommands class. It takes in all the data created in
	 * #myEventListener from the message received event and organizes it into
	 * different categories.
	 */
	public MusicCommands(Message mess, String cont, MessageChannel chan, User aut, MessageReceivedEvent even) {
		message = mess;
		content = cont;
		channel = chan;
		author = aut;
		event = even;
		// Prefix
		prefix = ">";
		/*
		 * One problem I ran into was that in using command, as opposed to content to
		 * simplify things, there sometimes would not be a substring of a given length.
		 * This often occurred with images - there was no message content to take a
		 * command from. So I implemented this if/else statement to get rid of the
		 * exceptions that that issue was causing.
		 */
		if (content.length() > 0) {
			command = content.substring(1, content.length()).toLowerCase();
		} else {
			command = "";
		}

		// Voice channel denoting where the user is.
		user_vc = event.getMember().getVoiceState().getChannel();
		// Creating the audio manager
		audioManager = event.getGuild().getAudioManager();
		// Manager for PlayerManager
		manager = PlayerManager.getInstance();
		// TextChannel denoting the jangle channel
		jangle_channel = event.getGuild().getTextChannelById("705237638273171546");
		// Creates DJ player
		DJ = manager.getGuildMusicManager(event.getGuild()).player;
		// Assign name of voice channel if not null
		if (user_vc != null)
			voice_channel_name = user_vc.getName();
		// Set up the track scheduler for managing tracks.
		queue = new TrackScheduler(DJ);

	}

	/*
	 * This is the driving function for handling commands. This will happen
	 * everytime a message is received, as shown in #myEventListener. It checks
	 * through each of the commands to see which one matches the prefix. TODO Break
	 * loop when a command is activated rather than looping through the rest.
	 */
	public void onInputReceived() {
		/*
		 * A problem I ran into was that if the content size was 0, exceptions would
		 * occur. So I put in this statement to actively check and see if there IS
		 * content to check.
		 */
		if (content.length() > 0) {
			/*
			 * The functions in the following if statement are all COMMANDS that must be
			 * invoked using the prefix.
			 */
			if (content.startsWith(prefix)) {
				Join(command);
				Leave(command);
				Play(command);
				Pause(command);
				Stop(command);
				Volume(command);
				Skip(command);
				Queue(command);
			}
		}
	}

	public void Join(String command) {
		/*
		 * Command description: Joins the bot to the voice channel that the user is
		 * currently in. If the user is not in a voice channel, will prompt the user to
		 * join one.
		 */
		if (command.equals("join")) {
			// Asserting that the user is in a voice channel
			if (user_vc != null) {
				// Send a message that the bot is joining the voice channel
				channel.sendMessage("Joining " + "**" + voice_channel_name + "**.").complete();
				// Open audio connection to the voice channel
				audioManager.openAudioConnection(user_vc);
			}
			// If the user is not in a voice channel, prompts them to join one first.
			else {
				channel.sendMessage("Please join a voice channel.").complete();
			}
		}
	}

	public void Leave(String command) {
		/*
		 * Command description: Leaves the current voice channel. If Jangle isn't in a
		 * voice channel, will inform the user of that.
		 */
		if (command.equals("leave")) {
			// Finds status of the audioManager
			status = audioManager.getConnectionStatus();
			// If the audioManager is in a voice channel, say that Jangle is leavin
			if (status.name() != "NOT_CONNECTED") {
				channel.sendMessage("Leaving **" + voice_channel_name + "**.").complete();
				// Closes audio connection
				audioManager.closeAudioConnection();
			}
			// If Jangle is not in a voice channel, inform the user.
			else {
				channel.sendMessage("Not currently in a voice channel.").complete();
			}
		}
	}

	public void Play(String command) {
		/*
		 * Command description: The driving force behind the music player. This command
		 * takes a YouTube link and plays it through Jangle's DJ capabilities. If the
		 * user is in a voice channel and the player is not already in it, Jangle will
		 * join the user. If the user is not in a voice channel, Jangle informs them of
		 * that.
		 */
		if (command.length() >= 4 && command.substring(0, 4).equals("play")) {
			// If the only command is play, treat as resume command
			if (command.length() == 4) {
				// Set paused to false
				DJ.setPaused(false);
				channel.sendMessage("Resuming track.").complete();
			}
			// If the command is longer than 4, utilize the full capability.
			// If the DJ is not playing a song, follow code
			else if (song_queue.isEmpty()) {
				// Safe code
				// Splits content into 2 pieces: command and link
				String[] split_into_two = content.split(" ");
				// URL is assigned to the latter half
				String URL = split_into_two[1];
				// New code

				// Initialize document
				String[] searchWords = content.split(" ");

				for (int index = 1; index < searchWords.length; ++index) {
					if (index == 1) {
					} else {

					}
				}
				try {
					YouTube youtubeService = UtilityFunctions.getService();
					// Define and execute the API request
					YouTube.Search.List request = youtubeService.search().list("items");
					SearchListResponse response = request.setMaxResults(25L).setQ("surfing").setType("video").execute();
					String x = response.get("videoId").toString();
					System.out.println(x);
				} catch (IOException e) {

				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Safe code
				UtilityFunctions.Queue(URL);
				// If the user is in a voice channel
				if (user_vc != null) {
					// Open an audio connection in the user's voice channel
					audioManager.openAudioConnection(user_vc);
					// Load and play the URL
					PlayerManager.loadAndPlay(jangle_channel, URL);
					// Initialize volume at 45.
					DJ.setVolume(45);
				}
				// If the user is not in a voice channel, prompt them to join one.
				else {
					channel.sendMessage("Please join a voice channel.").complete();
				}
			}
			// If the DJ is currently NOT playing a song, queue it
			else {
				// Splits content into 2 pieces: command and link
				String[] split_into_two = content.split(" ");
				// URL is assigned to the latter half
				String URL = split_into_two[1];
				song_queue.add(URL);
			}
		}
	}

	public void Pause(String command) {
		/* Command description: pauses the DJ */
		if (command.equals("pause")) {
			// Sets paused to true.
			DJ.setPaused(true);
			channel.sendMessage("Pausing track.").complete();

		}
	}

	public void Stop(String command) {
		/* Command description: Stops & destroys the DJ player */
		if (command.equals("stop")) {
			// Destroys the DJ
			Leave("leave");
			DJ.destroy();

		}
	}

	// TODO: make DJ continue track after volume shift
	public void Volume(String command) {
		/* Command description: Sets the volume of the DJ */
		if (command.length() >= 6 && command.substring(0, 6).equals("volume")) {
			// Splits the content into 2 pieces: the command and the volume
			String[] split_into_volume = command.split(" ");
			// Parses an int from the volume portion of the command
			int volume_to_set = Integer.parseInt(split_into_volume[1]);
			// If volume is valid, set it to the user's volume
			if (volume_to_set >= 10 && volume_to_set <= 100) {
				// Set volume
				DJ.setVolume(Integer.parseInt(split_into_volume[1]));
				// Resume track
				// TODO: make it continue track DJ.playTrack(DJ.getPlayingTrack().makeClone());
				channel.sendMessage("Changing volume to: " + split_into_volume[1] + "%").complete();
			}
			// Else if volume is lower than lower bound, set to 10
			else if (volume_to_set < 10) {
				// Set volume
				DJ.setVolume(10);
				// Play track
				// TODO: make it continue track DJ.playTrack(DJ.getPlayingTrack().makeClone());
				channel.sendMessage("Changing volume to: 10%").complete();
			}
			// Else if volume is higher than higher bound, set to 100
			else {
				// Set volume
				DJ.setVolume(100);
				// PLay track
				// TODO: make it continue track DJ.playTrack(DJ.getPlayingTrack().makeClone());
				channel.sendMessage("Changing volume to: 100%").complete();
			}
		}
	}

	public void Skip(String command) {
		/*
		 * Command description: Skips the currently playing track and starts the next.
		 */
		if (command.equals("skip")) {
			// Skips to the next track

			// If the song queue is empty:
			if (MusicCommands.song_queue.isEmpty()) {
				// Send a message that nothing is playing
				channel.sendMessage("Nothing is playing").complete();
			}
			// Else
			else {
				// Remove the first song in the queue
				MusicCommands.song_queue.remove(0);
				// If the queue is NOW empty
				if (MusicCommands.song_queue.isEmpty()) {
					// Leave & destroy the DJ
					Leave("leave");
					DJ.destroy();
					// Send a message that the queue is now empty
					channel.sendMessage("The queue is now empty.").complete();
				}
				// Else, if the queue still has songs
				else {
					// Load and play the next song
					PlayerManager.loadAndPlay(MusicCommands.jangle_channel, MusicCommands.song_queue.get(0));
				}
				// nextTrack();
			}
			queue.nextTrack();
		}
	}

	public void Queue(String command) {
		/* Command description: Shows the queue. */
		if (command.equals("queue") || command.equals("q")) {
			// Create queue string
			String queue_to_output = "";
			// For each string in the queue list:

			// If the queue is empty
			if (queue_to_output.isEmpty()) {
				// Say that the queue is empty
				channel.sendMessage("The queue is empty.").complete();
			}
			// Else if the queue is NOT empty
			else {
				// Go through each video in the song queue
				for (String x : song_queue) {
					// Add it to the queue, alongside an index indicatior and "<>" to make sure that
					// it doesn't show the video link, rather just prints out the linl
					queue_to_output += (song_queue.indexOf(x) + 1) + ": <" + x + ">" + "\n";
				}
				// Send the queue
				channel.sendMessage(queue_to_output).complete();
			}
		}
	}
}
