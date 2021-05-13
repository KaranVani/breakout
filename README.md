

# BREAK-OUT GAME

A Karan Vani Production

MAY 7, 2021

DOCUMENTATION

CI401 – Introduction to Programming





Breakout Game

Table of Contents

[Introduction](#br3)[ ](#br3)[............................................................................................................................................3](#br3)

[Running](#br3)[ ](#br3)[the](#br3)[ ](#br3)[Code....................................................................................................................................2](#br3)

[In](#br3)[ ](#br3)[Eclipse:](#br3)[ ](#br3)[............................................................................................................................................2](#br3)

[In](#br3)[ ](#br3)[BlueJ:...............................................................................................................................................2](#br3)

[The](#br3)[ ](#br3)[Code.................................................................................................................................................2](#br3)

[Data](#br3)[ ](#br3)[Types...........................................................................................................................................2](#br3)

[The](#br4)[ ](#br4)[MVC..............................................................................................................................................3](#br4)

[Game](#br4)[ ](#br4)[Objects......................................................................................................................................3](#br4)

[Adding](#br5)[ ](#br5)[Bricks.......................................................................................................................................4](#br5)

[The](#br6)[ ](#br6)[Collision........................................................................................................................................5](#br6)

[Tests........................................................................................................................................................7](#br8)

[Reflection................................................................................................................................................7](#br8)








## Introduction

Breakout or more commonly known as ‘Brick Breaker’ is a game a game consisting of three

more main objects and one main goal. The objects are, the bricks, a ball, and a bat. The goal of the game

is to break all the bricks on the screen using the bat. I will be using a skeleton code given to me by the

University of Brighton. This game is fully coded in Java in the Eclipse IDE.

## Running the Code

The submission includes a zip file that has the game’s source code and if needed the assets for

the game itself.

### In Eclipse:

To run the game, extract the zip file into a desired folder and import it into Eclipse. Once

imported, you will need to select your own JavaFX library installed on the computer to run the game

without any problems. Then click run on the top which is a green circle with a white play button.

### In BlueJ:

To run the game, there is a file called package and is a BlueJ project file. Run this file, it will open

BlueJ. Head to tools click on compile then right click on the main class and execute the code. This will

run the game and show a console in the background.

The Code

## Data Types

Developing games and programs has a certain impact on the memory/ram of the machine it is

running. A game like this would not take as much memory but for good practice I used the best possible







data types and access modifiers. Due to the classes sharing a majority of the information, a lot of the

access modifiers were kept as public.

## The MVC

The game and the code are based on the MVC (Model View Controller) design. Where all these

classes talk to each other and make the game work. The Model is responsible for the game initiation, the

calculation and is basically the brains of the game. The Controller class takes care of the user inputs and

sends the input to the Model which then sends it to the View class, which outputs the current position

of the objects on to the screen and is what the player can see.

The skeleton code the university provided already connected the model, view, and controller classes

together.

Game Objects

The game itself has the core objects, such as the bricks, the bat, and the ball. Which instead of

forming into one class I made three different classes, one for each of the objects. This enabled me to

take control over an individual object easily.



## Adding Bricks

The game was missing a crucial aspect, the bricks. To form

the bricks, I decided to design an algorithm that uses arrays

and loops to place the bricks. The figure on the right is how

I approached the problem. I had taken account of the width

and the height of the game screen along with the height

and width of the bricks them selves and checked the max

that could fit. I set a double loop, one for the rows and one

for the columns and formed the randomly colored bricks.







## The Collision

Once the core game objects and game was set in place, it was time for the collision. The code

provided already had collision detection but had some flaws, so I decided to completely reword the

collision check. Towards the start of the semester, our lecturer had pointed out a bug during the

demonstration. This bug was causing the

ball to get stuck inside of the bat if it was

hit from the corner or side of the bat, as

shown in the figure below.

The picture on the right is showing the

thought process for the collision.

I imagined the window as an X and Y plane

and placed a ball and brick on it. And in a

summary to check if the ball hit the bricks

or bat’s side, the game would check if it is

intersecting the Y axis, or if it is Intersecting

the X axis, or if it is intersecting both the X

and Y axis.



The code above shows the collision check for the ball. To break it down, it checks if the ball is

intersecting the X and/or Y axis and if it is ‘GOING TO’ intersect it the next frame. The reason for me to

check if it is going to intersect in the future is to determine where it is going to hit, if it is hitting the top

or bottom, the Y will be intersected first and X if it is hitting the side first.

This new collision check got rid of bug causing the ball getting stuck in the bat, but unfortunately

presented a new bug. The new bug will cause the ball to get stuck inside of the bat if the player moves

the bat the same instant/frame the ball is going to hit the side of the bat.


Adding another check, I made sure the bat will stay inside of the screen by checking if the bat WILL

move outside the width of the screen or not. If it does, the Model Class will not allow the user to move

the bat in the direction which will take the bat off screen.

Tests

To test the code, I continually played

the game the right way (which is how

the user would play the game to win)

and by trying to break the game in

the game. The code has ‘Debug

traces’ and a bunch of logs to all the

decisions the game will make, as in if

the ball is intersecting X and/or Y and

so on.

## Reflection

Looking at the final version of

the game compared to the original.

The game has come a far way. Was I

satisfied with the final version? Not as

much. Due to the time restrictions, a lot of features have been missed out on, such as super bricks that

spawn at a random time frame and once hit by the ball, it gains speed, better optimized, proper

AudioClip sounds for the hit sounds. There are a lot of bugs in the game which have not been fixed, such

as a cluster of bricks being broken right in the beginning. The thing I feel I exceled at was redesigning the

entire collision detection. Coding the game from scratch up would have given more me flexibility and

more control and a deeper understanding of the code, this would have also given me a chance to use

better access modifiers and data types that would have enhanced the memory usage and performance




of the game. Another thing this game would’ve been great would have been automated testing, making

a class that hard tests the game In the background or foreground. The game in general has been

improved a lot without the features.

A detailed commit history has also been uploaded on [Github](https://github.com/KaranVani/breakout)[.](https://github.com/KaranVani/breakout)



