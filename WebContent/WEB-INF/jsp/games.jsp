<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"games":[
		<c:forEach var="g" varStatus="i" items="${games}">
		{
			"id":${g.id}, 
			"version":${g.version},
			"players":[${g.player1},${g.player2}]
		}
		<c:if test="${not i.last}">, </c:if>
		</c:forEach>
	]
}