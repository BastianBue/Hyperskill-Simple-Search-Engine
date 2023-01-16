val lambda: (Long, Long) -> Long = { border1, border2 ->
    var result: Long = border1
    if (border1 != border2) {
        for (i in border1..border2) {
            if (i != border1) result *= i
        }
    }
    result
}
