# TwitterLab

A simple java program allowing you to tweet and view your home timeline.

Usage (from command line):
1) Create file `twitter4j.properties` with your Twitter application keys following this format:
`debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************`

2) Run program: 
    <br>`mvn clean install`
    <br>`java -jar target/twitter-1.0-SNAPSHOT.jar server`

3) To post tweet: `curl -POST "localhost:8080/api/1.0/twitter/tweet" -H "Content-Type: application/json" -d '{"text":"<Your tweet here>"}'`
    <br>To get timeline: `curl -GET "localhost:8080/api/1.0/twitter/timeline"`
