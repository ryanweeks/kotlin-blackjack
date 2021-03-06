package blackjack

// Class to play a simple game of blackjack
class BlackJack {

    // val - Read only
    // var - Mutable
    private var deck = mutableListOf<String>()
    private var playerHand = mutableListOf<String>()
    private var dealerHand = mutableListOf<String>()
    private var games = 0
    private var wins = 0
    private var losses = 0
    private var draws = 0
    private var busts = 0

    // Default initializer
    // Initialize the deck
    init {
        this.resetDecks()
        this.resetStats()
    }

    // Move a card from deck to hand
    private fun drawCard(playerTurn: Boolean) {
        val card = deck[0]
        deck.removeAt(0)
        if (playerTurn) { // player turn
            playerHand.add(card)
        } else { // player
            dealerHand.add(card)
        }
    }

    // Calculates score of a users hand
    private fun calculateScore(hand: MutableList<String>): Int {
    	var score = 0
		var hasAce = false
		for(i in hand.indices){
            val card = hand[i]
            try {
                score += card.toInt()
            } catch(e: Exception) {
                if (card == "A") {
                    hasAce = true
                }
                else if (card == "J" || card == "Q" || card == "K"){
                    score += 10
                }
            }
		}
		if (hasAce){
            score += if (score + 11 > 21){
                1
            } else{
                11
            }
		}
		return score
    }

    // Prints the status of both the player's and dealer's hands
    private fun printStatus() {
    	println("Player's Cards: ")

		for(card in playerHand){
			print("$card ")
		}
		println("\nPlayer's Score: ${calculateScore(playerHand)}")
		println("Dealer's Cards: ")
		for(card in dealerHand){
			print( "$card ")
		}
		println("\nDealer's Score: ${calculateScore(dealerHand)}")
    }

    // Resets all stats to 0
    fun resetStats() {
        this.wins = 0
        this.losses = 0
        this.draws = 0
        this.busts = 0
    }

    //Resets the deck & hands
    private fun resetDecks() {
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

    // Displays stats
    fun displayStats() {
        println("Games Played: $games")
        println("Wins: $wins")
        println("Draws: $draws")
        println("Losses: $losses")
        println("Busts: $busts")
    }

    // Used when player enters 'H' in play() game loop
    private fun playerHits(): Boolean{
        var busted = false
        println("User hits")
        drawCard(true)
        printStatus()

        if (calculateScore(playerHand) > 21){
            println("You busted! You lose!")
            losses ++
            busts ++
            busted = true // ends game
        }

        return busted
    }

    // Determines the final result at the end of play()
    private fun determineGameResult(){
        val dealerScore = calculateScore(dealerHand)
        val playerScore = calculateScore(playerHand)

        // Checks winner
        if (dealerScore > 21) { // Dealer busts
            println("Dealer busts! You win!")
            wins ++
        } else if(dealerScore > playerScore) {
            println("Dealer wins!")
            losses ++
        } else if(dealerScore < playerScore) {
            println("You win!")
            wins++
        } else {
            println("It's a draw!")
            draws ++
        }
    }

    // Plays a full game
    fun play(){
        var playing = true
        var busted = false
        resetDecks()
        deck.shuffle()

        println("Dealer draws the first card.")
        drawCard(false)

        println("Player draws next two cards.")
        drawCard(true)
        drawCard(true)

        printStatus()

        // Player loop
        while(playing && !busted) {
            println ("Do you want to (H)it, (S)tay, or (Q)uit?")
            val input = readLine().toString().toUpperCase()

            // Switch statement in Kotlin
            when (input){
                "H" -> busted = playerHits() // playerHits() returns true if busted
                "S" -> { // User decides to stay
                    println("User stays")
                    playing = false // ends game
                }
                "Q" -> { // User decides to leave current game
                    println("Quitting current game")
                    return // ends function
                }
                else -> println("Invalid input")
            }
        }

        if(!busted) {
            println("Dealer draws rest of cards.")
            while (calculateScore(dealerHand) < 17) {
                drawCard(false)
            }
            printStatus()
            determineGameResult()
        }
        games ++
    }
}