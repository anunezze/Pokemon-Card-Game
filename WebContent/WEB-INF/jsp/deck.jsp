
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"deck":{
		"id":${deck.id},
		"cards":[
		<c:forEach var="card" items="${deck.cards}">
		{"t":"${card.type}", "n":"${card.name}"},
		</c:forEach>
		]
	}
}