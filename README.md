# LottoGuess

In this project I wanted to try to crawl data from web page, sort them out, and then push it to AWS DynamoDB Table.
After all the data is in the database we can generate lotto number combinations and further check, if the combination has already happened in past.
This whole code can work on EC2 instance, and then with additional API you can reach it to get a lotto numbers combination.
My next step is to create a mobile app for Lotto Number guessing, which will then request number combinations through API from EC2 instance.
This will then be an Android and AWS connection with DynamoDB included.

Feel free to use any part of this code, and learn from it like I did when I wrote it.

Jozsef Soti 