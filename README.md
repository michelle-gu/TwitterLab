# TwitterLab

A simple java program that allows you to tweet and view your home timeline.

To configure:
1) Create `twitter-lab.yml` file with your own Twitter keys/secrets following the template `twitter-lab-example.yml`

To run program (from command line):
1) Run program: 
    <br>`mvn clean install`
    <br>`java -jar target/twitter-1.0-SNAPSHOT.jar server twitter-lab.yml`

2) To post tweet: `curl -POST "localhost:8080/api/1.0/twitter/tweet" -H "Content-Type: application/json" -d '{"message":"<Your tweet here>"}'`
    <br>To get timeline: `curl -GET "localhost:8080/api/1.0/twitter/timeline"`
    <br>To get filtered timeline: `curl -GET "localhost:8080/api/1.0/twitter/timeline/filter?keyword=<Your keyword here>`
    
To run tests:
1) `mvn test` or `mvn clean install`

To run code coverage:
1) `mvn test`
2) `mvn jacoco:report`
3) Open target/site/jacoco/index.html to see code coverage