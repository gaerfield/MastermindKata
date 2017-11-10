package org.swk.mastermind

class Mastermind(private vararg val secret : Color) {

    enum class Color { RED, BLUE, YELLOW, GREEN, PURPLE, PINK }
    data class EvaluationResult(val wellPlaced: Int, val correctButMisplaced: Int)

    fun evaluate(vararg guess : Color): EvaluationResult {
        if(secret.size != guess.size)
            throw IllegalArgumentException("Du Trottel!")

        val wellPlaced = countWellPlaced(guess)
        val correctButMisplaced = Math.max(0, countCorrectColors(guess) - wellPlaced)

        return EvaluationResult(wellPlaced, correctButMisplaced)
    }

    private fun countWellPlaced(guess: Array<out Color>) = secret
            .mapIndexed { index, color -> if (guess[index] == color) 1 else 0 }
            .sum()

    private fun countCorrectColors(guess: Array<out Color>) : Int {
        val result = mutableListOf<Color>()
        val clone = secret.toMutableList()
        guess.forEach {
            if(clone.contains(it)) {
                result.add(it)
                clone.remove(it)
            }
        }
        return result.size
    }



}


