package bigdata;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import org.json.simple.JSONObject;

import org.json.simple.JSONValue;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.SparkConf;
import org.apache.hadoop.mapred.InputFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import scala.Tuple2;
import scala.Tuple3;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import java.io.NotSerializableException;
import java.util.Iterator;

import scala.collection.JavaConversions.*;

public class TwitterConvert{
	public static void main(String[] args){
		SparkConf conf = new SparkConf().setAppName("TwitterConvert");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> data = sc.textFile(args[0]).cache();
		JavaRDD<Integer> tweets = data.map(s->{
		
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(s);
				Tweet tweet = new Tweet();
				JSONObject entities = (JSONObject) json.get("entities");
				JSONArray hashtags = (JSONArray) entities.get("hashtags");
				if(json != null){
					return 1;
				}
				/*
				if(hashtags != null){
					Iterator itr= hashtags.iterator();
					while(itr.hasNext()){
						JSONObject tmp = (JSONObject) itr.next();
						tweet.addHashtag(tmp.get("text").toString());
					}
				}
				return tweet;
				*/
				return 0;
				}).filter(t -> t==0);
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + Long.toString(tweets.count()));
		tweets.saveAsTextFile(args[1]);
		
	}
}
