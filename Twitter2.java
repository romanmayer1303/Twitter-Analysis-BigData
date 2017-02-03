package bigdata;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.spark.api.java.JavaRDD;

import org.apache.spark.SparkConf;

public class Twitter2{

	public static void main(String[] args){
		SparkConf conf = new SparkConf().setAppName("TwitterConvert");
		JavaSparkContext sc = new JavaSparkContext(conf);
	
		JavaRDD<String> data = sc.textFile(args[0]).cache().map(
				new Function<String, String>(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public String call(String line) throws Exception{
				
						JSONObject tweet = new JSONObject(line);
						JSONObject entities = null;
						JSONArray hashtags = null;
						JSONObject hashtag = null;
						String date = "";
						String[] dateA;
						StringBuilder sbTweet = new StringBuilder();
						StringBuilder sbHashtags = new StringBuilder();
						Tweet resultTweet = new Tweet();
						
						//Nur Tweets mit mindestens einem Hashtag werden beachtet hier
						//Ja, die hunderten If abfragen müssen sein, da sonst Errors kommen
						if(tweet.has("entities")){
							entities = tweet.getJSONObject("entities");
							if(entities.has("hashtags")){
								hashtags = entities.getJSONArray("hashtags");
								if(hashtags.length()>=1){
									
									//Hashtags auslesen
									for(int i = 0; i<hashtags.length();i++){
										hashtag = hashtags.getJSONObject(i);
										sbHashtags.append(hashtag.getString("text"));
										if(i<hashtags.length()-1){
											sbHashtags.append(",");
										}
									}
									resultTweet.setHashtags(sbHashtags.toString());
									
									//Text auslesen
									if(tweet.has("text")){
										resultTweet.setText(tweet.getString("text"));
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
									
									//Return vom endgültigem Tweet, noch als String
									return resultTweet.toString();
								}	
							}
						}
						return "";
					}
				});
		data.repartition(1).filter(tweet -> tweet.length()>0).saveAsTextFile(args[1]);

		
		
	}
	
	//Kb auf date format, Daten wie 20121124 kann man perfekt ordnen
	public static String getMonth(String month){
		switch(month){
			case "Jan": return "01";
			case "Feb": return "02"; 
			case "Mar": return "03"; 
			case "Apr": return "04"; 
			case "May": return "05"; 
			case "June": return "06"; 
			case "July": return "07"; 
			case "Aug": return "08"; 
			case "Sept": return "09"; 
			case "Oct": return "10"; 
			case "Nov": return "11"; 
			case "Dec": return "12";
			default: return "01";
			}
		}
}