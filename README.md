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
	
# Unit/Performance tests 
Created using Junit and can be run following the steps below.

cd src

javac Median_Degree.java

javac -cp ".;../lib/junit.jar;../lib/org.hamcrest.core_1.3.0.v201303031735.jar" Median_Degree_Tests.java

java -cp ".;../lib/junit.jar;../lib/org.hamcrest.core_1.3.0.v201303031735.jar" org.junit.runner.JUnitCore Median_Degree_Tests

# Performance Evaluation

Inputs:
For the performance evaluation of calculating median, I used the unit test module. First I created 10 different inputs splitted into 2 groups.
First set of inputs is where there are 100 different users and each user makes k payments where k is in set k'=[10,20,30,40,50]. 
These k payments are made to random k target users selected from the set of 100 users.
Second set is where there are 200 different users and each user again make k payments to random k target users. All the payments have made within the same 60 seconds window. 
This way, the performance of calculating median can be evaluated under a heavy load.

Algorithms:
For each set of input, I evaluated the total time it takes to calculate median with my implementation. For performance comparison, I also evaluated the total time for brute-force approach. More specifically, the following is what is represented in the graphs below:
1) Brute force approach calculating median: This is done by sorting the degrees corresponding to each user.
2) My efficient method of calculating the median: I achieved this through implementing QuickSelect() algorithm.
Method (1) time complexity is O(n log n) where n is the number of users. This is because of the need to sort to calculate the median. On the other hand, method (2) time complexity is O(n). It is only linear because QuickSelect() algorithm does not need to recursively evaluate both sub problems when narrowing down the problem which keeps the algorithm still in linear complexity.

On the graphs below, we can see how the total time it takes to calculate median changes as we have more payments. The first graphs is for the performance setting for 100 users, and the second graph is for the performance setting for 200 users.
A point n(k) on the x-axis of the graphs denotes the input for n users where each user made k payments.   

As it can be seen from the graph below more clearly, as the total number of payments increase, time spent on the efficient implementation grows only linearly where it grows non-linear for brute-force approach.

<img src='https://github.com/srivats666/Payment/blob/master/images/100.png' title='100 user graph' width='' alt='100 user graph' />

<img src='https://github.com/srivats666/Payment/blob/master/images/200.png' title='200 user graph' width='' alt='200 user graph' />
