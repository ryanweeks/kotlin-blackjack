package blackjack

class BlackJack {
    val deck = mutableListOf<String>()
    val playerHand = mutableListOf<String>()
    val dealerHand = mutableListOf<String>()
    var wins: Float
    var losses: Float
    var draws: Float
    var busts: Float

    // Initialize the deck
    init {
        this.resetDecks()
        wins = 0F
        losses = 0F
        draws = 0F
        busts = 0F
    }

    fun shuffleDeck(){
        deck.shuffle()
    }

    // Move a card from deck to hand
    fun drawCard(playerTurn: Boolean) {
        val card = deck[0]
        deck.removeAt(0)
        if (playerTurn) { // player turn
            playerHand.add(card)
        } else { // player
            dealerHand.add(card)
        }
    }

    fun calculateScore() {

    }

    fun printStatus() {

    }

    fun resetStats() {
        this.wins = 0F
        this.losses = 0F
        this.draws = 0F
        this.busts = 0F
    }
    fun resetDecks() {
        deck.clear()
        playerHand.clear()
        dealerHand.clear()

        deck.add("A")
        deck.add("2")
        deck.add("3")
        deck.add("4")
        deck.add("5")
        deck.add("6")
        deck.add("7")
        deck.add("8")
        deck.add("9")
        deck.add("10")
        deck.add("J")
        deck.add("Q")
        deck.add("K")
    }
}