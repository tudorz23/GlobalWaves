*Designed by Marius-Tudor Zaharia, 323CA, November 2023*

# GlobalWaves

---

## Table of contents
1. [What is GlobalWaves?](#what-is-globalwaves)
2. [Platform presentation](#platform-presentation)
3. [I/O](#io)
4. [Implementation details](#implementation-details)
    * [Design choices](#design-choices)
    * [Design patterns used](#design-patterns-used)
    * [Program flow](#program-flow)
    * [OOP principles used](#oop-principles-used)

---

## What is GlobalWaves?
* GlobalWaves is a music and podcast streaming service backend written in the
Java language.

---

## Platform presentation
* The platform offers the user the ability to play a **song**, a **playlist**
or a **podcast**.
* Each user disposes of a personal **audio player**, so they can use the
platform independently of one another.
* A search bar can be used to filter results based on personal wish.
* After a search operation, users can **select** a single piece of audio
material and then **load** it to the player.
* Users have multiple functionalities at their disposal, such as **creating** a
playlist, **adding** and **removing songs** to it, **pausing** and
**resuming**, putting on **repeat**, **shuffling**, **skipping** or
**rewinding**, **liking** a song or **following** a playlist.
* The platform *administrator* is granted access to statistics such as
displaying the top 5 most liked songs or top 5 most followed playlists.
* The platform simulates the passing of time, meaning that different actions
happen at different timestamps and, in between them, the internal state of the
music player might change.

---

## I/O
* The input is given through a JSON file.
* A `LibraryInput` is first passed, encapsulating the main content of the
Database.
* Then, a list of `CommandInput` is passed, containing the actions that the
program should perform.
* The output is also stored into a JSON file.
* The **Jackson** library is used for input/output operations.
* To run the tests, the `main()` method of the `Main` class should be run.

---

## Implementation details
### Design choices
* The class that controls the flow of the program is `AdminInteraction`.
* The `Session` class serves as the bridge between the internal data stored
in the Database and the code that uses it.
* The `Audio` abstract class represents the core of the OOP design of this
project, laying the foundation for the usage of polymorphism, serving as the
common ancestor of `Song`, `Playlist` and `Podcast` classes, thus it can
represent a generic Audio file being played by the `Player`.
* Internal states of the objects are generally stored using **enums**, such as
`CommandType`, `AudioType`, `PlayerState`, `RepeatState` and `Visibility`.
* These enums not only present an elegant solution to adequately label objects
of the same type, but with different characteristics, they are also a very
powerful tool for writing better code through their ability of storing fields
and their constructors.
* For example, their labels are helpful for printing details regarding a class
in the requested format by calling the `getLabel()` method.
* Another good example is the `cycleState()` method of the `RepeatState` enum,
which allows client code to elegantly change the state of an object.
* Deep copies of concrete objects inheriting from `Audio` class are placed
in the `Player`, so changes made to their internal states are not visible in
the database, and consequently, to other users.
* For output, a base `Printer` class is used as a foundation for inheritance,
containing fields common to all concrete printers.

---

### Design patterns used
* 3 design patterns have been used for this first stage:

#### Command Pattern
* Used for separating the implementations of multiple actions.
* Based on the usage of the `ICommand` interface, which exposes the `execute()`
method, thus abstracting the use of a command.
* The `AdminInteraction` class iterates through the commands given as input
and calls the `execute()` method of the `Invoker` class, which, in turn,
calls the `execute()` method of `ICommand` interface.
* The Invoker provides separation between the commands and the client code that
uses them, in this case, the `AdminInteraction`.

#### Strategy Pattern
* Used for diverging in implementation between the various ways of searching in
the database.
* Based on the `ISearchSrategy` interface, which exposes the `search()` method,
paving the way for polymorphism.
* Concrete strategies then implement this interface, providing separation of
logic between searching for a song, a podcast or a playlist.

#### Factory Pattern
* Used as a Factory Method in two places:
  * to create concrete command instances based on the `ICommand` interface in
  the `CommandFactory` class.
  * to create concrete search strategies based on `ISearchStrategy` in the
  `getSearchStrategy()` method of the `SearchCommand` class.

---

### Program Flow
* `Main` class calls the `startAdminInteraction()` method.
* `AdminInteraction` iterates through the input commands and uses the
`CommandFactory` to generate concrete objects implementing `ICommand`
interface, while `Invoker` serves as an intermediary to call the `execute()`
method.
* Depending on the type of command, actions are performed, usually modifying
the state of the `Player` instance of one `User`.
* Before a new command is applied, the time passing is simulated using the
polymorphic method `simulateTimePass()`.
* The output is then appended using specialized `Printer` objects.

---

### OOP Principles used
* ***Inheritance***
  * Used for the `Audio` files and the `Printers`.

* ***Abstraction***
  * Command and Strategy patterns are based on interfaces, which present
  abstract methods.
  * `Audio` is an abstract class, with multiple abstract methods implemented
  by its inheritors.

* ***Encapsulation***
  * All classes have *private* fields and *getter* and *setter* methods are
  implemented.
  * The main goal of using design patterns is to achieve encapsulation, for
  each class to have a single responsibility.

* ***Polymorphism***
  * The Command and Strategy patterns also use the polymorphism, as at compile
  time it is unknown which concrete objects will call the implementations of
  the abstract methods.
  * Multiple commands are implemented using the abstract methods from the
  `Audio` class, which, in turn, are implemented in different ways by its
  inheritors, `Song`, `Playlist` and `Podcast`.
