package search

import java.util.*

class SearchEngine(val queryData: QueryData = QueryData.Factory.createFromUserInput()) { // (val data: List<List<String>> = QueryData.Factory.)
    enum class Strategies(val search: (queryData: QueryData, searchTerms: List<String>, find: (searchTerm: String, queryData: QueryData) -> Map<String, Int>) -> Map<String, Int>) {
        ALL({ queryData, searchTerms, find ->
            buildMap {
                //check the hits on all the words
                val hitsPerTerm = buildList<List<Int>> {
                    searchTerms.forEach { term ->
                        add(find(term, queryData).values.toMutableList())
                    }
                }
                //compare the hits on all teh terms and put thoose who occure in all
                hitsPerTerm.forEach { hitList ->
                    hitList.forEach { hitIndex ->
                        run {
                            var isHit = true
                            hitsPerTerm.forEachIndexed { index, it ->
                                if (index != 0 && !it.contains(hitIndex)) isHit = false
                            }
                            if (isHit) put(queryData.getFullLines()[hitIndex], hitIndex)
                        }
                    }
                }
            }
        }),
        ANY({ queryData, searchTerms, find ->
            buildMap {
                val indices = kotlin.collections.buildList {
                    searchTerms.forEach { addAll(find(it, queryData).values) }
                }.toSet()
                indices.forEach { put(queryData.getFullLines()[it], it) }
            }
        }),
        NONE({ queryData, searchTerms, find ->
            buildMap {
                val excludedIndeces = buildList {
                    searchTerms.forEach { addAll(find(it, queryData).values) }
                }.toSet()
                val indices: MutableList<Int> =
                    buildList<Int> { kotlin.repeat(queryData.data.size) { index -> add(index) } }.toMutableList()
                indices.removeAll(excludedIndeces)
                indices.forEach { put(queryData.getFullLines()[it], it) }
            }
        })
    }

    fun findWordByInvertedIndex(searchTerm: String, queryData: QueryData = this.queryData): Map<String, Int> {
        return buildMap {
            queryData.invertedIndex[searchTerm.lowercase(Locale.getDefault())]?.forEach { index ->
                put(
                    queryData.getFullLines()[index], index
                )
            }
        }
    }

    fun useSingleLineSearch(strategies: Strategies) {
        println("Enter a name or email to search all suitable people.")
        val searchTerms = ConsoleHelper.querySearchTerm().split(" ")
        val hits = strategies.search(queryData, searchTerms, ::findWordByInvertedIndex)
        ConsoleHelper.printHits(hits)
    }
}
