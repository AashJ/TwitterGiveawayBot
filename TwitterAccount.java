import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Aashish Jain
 * @version September 20, 2015
 *
 * A Twitter account holds authentication keys from twitter and is also an
 * instance of a twitter (twitter4j).
 */
public class TwitterAccount
{
	//consumer key from twitter api
	private String cKey;
	//consumer secret from twitter api
	private String cSecret;
	//authentication token from twitter api
	private String aToken;
	//authentication token secret from twitter api
	private String aTokenSecret;
	//this accounts twitter instance
	private Twitter twitter;

	/**
	 * The constructor for this TwitterAccount.
	 * @param cK the cKey
	 * @param cS the cSecret
	 * @param aT the aToken
	 * @param aTS the aTokenSecret
	 */
	public TwitterAccount(String cK, String cS, String aT, String aTS)
	{
		setcKey(cK);
		setcSecret(cS);
		setaToken(aT);
		setaTokenSecret(aTS);
		createTwitter();
	}

	/**
	 * Sets the twitter instance variable from the authentication keys already
	 * gathered.
	 */
	public void createTwitter()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				  .setOAuthConsumerKey(cKey)
				  .setOAuthConsumerSecret(cSecret)
				  .setOAuthAccessToken(aToken)
				  .setOAuthAccessTokenSecret(aTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	/**
	 * Sets the cKey to what is specified.
	 * @param cK sets the cKey to this string.
	 */
	public void setcKey(String cK)
	{
		cKey = cK;
	}

	/**
	 * Sets the cSecret to what is specified;
	 * @param cS sets the cSecret to this string.
	 */
	public void setcSecret(String cS)
	{
		cSecret = cS;
	}

	/**
	 * sets the aToken to what is specified.
	 * @param aT sets the aToken to this string.
	 */
	public void setaToken(String aT)
	{
		aToken = aT;
	}

	/**
	 * Sets the aTokenSecret to what is specified.
	 * @param aTS sets the aTokenSecret to this string.
	 */
	public void setaTokenSecret(String aTS)
	{
		aTokenSecret = aTS;
	}

	public Twitter getTwitter()
	{
		return twitter;
	}


}
