package search

class Menu(searchEngine: SearchEngine = SearchEngine()) {
    init {
        do {
            println("=== Menu ===")
            println(
                "1. Search information\n" + "2. Print all data.\n" + "0. Exit.\n"
            )
            val line = readln().toIntOrNull()
            when (line) {
                1 -> {
                    val strategy: SearchEngine.Strategies = ConsoleHelper.queryStrategy()
                    searchEngine.useSingleLineSearch(strategy)
                }

                2 -> {
                    println("=== List of People ===")
                    searchEngine.queryData.getFullLines().forEach { println(it) }
                }

                else -> continue
            }

        } while (line != 0)
    }
}