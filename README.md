# 4D-Minesweeper
### A 4-Dimensional Minesweeper game using base Java 6 and Swing

This game was originally made by
[Alejandro Ramos](https://github.com/aeramos),
[Bowen Li](https://github.com/P250-SandDune), and
[Chris Evagora](https://github.com/evagorac)
for a high school AP Computer Science class. We were given the option to make a
Minesweeper game for one of our final projects, and for one extra credit point
we could make it in 3D. We decided to make it in 4D to look cool. I think our
teacher forgot to give us the extra credit, but it was still fun to do!

The game is written in base Java 6 (including Swing) because the class only
taught Java 6 so we wanted to keep to that soft requirement.

Most of the internal code, including the terminal version of the game, was
written by Alejandro Ramos. All of the GUI code was very smartly written by
Bowen Li with Chris Evagora.

### N-Dimensional Minesweeper
The following Fall for a hackathon, Alejandro Ramos and Bowen Li decided to try
to make N-Dimensional Minesweeper based on 4D Minesweeper, allowing the user to
choose how many dimensions he/she wants to play the game with. Bowen designed an
algorithm and wrote code so that given the theoretical nD position of a space,
we could get the position of that space in a 1D array that actually held the
board. Working around this code we started rewriting the 4D Minesweeper code so
that it could handle n dimensions.

Please note that the N-Dimensional Minesweeper code is located in the
n-dimensional branch in this git repository.