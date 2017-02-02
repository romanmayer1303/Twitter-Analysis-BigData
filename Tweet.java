package bigdata;

import java.util.Date;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectStreamException;

public class Tweet implements Serializable{
	
	//private String user;
	//private String location;
	//private Date date;
	//private String text;
	private transient int numberOfHashtags;
	private ArrayList<String> hashtags;
	private boolean hasHashtags;
	//private int numberOfRetweets;
	//private int likes;
	//private int follower;
	//private String device;
	
	Tweet(){
		//user = "";
		//location = "";
		//date = new Date();
		//text = "";
		numberOfHashtags = 0;
		ArrayList<String> hashtags = new ArrayList<String>();
		hasHashtags = false;
		//numberOfRetweets = 0;
		//likes = 0;
		//follower = 0;
		//device = "";
	}
	
	//public String getUser(){
	//	return user;
	//}
	
	public ArrayList<String> getHashtags(){
		return hashtags;
	}
	
	//public void setUser(String u){
	//	user = u;
	//}
	
	public void setHashtags(ArrayList<String> s){
		hashtags = s;
		numberOfHashtags = hashtags.size();
	}
	
	public void addHashtag(String tag){
		hasHashtags = true;
		hashtags.add(tag);
		numberOfHashtags++;
	}
	
	public boolean hasHashtags(){
		return hasHashtags;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String tmp : hashtags){
			sb.append(tmp + "\t");
		}
		return sb.toString();
	}
	
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
