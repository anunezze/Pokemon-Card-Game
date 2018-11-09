<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"games":[
		<c:forEach var="g" items="${games}">
		{
			"id":${g.id}, 
			"players":[${g.player1},${g.player2}],			
			"deck1":${g.deck1},
			"deck2":${g.deck2}
		},
		</c:forEach>
	]
}