/*Consumer Key (API Key)	lKHHOM44JTDNTE5jvL0GgwQrr
 * Consumer Secret (API Secret)	0H3uzgh0MwYLPhfHm3LhQLimom9oy2a76m5DxjqOYjQkSpGVF8
 * Access Token 	952099564940660737-8Zt53K78qIZSJbJtmBeyvzZjglfTVbm
 * Access Token Secret	cbfW9GbfdXRCNol2F0qkPJO7VPHq03t81hMts1GQHOazI
 * */
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;


public class JokerBot {
	final static String consumerKey = "lKHHOM44JTDNTE5jvL0GgwQrr";
	final static String consumerSecret = "0H3uzgh0MwYLPhfHm3LhQLimom9oy2a76m5DxjqOYjQkSpGVF8";
	final static String accessToken = "952099564940660737-8Zt53K78qIZSJbJtmBeyvzZjglfTVbm";
	final static String accessTokenSecret = "cbfW9GbfdXRCNol2F0qkPJO7VPHq03t81hMts1GQHOazI";
	final static int skipInterval = 600000;
	final static String quotes = "F:\\Eclipse\\Joker Bot\\quotes.txt";
	
	public static void main (String[] args) throws TwitterException {
		
		//Log in to Twitter
		TwitterFactory twitterFactory = new TwitterFactory ();
		
		Twitter twitter = twitterFactory.getInstance ();
		
		twitter.setOAuthConsumer (consumerKey, consumerSecret);
		
		twitter.setOAuthAccessToken (new AccessToken (accessToken, accessTokenSecret));
		
	
	
		try {
			
			File file = new File (quotes);
			System.out.println("File Open Successful");
			Scanner quotesFile = new Scanner (file);	
			//br.mark(0);
			
			while (true) {

				//Create tweet
				StatusUpdate statusUpdate = new StatusUpdate (quotesFile.nextLine());
		
				Status status = twitter.updateStatus(statusUpdate);
				
				System.out.println("status.toString() = " + status.toString());
			    System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
			    System.out.println("status.getSource() = " + status.getSource());
			    System.out.println("status.getText() = " + status.getText());
			    System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
			    System.out.println("status.getUserMentionEntities() = " + Arrays.toString (status.getUserMentionEntities ()));
				
				
				//Reset the buffered reader to the first line
				//br.reset();
				
				//Sleep for 10 minutes
				try {
					Thread.sleep(skipInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}
}