<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"games":[
		<c:forEach var="g" items="${games}">
		{
			"id":${g.id}, 
			"version":${g.version} }
			"players":[${g.player1},${g.player2}]
		},
		</c:forEach>
	]
}