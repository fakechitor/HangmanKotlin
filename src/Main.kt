import java.io.File
import kotlin.random.Random

const val AMOUNT_OF_WORDS = 21974
const val AMOUNT_OF_TRIES = 7
private val random = Random(System.currentTimeMillis())

private var failedTries = 0
private var amountOfWins = 0
private var amountOfLosses = 0
private var enteredLetters : List<String> = listOf()

fun main(){
    checkUserInput()
}

fun checkUserInput(){
    while(true) {
        val answerFromUser = getUserInputForStart()
        if (answerFromUser.toLowerCase() == "y" || answerFromUser.toLowerCase() == "д"){
            startGameLoop()
        }
        else if (answerFromUser.toLowerCase() == "n" || answerFromUser.toLowerCase() == "н"){
            break
        }
        else{
            println("Введите 'y'/'д' или 'n'/'н'")
        }
    }
}

fun getUserInputForStart() : String{
    println("Вы хотите начать игру? (y | д - да ) (n | н - нет)")
    val answer = readlnOrNull().toString()
    return answer
}

fun startGameLoop(){
    val gameWord = createWord()
    var guessedPart = MutableList(gameWord.length){"*"}
    while (failedTries < 7 && guessedPart.contains("*")){
        println(gameWord)
        printGameData(failedTries, guessedPart.joinToString(""))
        guessedPart = makeTurn(gameWord, guessedPart)
    }
    failedTries = 0
    enteredLetters = listOf()
    printGameEndInfo(guessedPart, gameWord)
    printGameStats()

}

fun printGameStats() {
    val totalGames = amountOfLosses + amountOfWins
    val percentage = (amountOfWins.toDouble()/totalGames.toDouble()) * 100.0
    println("Проведено игр всего: $totalGames")
    println("Побед : $amountOfWins")
    println("Поражений : $amountOfLosses")
    println("Процент побед: $percentage%")
    println()
}

fun printGameEndInfo(guessedPart: MutableList<String>, gameWord: String) {
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

fun makeTurn(word: String, guessedPart: MutableList<String>) : MutableList<String>{
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

fun printGameData(badTries : Int, currentWord : String){
    println("Текущее состояние виселицы:")
    println(HANGMAN_PICS[badTries])
    println("Слово: $currentWord")
    println("Использованные буквы: ${enteredLetters.joinToString(" ")}")
    println("Количество неудачных попыток: $badTries")
    println("Количество оставшихся попыток: ${AMOUNT_OF_TRIES - badTries}")
    println()
}

fun getLetterInput() : String{
    while (true){
        print("Введите букву: ")
        val userInput = readln()
        if (userInput.isNotEmpty() && userInput in ALL_RUSSIAN_LETTERS) {
            return userInput
        }
        else{
            println("Введите букву русского алфавита!")
        }
    }
}

fun createWord() : String{
    val randomNumber = random.nextInt(AMOUNT_OF_WORDS)
    val words = File("src/words.txt").bufferedReader()
    var word = ""
    for (item in 1..randomNumber) {
        word = words.readLine()
    }
    return word


}