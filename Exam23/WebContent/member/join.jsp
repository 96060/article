<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 화면</title>

<script>
	function submitJoinForm(jol) {

		if (form.name.value.length == 0) {
			form.name.focus();

			alert('이름을 입력해 주세요.');

			return false;
		}
		if (form.loginId.value.length == 0) {
			form.loginId.focus();

			alert('로그인 아이디를 입력해주세요.');

			return false;
		}

		if (form.passwd.value.length < 8) {

			form.passwd.focus();
			alert('비밀번호를 8자 이상 입력해주세요.');

			return;

		}

		form.submit();
	}
</script>
</head>
<h1>회원가입 화면</h1>
<body>

	<form action="./doJoin.sbs" method="POST"
		onsubmit="submitLoginForm(this); return false;">
		<table>
			<thead>
				<tr>
					<th>이름</th>
					<td><input maxlength="30" type="text" name="name"
						placeholder="이름"></td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td><input maxlength="30" type="text" name="nickname"
						placeholder="별명"></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td><input maxlength="30" type="text" name="loginId"
						placeholder="아이디"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input maxlength="30" type="password" name="loginPw"
						placeholder="비밀번호"></td>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
		<tr>
			<td><input type="submit" value="회원가입"></td>
		</tr>
</body>
</html>