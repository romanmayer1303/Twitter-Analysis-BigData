package bigdata;
import java.lang.StringBuilder;
import java.io.Serializable;

public class Tweet implements Serializable{
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private String user;
	//private String location;
	private String date;
	private String text;
	private transient int numberOfHashtags;
	private String hashtags;
	private boolean hasHashtags;
	private int numberOfRetweets;
	//private int likes;
	//private int follower;
	//private String device;
	
	Tweet(){
		//user = "";
		//location = "";
		date = "";
		text = "";
		numberOfHashtags = 0;
		hashtags = "";
		hasHashtags = false;
		//numberOfRetweets = 0;
		//likes = 0;
		//follower = 0;
		//device = "";
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("date: " + date + ", ");
		sb.append("text: " + text + ", ");
		sb.append("hashtags: " + hashtags);
		return sb.toString();
		
	}
	
	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}
	
	public String getHashtags(){
		return hashtags;
	}
	
	//public void setUser(String u){
	//	user = u;
	//}
		
	
	public boolean hasHashtags(){
		return hasHashtags;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumberOfHashtags() {
		return numberOfHashtags;
	}

	public void setNumberOfHashtags(int numberOfHashtags) {
		this.numberOfHashtags = numberOfHashtags;
	}

	public int getNumberOfRetweets() {
		return numberOfRetweets;
	}

	public void setNumberOfRetweets(int numberOfRetweets) {
		this.numberOfRetweets = numberOfRetweets;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isHasHashtags() {
		return hasHashtags;
	}

	public void setHasHashtags(boolean hasHashtags) {
		this.hasHashtags = hasHashtags;
	}

	/*public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String tmp : hashtags){
			sb.append(tmp + "\t");
		}
		return sb.toString();
	}*/
	
	/*
	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		out.defaultWriteObject(numberOfHashtags);
		for(int i = 0; i < numberOfHashtags; ++i){
			out.defaultWriteObject(hashtags.get(i));
    	}
    }
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		
		numberOfHashtags = in.defaultReadObject();
	    for(int i = 0; i < numberOfHashtags; ++i){
	    	hashtags.add(in.defaultReadObject());
	     }
    }
	
	private void readObjectNoData() throws ObjectStreamException{
    	return;
    }
    */
}
