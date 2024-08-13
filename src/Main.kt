import java.io.File
import kotlin.random.Random

const val AMOUNT_OF_WORDS = 59443
const val AMOUNT_OF_TRIES = 7
val random = Random(System.currentTimeMillis())
val ALL_RUSSIAN_LETTERS = listOf("а","А","б","Б","в","В","г","Г","д","Д","е","Е","ё","Ё","ж","Ж","з","З","и","И","й","Й","к","К","л","Л","м","М","н","Н","о","О","п","П","р","Р","с","С","т","Т","у","У","ф","Ф","х","Х","ц","Ц","ч","Ч","ш","Ш","щ","Щ","ъ","Ъ","ы","Ы","ь","Ь","э","Э","ю","Ю","я","Я")


fun main(){
    checkUserInput()
}

fun checkUserInput(){
    while(true) {
        val answerFromUser = getUserInput()
        if (answerFromUser == "y"){
            startGameLoop()
        }
        else if (answerFromUser == "n"){
            break
        }
        else{
            println("Введите 'y' или 'n'")
        }
    }
}

fun startGameLoop(){
    val gameWord = createWord()
    var guessedPart = MutableList(gameWord.length){"*"}
    var failedTries = 0

    while (failedTries < AMOUNT_OF_TRIES){
        printGameData(failedTries, guessedPart.joinToString(""))
        makeTurn(gameWord, guessedPart)
    }
    println("Загаданное слово: $gameWord")

}

fun makeTurn(word: String, guessedPart: MutableList<String>) : List<String>{
    print("Введите букву: ")
    val enteredLetter = getLetterInput()
    for (i  in 0..<word.length){
        if (word[i] == enteredLetter[i]){
            guessedPart[i] = word[i].toString()
        }
    }
    return guessedPart

}

fun getLetterInput() : String{
    while (true){
        val userInput = readln()
        if (userInput.isNotEmpty() && userInput in ALL_RUSSIAN_LETTERS) {
            return userInput
        }
    }
}

fun printGameData(badTries : Int, currentWord : String){
    println("Текущее состояние виселицы:")
    println(HANGMAN_PICS[badTries])
    println("Слово: $currentWord")
    println("Количество неудачных попыток: $badTries")
    println("Количество оставшихся попыток: ${AMOUNT_OF_TRIES - badTries}")
    println()
}

fun getUserInput() : String{
    println("Вы хотите начать игру? (y - да / n - нет)")
    val answer = readlnOrNull().toString()
    return answer
}

fun createWord() : String{
    val randomNumber = random.nextInt(AMOUNT_OF_WORDS)
    val words = File("src/words.txt").bufferedReader()
    var word : String = ""
    for (item in 1..randomNumber) {
        word = words.readLine()
    }
    return word


}