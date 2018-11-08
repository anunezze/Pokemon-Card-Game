
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

{
	"players":[
		<c:forEach var="p" items="${players}">
		{
			"id":${p.id}, 
			"user":"${p.username}"
		},
		</c:forEach>
	]
}