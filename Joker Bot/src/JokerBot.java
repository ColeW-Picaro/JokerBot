import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.util.Vector;


public class JokerBot {
	final static String consumerKey = "your_key_here";
	final static String consumerSecret = "your_key_here";
	final static String accessToken = "your_key_here";
	final static String accessTokenSecret = "your_key_here";
	final static int skipInterval = 3600000;
	final static String quotes = "/home/user/JokerBot/quotes.txt";
	final static int numQuotes = 0;
	
	public static void main (String[] args) throws TwitterException, IOException {
		
		//Log in to Twitter
		Twitter twitter = logIn ();
		
		//Open the file of quotes
		File file = new File (quotes);
		System.out.println("File Open Successful\n");
		Vector<String> quotes = new Vector<String> ();
		Scanner quotesFile = new Scanner (file);
	
		//This loop should continue forever
		while (true) {
			tweetCycle (twitter, quotesFile, quotes);
		}
	}
	public static void tweetCycle (Twitter twitter, Scanner quotesFile, Vector<String> quotes) throws IOException, TwitterException {
		
		Random interval = new Random ();
		String currentLine = null;
		int quoteCount = 0;
		
		//Add every quote to a vector
		for (int i = 0; i <= numQuotes; ++i)
			quotes.add (quotesFile.nextLine());
		
		//Tweet once per quote for hours equal to the number of quotes 
		while (!quotes.isEmpty()) {
			
			//Tweet and remove the tweet from the vector
			tweet (currentLine = quotes.get(interval.nextInt(numQuotes-quoteCount)), twitter);
			++quoteCount;
			quotes.remove(currentLine);
			currentLine = null;
			
			//Sleep for an hour
			try {
				Thread.sleep(skipInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static Twitter logIn () {
		
		//Log in to Twitter
		TwitterFactory twitterFactory = new TwitterFactory ();		
		Twitter twitter = twitterFactory.getInstance ();				
		twitter.setOAuthConsumer (consumerKey, consumerSecret);				
		twitter.setOAuthAccessToken (new AccessToken (accessToken, accessTokenSecret));		
		return twitter;
	}
	
	public static void tweet (String currentLine, Twitter twitter) throws TwitterException {
		
		//Create tweet
		StatusUpdate statusUpdate = new StatusUpdate (currentLine);
		Status status = twitter.updateStatus(statusUpdate);		
		printTweetInfo (status);
		
	}
	
	public static void printTweetInfo (Status status) {
		System.out.println("status.toString() = " + status.toString());
	    System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
	    System.out.println("status.getSource() = " + status.getSource());
	    System.out.println("status.getText() = " + status.getText());
	    System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
	    System.out.println("status.getUserMentionEntities() = " + Arrays.toString (status.getUserMentionEntities ()));
	    System.out.println();
		
	}
}
