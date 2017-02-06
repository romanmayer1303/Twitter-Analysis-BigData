package bigdata;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwitterMessages{

	public static void main(String[] args){
	
		SparkConf conf = new SparkConf().setAppName("TwitterMessageEvolution");
		@SuppressWarnings("resource")
		JavaSparkContext sc = new JavaSparkContext(conf);			
		
		JavaRDD<Tweet> tweets = sc.textFile(args[0]).cache().map(line->{
			String[] tweet = line.split("\t");
			if(tweet.length >1){
				Tweet resultTweet = new Tweet();
				resultTweet.setDate(tweet[0]);
				resultTweet.setText(tweet[1]);
				//resultTweet.setNumberOfRetweets(Integer.parseInt(tweet[3]));
				//resultTweet.setNumberOfLikes(Integer.parseInt(tweet[4]));
				//resultTweet.setNumberOfFollowers(Integer.parseInt(tweet[5]));
				return resultTweet;
			}
			return null;			
		});

		//MAGA
		//
		JavaPairRDD<String, Integer> MAGA = tweets.flatMapToPair(
				new PairFlatMapFunction<Tweet, String, Integer>(){
					private static final long serialVersionUID = 1L;
					public Iterator<Tuple2<String, Integer>> call (Tweet tweet){
						String text = tweet.getText();
						String key1;
						List<Tuple2<String, Integer>> result = new ArrayList<>();
												
						//Different Hashtags
						
						key1 = StringUtils.substringBetween(text, "makeamerica", "again");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						
						key1 = StringUtils.substringBetween(text, "make america ", " ");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						
						return result.iterator();	
					}
				}).reduceByKey((x,y) -> x+y);
		MAGA.saveAsTextFile(args[1] + "/magaV");	
		
		
		//CROOKED
		//
		JavaPairRDD<String, Integer> crooked = tweets.flatMapToPair(
				new PairFlatMapFunction<Tweet, String, Integer>(){
					private static final long serialVersionUID = 1L;
					public Iterator<Tuple2<String, Integer>> call (Tweet tweet){
						String text = tweet.getText();
						String key1;
						List<Tuple2<String, Integer>> result = new ArrayList<>();
						
						//Different Hashtags
						key1 = StringUtils.substringBetween(text, "crooked ", " ");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						key1 = StringUtils.substringBetween(text, "crooked", " ");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						
						return result.iterator();	
					}
				}).reduceByKey((x,y) -> x+y);
		crooked.saveAsTextFile(args[1] + "/crooked");
		
		
		//NEVER
		//
		JavaPairRDD<String, Integer> never = tweets.flatMapToPair(
				new PairFlatMapFunction<Tweet, String, Integer>(){
					private static final long serialVersionUID = 1L;
					public Iterator<Tuple2<String, Integer>> call (Tweet tweet){
						String text = tweet.getText();
						String key1;
						List<Tuple2<String, Integer>> result = new ArrayList<>();
						
						//Different Hashtags
						key1 = StringUtils.substringBetween(text, "never ", " ");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						key1 = StringUtils.substringBetween(text, "never", " ");
						if(key1 != null) result.add(new Tuple2<>(key1 + "," + tweet.getDate(), 1));
						
						return result.iterator();	
					}
				}).reduceByKey((x,y) -> x+y);
		never.saveAsTextFile(args[1] + "/never");
		
		
		
	}
	
	
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
