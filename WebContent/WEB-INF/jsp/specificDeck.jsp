<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"cards":[
		<c:forEach var="card" items="${cards}">
		{
			"id":${card.id}, 
			"t":"${card.type}", 
			"n":"${card.name}",
			<c:if test="${not empty card.base }">
			"b":${card.base },
			</c:if>
		},
		</c:forEach>
		]
	}
}