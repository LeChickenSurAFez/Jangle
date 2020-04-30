import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Commands {
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

	public Commands(Message mess, String cont, MessageChannel chan, User aut, MessageReceivedEvent even) {
		message = mess;
		content = cont;
		channel = chan;
		author = aut;
		event = even;
		prefix = ">";
		if (content.length() > 0) {
			command = content.substring(1, content.length()).toLowerCase();
		}
		else {
			command = "";
		}
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
		version = "3.0.0";
		scan = new Scanner(System.in);
		user_vc = event.getMember().getVoiceState().getChannel();
		audioManager = event.getGuild().getAudioManager();
	}

	public void WhenInputReceived() {
		Textlog();
		if (content.length() > 0) {
			DadJoke();
			Beemovie();
			Obama();
			if (content.startsWith(prefix)) {
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
			}
		}
	}

	/* Below are all methods relating to commands */
	public void Help(String command) {
		if (command.equals("help")) {
			channel.sendMessage(
					"```Commands:\n>ping\n>pizzatime?\n>tictactoe\n>jingle\n>beemovie (please be careful with this one)"
							+ "\n>luckynum\n>factor\n>peen\n>flip\n>random\n>version\n>info\n>rots```")
					.complete();
			System.out.println("Help completed @" + java.time.LocalDateTime.now());
		}
	}

	public void Info(String command) {
		if (command.equals("info")) {
			channel.sendMessage("```Commands:\n>ping [Jangle's ms response time]\n>pizzatime?\n>tictactoe\n>"
					+ "jingle\n>beemovie [2% odds to print bee movie] \n>" + "luckynum [gives a number 1-100]\n"
					+ ">factor ['>factor 1 2 3' factors x²+2x+3]" + "\n"
					+ ">peen ['>peen @name' to give penis length of @name]\n" + ">flip [flips a coin]\n" + ">random\n"
					+ ">version\n" + ">rots [sends the entire script of Star Wars: Episode III]```").complete();
			System.out.println("Info completed @" + java.time.LocalDateTime.now());
		}
	}

	public void Random(String command) {
		if (command.equals("random")) {
			Random randomNum = new Random();
			int random = randomNum.nextInt(100) + 1;
			channel.sendMessage("Random number: " + random).complete();
			System.out.println("Random completed @" + java.time.LocalDateTime.now());

		}
	}

	public void Ping(String command) {
		if (command.equals("ping")) {
			channel.sendMessage("Pong! " + event.getJDA().getGatewayPing() + "ms").complete();
			System.out.println("Pong completed @" + java.time.LocalDateTime.now());
		}

	}

	public void Flip(String command) {
		if (command.equals("flip")) {
			header = 0;
			tailer = 0;
			Random flipNum = new Random();
			int headOrTail = flipNum.nextInt(2);

			if (headOrTail == 0) {
				// channel.sendMessage("Heads!").complete();
				try {
					flipData.writeToFile("Heads!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (headOrTail == 1) {
				// channel.sendMessage("Tails!").complete();
				try {
					flipData.writeToFile("Tails!");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			try {
				htCount = flipFile.OpenFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (int read = 0; read < htCount.length; read++) {
				if (htCount[read].equals("Heads!")) {
					header++;
				}
				if (htCount[read].equals("Tails!")) {
					tailer++;
				}
			}
			if (headOrTail == 0) {
				channel.sendMessage("Heads!\n" + "```Total Heads: " + header + "\nTotal Tails: " + tailer + "\n"
						+ ((header / (header + tailer) * 100) + "% Heads\n"
								+ ((tailer / (header + tailer) * 100) + "% Tails```")))
						.complete();

			} else if (headOrTail == 1) {
				channel.sendMessage("Tails!\n" + "```Total Heads: " + header + "\nTotal Tails: " + tailer + "\n"
						+ ((header / (header + tailer) * 100) + "% Heads\n"
								+ ((tailer / (header + tailer) * 100) + "% Tails```")))
						.complete();
			}
			System.out.println("Flip completed @" + java.time.LocalDateTime.now());

		}

	}

	public void Peen(String command) {
		// dick size works
		// -----------------------------------------------------------------------------------------------------------
		if (command.length() >= 4) {
			command = command.substring(0, 4);
		} else {
			command = "not_peen";
		}
		if (command.equals("peen")) {

			String[] peenPeople = content.split(" ");
			if (peenPeople.length == 2) {
				authorSend = peenPeople[1].replaceAll("!", "");
				// System.out.println(authorSend);
			} else {
				authorSend = author.getAsMention().toString();
			}

			Random randoshin = new Random();
			int peenSize = randoshin.nextInt(18) + 1;
			try {
				String[] aryLines = peen_file.OpenFile();
				for (int y = 0; y < aryLines.length; y++) {
					correctArray += " " + aryLines[y];
				}
				String[] whatWeBaseItOff = correctArray.split(" ");
				// System.out.println(Arrays.toString(whatWeBaseItOff));
				// System.out.println(author.getId());

				Boolean thisIsSwitch = true;
				Boolean biggerSwitch = true;
				// System.out.println(authorSend);
				while (biggerSwitch) {
					if (thisIsSwitch) {
						for (int x = 0; x < whatWeBaseItOff.length; x++) {
							if (whatWeBaseItOff[x].equals("<@308761110394306560>")
									&& authorSend.replace("!", "").equals("<@308761110394306560>")) {
								channel.sendMessage("<@308761110394306560> has a twenty foot long schlong.").complete();
								System.out.println("Size @" + java.time.LocalDateTime.now());
								x = whatWeBaseItOff.length;
								thisIsSwitch = false;
								biggerSwitch = false;
							} else if (whatWeBaseItOff[x].equals("<@180825351109214208>")
									&& authorSend.replace("!", "").equals("<@180825351109214208>")) {
								channel.sendMessage("<@180825351109214208> has a ten foot long schlong.").complete();
								System.out.println("Size @" + java.time.LocalDateTime.now());
								x = whatWeBaseItOff.length;
								thisIsSwitch = false;
								biggerSwitch = false;
							}
						}
						if (thisIsSwitch) {
							for (int ab = 0; ab < whatWeBaseItOff.length; ab++) {
								if (whatWeBaseItOff[ab].equals(authorSend)) {
									channel.sendMessage(whatWeBaseItOff[ab] + "'s penis size is "
											+ whatWeBaseItOff[ab + 1] + " inches.").complete();
									System.out.println("Size @" + java.time.LocalDateTime.now());
									ab = whatWeBaseItOff.length;
									thisIsSwitch = false;
									biggerSwitch = false;
								}

							}
						}
					}

					if (thisIsSwitch) {
						printed = (authorSend + " " + peenSize);
						peen_data.writeToFile(printed);
						System.out.println("Something written @" + java.time.LocalDateTime.now());
						aryLines = peen_file.OpenFile();
						for (int y = 0; y < aryLines.length; y++) {
							correctArray += " " + aryLines[y];
						}
						// System.out.println(Arrays.toString(whatWeBaseItOff));
						whatWeBaseItOff = correctArray.split(" ");

					}
				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}

		}
		// -------------------------------------------------------------------------------------------------------------------------------
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
		//Textlog channel creation
		TextChannel textlog = event.getGuild().getTextChannelById("531953276816719874");
		/*What's going on here is that I've created an arrayList of the attachments
		 * found within a given message. What this will allow me to do is go
		 * through each attachment and handle it separately*/
		List<Attachment> image = message.getAttachments();
		//Creating the attachment, which will get re-assigned for each index
		Attachment image_file;
		//determining whether or not there is anything in the arrayList
		boolean contains_image = false;
		//Initialize file_name
		String file_name = "";
		//Initialize the file we'll be working with
		File temp_image = null;
		//If there are attachments, proceed
		if (image.size() > 0) {
			//It does contain an image
			contains_image = true;
			//This will loop through each index of the arrayList and handle each
			//attachment separately.
			for (int x = 0; x < image.size(); ++x) {
				//Assign the image file to index 'x'
				image_file = image.get(x);
				//Assign file name
				file_name = image_file.getFileName();
				//Create the temp file
				temp_image = new File("C:\\JangleImages\\" + file_name);
				//Download the file to C:\\JangleImages\\file_name
				image_file.downloadToFile(temp_image);
				/*The next block of code, the try/catch statement deals with
				 * handling the image once it's been downloaded*/
				try {
					/*Very important. A problem that I ran into was that Jangle
					 * would try to send the image before it's even been downloaded,
					 * so to mitigate that I set the thread to sleep for 1000ms,
					 * or 1 second to allow the image time to download, thus negating
					 * the problems that were occuring.*/
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//Print stack trace if exception
					e.printStackTrace();
				}
				/*Still in the for loop, and the image is downloaded. Now determining
				 * how to handle the image that has been downloaded*/
				if (contains_image) {
					//If there's an image, send it to the textlog.
					textlog.sendFile(temp_image).complete();
					try {
						//Then, if it exists, delete the image using its given path
						Files.deleteIfExists(temp_image.toPath());
					} catch (IOException e) {
						//Print stack trace if exception
						e.printStackTrace();
					} 
					//Send identification information to the textlog.
					textlog.sendMessage("```\n" + "The above image ^" + "\nAuthor: " + author + "\n" + "Channel: "
							+ event.getChannel() + "\nTime: " + java.time.LocalDateTime.now() + "```").complete();
					System.out.println("Image sent to textlog @" + java.time.LocalDateTime.now());
				}
			}
		}
		//If there is accompanying text, or any text in general, enact this statement
		//This also will always apply if there's content, regardless of whether there
		//is an image or not.
		if (content.length() > 0) {
			//Send identification information to the textlog.
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

}
