import twitter4j.*;
/**
 * @author Aashish Jain
 * @version September 20, 2015
 */
public class StatusUtils
{
	private static Status status;

	public StatusUtils(Status s)
	{
		status = s;
	}

	public static boolean checkToFollow()
	{
		String statusText = status.getText().toLowerCase();
		return statusText.contains("follow");
	}

	public static boolean checkToRetweet()
	{
		String statusText = status.getText().toLowerCase();
		return (statusText.contains("rt") || statusText.contains("retweet"));
	}

	public static String getPersonToFollow()
	{
		String[] words = breakString();

		for (int currentWord = 0; currentWord < words.length - 1; currentWord++)
		{
			String thisWord = words[currentWord];

			if (thisWord.equals("rt") || thisWord.equals("follow"))
			{
				return (words[currentWord + 1]);
			}
		}
		return null;
	}

	public static String[] breakString()
	{
		String s = status.getText().toLowerCase();
		String[] words = s.split("\\s+");
		for (int i = 0; i < words.length; i++)
		{
			words[i] = words[i].replaceAll("[^\\w]", "");
		}
		return words;
	}
}
