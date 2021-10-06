## AuthenticationLab
- Set the root folder (AuthenticationLab) as source - if using IntelliJ
- Use the Makefile for running the server and client. They each need their own terminal.
- Run server with: ```make start-server```
- Run client with: ```make start-client```

Note that when running the server the java rmi registry will also be launched. 
I.e. it can be necessary to kill it with. ```kill -9 <PID>```. \
(Use ```ps``` to see which PID).

The server waits for invocation by a client until manually terminated (or crashing).
The client currently only sends a single message to the server (then terminates) - the server displays the message. 

See https://docs.oracle.com/javase/tutorial/rmi/server.html (Setting up RMI server and client)

Solution is based on https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm

