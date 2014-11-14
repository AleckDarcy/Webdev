<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Map data = (Map)request.getAttribute("data");
%>
{
	"result" : <%= data.get("result") %>,
	"error_code" : "<%= data.get("error_code") %>"
}