package bigdata;
import java.lang.StringBuilder;
import java.io.Serializable;
import java.util.ArrayList;

public class Tweet implements Serializable{
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String location;
	private String date;
	private String text;
	private ArrayList<String> hashtags;
	private int numberOfRetweets;
	private int numberOfLikes;
	private int numberOfFollowers;
	private String device;
	
	Tweet(){
		location = "";
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
}
