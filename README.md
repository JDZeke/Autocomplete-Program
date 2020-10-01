# Autocomplete-Program

This program was made for the "Mobile Device Keyboard" Asymmetrik Challege (https://asymmetrik.com/programming-challenges/)

This program is meant to mimic the functionality of the text autocompletion of a simple mobile device. The algorithm learns its users behavior over time in order to suggest words based on word fragments. This program is made up of three classes: 

1. AutocompleteProvider.java handles most of the work, including the data structure used to store the users prefrences and the algorithm to get word recommendations.
2. Candidate.java is used to store word candidates that will be returned to the user. Each word candidate has a confidence level that reflects which words the program recommends over others.
3. Driver.java is a simple command line interface for training the algorithm and getting recommendations.

In order to run the command line interface to test the program, you need to have Java (version > 8) installed. After that, use the command "javac Driver.java" to compile the program and then "java Driver" to run it. For the program to compile, you may need to have the Java/jdk/bin folder set in your environment PATH variable.
