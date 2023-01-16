package search

import java.util.*

object ConsoleHelper {
    private enum class ConsoleLines(val info: String) {
        QUERY_ITEM_COUNT("Enter the number of people:"),
        QUERY_ITEMS("Enter all people:"),
        QUERY_SEARCHTERM("Enter data to search people:"),
        MULTI_LINE("People found:"),
        NOT_FOUND("No matching people found.")
    }

    fun queryLineCount(): Int {
        val itemCount: Int
        try {
            itemCount = readln().toInt()
        } catch (e: NumberFormatException) {
            return queryLineCount()
        }
        return itemCount
    }

    fun queryStrategy(): SearchEngine.Strategies {
        println("Select a matching strategy: ALL, ANY, NONE")
        return try {
            SearchEngine.Strategies.valueOf(readln().uppercase(Locale.getDefault()))
        } catch (e: IllegalArgumentException) {
            queryStrategy()
        }
    }

    fun queryItemCount(): Int {
        println(ConsoleLines.QUERY_ITEM_COUNT.info)
        return queryLineCount()
    }

    fun queryItems(count: Int): List<List<String>> {
        println(ConsoleLines.QUERY_ITEMS.info)
        return List(count) {
            readln().split(" ")
        }
    }

    fun querySearchTerm(): String {
        println(ConsoleLines.QUERY_SEARCHTERM.info)
        return readln().trim()
    }

    fun printHits(hits: Map<String, Int>) {
        if (hits.size == 0) println(ConsoleLines.NOT_FOUND.info) else {
            println(ConsoleLines.MULTI_LINE.info)
            hits.forEach { println(it.key) }
        }
    }
}