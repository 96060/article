
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	Map<String, Object> loginedMember = (Map) request.getSession().getAttribute("loginedMember");
%>

<script>
	function getInformation(loginedMemberLoginId) {

	}
</script>

<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>

</head>

<body>
	<h1>회원 정보 수정</h1>
</body>
<style>
</style>
<table border="1">

	<thead>
	<tbody>

	<form action="./doMemberModify.sbs" method="POST"
		onsubmit="submitLoginForm(this); return false;">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="loginId"
				value="<%=loginedMember.get("loginId")%>"></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name"
				value="<%=loginedMember.get("name")%>"></td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td><input type="text" name="nickname"
				value="<%=loginedMember.get("nickname")%>"></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="text" name="loginPw"
				value="<%=loginedMember.get("loginPw")%>"></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>
			<td><input type="text" name="newLoginPw"
				value="<%=loginedMember.get("nickname")%>"></td>
		</tr>

	</tbody>
</table>
		<tr>
			<td><input type="submit" name="modify" value="수정하기"></td>
		</tr>
</thead>

</html>