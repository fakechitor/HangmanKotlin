import java.io.File
import kotlin.random.Random

const val AMOUNT_OF_WORDS = 21974
const val AMOUNT_OF_TRIES = 7
private const val COMMAND_START_ENG = "y"
private const val COMMAND_START_RUS = "д"
private const val COMMAND_QUIT_ENG = "n"
private const val COMMAND_QUIT_RUS = "н"
private val random = Random(System.currentTimeMillis())
private var failedTries = 0
private var amountOfWins = 0
private var amountOfLosses = 0
private var enteredLetters : List<String> = listOf()

fun main(){
    askUserAboutStartGame()
}

private fun askUserAboutStartGame(){
    while(true) {
        val answerFromUser = getUserInputForStart()
        if (isStartCommand(answerFromUser)){
            startGameLoop()
        }
        else if (isEndCommand(answerFromUser)){
            break
        }
        else{
            println("Введите 'y'/'д' или 'n'/'н'")
        }
    }
}

private fun getUserInputForStart() : String{
    println("Вы хотите начать игру? ($COMMAND_START_ENG | $COMMAND_START_RUS - да ) ($COMMAND_QUIT_ENG | $COMMAND_QUIT_RUS - нет)")
    val answer = readlnOrNull().toString()
    return answer
}

private fun isStartCommand(answerFromUser:String) : Boolean {
    return answerFromUser.toLowerCase() == COMMAND_START_ENG || answerFromUser.toLowerCase() == COMMAND_START_RUS;
}


private fun isEndCommand(answerFromUser:String) : Boolean {
    return answerFromUser.toLowerCase() == COMMAND_QUIT_ENG || answerFromUser.toLowerCase() == COMMAND_QUIT_RUS;
}
private fun startGameLoop(){
    val gameWord = createWord()
    var guessedPart = MutableList(gameWord.length){"*"}
    while (failedTries < 7 && guessedPart.contains("*")){
        printGameData(failedTries, guessedPart.joinToString(""))
        guessedPart = makeTurn(gameWord, guessedPart)
    }
    failedTries = 0
    enteredLetters = listOf()
    printGameEndInfo(guessedPart, gameWord)
    printGameStats()

}

private fun printGameStats() {
    val totalGames = amountOfLosses + amountOfWins
    val percentage = (amountOfWins.toDouble()/totalGames.toDouble()) * 100.0
    println("Проведено игр всего: $totalGames")
    println("Побед : $amountOfWins")
    println("Поражений : $amountOfLosses")
    println("Процент побед: $percentage%")
    println()
}
private fun printGameEndInfo(guessedPart: MutableList<String>, gameWord: String) {
    println()
    if (guessedPart.contains("*")){
        println("Вы проиграли :(")
        amountOfLosses++
    }
    else{
        println("Поздравляем  с победой!!")
        amountOfWins++
    }
    println("Загаданное слово: $gameWord")
    println()

}

private fun makeTurn(word: String, guessedPart: MutableList<String>) : MutableList<String>{
    val enteredLetter = getLetterInput()
    var flag = false
    for (i  in 0..<word.length){
        if (word[i].toString() == enteredLetter.toLowerCase()){
            guessedPart[i] = word[i].toString()
            flag = true
        }
        else if (enteredLetters.contains(enteredLetter.toLowerCase())){
            flag = true
        }
    }
    if(!flag){
        enteredLetters = enteredLetters.plus(enteredLetter.toLowerCase())
        failedTries++
    }
    return guessedPart

}

private fun printGameData(badTries : Int, currentWord : String){
    println("Текущее состояние виселицы:")
    println(HANGMAN_PICS[badTries])
    println("Слово: $currentWord")
    println("Использованные буквы: ${enteredLetters.joinToString(" ")}")
    println("Количество неудачных попыток: $badTries")
    println("Количество оставшихся попыток: ${AMOUNT_OF_TRIES - badTries}")
    println()
}

private fun getLetterInput() : String{
    while (true){
        print("Введите букву: ")
        val userInput = readln().toCharArray()
        if (isCyrillic(userInput[0])) {
            return userInput[0].toString()
        }
        println("Введите букву русского алфавита!")

    }
}

private fun isCyrillic(symbol: Char) : Boolean {
    return symbol in 'а'..'я' || symbol in 'А'..'Я'
}

private fun createWord() : String{
    val randomNumber = random.nextInt(AMOUNT_OF_WORDS)
    val words = File("src/words.txt").bufferedReader()
    var word = ""
    for (item in 1..randomNumber) {
        word = words.readLine()
    }
    return word
}