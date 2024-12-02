import kotlin.random.Random

enum class Suit(val symbol: String, val color: String) {
    HEARTS("♥", "\u001B[31m"),
    DIAMONDS("♦", "\u001B[31m"),
    CLUBS("♣", "\u001B[30m"),
    SPADES("♠", "\u001B[30m")
}

enum class Rank(val symbol: String) {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
    EIGHT("8"), NINE("9"), TEN("T"), JACK("J"), QUEEN("Q"), KING("K")
}

data class Card(val suit: Suit, val rank: Rank) {
    override fun toString(): String {
        return "${suit.color}${rank.symbol}${suit.symbol}\u001B[0m"
    }

    fun toFixedWidthString(): String {
        return String.format("${suit.color}%2s\u001B[0m", "${rank.symbol}${suit.symbol}")
    }
}

class Solitaire {
    private val deck = mutableListOf<Card>()
    private val tableau = Array(7) { mutableListOf<Card>() }
    private val foundation = Array(4) { mutableListOf<Card>() }
    private val waste = mutableListOf<Card>()
    private var stock = mutableListOf<Card>()

    init {
        initializeDeck()
        dealCards()
    }

    private fun initializeDeck() {
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                deck.add(Card(suit, rank))
            }
        }
        deck.shuffle(Random)
    }

    private fun dealCards() {
        for (i in tableau.indices) {
            for (j in 0..i) {
                tableau[i].add(deck.removeAt(0))
            }
        }
        stock.addAll(deck)
    }

    fun play() {
        while (true) {
            displayGame()
            println("\nEnter command (draw, move <from> <to>, quit):")
            val input = readLine()?.toLowerCase()?.split(" ") ?: continue

            when (input[0]) {
                "draw" -> drawCard()
                "move" -> {
                    if (input.size == 3) {
                        moveCard(input[1], input[2])
                    } else {
                        println("Invalid move command. Use 'move <from> <to>'")
                    }
                }
                "quit" -> break
                else -> println("Invalid command")
            }

            if (checkWinCondition()) {
                println("Congratulations! You've won!")
                break
            }
        }
    }

    private fun displayGame() {
        println("\nFoundation:")
        for (i in foundation.indices) {
            print("F$i: ")
            if (foundation[i].isNotEmpty()) {
                print(foundation[i].last().toFixedWidthString())
            } else {
                print("[ ]")
            }
            print("  ")
        }

        println("\n\nTableau:")
        val maxTableauSize = tableau.maxOf { it.size }
        for (row in 0 until maxTableauSize) {
            for (col in tableau.indices) {
                if (row < tableau[col].size) {
                    print(tableau[col][row].toFixedWidthString() + " ")
                } else {
                    print("   ")
                }
            }
            println()
        }
        println("T0 T1 T2 T3 T4 T5mo T6")

        println("\nWaste: ${if (waste.isNotEmpty()) waste.last().toFixedWidthString() else "[ ]"}")
        println("Stock: ${if (stock.isNotEmpty()) stock.size else "Empty"}")
    }

    private fun drawCard() {
        if (stock.isEmpty()) {
            stock = waste.asReversed().toMutableList()
            waste.clear()
        } else {
            waste.add(stock.removeAt(stock.lastIndex))
        }
    }

    private fun moveCard(from: String, to: String) {
        val sourceCard = when {
            from.startsWith("t") -> {
                val index = from.substring(1).toIntOrNull() ?: return
                if (index in tableau.indices && tableau[index].isNotEmpty()) {
                    tableau[index].last()
                } else null
            }
            from == "w" -> if (waste.isNotEmpty()) waste.last() else null
            else -> null
        }

        if (sourceCard == null) {
            println("Invalid source")
            return
        }

        when {
            to.startsWith("f") -> {
                val index = to.substring(1).toIntOrNull() ?: return
                if (index in foundation.indices) {
                    if (canMoveToFoundation(sourceCard, foundation[index])) {
                        foundation[index].add(sourceCard)
                        removeCardFromSource(from)
                    } else {
                        println("Invalid move to foundation")
                    }
                }
            }
            to.startsWith("t") -> {
                val index = to.substring(1).toIntOrNull() ?: return
                if (index in tableau.indices) {
                    if (canMoveToTableau(sourceCard, tableau[index])) {
                        tableau[index].add(sourceCard)
                        removeCardFromSource(from)
                    } else {
                        println("Invalid move to tableau")
                    }
                }
            }
            else -> println("Invalid destination")
        }
    }

    private fun canMoveToFoundation(card: Card, pile: List<Card>): Boolean {
        return when {
            pile.isEmpty() -> card.rank == Rank.ACE
            else -> {
                val topCard = pile.last()
                card.suit == topCard.suit && card.rank.ordinal == topCard.rank.ordinal + 1
            }
        }
    }

    private fun canMoveToTableau(card: Card, pile: List<Card>): Boolean {
        return when {
            pile.isEmpty() -> card.rank == Rank.KING
            else -> {
                val topCard = pile.last()
                card.suit.color != topCard.suit.color && card.rank.ordinal == topCard.rank.ordinal - 1
            }
        }
    }

    private fun removeCardFromSource(from: String) {
        when {
            from.startsWith("t") -> {
                val index = from.substring(1).toIntOrNull() ?: return
                if (index in tableau.indices && tableau[index].isNotEmpty()) {
                    tableau[index].removeAt(tableau[index].lastIndex)
                }
            }
            from == "w" -> {
                if (waste.isNotEmpty()) {
                    waste.removeAt(waste.lastIndex)
                }
            }
        }
    }

    private fun checkWinCondition(): Boolean {
        return foundation.all { it.size == 13 }
    }
}

fun main() {
    val game = Solitaire()
    game.play()
}