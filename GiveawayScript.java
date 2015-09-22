import twitter4j.*;
import java.util.ArrayList;

/**
 * @author Aashish Jain
 * @version September 20, 2015
 *
 * Runs a script to find giveaways and retweet/follow if necessary
 */
public class GiveawayScript
{
	/**
	 * The twitterAccount to run the script on
	 */
	private static TwitterAccount tA;

	/**
	 * The number of refresh iterations
	 */
	private static final int REFRESH_ITERATIONS = 5;
	/**
	 * The sleep time after reaching proper refresh iterations
	 */
	private static final int SLEEP_TIME = 5000;
	/**
	 * The search term
	 */
	private static final String searchTerm = "giveaway";
	/**
	 * The twitterAccounts twitter instance
	 */
	private static Twitter twitter;

	/**
	 * Initializes everything necessary and runs script
	 * @param args
	 * @throws twitter4j.TwitterException
	 * @throws java.lang.InterruptedException
	 */
	public static void main(String[] args) throws twitter4j.TwitterException,
			  java.lang.InterruptedException
	{
		initializeTwitterAccount();
		twitter = tA.getTwitter();
		runScript();
	}

	/**
	 * Initializes the twitterAccount with the proper keys
	 */
	public static void initializeTwitterAccount()
	{
		String cK = "";
		String cS = "";
		String aT = "";
		String aTS = "";
		tA = new TwitterAccount(cK, cS, aT, aTS);
	}

	/**
	 * Runs the giveaway script
	 * @throws twitter4j.TwitterException
	 * @throws java.lang.InterruptedException
	 */
	public static void runScript() throws twitter4j.TwitterException, java
			  .lang.InterruptedException
	{
		int currentIteration = 0;

		while (currentIteration < REFRESH_ITERATIONS)
		{

			ArrayList<Status> searches = getSearch(searchTerm);

			if (searches.size() == 0)
			{
				System.out.println("No tweets found with current search term " +
						  searchTerm);
			}

			for (Status status : searches)
			{
				/*
				Prints out the search
				 */
				System.out.println("@" + status.getUser().getScreenName() + ":" +
						  status.getText());

				StatusUtils sU = new StatusUtils(status);
				boolean follow = sU.checkToFollow();
				boolean retweet = sU.checkToRetweet();

				if (!follow && !retweet)
				{
					System.out.println("IGNORE");
					continue;
				}

				if (follow)
				{
					try
					{
						twitter.createFriendship(sU.getPersonToFollow());
						System.out.println("FOLLOWED");
						fixFollowers();
					}
					catch (Exception e)
					{
						System.out.println("Threw error");
					}
				}

				if (retweet)
				{
					try
					{
						twitter.retweetStatus(status.getId());
						System.out.println("RETWEETED");
					}
					catch (Exception e)
					{
						System.out.println("Threw error");
					}
				}

				currentIteration++;

				if (currentIteration == REFRESH_ITERATIONS)
				{
					System.out.println("sleeping");
					Thread.sleep(SLEEP_TIME);
					currentIteration = 0;
				}

			}
		}
	}

	/**
	 * Fixes followers if account goes over the limit
	 * @throws twitter4j.TwitterException
	 */
	public static void fixFollowers() throws twitter4j.TwitterException
	{

		long[] friends = twitter.getFriendsIDs(-1).getIDs();

		if (friends.length > 4995)
		{
			for (int i = 0; i < friends.length / 2; i++)
			{
				twitter.destroyFriendship(friends[i]);
			}
		}
	}

	/**
	 * Searches twitter for the given search term.
	 * @param searchTerm The search term to search twitter for.
	 * @return An ArrayList of the Status' searches
	 * @throws twitter4j.TwitterException exception for search
	 */
	public static ArrayList<Status> getSearch(String searchTerm) throws twitter4j
			  .TwitterException
	{
		Query q = new Query(searchTerm);
		QueryResult result = twitter.search(q);
		return (ArrayList<Status>) result.getTweets();
	}
}
