# MarketPlace

The application is based on spring boot and provides several features of an online based market place. 

***
### Table of Contents

### 1 - Features/API's 

### 2 - Installation

### 3 - Cloud hosting plan

### 4 - Automated CI/CD rollout 

### 5 - Assignment Feedback



***
## 1 - Features/API's :-

### 1 - registerProject
  -Register a Project in the marketplace.

Sample JSON Body & API
`'http://**.***.***.***:8080/marketplace/projects'`

`{"sellerId": 1,"description": "cool project","maxBudget": 7777, "projectBiddingDeadline":"2018/04/01 14:55:55"}`

  -If the input deadline is from past, the order would be processed immediately.   
  -Hence, in case you want to test the bidding feature, future time with 2-4 minues margin for bidding is advised.


### 2- registerBid
  -Register a Bid in the marketplace

`Sample JSON Body & API
'http://**.***.***.***:8080/marketplace/bids'`

`{"projectId": 1,"buyerId": 1, "offerPrice": 22}`

  -If the project does not exists the bid won't be sucessful and API would return (informative) error.

### 3- getProject
  -Returns project details along with lowest bid so far. else would return the Finished order
  -If the project does not exists and API would return (informative) error.


`Sample  API
'http://**.***.***.***:8080/marketplace/projects/1'`
^ in this case project id is 1



### 4- getAllOpenProjects

  -Returns all open projects which have not reached their deadline yet

`Sample API
'http://**.***.***.***:8080/marketplace/projects/all'`


### 5- getAllOrders
  -Return all the orders(processed projects)

  -Returns all  projects which have reached their deadline and have been processed into a order.

`Sample API
'http://**.***.***.***:8080/marketplace/orders/all'`


### 6- getAllBids
  -Return all the bids

`Sample API
'http://**.***.***.***:8080/marketplace/bids/all'`






## 2 - Installation

// Docker container

`sudo apt-get update` <br>
`sudo apt install docker.io` <br>
`sudo docker pull abhijeetrawat/marketplace:final`<br>
``sudo docker run -p 8080:8080 abhijeetrawat/marketplace:final`` <br>

// Maven / Jar execution

`git clone https://github.com/abhirawat7/MarketPlace`<br>
`sudo apt install maven`<br>
`mvn spring-boot:run`<br>


cd to project directory <br>
`java -jar ./target/demo-0.0.1-SNAPSHOT.jar` 

## 3 - Cloud hosting plan
Cloud hosting plan for this service, incorporating scalability, stability, monitoring and disaster recovery.
:-

#### `Scalability` : 

Instead of H2 database, `Dynamo DB` or other Datbase from `AWS RDS` can be used so as to make the application's Database scale easily.

Pros:
- If application goes down, we won't lose our data as it will be safely stored in `RDS`. Also, there will be backups done in a timely manner.


We can also utilize `Amazon RDS Read Replicas` as this application would probably have more reads than writes.

Pros:
- Application will be able to handle massive read requests.

`Amazon Cloudfront` to provide faster access worldwide (reduce hops).

Pros:
- People would be able to access application from different regions of the world at the same speed.(more useful for static content like css, images)


`AWS ELB` to provide instances in multiple regions and load balancing requests.
- Autoscaling
- Integration with `Cloudwatch` to design custom alarms
- Define `Scaling plans`  based on scheule /demand

`Amazon ElastiCache`
- Disk based storage would be slow and hence would be better if we use `Memcached / Redis` (Depending upon the type of data)



#### `Stability:`

The service can be disected into two micro services :
1- Application to process requests for adding projects and bids
2- Application to process finished projects (Finished Orders)  and send notifications

Pros:
1- We will only have to scale application 1 as the application 2 won't be used a lot.
2- Even if Application 1 goes down, the order processing won't be halted.

`Amazon's SQS service` can be used to pass messages between two services.

SNS to send notifications to users whose bids were picked and to the sellers whose projects were processed and coverted into a finished order. We can also send notifications to people whose bids were not picked.


### `Monitoring:`

`AWS CloudTrail`<br>
`AWS CloudWatch`<br>


### `Security:`

`DNS Services with Amazon Route 53`<br>
`DDoS Protection with AWS Shield`<br>
`Edge Security for Amazon CloudFront with AWS WAF`<br>

### `BackUp and Recovery:`

`S3 for backing up artifacts`<br>
`Enable auto backup for RDS`<br>
`EBS `<br>
`Amazon Glacier`<br>

## 4 - Automated CI/CD rollout

Automated, continuous integration and deployment (CICD) process for production rollout.


This application can be easily deployed using AWS CodeStar  / AWS Elastic Beanstalk

Code star comes with code commit, jira , github intergration.

We'll make use of :


 ```
 AWS CodeCommit (trigerring builds)
 AWS CodeBuild  (Building projects)
 AWS CodePipeline (for managing and reviewing above mentioned services)
 CloudFormation to deploy
 Jira cloud for issues (comes with codestar)
 IAM to manage users/access
 S3 for storing artifacts
 ```

 ```
 Amazon CloudWatch to monitor
 Lambda (trigger services)
 ```



### 5 - Assignment Feedback

```
-The time the exercise took (after dev environment is set up)  
1:30 minutes for API's , 40 minutes for ds/ coding 30 minutes for Documentation of both .
```

```
-Exercise Difficulty: Easy, Moderate, Difficult, Very Difficult  
Moderate
```

```
-How did you feel about the exercise itself? (1 lowest, 10 highest—awesome way to assess coding ability)
10
```

```
-How do you feel about coding an exercise as a step in the interview process?  (1 lowest, 10 highest—awesome way to assess coding ability) 
10
```

```
-What would you change in the exercise and/or process?
I belive the process is excellent and so is the exercise. 
```









