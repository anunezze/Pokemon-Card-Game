
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"deck":[
		<c:forEach var="d" items="${decks}">
		${d.id},
		</c:forEach>
		]
	}
}