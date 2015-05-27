#ArquiSoft RMI Broker

This Project contains 4 executable Java Applications:

- Broker: The broker itself, it MUST be running so the clients can use it
- LibraryClient: A client providing `addBook(title)` and `getBook()` methods
- TimeClient: A client providing `getDate()` and `getHour()` methods

    This client contains an intended bug [here](https://github.com/RecuencoJones/RmiBroker/blob/master/src/main/java/es/unizar/as/rmi/broker/clients/time/methods/TimeImpl.java#L21) to demonstrate that live-reloading clients changes broker methods on-the-fly
    so only this client will need to be recompiled.

- ExternalClient: A consumer-only client

Every client provides an interactive interface for calling any available methods.

#WARNING (don't skip this section!)

__THERE IS NO NEED TO RUN RMIREGISTRY AS ALL THE APPLICATIONS WILL CREATE THEIR OWN INSTANCE OF THE REGISTRY WITH `LocateRegistry.createRegistry(port)`__

__BEFORE RUNNING THE CLIENTS IN HOSTS DIFFERENT FROM LOCALHOST MAKE SURE YOU REPLACE `"host": "localhost"` PROPERTY ON `src/main/resources/<clientType>.conf` WITH BROKER'S HOST IP AND CLIENT'S IP__

#Building

To fast build just run `gradle build`.

Or you can compile with your IDE or with old-fashioned-way (javac).

#Execution with local gradle distribution

To execute the broker just run `gradle runBroker`

Run clients with gradle too:

- `gradle runLibraryClient`
- `gradle runTimeClient`
- `gradle runExternalClient`

#Execution with gradle wrapper

##Linux

To execute the broker just run `./gradlew runBroker`

Run clients with gradle too:

- `./gradlew runLibraryClient`
- `./gradlew runTimeClient`
- `./gradlew runExternalClient`

##Windows

To execute the broker just run `gradlew.bat runBroker`

Run clients with gradle too:

- `gradlew.bat runLibraryClient`
- `gradlew.bat runTimeClient`
- `gradlew.bat runExternalClient`
