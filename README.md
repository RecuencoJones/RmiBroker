#ArquiSoft RMI Broker

This Project contains 4 executable Java Applications:

- Broker: The broker itself, it MUST be running so the clients can use it
- LibraryClient: A client providing `addBook(title)` and `getBook()` methods
- TimeClient: A client providing `getDate()` and `getHour()` methods

    This client contains an intended bug [here]() to demonstrate that live-reloading clients changes broker methods on-the-fly
    so only this client will need to be recompiled.

- ExternalClient: A consumer-only client

Every client provides an interactive interface for calling any available methods.

#Building

To fast build just run `gradle build`.

Or you can compile with your IDE or with old-fashioned-way (javac).

#Execution

To execute the broker just run `gradle runBroker`

Run clients with gradle too:

- `gradle runLibraryClient`
- `gradle runTimeClient`
- `gradle runExternalClient`