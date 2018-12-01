<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"game":{
		"id":${game.id},
		"version":${game.version},
		"players":[${game.player1}, ${game.player2}],
		"decks":[${game.deck1}, ${game.deck2}],
		"play":{
			"${game.player1}":{
				"status":"${game.p1Status}",
				"handsize":${hand1.handSize},
				"decksize":${hand1.deckSize},
				"discardsize":${hand1.discardSize},
				"bench":[
				<c:forEach var="b1" items="${bench1}">
					{
						"id":${b1.pokemonId}
						<c:if test="${not empty b1.energies}">
						, "e":[
							<c:forEach var="energyId" items="${b1.energies}]">
							${energyId},
							</c:forEach>
						]
						</c:if>
						<c:if test="${not empty b1.base}">
						,"b": ${b1.base}
						</c:if>
					},
				</c:forEach>
				]
			},
			"${game.player2}":{
				"status":"${game.p2Status}",
				"handsize":${hand2.handSize},
				"decksize":${hand2.deckSize},
				"discardsize":${hand2.discardSize},
				"bench":[
				<c:forEach var="b2" items="${bench2}">
					{
						"id":${b2.pokemonId}
					},
				</c:forEach>
				]
			}
		}
	}
}