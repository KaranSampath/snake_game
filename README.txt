=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 72863867
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D Arrays: I used my 2-D array to model my board with the positions of the food pieces the 
  snake was going to eat. I did not implement this initially because I thought I could just 
  represent the food positions as an array of Food objects, though I think this is more efficient.
  I use an int array here because the positions of the food objects are represented as integer
  pixels, and therefore this allows for an easy conversion.
  I use break commands to catch ArrayIndexOutofBoundsExceptions as well as any other buggy entries
  in the file if it is corrupted. This ensures that either the array is null or contains only 
  readable values, which is handled at the view portion of my program.
  I also used a 2D array to read my High Scores data into the main class, with one column 
  being the user names and the other being the scores. I used a String array here as file reading
  returns strings, which can later then be converted to ints if required.

  2. Collections: I stored each part of the snake’s body in a collection, so that the body is 
  able to turn and move in an appropriate manner. Specifically, I stored them in a list since 
  order was important. I used a LinkedList, because I needed to check the position 
  of the head of the list with respect to the positions of the bounding box/the other parts of the 
  body. Every time the snake moves, the positions of the predecessor got transferred to the 
  successor, thereby giving the impression of moving in one body. If the snake head intersects the 
  walls or its body, the game ends, with the user given a reset option.
  I use a collection because the body did not have any fixed size, and therefore a collection
  allowed me to constantly increase size. Since I needed the collection to have an order, using the
  ordered List was the best option.

  3. File I/O: My Snake implementation used I/O to store the game state, so that the user can pause 
  a game. Specifically, the positions of the Food objects and the positions of the Collection are
  saved in separate text files when the save button is pressed. Whenever a player wants to load the 
  saved game, my game reads these text file and parses the data so it can be displayed. Along with 
  this, the high scores of all the players are written into a text file and displayed if the user 
  presses a button on the home page.

  4. Testable Component: I have created methods that take in coordinates as a parameter, and 
  update the game accordingly. This functionality is designed such that I can test it with JUnit. 
  I test that the 2-D food array is updated correctly (random food popping works), 
  if the snake eating food causes the body to grow, and if the snake moves. 
  I also test edge cases, such as when the snake intersects its own body or the bounding wall. I 
  also test that reading and writing into the file works correctly, along with testing edge cases
  like null and empty entries.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Game.java : Run the Code and initialize the home screen, high score screen and game screen
  GameCourt.java : Holds the primary game logic for how different objects interact with one another. 
  Food.java : Create a food object which remains stationary
  Snakeunit.java : Create a snakeunit object with a defined velocity, position and color.
  Writer.java : Writes the data types into a list of strings in the file
  Reader.java : Reads the string file into usable data formats
  GameObj.java : Superclass providing the attributes for the Food and Snakeunit Objects
  RandomNumberGenerator.java: Generates random numbers
  Direction.java : Enum with various direction values
  
- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  There were stumbling blocks related to understanding how to capture the intersections between the
  head of the snake and its body, as the intersections should only happen with the front part
  of the head and not its back portion. I rectified this by modifying the intersects function in
  GameObj.java to not include intersections where the pixels were only equal to each other.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  I believe there is a reasonably good separation of functionality, with the private states being
  fully encapsulated. I would refactor the initialization of my screen in Game.java into different 
  classes as right now the code is slightly hard to understand.
	

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  Food Image: https://pngtree.com/free-png-vectors/apple-cartoon
