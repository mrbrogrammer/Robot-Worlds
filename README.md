# Robot Worlds
Client/Server - Robot World


## Getting started
Creating a robot world that will have the following implementations:
* Client(launches the robots into the world)
* Server(responses the messages sent from the client and builds the world, with obstacles invented for the world)


## Sockets
*The browser sends messages using the HTTP protocol to the server
*The server responds to the from the client

## What we have learned
* Sockets
    - We learnt that a socket is a one-end point of a two-way communication link between two programs running on the network.
    - Sockets allow you to exchange information across a network.
* Threading
    - We learnt about multithreading, which is a process of executing multiple threads simultaneously.
    - It's a feature that allows concurrent execution of two or more parts of a program for maximum utilization.

## How to run
<br>
**To run this program firstly you have to run the Server then the Client on the command line using these commands:**
<br>
To run the Server:
<br>
`mvn compile exec:java -Dexec.mainClass="za.co.wethinkcode.Server.RobotWorldServer" -Dexec.args="8080"`
To run the Client:
<br>
`mvn compile exec:java -Dexec.mainClass="za.co.wethinkcode.Client.RobotWorldClient" -Dexec.args="8080 localhost"`


## Authors and acknowledgment
* Lesedi Jonas
* Prince Mangwa
* Simphiwe Madi
* Paballo Mathipa
