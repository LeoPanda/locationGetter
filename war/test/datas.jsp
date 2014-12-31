<%@page import="jp.leopanda.locationGetter.dataStore.StoredLabel"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.leopanda.locationGetter.dataStore.StoredLocation" %>
<%@ page import="jp.leopanda.locationGetter.dataStore.StoredLabel" %>
<%@ page import="jp.leopanda.locationGetter.dataStore.StoredTrigger" %>
<%@ page import="jp.leopanda.locationGetter.dataStore.Dao" %>
<%@ page import="java.util.ArrayList"%>
<!-- データストアの内容を表示するテスト用ページ -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<% 
Dao dao = Dao.INSTANCE;
List<StoredLocation> locations = new ArrayList<StoredLocation>();
locations = dao.listStoredLocation();
%>
<% List<StoredTrigger> triggers = dao.getTrigger();%>
<div>trigger record count:<%=triggers.size() %></div>
<table border="1">
<tr><td>doneDate</td></tr>
<%for(StoredTrigger trigger:triggers){ %>
	<tr><td><%=trigger.getDoneDate() %></td></tr>
<%} %>
</table>
<div>locations record count:<%=locations.size() %></div>
<table border="1">
<tr><td>key</td><td>id</td><td>name</td><td>url</td><td>labels</td></tr>
<%for (StoredLocation location : locations) {%>
	<tr>
	<td><%=location.getKey() %></td>
	<td><%=location.getId() %></td>
	<td><%=location.getName() %></td>
	<td><%=location.getUrl() %></td>
	<td>
	<%if(location.getId() != null){%>
		<table>
		<%List<StoredLabel>labels = dao.getStoredLabels(location.getId());
			for(StoredLabel label:labels){%>
				<tr><td><%=label.getLabel() %></td></tr>
			<%} %>
		</table>
	<%}%>
	</td></tr>
<%}%>
</table>
</body>
</html>