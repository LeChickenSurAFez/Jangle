import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


/*
 * 
 * Project name: Jangle
 * Update date: 4/30/20
 * Author: Chris Cardimen
 * Overview: Jangle is a discord bot with both text and music capabilities.
 * He is sourced from multiple libraries including lavaplayer, jSoup, and more.
 * All the code written in Jangle is my own, unless otherwise noted.
 *  
 */


public class Main {
	/*Suppressing the warning about deprecation because with the JDABuilder,
	 * some things are depreciated due to the way discord handles JDA/the builder.*/
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		/*Creating a new JDA object with the token*/
		 JDA jda = new JDABuilder("NTMxNTMyODIxMTQ0MTQxODM1.XhDK-w.bWXP8INrPcWyuiLzE9GYs_DSUL8")
				 //Adding event listener
		            .addEventListeners(new myEventListener())
		            //Sets the activity
		            .setActivity(Activity.playing(">help for commands"))
		            //Builds the bot
		            .build();
	}

}