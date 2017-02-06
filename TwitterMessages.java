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

	public static void main(String[] args) throws NullPointerException {

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
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));
						// StringUtils.isEmpty(s) returns true, if s is "", " ", or null
						// regexp from above can return empty String, so we catch that here

						key1 = StringUtils.substringBetween(text, "make america ", " ");
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));

						return result.iterator();
					}
				}).reduceByKey((x,y) -> x+y);
		MAGA.saveAsTextFile(args[1] + "/maga");


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
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));
						key1 = StringUtils.substringBetween(text, "crooked", " ");
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));

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
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));
						key1 = StringUtils.substringBetween(text, "never", " ");
						if(!StringUtils.isEmpty(key1)) result.add(new Tuple2<>(key1.replaceAll("[^a-zA-Z]","") + "," + tweet.getDate(), 1));

						return result.iterator();
					}
				}).reduceByKey((x,y) -> x+y);
		never.saveAsTextFile(args[1] + "/never");



	}

}