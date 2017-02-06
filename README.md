# Twitter-Analysis-BigData
Analyzing Twitter Data with Spark 

More detailed README will come soon.

get Twitter data from https://archive.org/details/twitterstream <br>
The .tar files will be in subfolders in the form <br>
yyyy/mm/dd/xx/zz.json.bz2, (xx enumerates from 00 to max), e.g. <br>
2016/07/01/00/00.json.bz2

With our Spark program, all JSON files must be in one folder (no subfolders).
With the following command, you can unpack the files, rename them, and move them all to one folder:

for h in $(seq -f '%02g' 1 31); do for i in $(seq -f '%02g' 0 23); do for j in $(seq -f '%02g' 0 59); do bunzip2 "2016/07/$h/$i/$j.json.bz2"; mv "2016/07/$h/$i/$j.json" "ALL_JSONS/2016-07-$h-$i-$j.json"; done; done; done

The numbers in the sequences ($(seq -f ...)) should be adjusted according to the current data.

---

There are 3 classes that can be executed. <br>
I used the commands below. <br>
Put the #hashtags you're looking for in a .csv file and provide the path to it. <br>

TWITTER2<br>
./bin/spark-submit --master local --class bigdata.Twitter2 target/bigdata-1.0-SNAPSHOT-jar-with-dependencies.jar ~/Downloads/tweets/test ~/Downloads/tweets/Hashtagfile/hashtagfile.csv ~/Downloads/tweets/Outputfile_Twitter2_1

TWITTERMESSAGES (wants only folder to hashtagfile, not the .csv)<br>
./bin/spark-submit --master local --class bigdata.TwitterMessages target/bigdata-1.0-SNAPSHOT-jar-with-dependencies.jar ~/Downloads/tweets/Outputfile_Twitter2_1 ~/Downloads/tweets/Hashtagfile ~/Downloads/tweets/Outputfile_TwitterMessages_1 

TWITTERTIMEEVOLUTION<br>
./bin/spark-submit --master local --class bigdata.TwitterTimeEvolution target/bigdata-1.0-SNAPSHOT-jar-with-dependencies.jar ~/Downloads/tweets/Outputfile_Twitter2_1 ~/Downloads/tweets/Hashtagfile/hashtagfile.csv ~/Downloads/tweets/Outputfile_TwitterTimeEvolution_1
