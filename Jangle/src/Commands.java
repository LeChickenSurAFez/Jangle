import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import music.PlayerManager;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import utility.Methods;
import utility.ReadFile;
import utility.WriteFile;

public class Commands {
	/*
	 * A lot of variables are being declared here. Currently working to see if I can
	 * clean this up, I imagine a lot of these do not need to be global variables
	 * throughout the class.
	 */
	String content, prefix, command, flip_name, authorSend, peen_file_name, correctArray, printed, dadJoke, s, s1, s2,
			s3, s4, s5, s51, version;
	String[] htCount;
	double header, tailer;
	boolean dadOn;
	MessageChannel channel;
	User author;
	Message message;
	MessageReceivedEvent event;
	WriteFile flipData, peen_data;
	ReadFile flipFile, peen_file;
	Scanner scan;
	VoiceChannel user_vc;
	AudioManager audioManager;
	ConnectionStatus status;
	PlayerManager manager;
	TextChannel jangle_channel;

	/*
	 * Constructor for the Commands class. It takes in all the data created in
	 * #myEventListener from the message received event and organizes it into
	 * different categories.
	 */
	public Commands(Message mess, String cont, MessageChannel chan, User aut, MessageReceivedEvent even) {
		// Event distribution
		message = mess;
		content = cont;
		channel = chan;
		author = aut;
		event = even;
		// Prefix
		prefix = ">";
		// Version update
		version = "3.0.0";
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
		/*
		 * I believe that most of the variables below this line don't need to be in the
		 * constructor. So that's going to be a work in progress of categorizing and
		 * moving them to their respective places/methods.
		 */
		flipData = new WriteFile("flipTrack.txt", true);
		flip_name = "flipTrack.txt";
		flipFile = new ReadFile(flip_name);
		peen_file_name = "sizeTester.txt";
		peen_file = new ReadFile(peen_file_name);
		peen_data = new WriteFile("sizeTester.txt", true);
		printed = "";
		dadOn = false;
		s = "im";
		s1 = "i'm";
		s2 = "i\"m";
		s3 = "i`m";
		s4 = "i’m";
		s5 = "i";
		s51 = "am";
		// Scanner for console input
		scan = new Scanner(System.in);
		// Voice channel denoting where the user is.
		user_vc = event.getMember().getVoiceState().getChannel();
		// Creating the audio manager
		audioManager = event.getGuild().getAudioManager();
		// Manager for PlayerManager
		manager = PlayerManager.getInstance();
		// TextChannel denoting the jangle channel
		jangle_channel = event.getGuild().getTextChannelById("705237638273171546");
	}
//test
	/*
	 * This is the driving function for handling commands. This will happen
	 * everytime a message is received, as shown in #myEventListener. It checks
	 * through each of the commands to see which one matches the prefix. TODO Break
	 * loop when a command is activated rather than looping through the rest.
	 */
	public void WhenInputReceived() {
		// Textlog is called every single time this method is invoked.
		Textlog();
		/*
		 * A problem I ran into was that if the content size was 0, exceptions would
		 * occur. So I put in this statement to actively check and see if there IS
		 * content to check.
		 */
		if (content.length() > 0) {
			/*
			 * The functions below, but above the if statement are text-based commands that
			 * execute without the prefix. Generally, these are jokes that respond to
			 * something users have said that triggers a keyword.
			 */
			DadJoke();
			Beemovie();
			Obama();
			/*
			 * The functions in the following if statement are all COMMANDS that must be
			 * invoked using the prefix.
			 */
			if (content.startsWith(prefix)) {
				// TODO once again, find a way to handle rather than if/else looping.
				// TODO organize better/more used commands to the top.
				// TODO break the statement once a command is executed.
				Help(command);
				Info(command);
				Random(command);
				Ping(command);
				Flip(command);
				Init(command);
				Snowday(command);
				Pingle(command);
				PizzaTime(command);
				TicTacToe(command);
				Jingle(command);
				LuckyNum(command);
				Version(command);
				Console(command);
				Rots(command);
				Peen(command);
				Factoring(command);
				Join(command);
				Leave(command);
				Play(command);
				Stop(command);
				Volume(command);
			}
		}
	}

	/* Below are all methods relating to commands */
	public void Help(String command) {
		/*
		 * Command description: Lists all of the commands that Jangle has that can be
		 * invoked using the prefix.
		 */
		if (command.equals("help")) {
			// Send list
			channel.sendMessage(
					"```Commands:\n>ping\n>pizzatime?\n>tictactoe\n>jingle\n>beemovie (please be careful with this one)"
							+ "\n>luckynum\n>factor\n>peen\n>flip\n>random\n>version\n>info\n>rots```")
					.complete();
			// Console log
			System.out.println("Help completed @" + java.time.LocalDateTime.now());
		}
	}

	public void Info(String command) {
		/*
		 * TODO get rid of Info. Instead, make each command modular. For example,
		 * ">help info" woudl give info on help, rather than listing all info at once.
		 */
		/*
		 * Command description: What this command currently does is send info on how to
		 * use each command (it's VERY outdated).
		 */
		if (command.equals("info")) {
			// Send info
			channel.sendMessage("```Commands:\n>ping [Jangle's ms response time]\n>pizzatime?\n>tictactoe\n>"
					+ "jingle\n>beemovie [2% odds to print bee movie] \n>" + "luckynum [gives a number 1-100]\n"
					+ ">factor ['>factor 1 2 3' factors x²+2x+3]" + "\n"
					+ ">peen ['>peen @name' to give penis length of @name]\n" + ">flip [flips a coin]\n" + ">random\n"
					+ ">version\n" + ">rots [sends the entire script of Star Wars: Episode III]```").complete();
			// Console log
			System.out.println("Info completed @" + java.time.LocalDateTime.now());
		}
	}

	public void Random(String command) {
		/* Command description: Selects a random number and send it to the user. */
		if (command.equals("random")) {
			// Creates a new random number from 1-100
			Random randomNum = new Random();
			int random = randomNum.nextInt(100) + 1;
			channel.sendMessage("Random number: " + random).complete();
			// Console log
			System.out.println("Random completed @" + java.time.LocalDateTime.now());

		}
	}

	public void Ping(String command) {
		/*
		 * Command description: Sends a corresponding pong message with the time it
		 * takes to ping the gateway to the user.
		 */
		// TODO assign event.getJDA().getGatewayPing() to a string varibale for cleaner
		// code.
		if (command.equals("ping")) {
			channel.sendMessage("Pong! " + event.getJDA().getGatewayPing() + "ms").complete();
			System.out.println("Pong completed @" + java.time.LocalDateTime.now());
		}

	}

	public void Flip(String command) {
		/*
		 * Command description: Flips a coin, then logs those flips to a file. In that
		 * file, all the flips are stored to create data for the use about how accurate
		 * the randomness is. Goal is 50% and in time it will even out. Once flip has
		 * been stored, logged, and declared, a bunch of data is output to the user
		 * including, but not limited to, percentages of possibilities, what the flip
		 * was, and total number of flips.
		 */
		if (command.equals("flip")) {
			// Initialize head & tail values to 0. For reference, header is for heads and
			// tailer is for tails. Dumb variables names, I know.
			header = 0;
			tailer = 0;
			// Declares a new random, either 0 or 1.
			Random flipNum = new Random();
			// Assigns the int to the random (0 or 1).
			int headOrTail = flipNum.nextInt(2);

			// If it is 0, it's heads
			if (headOrTail == 0) {
				/*
				 * Try/catch statement necessary because we're writing to the file. The file in
				 * this case is flipData, and it's where all the flips are stored.
				 */
				try {
					// Write "Heads!"
					flipData.writeToFile("Heads!");
				} catch (IOException e) {
					// Print stack trace with exception
					e.printStackTrace();
				}

			}
			// If it is 1, it's tails.
			else if (headOrTail == 1) {
				/*
				 * Try/catch statement necessary because we're writing to the file. The file in
				 * this case is flipData, and it's where all the flips are stored.
				 */ try {
					// Write "Tails!"
					flipData.writeToFile("Tails!");
				} catch (IOException e) {
					// Print stack trace with exception
					e.printStackTrace();
				}

			}
			/*
			 * Try/catch statement because we need to open the flipFile to read in all the
			 * data.
			 */
			try {
				htCount = flipFile.OpenFile();
			} catch (IOException e) {
				// Print stack trace with exception
				e.printStackTrace();
			}

			/*
			 * What this for loop does is go through the length of the file, and count each
			 * time that either a "Heads!" or a "Tails!" appears. It then increments the
			 * head/tail count based upon the quantities found.
			 */
			for (int read = 0; read < htCount.length; read++) {
				if (htCount[read].equals("Heads!")) {
					header++;
				}
				if (htCount[read].equals("Tails!")) {
					tailer++;
				}
			}
			/* The following commands are the output to the user. */
			// If it's heads
			if (headOrTail == 0) {
				channel.sendMessage("Heads!\n" + "```Total Heads: " + header + "\nTotal Tails: " + tailer + "\n"
						+ ((header / (header + tailer) * 100) + "% Heads\n"
								+ ((tailer / (header + tailer) * 100) + "% Tails```")))
						.complete();

			}
			// If it's tails
			else if (headOrTail == 1) {
				channel.sendMessage("Tails!\n" + "```Total Heads: " + header + "\nTotal Tails: " + tailer + "\n"
						+ ((header / (header + tailer) * 100) + "% Heads\n"
								+ ((tailer / (header + tailer) * 100) + "% Tails```")))
						.complete();
			}
			// Console output
			System.out.println("Flip completed @" + java.time.LocalDateTime.now());

		}

	}

	public void Peen(String command) {
		/*
		 * Command description: When rolled, Peen stores the penis length of a given
		 * user, be it the user who sent the command or a specified user in the command
		 * parameters. It then loops through all the stored users and the IDs associated
		 * with them so that once rolled, a person's penis length stays static. Then, it
		 * outputs the penis length to the user.
		 */
		if (command.length() >= 4 && command.equals("peen")) {
			/*
			 * First thing that we do is split the command into two pieces. The command
			 * portion, peenPeople[0] and the name peenPeople[1].
			 */
			String[] peenPeople = content.split(" ");
			/*
			 * Determining the author. If the length is 2, then we go to the following if
			 * statement
			 */
			if (peenPeople.length == 2) {
				// Getting rid of exclamation marks
				authorSend = peenPeople[1].replaceAll("!", "");
			}
			/*
			 * If the length is anything other than 2, then we will assume that the author/
			 * person whom we're measuring is the user who intiated the command.
			 */
			else {
				authorSend = author.getAsMention().toString();
			}

			/* Penis size is somewhere in the range of 1-18. */
			Random randoshin = new Random();
			// Assign penis size.
			int peenSize = randoshin.nextInt(18) + 1;
			/* Try/catch loop necessary because we're opening files and writing to them. */
			try {
				// String array comprised of data from the peen_file.
				String[] aryLines = peen_file.OpenFile();
				/*
				 * A problem that was run into was that aryLines wasn't exactly the right array.
				 * So what I did was create a string comprised of all the data from aryLines,
				 * then split it up and put it into a new array.
				 */
				for (int y = 0; y < aryLines.length; y++) {
					correctArray += " " + aryLines[y];
				}
				// This is the new array that everything is based off of.
				String[] whatWeBaseItOff = correctArray.split(" ");

				// Creates two switches for the following while loops.
				Boolean thisIsSwitch = true;
				Boolean biggerSwitch = true;

				// Both switches start off as true
				while (biggerSwitch) {
					if (thisIsSwitch) {
						/*
						 * First things first, we check for the custom messages. What these do is loop
						 * through and look for a specific name/ID that is customized to a person. In
						 * this case it's Adam and I.
						 */
						for (int x = 0; x < whatWeBaseItOff.length; x++) {
							// This ID corresponds to Adam
							if (whatWeBaseItOff[x].equals("<@308761110394306560>")
									&& authorSend.replace("!", "").equals("<@308761110394306560>")) {
								channel.sendMessage("<@308761110394306560> has a twenty foot long schlong.").complete();
								System.out.println("Size @" + java.time.LocalDateTime.now());
								// Stop the loop by turning x to be a value higher than equal
								x = whatWeBaseItOff.length;
								// Switch both loops off
								thisIsSwitch = false;
								biggerSwitch = false;
								// This ID corresponds to Chris
							} else if (whatWeBaseItOff[x].equals("<@180825351109214208>")
									&& authorSend.replace("!", "").equals("<@180825351109214208>")) {
								channel.sendMessage("<@180825351109214208> has a ten foot long schlong.").complete();
								System.out.println("Size @" + java.time.LocalDateTime.now());
								// Stop the loop by turning x to be a value higher than equal
								x = whatWeBaseItOff.length;
								// Switch both loops off
								thisIsSwitch = false;
								biggerSwitch = false;
							}
						}
						// Now, this loop is executed if neither of the IDs above match the given ID.
						if (thisIsSwitch) {
							/* Loops through the array of IDs and penis sizes */
							for (int ab = 0; ab < whatWeBaseItOff.length; ab++) {
								// If the ID == specified author's, send the message
								if (whatWeBaseItOff[ab].equals(authorSend)) {
									channel.sendMessage(whatWeBaseItOff[ab] + "'s penis size is "
									// Uses the next index for the actual penis size
											+ whatWeBaseItOff[ab + 1] + " inches.").complete();
									System.out.println("Size @" + java.time.LocalDateTime.now());
									// Cancels loop by making ab bigger than equal
									ab = whatWeBaseItOff.length;
									// Switches off loops
									thisIsSwitch = false;
									biggerSwitch = false;
								}

							}
						}
					}
					/*
					 * At the end, if the second switch was never executed, meanining that the
					 * author ID was NOT listed in the storage, we write a NEW storage module with
					 * the new ID.
					 */
					if (thisIsSwitch) {
						// author ID + penis size written to a string.
						printed = (authorSend + " " + peenSize);
						// Write the string to the file.
						peen_data.writeToFile(printed);
						// Console output that a new entry was logged.
						System.out.println("Something written @" + java.time.LocalDateTime.now());
						/* Following lines encompass updating arrays like we initially did */
						aryLines = peen_file.OpenFile();
						for (int y = 0; y < aryLines.length; y++) {
							correctArray += " " + aryLines[y];
						}
						whatWeBaseItOff = correctArray.split(" ");
						// Loop will restart with the new data.
					}
				}
			}

			catch (IOException e) {
				// Print stack trace exception
				e.printStackTrace();
			}

		}
	}

	public void Init(String command) {
		if (command.equals("init")) {

			try {
				peen_data.writeToFile(" ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void Snowday(String command) {
		if (command.equals("snowday")) {
			Document doc = null;
			try {
				doc = Jsoup
						.connect("https://www.snowdaycalculator.com/prediction.php?zipcode=48306&snowdays=0&extra=0&")
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String snowChance = doc.text();
			String toSend = snowChance;
			System.out.println(toSend);

		}
	}

	public void Obama() {
		String[] equalityArray = content.split(" ");
		for (int equal = 0; equal < equalityArray.length; equal++) {
			if (equal < equalityArray.length - 2 && equalityArray[equal].toLowerCase().equals("say")
					&& equalityArray[equal + 1].toLowerCase().equals("the")
					&& equalityArray[equal + 2].toLowerCase().equals("n") && !equalityArray[equal - 1].equals("not")) {
				channel.sendMessage("YOU CAN'T DO THAT THAT'S RACIST").complete();
				System.out.println("Mrs Obama saved @" + java.time.LocalDateTime.now());
			}
		}

	}

	public void Factoring(String command) {
		// Alright let's do some factoring
		if (command.length() >= 6) {
			command = command.substring(0, 6);
		} else {
			command = "not_factoring";
		}
		if (content.length() > 7 && command.equals("factor")) {
			// System.out.println("Checkpoint 1 Factoring Completed " +
			// java.time.LocalDateTime.now());
			String[] numberSplit = content.split(" ");
			String aString = numberSplit[1];
			int a = Integer.parseInt(aString);
			String bString = numberSplit[2];
			int b = Integer.parseInt(bString);
			String cString = numberSplit[3];
			int c = Integer.parseInt(cString);
			// System.out.println("Checkpoint 2 Factoring Completed " +
			// java.time.LocalDateTime.now());

			@SuppressWarnings("unused")
			Methods Method = new Methods(a, b, c);
			channel.sendMessage("\nPolynomial: ").complete();
			// Method.Polynomial();
			if (Methods.b > 0 && Methods.c > 0) {
				channel.sendMessage(Methods.a + "x²" + " + " + Methods.b + "x" + " + " + Methods.c).complete();
			} else if (Methods.b > 0 && Methods.c < 0) {
				channel.sendMessage(Methods.a + "x²" + " + " + Methods.b + "x" + " - " + Math.abs(Methods.c))
						.complete();
			} else if (Methods.b < 0 && Methods.c > 0) {
				channel.sendMessage(Methods.a + "x²" + " - " + Math.abs(Methods.b) + "x" + " + " + Methods.c)
						.complete();
			} else if (Methods.b < 0 && Methods.c < 0) {
				channel.sendMessage(Methods.a + "x²" + " - " + Math.abs(Methods.b) + "x" + " - " + Math.abs(Methods.c))
						.complete();
			}
			// System.out.println("Checkpoint 3 Factoring Completed " +
			// java.time.LocalDateTime.now());

			// Method.Factor
			Methods.PerfectRootCalculations();
			if (Methods.perfect) {
				channel.sendMessage(Methods.returnRoot).complete();
			} else {
				Methods.RealRootCalculations();
				if (Methods.rootposString.equals("NaN") && Methods.rootnegString.equals("NaN")) {
					channel.sendMessage("No real roots.").complete();
				} else {
					Methods.IsRootPos();
					if (Methods.isInt) {
						if (Methods.posintpos && Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (!Methods.posintpos && !Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (!Methods.posintpos && Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (Methods.posintpos && !Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						}
					}

					else {
						if (Methods.posdblpos && Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (!Methods.posdblpos && !Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (!Methods.posdblpos && Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						} else if (Methods.posdblpos && !Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
							// System.out.println("Checkpoint 4 Factoring Completed " +
							// java.time.LocalDateTime.now());

						}
					}
				}

			}
			// System.out.println("Checkpoint 5 Factoring Completed " +
			// java.time.LocalDateTime.now());

		}
	}

	public void DadJoke() {
		// Dad joke territory
		char[] textChangingChars = { '|', '*', '`', '~', '_', };
		for (int x = 0; x < content.length(); ++x) {
			for (int y = 0; y < textChangingChars.length; ++y) {
				if (content.charAt(x) == textChangingChars[y]) {
					String char_to_replace = Character.toString(content.charAt(x));
					content.replace(char_to_replace, "");
				}
			}
		}
		Random dadRandom = new Random();
		int dadChance = dadRandom.nextInt(100) + 1;
		if (dadChance >= 1 && dadChance <= 15) {
			dadOn = false;
			dadJoke = "Hi";
			// System.out.println("Dad Joke Num: " + dadChance + " @" +
			// java.time.LocalDateTime.now());
			String[] dadArray = content.split(" ");
			for (int x = 0; x < dadArray.length; x++) {
				if (dadArray[x].toLowerCase().equals(s) || dadArray[x].toLowerCase().equals(s1)
						|| dadArray[x].toLowerCase().equals(s2) || dadArray[x].toLowerCase().equals(s3)
						|| dadArray[x].toLowerCase().equals(s4)) {
					for (int y = (x + 1); y < dadArray.length; y++) {
						dadJoke = dadJoke.concat(" " + dadArray[y]);
					}
					System.out.println("Dad Joke Checkpoint 2 @" + java.time.LocalDateTime.now());
					dadOn = true;

				}
			}
			if (dadOn) {
				dadJoke = dadJoke.concat(", I'm Jangle.");
				channel.sendMessage(dadJoke).complete();
				System.out.println("Dad Joke Checkpoint 3 @" + java.time.LocalDateTime.now());
			} else {
				System.out.println("Dad Joke Num: " + dadChance + " @" + java.time.LocalDateTime.now());

			}
		} else {

		}

	}

	public void Pingle(String command) {
		// Andrew's pingle
		if (command.equals("pingle")) {
			channel.sendMessage("Pongle!").complete();
			System.out.println("Pongle completed @" + java.time.LocalDateTime.now());

		}
	}

	public void PizzaTime(String command) {
		// Pizza time command
		if (command.equals("pizzatime?")) {
			channel.sendMessage("It's pizza time ;)").complete();
			System.out.println("Pizza time completed @" + java.time.LocalDateTime.now());
		}
	}

	public void TicTacToe(String command) {
		// Tic Tac Toe
		if (command.equals("tictactoe")) {
			// Setting up the game board
			String tic1 = ("    |    |    ");
			String tic2choice = (" L1 | M1 | R1 ");
			String tic3 = ("----|----|----");
			String tic4choice = (" L2 | M2 | R2 ");
			String tic5 = ("----|----|----");
			String tic6choice = (" L3 | M3 | R3 ");
			String tic7 = ("    |    |    ");

			// Starting board
			channel.sendMessage(tic1).complete();
			channel.sendMessage(tic2choice).complete();
			channel.sendMessage(tic3).complete();
			channel.sendMessage(tic4choice).complete();
			channel.sendMessage(tic5).complete();
			channel.sendMessage(tic6choice).complete();
			channel.sendMessage(tic7).complete();
			System.out.println("Tictactoe completed @" + java.time.LocalDateTime.now());

		}
	}

	public void Jingle(String command) {
		// Jingle Jangle
		if (command.equals("jingle")) {
			channel.sendMessage("Jingle Jangle go the keys <@195284766143021057>").complete();
			System.out.println("Jingle Jangle completed @" + java.time.LocalDateTime.now());

		}

	}

	public void LuckyNum(String command) {
		// lucky #
		if (command.equals("luckynum")) {
			Random rand = new Random();
			int luckyNum = rand.nextInt(100) + 1;
			channel.sendMessage("Your lucky number is: " + luckyNum).complete();
			System.out.println("Luckynum completed @" + java.time.LocalDateTime.now());

		}
	}

	public void Beemovie() {
		if (command.equals("beemovie")) {
			Random BeeRandom = new Random();
			int beeChance = BeeRandom.nextInt(100) + 1;
			if (beeChance == 1 || beeChance == 2) {

				Document doc = null;
				boolean rotsDone = false;
				int indexer = 779;
				try {
					doc = Jsoup.connect(
							"http://www.script-o-rama.com/movie_scripts/a1/bee-movie-script-transcript-seinfeld.html")
							.get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String beeScript = doc.text();
				String toSend = beeScript;
				System.out.println(toSend.indexOf("According to all"));
				while (!rotsDone && (indexer <= 56121)) {
					if (indexer >= 54000) {
						System.out.println(toSend.substring(indexer, 56095));
						channel.sendMessage(toSend.substring(indexer, 56095)).complete();
						rotsDone = true;
					} else {
						System.out.println(toSend.substring(indexer, indexer + 2000));
						channel.sendMessage(toSend.substring(indexer, indexer + 2000)).complete();
						indexer += 2000;
					}
				}
				System.out.println("Beemovie completed @" + java.time.LocalDateTime.now());
			} else {
				System.out.println("Beemovie NOT completed @" + java.time.LocalDateTime.now());
			}
		}
	}

	public void Textlog() {
		// Textlog channel creation
		TextChannel textlog = event.getGuild().getTextChannelById("531953276816719874");
		/*
		 * What's going on here is that I've created an arrayList of the attachments
		 * found within a given message. What this will allow me to do is go through
		 * each attachment and handle it separately
		 */
		List<Attachment> image = message.getAttachments();
		// Creating the attachment, which will get re-assigned for each index
		Attachment image_file;
		// determining whether or not there is anything in the arrayList
		boolean contains_image = false;
		// Initialize file_name
		String file_name = "";
		// Initialize the file we'll be working with
		File temp_image = null;
		// If there are attachments, proceed
		if (image.size() > 0) {
			// It does contain an image
			contains_image = true;
			// This will loop through each index of the arrayList and handle each
			// attachment separately.
			for (int x = 0; x < image.size(); ++x) {
				// Assign the image file to index 'x'
				image_file = image.get(x);
				// Assign file name
				file_name = image_file.getFileName();
				// Create the temp file
				temp_image = new File("C:\\JangleImages\\" + file_name);
				// Download the file to C:\\JangleImages\\file_name
				image_file.downloadToFile(temp_image);
				/*
				 * The next block of code, the try/catch statement deals with handling the image
				 * once it's been downloaded
				 */
				try {
					/*
					 * Very important. A problem that I ran into was that Jangle would try to send
					 * the image before it's even been downloaded, so to mitigate that I set the
					 * thread to sleep for 1000ms, or 1 second to allow the image time to download,
					 * thus negating the problems that were occuring.
					 */
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Print stack trace if exception
					e.printStackTrace();
				}
				/*
				 * Still in the for loop, and the image is downloaded. Now determining how to
				 * handle the image that has been downloaded
				 */
				if (contains_image) {
					// If there's an image, send it to the textlog.
					textlog.sendFile(temp_image).complete();
					try {
						// Then, if it exists, delete the image using its given path
						Files.deleteIfExists(temp_image.toPath());
					} catch (IOException e) {
						// Print stack trace if exception
						e.printStackTrace();
					}
					// Send identification information to the textlog.
					textlog.sendMessage("```\n" + "The above image ^" + "\nAuthor: " + author + "\n" + "Channel: "
							+ event.getChannel() + "\nTime: " + java.time.LocalDateTime.now() + "```").complete();
					System.out.println("Image sent to textlog @" + java.time.LocalDateTime.now());
				}
			}
		}
		// If there is accompanying text, or any text in general, enact this statement
		// This also will always apply if there's content, regardless of whether there
		// is an image or not.
		if (content.length() > 0) {
			// Send identification information to the textlog.
			textlog.sendMessage("```\n" + "\"" + content + "\"" + "\nAuthor: " + author + "\n" + "Channel: "
					+ event.getChannel() + "\nTime: " + java.time.LocalDateTime.now() + "```").complete();
			System.out.println("Message sent to textlog @" + java.time.LocalDateTime.now());
		}

	}

	public void Version(String command) {
		if (content.startsWith(prefix) && content.substring(1, content.length()).toLowerCase().equals("version")) {
			System.out.println("Jangle version " + version + " @ " + java.time.LocalDateTime.now());
			channel.sendMessage("Jangle version " + version + ": Chris Cardimen").complete();
		}
	}

	public void Console(String command) {
		if (command.equals("console") && author.getId().equals("180825351109214208")) {
			TextChannel general = event.getGuild().getTextChannelById("570627928564432897");
			String toConsole = scan.nextLine();
			general.sendMessage(toConsole).complete();
		}

	}

	public void Rots(String command) {
		if (command.equals("rots")) {
			Random rotsRandom = new Random();
			int rotsChance = rotsRandom.nextInt(100) + 1;
			if (rotsChance == 1) {
				Document doc = null;
				boolean rotsDone = false;
				int indexer = 757;
				try {
					doc = Jsoup.connect("https://www.imsdb.com/scripts/Star-Wars-Revenge-of-the-Sith.html").get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String rotsScript = doc.text();
				String toSend = rotsScript;
				while (!rotsDone && (indexer <= 176061)) {
					if (indexer >= 174000) {
						System.out.println(toSend.substring(indexer, 175859));
						channel.sendMessage(toSend.substring(indexer, 175859)).complete();
						rotsDone = true;
					} else {
						System.out.println(toSend.substring(indexer, indexer + 2000));
						channel.sendMessage(toSend.substring(indexer, indexer + 2000)).complete();
						indexer += 2000;
					}
				}
				System.out.println("RoTS completed @" + java.time.LocalDateTime.now());
			} else {
				System.out.println("RoTS NOT completed @" + java.time.LocalDateTime.now());
			}
		}
	}

	public void ARGame() {
		/*
		 * WriteFile countData = new WriteFile("arcounter.txt", true); String
		 * counter_name = "arcounter.txt"; ReadFile arFile = new ReadFile(counter_name);
		 * 
		 * TextChannel keyChannel =
		 * event.getGuild().getTextChannelById("669712973240991761");
		 * 
		 * 
		 * 
		 * 
		 * if (content.startsWith(prefix) && content.substring(1,
		 * content.length()).toLowerCase().equals("artrigger")) { TextChannel general =
		 * event.getGuild().getTextChannelById("570627928564432897"); File toGeneral =
		 * new File("3841914229748489359054734494422.png");
		 * general.sendMessage("The day has come. The game has begun." + "\n" +
		 * "Keep this image, it will be relevant later.").complete();
		 * general.sendFile(toGeneral).complete(); }
		 * 
		 * 
		 * 
		 * // AR GAME Functions //Key testing if (content.substring(0,
		 * content.length()).toLowerCase().equals("key") &&
		 * channel.getId().equals("669712973240991761")) {
		 * 
		 * keyChannel.sendMessage("key").complete(); }
		 * 
		 * }
		 */
		/*
		 * private int parseInt(String string) { // TODO Auto-generated method stub
		 * return 0; }
		 * 
		 * 
		 * AR game trigger if (content.startsWith(prefix) && content.substring(1,
		 * 5).toLowerCase().equals("peen") &&
		 * author.getAsMention().toString().equals("<@148839614029889536>") &&
		 * (countArray.length == 1)) {
		 * 
		 * try { countArray = arFile.OpenFile(); int countNum = parseInt(countArray[0]);
		 * if (countNum == 0 && (countArray.length == 1)) {
		 * channel.sendMessage(".... . .-.. .--.").complete();
		 * countData.writeToFile("complete"); } } catch (IOException e1) {
		 * e1.printStackTrace(); }
		 * 
		 * }
		 */

		/*
		 * dick size FOR ANDREW (delete all this afterwards) if
		 * (content.startsWith(prefix) && content.substring(1,
		 * 5).toLowerCase().equals("peen") &&
		 * author.getAsMention().toString().equals("<@148839614029889536>") &&
		 * (countArray.length > 1)) {
		 * 
		 * String[] peenPeople = content.split(" "); if (peenPeople.length == 2) {
		 * authorSend = peenPeople[1].replaceAll("!", ""); //
		 * System.out.println(authorSend); } else { authorSend =
		 * author.getAsMention().toString(); }
		 * 
		 * Random randoshin = new Random(); int peenSize = randoshin.nextInt(18) + 1;
		 * try { String[] aryLines = file.OpenFile(); for (int y = 0; y <
		 * aryLines.length; y++) { correctArray += " " + aryLines[y]; } String[]
		 * whatWeBaseItOff = correctArray.split(" "); //
		 * System.out.println(Arrays.toString(whatWeBaseItOff)); //
		 * System.out.println(author.getId());
		 * 
		 * Boolean thisIsSwitch = true; Boolean biggerSwitch = true;
		 * System.out.println(authorSend); while (biggerSwitch) { if (thisIsSwitch) {
		 * for (int x = 0; x < whatWeBaseItOff.length; x++) { if
		 * (whatWeBaseItOff[x].equals("<@308761110394306560>") &&
		 * authorSend.replace("!", "").equals("<@308761110394306560>")) {
		 * channel.sendMessage("<@308761110394306560> has a twenty foot long schlong.").
		 * complete(); System.out.println("Size @" + java.time.LocalDateTime.now()); x =
		 * whatWeBaseItOff.length; thisIsSwitch = false; biggerSwitch = false; } if
		 * (whatWeBaseItOff[x].equals("<@180825351109214208>") &&
		 * authorSend.replace("!", "").equals("<@180825351109214208>")) {
		 * channel.sendMessage("<@180825351109214208>'s penis size is big").complete();
		 * System.out.println("Size @" + java.time.LocalDateTime.now()); x =
		 * whatWeBaseItOff.length; thisIsSwitch = false; biggerSwitch = false; } } if
		 * (thisIsSwitch) { for (int ab = 0; ab < whatWeBaseItOff.length; ab++) { if
		 * (whatWeBaseItOff[ab].equals(authorSend)) {
		 * channel.sendMessage(whatWeBaseItOff[ab] + "'s penis size is " +
		 * whatWeBaseItOff[ab + 1] + " inches.").complete(); System.out.println("Size @"
		 * + java.time.LocalDateTime.now()); ab = whatWeBaseItOff.length; thisIsSwitch =
		 * false; biggerSwitch = false; }
		 * 
		 * } } }
		 * 
		 * if (thisIsSwitch) { printed = (authorSend + " " + peenSize);
		 * data.writeToFile(printed); System.out.println("Something written @" +
		 * java.time.LocalDateTime.now()); aryLines = file.OpenFile(); for (int y = 0; y
		 * < aryLines.length; y++) { correctArray += " " + aryLines[y]; } //
		 * System.out.println(Arrays.toString(whatWeBaseItOff)); whatWeBaseItOff =
		 * correctArray.split(" ");
		 * 
		 * } } }
		 * 
		 * catch (IOException e) { e.printStackTrace(); }
		 * 
		 * }
		 */

	}

	public void Join(String command) {
		if (command.equals("join")) {
			if (user_vc != null) {
				channel.sendMessage("Joining " + "**" + user_vc.getName() + "**" + ".").complete();
				audioManager.openAudioConnection(user_vc);
			} else {
				channel.sendMessage("Please join a voice channel.").complete();
			}
		}
	}

	public void Leave(String command) {
		if (command.equals("leave")) {
			status = audioManager.getConnectionStatus();
			channel.sendMessage("Leaving " + "**" + user_vc.getName() + "**" + ".").complete();
			audioManager.closeAudioConnection();
		}
	}

	public void Play(String command) {
		if (command.length() >= 4 && command.substring(0, 4).equals("play")) {
			String[] split_into_two = content.split(" ");
			String URL = split_into_two[1];
			// channel.sendMessage("Playing a test song").complete();
			if (user_vc != null) {
				audioManager.openAudioConnection(user_vc);
				manager.loadAndPlay(jangle_channel, URL);
				manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
			} else {
				channel.sendMessage("Please join a voice channel.").complete();
			}

		}
	}

	public void Stop(String command) {
		if (command.equals("stop"))
			;
		{
			manager.getGuildMusicManager(event.getGuild()).player.destroy();
		}
	}

	public void Volume(String command) {
		if (command.length() >= 6 && command.substring(0, 6).equals("volume")) {
			String[] split_into_volume = command.split(" ");
			manager.getGuildMusicManager(event.getGuild()).player.setVolume(Integer.parseInt(split_into_volume[1]));
			channel.sendMessage("Changing volume to: " + split_into_volume[1] + "%").complete();

		}
	}
}
