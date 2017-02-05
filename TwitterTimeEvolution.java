package bigdata;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import org.apache.spark.SparkConf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class TwitterTimeEvolution{

	public static void main(String[] args){
	
		SparkConf conf = new SparkConf().setAppName("TwitterTimeEvolution");
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
		//final ArrayList<String> importantHashtags = hashtagsFromFile;
		
		JavaRDD<String> data = sc.textFile(args[0]).cache();
		JavaRDD<Tweet> tweets = data.map(s -> Tweet.readStringToTweet(s)).cache();

		/*
		JavaPairRDD<String, Integer> hashtagTime = tweets.flatMapToPair(t -> {
		
				ArrayList<Tuple2<String,Integer>> ht = new ArrayList<Tuple2<String,Integer>>();
				for(String hashtag : t.getHashtags()){
					ht.add(new Tuple2<String, Integer>(hashtag, Integer.parseInt(t.getDate())));
				} 
				
				return Arrays.asList(ht).iterator();	
				});
		*/
		
		for(String hashtag: hashtagsFromFile){
			JavaPairRDD<Integer,Integer> tmp = tweets.filter(t -> t.getHashtagsToLower().contains(hashtag.toLowerCase())).mapToPair(t -> new Tuple2<Integer, Integer>(Integer.parseInt(t.getDate()), 1)).cache();
			
			JavaPairRDD<Integer, Integer> hashtagsPerDay = tmp.reduceByKey((x,y)->x+y);
			
			hashtagsPerDay.saveAsTextFile(args[args.length-1] + "/" + hashtag);
			
		}
	
	}
		
}
