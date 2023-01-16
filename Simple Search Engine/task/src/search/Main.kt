package search

fun main(args: Array<String>) {
    Menu(SearchEngine(QueryData.Factory.loadFromFile(args[1])))
    //Menu()
}