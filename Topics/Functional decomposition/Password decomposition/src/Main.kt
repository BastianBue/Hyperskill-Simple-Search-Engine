fun validatePassword(password: String): Boolean {
    // write your code here
    if (password.length < 5) {
        println("Your password should be longer than five characters, please write a new password")
        return false
    }
    println("Good password")
    return true
}

// do not change the code below
fun main() {
    var validationResult = false
    while (!validationResult) {
        val password = readln()
        validationResult = validatePassword(password)
    }
}