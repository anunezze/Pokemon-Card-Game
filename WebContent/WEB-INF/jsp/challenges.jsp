<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"challenges":[
	<c:forEach var="challenge" items="${challenges}">
		{
			"id":${challenge.id}, 
			"challenger":${challenge.challenger}, 
			"challengee": ${challenge.challengee}, 
			"status": ${challenge.status.ordinal()}
		},
	</c:forEach>
	]
	
}