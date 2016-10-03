<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageUrl"     required="true" rtexprvalue="true" description="the URI with the page" %>

<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true" description="the name of the key in Request domain" %>

<c:set var="pageUrl" value="${pageUrl}"/>

<div >
    Total Page: ${page}
</div>
