import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import utility.ReadFile;

/*
 * 
 * Project name: Jangle
 * Update date: 9/23/20
 * Author: Chris Cardimen
 * Overview: Jangle is a discord bot with both text and music capabilities.
 * He is sourced from multiple libraries including lavaplayer, jSoup, and more.
 * All the code written in Jangle is my own, unless otherwise noted.
 *  
 */

public class Main {

	public static void main(String[] args) throws Exception {
		/* Creating a new JDA object with the token */
		//Assign the file that the token will be associated with
		String token_file = "Token.txt";
		//Read the token file
		ReadFile token_read = new ReadFile(token_file);
		//Open the token file
		String[] token_array = token_read.OpenFile();

		//Create a new JDABuilder with the token provided
		JDA jda = JDABuilder.createDefault(token_array[0])
				// Adding event listener
				.addEventListeners(new myEventListener())
				// Sets the activity
				.setActivity(Activity.playing(">help for commands"))
				// Builds the bot
				.build();
	}

}