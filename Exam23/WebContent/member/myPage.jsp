<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Map<String, Object> loginedMember = (Map<String, Object>) request.getAttribute("loginedMember");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 - 마이페이지</title>
</head>
<body>
	<h1>회원 - 마이페이지</h1>

	<table>
	
	<td><input type="button" value="글 리스트 보기"
			onclick="location.href='../article/list.sbs'"></td>
			
		<td><a href="./add.jsp">글 작성하기</a></td>
		<td><a href="./memberModify.jsp">정보 수정하기</a></td>
		<tbody>
			<tr>
				<th>아이디</th>
				<td><%=loginedMember.get("loginId")%>
			</tr>
			<tr>
				<th>이름</th>
				<td><%=loginedMember.get("name")%>
			</tr>
		</tbody>
	</table>
</body>
</html>