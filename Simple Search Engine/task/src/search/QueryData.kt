package search

import java.io.File
import java.util.*

data class QueryData(
    val data: List<List<String>>
) {
    var invertedIndex: Map<String, List<Int>>

    init {
        val map = mutableMapOf<String, MutableList<Int>>()
        data.forEachIndexed { index, line ->
            line.forEach { word ->
                val wordToLowerCase = word.lowercase(Locale.getDefault())
                if (!map.containsKey(wordToLowerCase)) {
                    map[wordToLowerCase] = mutableListOf(index)
                } else {
                    map[wordToLowerCase]?.add(index)
                }
            }
        }
        invertedIndex = map
    }

    fun getFullLines(): List<String> {
        return data.map { it.joinToString(" ") }
    }

    object Factory {
        fun loadFromFile(fileName: String): QueryData {
            val fileContent = File(fileName).readLines().map { it.split(" ") }
            return QueryData(fileContent)
        }

        fun createFromUserInput(): QueryData {
            return QueryData(ConsoleHelper.queryItems(ConsoleHelper.queryItemCount()))
        }
    }
}