package bigdata;
import java.lang.StringBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.Exception;

public class Tweet implements Serializable{
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private String location;
	private String date;
	private String text;
	private ArrayList<String> hashtags;
	private int numberOfRetweets;
	private int numberOfLikes;
	private int numberOfFollowers;
	private String device;
	
	Tweet(){
		//location = "";
		date = "";
		text = "";
		hashtags = new ArrayList<String>();
		numberOfRetweets = 0;
		numberOfLikes = 0;
		numberOfFollowers = 0;
		device = "";
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(date + "\t");
		sb.append(text + "\t[ ");
		
		StringBuilder hsb = new StringBuilder();
		for(String hash : hashtags){
			hsb.append(hash + " ");
		}
		hsb.append("]\t");
		sb.append(hsb.toString());
		sb.append(Integer.toString(numberOfRetweets) + "\t");
		sb.append(Integer.toString(numberOfLikes) + "\t");
		sb.append(Integer.toString(numberOfFollowers) + "\t");
		sb.append(device);
		return sb.toString();
		
	}
	
	public void setHashtags(ArrayList<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	public ArrayList<String> getHashtags(){
		return hashtags;
	}
	
	public ArrayList<String> getHashtagsToLower(){
		ArrayList<String> tmp = new ArrayList<String>();
		for(String hashtag: hashtags){
			tmp.add(hashtag.toLowerCase());
		}
		return tmp;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumberOfRetweets() {
		return numberOfRetweets;
	}

	public void setNumberOfRetweets(int numberOfRetweets) {
		this.numberOfRetweets = numberOfRetweets;
	}
	
	public int getNumberOfLikes() {
		return numberOfLikes;
	}
	
	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public void setNumberOfFollowers(int numberOfFollowers) {
		this.numberOfFollowers = numberOfFollowers;
	}
	
	public int getNumberOfFollowers() {
		return numberOfFollowers;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
	public static Tweet readStringToTweet(String s) throws StringToTweetConversionException{
		Tweet tmp = new Tweet();
		StringTokenizer tok = new StringTokenizer(s, "\t");
		
		if(tok.hasMoreTokens()){
			tmp.setDate(tok.nextToken());
		}
		else{
			throw new StringToTweetConversionException("No date found.");
		}
		
		if(tok.hasMoreTokens()){
			tmp.setText(tok.nextToken());
		}
		else{
			throw new StringToTweetConversionException("No text found.");
		}
		
		if(tok.hasMoreTokens()){
			ArrayList<String> hashtagArray = new ArrayList<String>();
			StringTokenizer tok2 = new StringTokenizer(tok.nextToken());
			while(tok2.hasMoreTokens()){
				String tag = tok2.nextToken();
				if(!tag.equals("[") && !tag.equals("]")){
					hashtagArray.add(tag);
				}
			}
			tmp.setHashtags(hashtagArray);
		}
		else{
			throw new StringToTweetConversionException("No hashtag-array found.");
		}
		
		if(tok.hasMoreTokens()){
			tmp.setNumberOfRetweets(Integer.parseInt(tok.nextToken()));
		}
		else{
			throw new StringToTweetConversionException("No number of retweets found.");
		}
		
		if(tok.hasMoreTokens()){
			tmp.setNumberOfLikes(Integer.parseInt(tok.nextToken()));
		}
		else{
			throw new StringToTweetConversionException("No number of likes found.");
		}
		
		if(tok.hasMoreTokens()){
			tmp.setNumberOfFollowers(Integer.parseInt(tok.nextToken()));
		}
		else{
			throw new StringToTweetConversionException("No number of followers found.");
		}
		/*
		if(tok.hasMoreTokens()){
			tmp.setDevice(tok.nextToken());
		}
		else{
			throw new StringToTweetConversionException("No device found.");
		}
		*/
		
		return tmp;
	}
}

class StringToTweetConversionException extends Exception {
   public StringToTweetConversionException(String msg){
      super(msg);
   }
}
