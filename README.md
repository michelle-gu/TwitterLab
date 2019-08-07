# TwitterLab

A simple java program allowing you to tweet and view your home timeline.

Usage (from command line):
1) Create file `twitter4j.properties` with your Twitter application keys following this format:
`debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************
`

2) Change directory to src folder `cd src`

3) Compile java files
`javac -cp ../lib/twitter4j-4.0.7/lib/twitter4j-core-4.0.7.jar GetHomeTimeline.java`
`javac -cp ../lib/twitter4j-4.0.7/lib/twitter4j-core-4.0.7.jar com.khoros.twitter.PostTweet.java`

4) Create jars 
    1) Modify Main-Class attribute in MANIFEST.MF to `com.khoros.twitter.PostTweet` and create jar `jar -cfm com.khoros.twitter.PostTweet.jar MANIFEST.MF com.khoros.twitter.PostTweet.class`
    2) Modify Main-Class attribute in MANIFEST.MF to  `GetHomeTimeline` and create jar
`jar -cfm GetHomeTimeline.jar MANIFEST.MF GetHomeTimeline.class`

5) Run programs
    1) To run program 1: `java -jar com.khoros.twitter.PostTweet.jar`
    2) To run program 2: `java -jar GetHomeTimeline.jar`
