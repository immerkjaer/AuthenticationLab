delete-compiled:
	rm -rf classes

create_registry:
	@if ps | grep -q "rmiregistry"; then \
		echo "Rmi registry is already running"; \
	else \
		cd classes && rmiregistry & \
		echo "Starting rmi registry..."; \
	fi

compile: delete-compiled
	mkdir -p classes
	javac -d classes src/*.java

start-server: compile create_registry
	java -cp classes/ src/Server

start-client: compile
	java -cp classes/ src/Client
