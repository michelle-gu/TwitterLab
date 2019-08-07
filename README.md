# TwitterLab

A simple java program allowing you to tweet and view your home timeline.

Usage (from command line):
1) Use a text editor to add your Twitter application keys to twitter4j.properties

2) Change directory to src folder `cd src`

3) Compile java files
`javac -cp ../lib/twitter4j-4.0.7/lib/twitter4j-core-4.0.7.jar GetHomeTimeline.java`
`javac -cp ../lib/twitter4j-4.0.7/lib/twitter4j-core-4.0.7.jar PostTweet.java`

4) Create jars 
    1) Modify Main-Class attribute in MANIFEST.MF to `PostTweet` and create jar `jar -cfm PostTweet.jar MANIFEST.MF PostTweet.class`
    2) Modify Main-Class attribute in MANIFEST.MF to  `GetHomeTimeline` and create jar
`jar -cfm GetHomeTimeline.jar MANIFEST.MF GetHomeTimeline.class`

5) Run programs
    1) To run program 1: `java -jar PostTweet.jar`
    2) To run program 2: `java -jar GetHomeTimeline.jar`
