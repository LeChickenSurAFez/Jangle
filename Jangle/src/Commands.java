import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
		version = "3.0.2";
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

	}

	/*
	 * This is the driving function for handling commands. This will happen
	 * everytime a message is received, as shown in #myEventListener. It checks
	 * through each of the commands to see which one matches the prefix. TODO Break
	 * loop when a command is activated rather than looping through the rest.
	 */
	public void OnInputReceived() {
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
				//TODO use an array containing the keywords and check against input OR command handler
				// TODO organize better/more used commands to the top.
				// TODO break the statement once a command is executed.
				Help(command);
				Serial(command);
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
				Version(command);
				Console(command);
				Rots(command);
				Peen(command);
				Factoring(command);
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

	// TODO: remove
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

	// TODO: clean
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

	// Obsolete
	public void Init(String command) {
		/*
		 * Command description: Initializes the file to which the penis size function
		 * will write. At the given time, this method is pretty much obsolete.
		 */
		if (command.equals("init")) {

			try {
				// Write an empty string to the file
				peen_data.writeToFile(" ");
			} catch (IOException e) {
				// Print stack trace
				e.printStackTrace();
			}
		}
	}

	// TODO: fix
	public void Snowday(String command) {
		/*
		 * Command description: Crawls over a website that has snow day data, obtaining
		 * relevant text to predict the chance of a snowday the next day. At the moment,
		 * this is not functioning. Mainly because the website on which the calculator
		 * is based uses a widget to display the text, rather than a box that can be
		 * text-crawled over. So, TODO, fix this function.
		 */
		if (command.equals("snowday")) {
			// Initialize document
			Document doc = null;
			// Try catch statement since we're crawling over text
			try {
				// Assign the URL to the doc & get the text.
				doc = Jsoup
						.connect("https://www.snowdaycalculator.com/prediction.php?zipcode=48306&snowdays=0&extra=0&")
						.get();
			} catch (IOException e) {
				// Print stack trace
				e.printStackTrace();
			}
			// String of all the text
			String snowChance = doc.text();
			// String to return is created & assigned
			String toSend = snowChance;
			// Send all the text to the console
			System.out.println(toSend);
			// TODO literally the rest of this function.

		}
	}

	public void Obama() {
		/*
		 * Command description: This isn't so much a command as it is a chatbot
		 * response. When Jangle notices some combination of
		 * "I'm going to say the N word", he responds with
		 * "YOU CANT DO THAT THATS RACIST".
		 */
		// Split the content of every message into an array
		String[] equalityArray = content.split(" ");
		/*
		 * For each word in the newly created array, this for loop will go through and
		 * compare the words to see if they form any combination of the given phrase
		 * that will trigger a response.
		 */
		for (int equal = 0; equal < equalityArray.length; equal++) {
			if (equal < equalityArray.length - 2 && equalityArray[equal].toLowerCase().equals("say")
					&& equalityArray[equal + 1].toLowerCase().equals("the")
					&& equalityArray[equal + 2].toLowerCase().equals("n") && !equalityArray[equal - 1].equals("not")) {
				// Send message to text channel
				channel.sendMessage("YOU CAN'T DO THAT THAT'S RACIST").complete();
				// Console log
				System.out.println("Mrs Obama saved @" + java.time.LocalDateTime.now());
			}
		}

	}

	// TODO: make more efficient.
	public void Factoring(String command) {
		/*
		 * Command description: This command allows for the user to input 3 numbers,
		 * corresponding to a polynomial equation that requires factoring. This equation
		 * is of the form Ax^2+Bx+C. Users enter A B C after factoring, and then the
		 * method will output to them if the equation is factorable. If it is
		 * factorable, it will show in a format suhc as (x+1) and (x-2) for ease of use
		 * with homeowrk assignments. This method draws from the Utility package that I
		 * created, using the Methods class.
		 */
		if (content.length() >= 6 && command.equals("factor")) {
			/*
			 * First things first, we split content up into 3 parts, corresponding to the
			 * three numbers that the user enters, a, b, and c.
			 */
			// This is checkpoint 1
			String[] numberSplit = content.split(" ");
			String aString = numberSplit[1];
			int a = Integer.parseInt(aString);
			String bString = numberSplit[2];
			int b = Integer.parseInt(bString);
			String cString = numberSplit[3];
			int c = Integer.parseInt(cString);
			/*
			 * The next step is to return to the user a polynomial representation of what
			 * they have entered.
			 */
			// Chekpoint 2 reached
			// Use of the Methods class. Creates a new object with values a,b, and c for
			// calculations. There was a warning because the object is never actually used
			// in this method, but I supressed it.
			@SuppressWarnings("unused")
			Methods Method = new Methods(a, b, c);
			// Start the output to the user
			channel.sendMessage("\nPolynomial: ").complete();

			/*
			 * These four if/else-if statements depend upon whether b & c are positive or
			 * negative. For formatting's sake, it's set up so that if a variable is
			 * negative, it will show up as negative in the output.
			 */
			// TODO: could make this more efficient by assigning the negative sign earlier.
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
			// Checkpoint 3 reached.

			/*
			 * The next step is to do the calculations. We start of with the perfect root
			 * calculations, as those are the most obscure.
			 */
			Methods.PerfectRootCalculations();
			// If the calculations are a perfect root, return the root.
			if (Methods.perfect) {
				channel.sendMessage(Methods.returnRoot).complete();
			}
			// Else, move on to see if the root is a real root or not.
			else {
				Methods.RealRootCalculations();
				/*
				 * If the positive root string and the negative root stirng both equal "NaN" or
				 * "Not a Number", then there are no real roots.
				 */
				if (Methods.rootposString.equals("NaN") && Methods.rootnegString.equals("NaN")) {
					// Tell user that there are no real roots.
					channel.sendMessage("No real roots.").complete();
				}
				// If it IS a number, then we go into the more complex calculations.
				else {
					// Determine if the root is positive
					Methods.IsRootPos();
					// If an int is returned
					if (Methods.isInt) {
						// If we have a case of two negative numbers
						if (Methods.posintpos && Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
						}
						// If we have a case of two positive numbers
						else if (!Methods.posintpos && !Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
						}
						// If we have a case of a positive number and a negative number
						else if (!Methods.posintpos && Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
						}
						// If we have a case of a negative number and a positive number
						else if (Methods.posintpos && !Methods.negintpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposIntFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegIntFinal) + ")")
									.complete();
						}
						// At this point, checkpoint 4 would be passed
					}

					// If the number is not an integer and instead a double
					else {
						// If we have a case of two negative numbers
						if (Methods.posdblpos && Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
						}
						// If we have a case of two positive numbers
						else if (!Methods.posdblpos && !Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
						}
						// If we have a case of a positive number and a negative number
						else if (!Methods.posdblpos && Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x + " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x - " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
						}
						// If we have a case of a negative and a positive number
						else if (Methods.posdblpos && !Methods.negdblpos) {
							channel.sendMessage(
									"\nThis is factorable.\nFactors: (x - " + Math.abs(Methods.rootposDblFinal)
											+ ") and (x + " + Math.abs(Methods.rootnegDblFinal) + ")")
									.complete();
						}
						// At this point, checkpoint 4 would be passed
					}

				}

			}
			// At this point, checkpoint 5 would be passed.
		}
	}

	public void DadJoke() {
		/*
		 * Command description: Whenever a message is detected with the word, or some
		 * variation of, "I'm", Jangle will respond with "Hi (blank) I'm Jangle. This is
		 * more of a chatbot feature than an actual command, as it takes no arguments in
		 * the header.
		 */
		// First thing we do is strip text of any illegal characters
		char[] textChangingChars = { '|', '*', '`', '~', '_', };
		/*
		 * For each letter in the content, this for loop will iterate over and check it
		 * against the array of chars to see if they match. If they match, then the char
		 * will be removed. This is mainly to get rid of formatting.
		 */
		// Outside loop for each letter
		for (int x = 0; x < content.length(); ++x) {
			// Inside loop to check aaginst textChangingChars
			for (int y = 0; y < textChangingChars.length; ++y) {
				// Checks equality
				if (content.charAt(x) == textChangingChars[y]) {
					// Assign replacement char
					String char_to_replace = Character.toString(content.charAt(x));
					// Replace
					content.replace(char_to_replace, "");
				}
			}
		}
		/*
		 * By this point, all the illegal characters have been removed. Now, we get to
		 * the main part of the method, which is returning the actual joke. One thing
		 * that we noticed when this function was originally created was that it got
		 * annoying FAST if it happened everytime. To mitigate this, I've assigned a
		 * range of numbers between 1-100 to return the joke. If the number rolled isn't
		 * within that range, then the joke doesn't execute.
		 */
		Random dadRandom = new Random();
		int dadChance = dadRandom.nextInt(100) + 1;
		// Range of execution numbers.
		if (dadChance >= 1 && dadChance <= 15) {
			// Initialize the boolean of whether the joke is on or not to be false.
			dadOn = false;
			// Initialize the joke as "Hi"
			dadJoke = "Hi";
			// Split the rest of the message up into an array
			String[] dadArray = content.split(" ");
			// Loop through the array
			for (int x = 0; x < dadArray.length; x++) {
				// If any of the variations of "i'm" match, concat the rest of the array onto
				// the joke
				if (dadArray[x].toLowerCase().equals(s) || dadArray[x].toLowerCase().equals(s1)
						|| dadArray[x].toLowerCase().equals(s2) || dadArray[x].toLowerCase().equals(s3)
						|| dadArray[x].toLowerCase().equals(s4)) {
					// Concat if true
					for (int y = (x + 1); y < dadArray.length; y++) {
						dadJoke = dadJoke.concat(" " + dadArray[y]);
					}
					// Console log
					//System.out.println("Dad Joke Checkpoint 2 @" + java.time.LocalDateTime.now());
					// Dad joke did happen
					dadOn = true;
				}
			}
			// If the dad joke happened, send it to the user in their textchannel
			if (dadOn) {
				dadJoke = dadJoke.concat(", I'm Jangle.");
				channel.sendMessage(dadJoke).complete();
				//System.out.println("Dad Joke Checkpoint 3 @" + java.time.LocalDateTime.now());
				System.out.println("Dad Joke Num: " + dadChance + " Completed @" + java.time.LocalDateTime.now());
			}
			// Else, send nothing but tell the console what the number was.
			else {
				//System.out.println("Dad Joke Num: " + dadChance + " @" + java.time.LocalDateTime.now());
			}
		}
		// If the number is not in the range, do nothing
		else {
		}

	}

	public void Pingle(String command) {
		/* Command description: Returns the word "Pongle!", that's pretty much all. */
		if (command.equals("pingle")) {
			// Send message
			channel.sendMessage("Pongle!").complete();
			// Console log
			System.out.println("Pongle completed @" + java.time.LocalDateTime.now());
		}
	}

	public void PizzaTime(String command) {
		/*
		 * Command description: Simple command that just returns the fact that it is,
		 * indeed, pizzatime.
		 */
		if (command.equals("pizzatime?")) {
			// Send messsage
			channel.sendMessage("It's pizza time ;)").complete();
			// Console log.
			System.out.println("Pizza time completed @" + java.time.LocalDateTime.now());
		}
	}

	public void TicTacToe(String command) {
		/*
		 * Command description: This was the very first command that I ever wrote on
		 * Jangle, and I had VERY high aspirations. It's an absolute mess. I shot way
		 * too high and all this command does is output the board, but terribly. I have
		 * no plans to fix this command, rather letting it serve as an indication of how
		 * far I have come.
		 */
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

			// Console log
			System.out.println("Tictactoe completed @" + java.time.LocalDateTime.now());

		}
	}

	public void Jingle(String command) {
		/* Command description: Super simple command that just jangles some keys. */
		if (command.equals("jingle")) {
			// Send message
			channel.sendMessage("Jingle Jangle go the keys <@195284766143021057>").complete();
			// Console log.
			System.out.println("Jingle Jangle completed @" + java.time.LocalDateTime.now());

		}

	}

	public void Beemovie() {
		/* Command description: Outputs the entirety of the bee movie script. */
		if (command.equals("beemovie")) {
			/*
			 * One problem that I ran into this command was that if it worked 100% of the
			 * time, it could be very easily abused. So, to combat this, I set up a range of
			 * values that are executed (potentially) by a random number generator. We found
			 * that 1% was too low, and never happened, but that 2-3% is a good compromise.
			 */
			Random BeeRandom = new Random();
			int beeChance = BeeRandom.nextInt(100) + 1;
			// 2 chances for it to be executed out of 100.
			if (beeChance == 1 || beeChance == 2) {

				// Initialize document
				Document doc = null;
				// Initialize the boolean controllwing whether it's done or not
				boolean rotsDone = false;
				// Initialize indexer in where to start
				int indexer = 779;
				// Try catch block for the doc to connect to the website
				try {
					/*
					 * Much like with the snowday calculator, this crawls over the text of the page
					 * and throws it into the document.
					 */
					doc = Jsoup.connect(
							"http://www.script-o-rama.com/movie_scripts/a1/bee-movie-script-transcript-seinfeld.html")
							.get();
				} catch (IOException e) {
					// Prints stack trace.
					e.printStackTrace();
				}
				// Puts the doc into a string
				String beeScript = doc.text();
				// Creates the string to send back
				String toSend = beeScript;
				// For testing purposes: Shows the index of where the script starts
				// System.out.println(toSend.indexOf("According to all"));

				// This loop occurs while the indexer is not done or smaller than the limit
				while (!rotsDone && (indexer <= 56121)) {
					// If the indexer reachers 54000, send a custom message with the rest.
					if (indexer >= 54000) {
						System.out.println(toSend.substring(indexer, 56095));
						channel.sendMessage(toSend.substring(indexer, 56095)).complete();
						// Script is done.
						rotsDone = true;
					}
					// Else, while the indexer is below 54000, send messages in heaps of 2000
					// characters.
					else {
						System.out.println(toSend.substring(indexer, indexer + 2000));
						channel.sendMessage(toSend.substring(indexer, indexer + 2000)).complete();
						indexer += 2000;
					}
				}
				// Console log
				System.out.println("Beemovie completed @" + java.time.LocalDateTime.now());
			}
			// If the random num generator didn't hit a 1 or two, output that it didn't
			// occur.
			else {
				System.out.println("Beemovie NOT completed @" + java.time.LocalDateTime.now());
			}
		}
	}

	public void Textlog() {
		/*
		 * Command description: Logs text, images, and attatchments to the textlog
		 * channel. Used for safety and logging/archival purposes.
		 */

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
				temp_image = new File("/home/LeChickenSurAFez/JangleImages/" + file_name);
				// Download the file to /home/LeChickenSurAFez/JangleImages/file_name
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
		/*
		 * Command description: Outputs the current version of Jangle, along with author
		 * information & timestamp.
		 */
		if (command.equals("version")) {
			// Console log
			System.out.println("Jangle version " + version + " @ " + java.time.LocalDateTime.now());
			// Send message
			channel.sendMessage("Jangle version " + version + ": Chris Cardimen").complete();
		}
	}

	public void Console(String command) {
		/*
		 * Command description: Allows me to access Jangle and send messages through him
		 * using a Linux console. It can only be accessed by me, and will freeze him
		 * until some input is entered into the console, so be wary of that.
		 */
		if (command.equals("console") && author.getId().equals("180825351109214208")) {
			// Creates the textchannel general where the console message will be sent
			TextChannel general = event.getGuild().getTextChannelById("570627928564432897");
			// Take in a string to be sent to the channel
			String toConsole = scan.nextLine();
			// Send the message to general
			general.sendMessage(toConsole).complete();
		}

	}

	public void Rots(String command) {
		/*
		 * Command description: Outputs the entirety of the script for Star Wars Episode
		 * 3: Revenge of the Sith
		 */
		if (command.equals("rots")) {
			/*
			 * Similar to beemovie, we ran into the issue that if this command were to
			 * execute 100% of the time, it could be easily abused. So, I've created a
			 * random number generator to generate a number between 1 and 100.
			 */
			Random rotsRandom = new Random();
			int rotsChance = rotsRandom.nextInt(100) + 1;
			// The command will only execute when rotsChance == 1.
			if (rotsChance == 1) {
				// Initialize document
				Document doc = null;
				// Boolean to determine whether the script is done.
				boolean rotsDone = false;
				// Initialize the index to start at.
				int indexer = 757;
				// Try/catch statement to crawl over the script website text
				try {
					doc = Jsoup.connect("https://www.imsdb.com/scripts/Star-Wars-Revenge-of-the-Sith.html").get();
				} catch (IOException e) {
					// Print stack trace
					e.printStackTrace();
				}
				// Assign the document text to a sring
				String rotsScript = doc.text();
				// Assing the previous string to another string to send
				String toSend = rotsScript;
				// While loop that loops while the script is not done & index is smaller than
				// end
				while (!rotsDone && (indexer <= 176061)) {
					// If the index is at its end, finish it custom.
					if (indexer >= 174000) {
						System.out.println(toSend.substring(indexer, 175859));
						channel.sendMessage(toSend.substring(indexer, 175859)).complete();
						// Script is done
						rotsDone = true;
					}
					// Otherwise, will increment in 2000 character messages.
					else {
						System.out.println(toSend.substring(indexer, indexer + 2000));
						channel.sendMessage(toSend.substring(indexer, indexer + 2000)).complete();
						indexer += 2000;
					}
				}
				// Console log
				System.out.println("RoTS completed @" + java.time.LocalDateTime.now());
			}
			// Console log
			else {
				System.out.println("RoTS NOT completed @" + java.time.LocalDateTime.now());
			}
		}
	}

	public void Serial(String command) {
		/*
		 * Command description: When given a Elgin watch serial number, crawls/indexes
		 * therough the elgin national watch database to give important information on
		 * the aforementioned watch movement.
		 */
		if (command.length() >= 5 && command.substring(0, 6).equals("serial")) {
			String to_send_to_channel = "";
			String[] to_split = content.split(" ");
			String serial_num = to_split[1];
			// Initialize document
			Document doc = null;
			// Try/catch statement to crawl over the movement info text
			try {
				doc = Jsoup.connect("https://pocketwatchdatabase.com/search/result/elgin/" + serial_num).get();
			} catch (IOException e) {
				// Print stack trace
				e.printStackTrace();
			}

			// Assign the document text to a sring
			String watch_database = doc.wholeText();
			watch_database = watch_database.replaceAll("\t", "");
			// Initialize the index to start at.
			int indexer = watch_database.indexOf("Manufacturer:");
			if (indexer < 0) {
				to_send_to_channel += "This serial number does not exist in the database.";
			} else {
				to_send_to_channel += "```";
				String information_we_want = watch_database.substring(indexer,
						watch_database.indexOf("Data Confidence Rating"));
				for (int x = 0; x < information_we_want.indexOf("Data Research")
						|| x <= information_we_want.indexOf("U.S."); ++x) {
					char current = information_we_want.charAt(x);
					if (current >= 65 && current <= 90) {

						if (to_send_to_channel.charAt(to_send_to_channel.length() - 2) == ':') {
							to_send_to_channel = to_send_to_channel.substring(0, to_send_to_channel.length() - 1);
						}
						to_send_to_channel += " "
								+ information_we_want.substring(x, information_we_want.indexOf("\n", x)) + "\n";
						x = information_we_want.indexOf("\n", x) - 1;
					}
				}
				int count = 0;
				if (to_send_to_channel.charAt(to_send_to_channel.length() - 2) == ':') {
					for (int x = 0; x < to_send_to_channel.length(); ++x) {
						if (to_send_to_channel.charAt(x) == '\n') {
							++count;
						}
					}
					System.out.println(count);
					String temp = "";
					int max_count = count;
					count = 0;
					for (int x = 0; x < to_send_to_channel.length(); ++x) {

						if (count < max_count - 1) {
							temp += to_send_to_channel.charAt(x);
						}
						if (to_send_to_channel.charAt(x) == '\n') {
							++count;
						}
					}
					to_send_to_channel = temp;
					// to_send_to_channel = to_send_to_channel.substring(0,
					// to_send_to_channel.lastIndexOf('\n'));
				}
			}
			to_send_to_channel += "```";

			channel.sendMessage(to_send_to_channel).complete();

			// Console log
			System.out.println("Serial# completed @" + java.time.LocalDateTime.now());

		}
	}

	// TODO: Actually create an AR game
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

}
