package bigdata;

import org.apache.spark.api.java.JavaSparkContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.spark.api.java.JavaRDD;

import org.apache.spark.SparkConf;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;


public class Twitter2{

	public static void main(String[] args){
		SparkConf conf = new SparkConf().setAppName("TwitterConvert");
		JavaSparkContext sc = new JavaSparkContext(conf);
		ArrayList<String> hashtagsFromFile = new ArrayList<String>();
		
		
		if(args.length > 2){
			try{
				BufferedReader fis = new BufferedReader(new FileReader(args[1]));
			
				StringTokenizer tok;
				String line = null;
			
				while((line = fis.readLine()) != null){
					tok = new StringTokenizer(line, ", ");
					while(tok.hasMoreTokens()){
						hashtagsFromFile.add(tok.nextToken());
					}
				}

			}
			catch(FileNotFoundException e){
				System.out.println("Hashtagfile " + args[1] + " not found. Continuing without importance-sampling of hashtags.");
				hashtagsFromFile = new ArrayList<String>();
			}
			catch(IOException e){
				System.out.println("IOException while reading hashtagfile " + args[1] + ". Continuing without importance-sampling of hashtags.");
				hashtagsFromFile = new ArrayList<String>();
			}
		}
		final ArrayList<String> importantHashtags = hashtagsFromFile;
	
		JavaRDD<Tweet> data = sc.textFile(args[0]).cache().map(line ->{
					//private static final long serialVersionUID = 1L;				
						JSONObject tweet = new JSONObject(line);
						JSONObject entities = null;
						JSONArray hashtags = null;
						JSONObject hashtag = null;
						String date = "";
						String[] dateA;
						StringBuilder sbTweet = new StringBuilder();
						ArrayList<String> sbHashtags = new ArrayList<>();
						Tweet resultTweet = new Tweet();
						
						//Get hashtags from Tweets
						if(tweet.has("entities")){
							entities = tweet.getJSONObject("entities");
							if(entities.has("hashtags")){
								hashtags = entities.getJSONArray("hashtags");
								if(hashtags.length()>=1){
									//Hashtags auslesen
									for(int i = 0; i<hashtags.length();i++){
										hashtag = hashtags.getJSONObject(i);
										sbHashtags.add(hashtag.getString("text").toLowerCase());
									}
									resultTweet.setHashtags(sbHashtags);
								}	
							}
						}
						
						//Datum auslesen
						if(tweet.has("created_at")){
							date = tweet.getString("created_at");
							dateA = date.split("\\s+");
							sbTweet.append(dateA[5]);
							sbTweet.append(getMonth(dateA[1]));
							sbTweet.append(dateA[2]);
							resultTweet.setDate(sbTweet.toString());
						}
						
						//Text auslesen
						if(tweet.has("text")){
							resultTweet.setText(tweet.getString("text").toLowerCase().replace("\n", "").replace("\r",""));
						}
						
						return resultTweet;
						
					});
		JavaRDD<Tweet> tweets = data.repartition(1).filter(tweet -> (
				(tweet.getDate().length()>4)
				&& (tweet.containsHashtag(importantHashtags) ||
				tweet.getText().contains("make america") ||
				tweet.getText().contains("makeamerica") ||
				tweet.getText().contains("crooked") ||
				tweet.getText().contains("your account") ||
				tweet.getText().contains("never")
				))
		);
		
		tweets.saveAsTextFile(args[args.length-1]);

		
		
		
	}
	
	//Kb auf date format, Daten wie 20121124 kann man perfekt ordnen
	public static String getMonth(String month){
		switch(month){
			case "Jan": return "01";
			case "Feb": return "02"; 
			case "Mar": return "03"; 
			case "Apr": return "04"; 
			case "May": return "05"; 
			case "Jun": return "06";
			case "Jul": return "07";
			case "Aug": return "08"; 
			case "Sep": return "09";
			case "Oct": return "10"; 
			case "Nov": return "11"; 
			case "Dec": return "12";
			default: return "00";
			}
		}
}
