<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"hand":[
	<c:forEach var="card" items="${cards}">
		card.id,
	</c:forEach>
	]
}