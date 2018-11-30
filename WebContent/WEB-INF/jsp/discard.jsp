<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
{
	"discard":[
	<c:forEach var="p" items="${pile}">
		${p.cardId},
	</c:forEach>
	]
}