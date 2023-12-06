import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

const val COMMA = ","
const val COLON = ":"
const val SEMICOLON = ";"
const val SPACE = " "

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.splitTrim(delimiter: String): List<String> =
    this.trim().split(delimiter).map { it.trim() }

val numbersSeparatedBySpace = "(\\d+)".toRegex()
fun String.toListOfLong(): List<Long> =
    numbersSeparatedBySpace.findAll(this)
        .map { it.groupValues[0] }
        .map { it.toLong() }
        .toList()
