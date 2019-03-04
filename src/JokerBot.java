/*
  I can't look at the docs because the twitter4j website doesn't work

  pleease help
*/

// Local imports
import twitter4j.*;
import twitter4j.auth.AccessToken;
// System imports
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JokerBot {
    final static Path quotes = Paths.get ("quotes.txt");
    final static Path oauth = Paths.get ("oauth.txt");
    static boolean errorFlag = false;
    
    public static void main (String[] args) throws TwitterException, IOException {

	Vector<String> oauthKeys = new Vector<String> ();
	Vector<String> quotesList = new Vector<String> ();
	// Open oauth file and quotes file
	try {
	    Stream<String> oauthFile = Files.lines (oauth);
	    oauthFile.forEach (key -> oauthKeys.add (key));

	    Stream<String> quotesFile = Files.lines (quotes);
	    quotesFile.forEach (quote -> quotesList.add (quote));
	} catch (IOException e) {
	    System.out.println (e);
	    errorFlag = true;
	}	
	
	if (!errorFlag) {
	    Twitter twitter = logIn (oauthKeys);
	    String tweet = getQuote (quotesList);
	    sendTweet (tweet, twitter);
	}	
    }

    // Login to twitter
    public static Twitter logIn (Vector<String> oauth) {	
	TwitterFactory twitterFactory = new TwitterFactory ();		
	Twitter twitter = twitterFactory.getInstance ();				
	twitter.setOAuthConsumer (oauth.get (0), oauth.get (1));
	AccessToken accessToken = new AccessToken (oauth.get (2), oauth.get (3));
	twitter.setOAuthAccessToken (accessToken);
	return twitter;
    }

    // Get a quote from the quote vector
    public static String getQuote (Vector quotes) {
	Random gen = new Random ();
	return quotes.get (gen.nextInt (quotes.size ())).toString ();
    }

    // Send a tweet
    public static void sendTweet (String tweet, Twitter twitter) throws TwitterException {
       	StatusUpdate statusUpdate = new StatusUpdate (tweet);
	Status status = twitter.updateStatus(statusUpdate);			
    }
}
