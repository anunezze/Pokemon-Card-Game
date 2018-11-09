{
	"board":{
		"id":${game.id},
		"players":[${game.player1}, ${game.player2}],
		"decks":[${game.deck1}, ${game.deck2}],
		"play":{
			"${game.player1}":{
				"status":"${game.p1Status}",
				"handsize":${hand1.handSize},
				"decksize":${hand1.deckSize},
				"discardsize":${hand1.discardSize},
				"bench":[]
			},
			"${game.player2}":{
				"status":"${game.p2Status}",
				"handsize":${hand2.handSize},
				"decksize":${hand2.deckSize},
				"discardsize":${hand2.discardSize},
				"bench":[]
			}
		}
	}
}