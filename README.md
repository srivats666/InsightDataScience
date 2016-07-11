# Finding running Median

Node object stores each payment info. It stores the actor, target and the timeStamp information. The key field is used to find existing payments between 2 users.

We use timeMap to map the Node(payment) to its timeStamp and edgeMap to map the timeStamp to a Set of Nodes(payments). The map object stores userName to count of degrees mapping which is used to find the median.

The generateMedian method follows the below sequence to calculate the median.
* If the payment is before the 60 second window print the previously calculated median.
* If there already exists a payment between these two users, update the timeMap and the edgeMap with the latest timeStamp. 
* Otherwise add a new Node between these users and add them to the timeMap and the edgeMap. Also add/update the map object with the      degree.
* If the payment is within the window for existing users then return the previously calculated median. Or calculate the new median. 
* Remove old entries if the payment is after the previous payment time.
	
# Median calculation
Brute force method sorts the map values by the degree count and gets the median which has a time complexity of o(nlogn).
Efficient calcMedian2 method uses Quick select algorithm to get median in o(n) time

# Running the Program
Sample test inputs and outputs have been included in tests, venmo_input and venmo_output folders.
Follow steps below to execute the program

cd insight_testsuite/

./run_tests.sh
	
# Unit tests 
Created using Junit and can be run following the steps below.

cd src

javac Median_Degree.java

javac -cp ".;../lib/junit.jar;../lib/org.hamcrest.core_1.3.0.v201303031735.jar" Median_Degree_Tests.java

java -cp ".;../lib/junit.jar;../lib/org.hamcrest.core_1.3.0.v201303031735.jar" org.junit.runner.JUnitCore Median_Degree_Tests

# Performance graph between Sort and Quick select
For 100 and 200 users with 10 to 50 payments per user within same window.

<img src='https://github.com/srivats666/Payment/blob/master/images/100.png' title='100 user graph' width='' alt='100 user graph' />

<img src='https://github.com/srivats666/Payment/blob/master/images/200.png' title='200 user graph' width='' alt='200 user graph' />
