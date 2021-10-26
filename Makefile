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
	javac -d classes src/*.java

start-server: create_registry
	java -cp classes/ src/Server

start-client:
	java -cp classes/ src/Client
