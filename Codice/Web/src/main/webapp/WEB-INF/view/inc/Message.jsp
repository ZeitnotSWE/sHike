<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../inc/Header.jsp" />
<title>${msg.getTitle()} - Zeitweb</title>
<script>
	function goBack() {
	    window.history.back();
	}
</script>
<jsp:include page="../inc/Menu.jsp" />

			<h1>${msg.getTitle()}</h1>
			<div class="alert ${msg.getAlert().getName()}">${msg.getContent()}</div>
			<c:choose>
	   			<c:when test="${msg.isBackToList()}">
					<nav id="navbar-sec"><a href="../"><button class="btn btn-default">Torna alla Lista</button></a></nav>
				</c:when>
				<c:otherwise>
					<nav id="navbar-sec"><button onclick="goBack()" class="btn btn-default">Torna Indietro</button></nav>
				</c:otherwise>
			</c:choose>
			
<jsp:include page="../inc/Footer.jsp" />
</body>
</html>