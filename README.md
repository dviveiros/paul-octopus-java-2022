# Paul the Octopus (Java Quick Starter)

A basic project to use for the Paul the Octopus challenge (World Cup 2022) written in Java. This player is designed
to run on AWS but the main concepts can be applied to any cloud.

## Getting started

Steps to install and configure the project:

1. Install [JDK 18.0.2](https://jdk.java.net/18/). Check if the installation was successful running:
    ```
    > java -version
    openjdk version "18.0.2.1" 2022-08-18
    OpenJDK Runtime Environment (build 18.0.2.1+1-1)
    OpenJDK 64-Bit Server VM (build 18.0.2.1+1-1, mixed mode, sharing)
    ```

2. Clone the repository

    ```
    > git clone https://github.com/dviveiros/paul-octopus-java-2022
    ```

3. Install [Gradle 7.5.1](https://gradle.org/releases/) or later. After that, make sure that the GRADLE_HOME environment variable is properly set and exported.

    ```
    > export GRADLE_HOME=<my_path>/gradle
    > echo $GRADLE_HOME
    <my_path>/gradle
    ```

   Test your gradle installation:

    ```
    > gradle -version
    ------------------------------------------------------------
    Gradle 7.5.1
    ------------------------------------------------------------

    Build time:   2022-08-05 21:17:56 UTC
    Revision:     d1daa0cbf1a0103000b71484e1dbfe096e095918

    Kotlin:       1.6.21
    Groovy:       3.0.10
    Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
    JVM:          18.0.2.1 (Oracle Corporation 18.0.2.1+1-1)
    OS:           Mac OS X 13.0 x86_64
    ```

4. Configure your access to AWS. This solutions is reading data from a AWS Bucket. You need to do the following:
    
   a) Create or reuse a S3 bucket. Upload all the CSV files with historical data to it;
   b) Create an ACCESS KEY ID and SECURITY ACCESS KEY to allow the solution to read data from it securely;
   c) Create two environment variables as presented bellow:
   ```
   export AWS_ACCESS_KEY_ID=<your access key>
   export AWS_SECRET_ACCESS_KEY=<your secret key>
   ```
   d) Change the name of the bucket in the source code. There is one "TODO" mark in file Config.java for that.
   ```
   //TODO Change your bucket name here
    private String datasetBucket = "<your bucket name goes here>";
   ```
   
   **!!! SHORTCUT if you are using our internal AWS environment !!!**

   Just go to https://ciandt.awsapps.com/start#/
   Use your login (with our @ciandt.com) as the username
   Use your corporate password as the password
   Expand the "GU Gemini.Engagement-Operation" tab
   Click on "Command line or programmatic access"
   You will see the keys there. In this case, they rely on a session, so you'll need to create another environment variable with this info. And it will expire after some time... so you need to re-do the process again.
   ```
   export AWS_ACCESS_KEY_ID=<your access key>
   export AWS_SECRET_ACCESS_KEY=<your secret key>
   export AWS_SESSION_TOKEN=<your session token>
   ```
   In this case, the bucket name is already configured in Config.java
   ```
   private String datasetBucket = "paul-octopus-2022";
   ```

5. Test your installation: 
```
> ./paul.sh

    ____              __   __  __            ____       __
   / __ \____ ___  __/ /  / /_/ /_  ___     / __ \_____/ /_____  ____  __  _______
  / /_/ / __ `/ / / / /  / __/ __ \/ _ \   / / / / ___/ __/ __ \/ __ \/ / / / ___/
 / ____/ /_/ / /_/ / /  / /_/ / / /  __/  / /_/ / /__/ /_/ /_/ / /_/ / /_/ (__  )
/_/    \__,_/\__,_/_/   \__/_/ /_/\___/   \____/\___/\__/\____/ .___/\__,_/____/
                                                             /_/
2022-11-04 13:21:15 - Starting Application using Java 18.0.2.1 on iMac-2020.lan with PID 52714 (/Users/dviveiros/git/paul-octopus-java-2022/build/libs/paul-the-octopus-0.1.jar started by dviveiros in /Users/dviveiros/git/paul-octopus-java-2022)
2022-11-04 13:21:15 - Running with Spring Boot v2.7.5, Spring v5.3.23
2022-11-04 13:21:15 - No active profile set, falling back to 1 default profile: "default"
2022-11-04 13:21:16 - Started Application in 1.129 seconds (JVM running for 1.433)
2022-11-04 13:21:16 - Predicting results for year: 2022
2022-11-04 13:22:05 - Predicting results for year: 2006
2022-11-04 13:22:24 - Predicting results for year: 2010
2022-11-04 13:22:43 - Predicting results for year: 2014
2022-11-04 13:22:59 - Predicting results for year: 2018
2022-11-04 13:23:14 - **********************************************
2022-11-04 13:23:14 - * Algorithm performance
2022-11-04 13:23:14 - * 2006: Score = 375, Performance = 31.2500 %
2022-11-04 13:23:14 - * 2010: Score = 280, Performance = 23.3333 %
2022-11-04 13:23:14 - * 2014: Score = 308, Performance = 25.6667 %
2022-11-04 13:23:14 - * 2018: Score = 247, Performance = 20.5833 %
2022-11-04 13:23:14 - * 
2022-11-04 13:23:14 - * Overall performance = 25.2083 %
2022-11-04 13:23:14 - **********************************************
2022-11-04 13:23:14 - Process completed successfully!
``` 

## Working on your prediction

Let start! I strongly recommend you to run some basic commands just to try it out.

```
> ./paul.sh
    ____              __   __  __            ____       __
   / __ \____ ___  __/ /  / /_/ /_  ___     / __ \_____/ /_____  ____  __  _______
  / /_/ / __ `/ / / / /  / __/ __ \/ _ \   / / / / ___/ __/ __ \/ __ \/ / / / ___/
 / ____/ /_/ / /_/ / /  / /_/ / / /  __/  / /_/ / /__/ /_/ /_/ / /_/ / /_/ (__  )
/_/    \__,_/\__,_/_/   \__/_/ /_/\___/   \____/\___/\__/\____/ .___/\__,_/____/
                                                             /_/
2022-11-05 13:26:55 - Starting Application using Java 18.0.2.1 on iMac-2020.lan with PID 67496 (/Users/dviveiros/git/paul-octopus-java-2022/build/libs/paul-the-octopus-0.1.jar started by dviveiros in /Users/dviveiros/git/paul-octopus-java-2022)
2022-11-05 13:26:55 - Running with Spring Boot v2.7.5, Spring v5.3.23
2022-11-05 13:26:55 - No active profile set, falling back to 1 default profile: "default"
2022-11-05 13:26:56 - Started Application in 2.074 seconds (JVM running for 2.374)
2022-11-05 13:26:56 - Predicting results for year: 2022
2022-11-05 13:27:15 - Predicting results for year: 2006
2022-11-05 13:27:33 - Predicting results for year: 2010
2022-11-05 13:27:56 - Predicting results for year: 2014
2022-11-05 13:28:13 - Predicting results for year: 2018
2022-11-05 13:28:29 - **********************************************
2022-11-05 13:28:29 - * Algorithm performance
2022-11-05 13:28:29 - * 2006: Score = 375, Performance = 31.2500 %
2022-11-05 13:28:29 - * 2010: Score = 280, Performance = 23.3333 %
2022-11-05 13:28:29 - * 2014: Score = 308, Performance = 25.6667 %
2022-11-05 13:28:29 - * 2018: Score = 247, Performance = 20.5833 %
2022-11-05 13:28:29 - * 
2022-11-05 13:28:29 - * Overall performance = 25.2083 %
2022-11-05 13:28:29 - **********************************************
2022-11-05 13:28:29 - Process completed successfully!

```

The default predictor is the OneZeroPredictor. Which means that all predictions will be 1x0. Note that the performance of this algorithm is ~25%.

Now, let's try a different one:
```
> ./paul.sh -p ZeroZeroPredictor
    ____              __   __  __            ____       __
   / __ \____ ___  __/ /  / /_/ /_  ___     / __ \_____/ /_____  ____  __  _______
  / /_/ / __ `/ / / / /  / __/ __ \/ _ \   / / / / ___/ __/ __ \/ __ \/ / / / ___/
 / ____/ /_/ / /_/ / /  / /_/ / / /  __/  / /_/ / /__/ /_/ /_/ / /_/ / /_/ (__  )
/_/    \__,_/\__,_/_/   \__/_/ /_/\___/   \____/\___/\__/\____/ .___/\__,_/____/
                                                             /_/
2022-11-04 13:29:11 - Starting Application using Java 18.0.2.1 on iMac-2020.lan with PID 52967 (/Users/dviveiros/git/paul-octopus-java-2022/build/libs/paul-the-octopus-0.1.jar started by dviveiros in /Users/dviveiros/git/paul-octopus-java-2022)
2022-11-04 13:29:11 - Running with Spring Boot v2.7.5, Spring v5.3.23
2022-11-04 13:29:11 - No active profile set, falling back to 1 default profile: "default"
2022-11-04 13:29:12 - Started Application in 1.207 seconds (JVM running for 1.521)
2022-11-04 13:29:12 - Predicting results for year: 2022
2022-11-04 13:29:31 - Predicting results for year: 2006
2022-11-04 13:29:54 - Predicting results for year: 2010
2022-11-04 13:30:15 - Predicting results for year: 2014
2022-11-04 13:30:32 - Predicting results for year: 2018
2022-11-04 13:30:48 - **********************************************
2022-11-04 13:30:48 - * Algorithm performance
2022-11-04 13:30:48 - * 2006: Score = 363, Performance = 30.2500 %
2022-11-04 13:30:48 - * 2010: Score = 406, Performance = 33.8333 %
2022-11-04 13:30:48 - * 2014: Score = 341, Performance = 28.4167 %
2022-11-04 13:30:48 - * 2018: Score = 301, Performance = 25.0833 %
2022-11-04 13:30:48 - * 
2022-11-04 13:30:48 - * Overall performance = 29.3958 %
2022-11-04 13:30:48 - **********************************************
2022-11-04 13:30:48 - Process completed successfully!
```

Wow! If you change your prediction from 1x0 to 0x0, you should expect a better performance. This is consistent for previous editions of the World Cup.

*Your new benchmark is ~29%*

Are you ready to improve it? Just change the implementation of the class `DefaultPredictor` or create a new one and let's see how accurate you can be. Good luck!

## Generating the CSV File

First, you must execute a prediction using the `-f` flag.

```
> ./paul.sh -p ZeroZeroPredictor -f
    ____              __   __  __            ____       __
   / __ \____ ___  __/ /  / /_/ /_  ___     / __ \_____/ /_____  ____  __  _______
  / /_/ / __ `/ / / / /  / __/ __ \/ _ \   / / / / ___/ __/ __ \/ __ \/ / / / ___/
 / ____/ /_/ / /_/ / /  / /_/ / / /  __/  / /_/ / /__/ /_/ /_/ / /_/ / /_/ (__  )
/_/    \__,_/\__,_/_/   \__/_/ /_/\___/   \____/\___/\__/\____/ .___/\__,_/____/
                                                             /_/
2018-05-08 20:29:46 - Starting Application on MacBookDaniel15 with PID 83585 (/Users/dviveiros/git/paul-octopus-java/build/libs/paul-the-octopus-0.1.jar started by dviveiros in /Users/dviveiros/git/paul-octopus-java)
2018-05-08 20:29:46 - Running with Spring Boot v2.0.1.RELEASE, Spring v5.0.5.RELEASE
2018-05-08 20:29:46 - No active profile set, falling back to default profiles: default
2018-05-08 20:29:47 - Started Application in 1.086 seconds (JVM running for 1.553)
2018-05-08 20:29:47 - Predicting results for year: 2018
2018-05-08 20:29:56 - Predicting results for year: 2006
2018-05-08 20:29:59 - Predicting results for year: 2010
2018-05-08 20:30:03 - Predicting results for year: 2014
2018-05-08 20:30:05 - **********************************************
2018-05-08 20:30:05 - * Algorithm performance
2018-05-08 20:30:05 - * 2006: Score = 363, Performance = 30.2500 %
2018-05-08 20:30:05 - * 2010: Score = 406, Performance = 33.8333 %
2018-05-08 20:30:05 - * 2014: Score = 341, Performance = 28.4167 %
2018-05-08 20:30:05 - * 
2018-05-08 20:30:05 - * Overall performance = 30.8333 %
2018-05-08 20:30:05 - **********************************************
2018-05-08 20:30:05 - Generating file predictions.csv
2018-05-08 20:30:05 - File created successfully. Run './paul.sh -c upload -u <username>' to upload it.
2018-05-08 20:30:05 - Process completed successfully!

```

Double check if the file was correctly created.

```
> cat predictions.csv
home,home_score,away_score,away
Qatar,0,0,Ecuador
Senegal,0,0,Netherlands
England,0,0,Iran
USA,0,0,Wales
France,0,0,Australia
Denmark,0,0,Tunisia
Mexico,0,0,Poland
Argentina,0,0,Saudi Arabia
...
```

That's it! Good luck :)