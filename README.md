## AuthenticationLab
- Set the root folder (AuthenticationLab) as source - if using IntelliJ
- Use the Makefile for running the server and client. They each need their own terminal.
- Compile the project using: ```make compile```
- Run server with: ```make start-server```
- Run client with: ```make start-client```

Note that when running the server the java rmi registry will also be launched. 
I.e. it can be necessary to kill it with. ```kill -9 <PID>```. \
(Use ```ps``` to see which PID).



