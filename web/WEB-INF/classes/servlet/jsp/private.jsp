<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
    <title>Private</title>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/css/foundation.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/vendor/foundation.js"></script>
    <script src="${pageContext.request.contextPath}/js/vendor/jquery.js"></script>
</head>
<body>

<h2>servlet for LK</h2>
<h4>создал ${requestScope.name}</h4>

<div class="callout primary">
    <h5>Вот тут мы и начнем.</h5>
    <c:if test="${not empty requestScope.user.firstName}">
        <p>${requestScope.user.firstName}</p>
        <p>${requestScope.user.lastName}</p>
        <p>${requestScope.user.nickName}</p>
        <p>${requestScope.user.mail}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/saveData" method="post">


        <label for="data">Введите страну
            <div class="small-9 columns">
                <input id="data" name="dataToSave" type="text" placeholder="Россия"
                       aria-describedby="exampleHelpText">
            </div>
        </label>
        <p class="help-text" id="exampleHelpText">Страну можно ввести в этом поле...</p>

        <button type="submit" class="success button">Save</button>
    </form>

    <%--Тестируем работу IF--%>
    <c:if test="${not empty requestScope.country1.name}">
        ${requestScope.country1.name}
    </c:if>
    <c:if test="${not empty requestScope.country2.name}">
        ${requestScope.country2.name}
    </c:if>

    <%--<c:forEach var="country" items="${requestScope.countries}">--%>
        <%--<p>id страны: ${country.id}, наименование страны: ${country.name} </p>--%>
    <%--</c:forEach>--%>
</div>
<div class="callout success">
    <h5>Страны в БД</h5>
    <fieldset class="large-6 columns">
        <label>Список стран в Базе Данных
            <select>
                <c:forEach var="country" items="${requestScope.countries}">
                    <p>id страны: ${country.id}, наименование страны: ${country.name} </p>
                    <option value="${country.id}">${country.name}</option>
                </c:forEach>

            </select>
        </label>
    </fieldset>

</div>
<script type="text/javascript"
        src="<html:rewrite page='${pageContext.request.contextPath}/js/vendor/foundation.js' />"></script>
<script type="text/javascript"
        src="<html:rewrite page='${pageContext.request.contextPath}/js/vendor/jquery.js' />"></script>
</body>
</html>