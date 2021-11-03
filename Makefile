delete-compiled:
	rm -rf classes

create_registry:
	@if ps | grep -q "rmiregistry"; then \
		echo "Rmi registry is already running"; \
	else \
		cd classes && rmiregistry & \
		echo "Starting rmi registry..."; \
	fi

compile: 
	mkdir -p classes
	javac -cp jackson-annotations-2.13.0-rc1.jar:jackson-core-2.13.0-rc1.jar:jackson-databind-2.13.0-rc1.jar -d classes src/*.java

start-server: create_registry
	java -cp classes/:jackson-annotations-2.13.0-rc1.jar:jackson-core-2.13.0-rc1.jar:jackson-databind-2.13.0-rc1.jar src/Server

start-client:
	java -cp classes/:jackson-annotations-2.13.0-rc1.jar:jackson-core-2.13.0-rc1.jar:jackson-databind-2.13.0-rc1.jar src/Client
