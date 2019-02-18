package blackjack

class BlackJack {
    private val deck = mutableListOf<String>()
    private val playerHand = mutableListOf<String>()
    private val dealerHand = mutableListOf<String>()
    private var games = 0
    private var wins = 0
    private var losses = 0
    private var draws = 0
    private var busts = 0

    // Initialize the deck
    init {
        this.resetDecks()
        this.resetStats()
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

    fun calculateScore(hand: MutableList<String>): Int {
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

    fun printStatus() {
    	println("Player's Cards: ")
		for(i in playerHand.indices){
			print("$playerHand[i] ")
		}
		println("Player's Score: ${calculateScore(playerHand)}")
		println("Dealer's Cards: ")
		for(i in dealerHand.indices){
			print( "$dealerHand[i] ")
		}
		println("Dealer's Score: ${calculateScore(dealerHand)}")
	
    }

    fun resetStats() {
        this.wins = 0
        this.losses = 0
        this.draws = 0
        this.busts = 0
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

    fun displayStats() {
        println("Games Played: $games")
        println("Wins: $wins")
        println("Draws: $draws")
        println("Losses: $losses")
        println("Busts: $busts")
    }

    fun playerHits(): Boolean{
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

    fun determineGameResult(){
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

    fun play(){
        var playing = true
        resetDecks()
        deck.shuffle()

        println("Dealer draws the first card.")
        drawCard(false)

        println("Player draws next two cards.")
        drawCard(true)
        drawCard(true)

        printStatus()

        // Player loop
        while(playing) {
            println ("Do you want to (H)it, (S)tay, or (Q)uit?")
            val input = readLine()

            // Switch statement in Kotlin
            when (input){
                "H" -> playing = !playerHits() // playerHits() returns true if busted
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

        println("Dealer draws rest of cards.")
        while (calculateScore(dealerHand) < 17) {
            drawCard(false)
        }

        printStatus()
        determineGameResult()
        games ++
    }
}