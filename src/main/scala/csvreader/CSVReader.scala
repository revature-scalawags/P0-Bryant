package csvreader

import scala.collection.mutable.Map
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//added imports
import scala.collection.mutable.ListBuffer
import java.io.File
import java.io.PrintWriter
import org.log4s._

//Class to hold stats
class PokemonStats(nNum:String, nName:String, nType1:String, nType2:String, nTotal:String, nHp:String, nAttk:String, nDefense:String, nSpAttk:String, nSpDef:String, nSpeed:String, nGen:String, nLegend:String){
  //Empty Constructor for testing
  def pokemonStats(){
    println("Empty Constructor")
  }
  
  val num:String = nNum
  val name:String = nName
  val type1:String = nType1
  val type2:String = nType2
  val total:String = nTotal
  val hp:String = nHp
  val attk:String = nAttk
  val defense:String = nDefense
  val spAttk:String = nSpAttk
  val spDef:String = nSpDef
  val speed:String = nSpeed
  val gen:String = nGen
  val legend:String = nLegend
}

object CSVReader extends App{

  //Print all of a single pokemon's stats
  def printPokeStats(stats:PokemonStats){
    println("Pokemon Number: " + stats.num)
    println("Pokemon Name: " + stats.name)
    println("Pokemon type one: " + stats.type1)
    if(stats.type2.isBlank()){
      println("Pokemon type two: None")
    }
    else{
      println("Pokemon type two: " + stats.type2)
    }
    println("Total stats value: " + stats.total)
    println("Hit Points: " + stats.hp)
    println("Attack: " + stats.attk)
    println("Defence: " + stats.defense)
    println("Special Attack: " + stats.spAttk)
    println("Special Defense: " + stats.spDef)
    println("Speed: " + stats.speed)
    println("Generation: " + stats.gen)
    println("Legendary status: " + stats.legend)
  }

  //Print Pokemon in Arr stats
  def printArrPokeStats(pokeArr:ListBuffer[PokemonStats]){
    for(poke <- pokeArr){
      printPokeStats(poke)
    }
  }

  //Number of a type of pokemon specified by user
  def numTypeXPokemon(pokemonType:String, pokemonList:ListBuffer[PokemonStats]){
    var numPokeType = 0;
    for(poke <- pokemonList){
      if(poke.type1 == pokemonType.toLowerCase || poke.type2 == pokemonType.toLowerCase()){
        numPokeType = numPokeType + 1
      }
    }
    println("There are " + numPokeType + " " + pokemonType + " Pokemon.")
  }

  //Save arguments to new txt file (pulled from online)
  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  //Print usage message
  def printUsageMsg(){
    println("Type quit to exit.")
    println("usage: CSVReader [-paps] : print all pokemon stats")
    println("                 [-patps pokemonType] : Print all 'type' pokemons stats")
    println("                 [-gntp pokemonType] : gets the number of 'type' pokemon")
    println("                 [-ppxs pokemonName] : Prints all of a pokemon's stats")
  }

  //Logger 
  val CSVRLogger:Logger = org.log4s.getLogger
  
  //Set up file io and info
  val file = io.Source.fromFile("pokemon.csv")
  //total number of pokemon in csv
  val numLinesCSV = io.Source.fromFile("pokemon.csv").getLines.size
  CSVRLogger.debug(s"(Line 93) Number of lines in CSV: $numLinesCSV")
  //number of stats (rows) 
  val numRows = 13
  //List to hold all the pokemon and stats
  var pokemonList = new ListBuffer[PokemonStats]()

  //List of pokemon types for input validation
  val pokeTypes:List[String] = List("normal", "fire", "water", "grass",
                                    "electric", "ice", "fighting", "poison",
                                    "ground", "flying", "psychic", "bug", "rock",
                                    "ghost", "dark", "dragon", "steel", "fairy")
  
  //parse file for data and put into class
  for(line <- file.getLines){
    var row = line.split(",")
    //Each element of row is now a word in a file line which is passed into pokemon stats constructor
    var newPoke = new PokemonStats(row(0), row(1).toLowerCase(), row(2).toLowerCase(), row(3).toLowerCase(), row(4), row(5), row(6), row(7), row(8), row(9), row(10), row(11), row(12).toLowerCase())
    pokemonList += newPoke
  }
  val argLength = args.length
  CSVRLogger.debug(s"(Line 120) Number of args: $argLength")
  if(argLength == 0){
    printUsageMsg()
  }
  if(args.length == 1){
    var param:String = args(0)
    param match{
      //print all of every pokemon's stats
      case "-paps" => printArrPokeStats(pokemonList)

      case "help" => printUsageMsg()
      //Case when input doesnt match any cases
      case _ => println("Invalid argument print usage")
    }
  }
  if(args.length == 2){
    var param1:String = args(0).toLowerCase()
    var param2:String = args(1).toLowerCase()
    if(param1 == "-patps"){
      for(poke <- pokemonList){
        if(poke.type1.toLowerCase == param2 || poke.type2.toLowerCase() == param2){
          printPokeStats(poke)
        }
      }
    }
    if(param1 == "-gntp"){
      if(pokeTypes.contains(param2)){
        numTypeXPokemon(param2, pokemonList)
      }
      else{
        println("Second parameter is not accepted")
      }
    }
    if(param1 == "-ppxs"){
      //check to see if pokemon is found
      var foundPoke:Boolean = false
      for(poke <- pokemonList){
        if(poke.name.toLowerCase() == param2){
          CSVRLogger.debug("(Line 158) Attempting to print pokemon stats.")
          printPokeStats(poke)
          foundPoke = true
        }
      }
      if(foundPoke == false){
        println("Pokemon not found")
      }  
    }
  } 
  if(argLength > 2){
    println("Too many arguments")
    //Test env variables
    println("...But since we are here, here's the environment variable test")
    val username = System.getenv("USERNAME")
    println("username is: " + username)
    val javaHome = System.getenv("JAVA_HOME")
    println("Java_Home: " + javaHome)  
    val operatingSys = System.getenv("OS")
    println("OS: " + operatingSys)    
  }
  //write args to file
  println("Opening and writing to new file.")
  printToFile(new File("argData.txt")){
    p => args.foreach(p.println)
  }

  println("Exiting program.")
 }

