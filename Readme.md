# CSVReader
Pulls data from Pokemon CSV to analyze data

## Compile
>sbt compile

## Test
>sbt test

## Run
>stb run

# Requirements
- [ ] Written in Scala/SBT
- [ ] Takes input from CLI
    - [ ] CSV
    - [ ] arguments
- [ ] Parses datasets into a Collection
    - [ ] Every line parsed into a class then an array
    - [ ] Every item in a line is gathered into a Class
- [ ] Analysis
    - [ ] Counts number of type pokemon
    - [ ] Prints all of every pokemon's stats or specific ones/types
- [ ] Output analysis
    - [ ] Output to STDOUT
    - [ ] Output to txt File (arguments inputed)
    

# Features
- [ ] CLI that takes args to find data in csv
- [ ] Well documented
- [ ] Logs events

# Usage 

usage: CSVReader [-paps] : print all pokemon stats
                 [-patps pokemonType] : Print all 'type' pokemons stats
                 [-gntp pokemonType] : gets the number of 'type' pokemon
                 [-ppxs pokemonName] : Prints all of a pokemon's stats
  }