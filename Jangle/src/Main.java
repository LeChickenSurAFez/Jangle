import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		 JDA jda = new JDABuilder("NTMxNTMyODIxMTQ0MTQxODM1.XhDK-w.bWXP8INrPcWyuiLzE9GYs_DSUL8")
		            .addEventListeners(new myEventListener())
		            .setActivity(Activity.playing(">help for commands"))
		            .build();
	}

}