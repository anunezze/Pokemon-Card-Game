<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"challenges":[
	<c:forEach var="challenge" varStatus="i" items="${challenges}">
		{
			"id":${challenge.id}, 
			"version":${challenge.version},
			"challenger":${challenge.challenger}, 
			"challengee": ${challenge.challengee}, 
			"status": ${challenge.status.ordinal()},
			"deck":${challenge.challengerDeck}
		}<c:if test="${not i.last}">, </c:if>
	</c:forEach>
	]
	
}