<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>

<script>
	function submitLoginForm(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			form.loginId.focus();

			alert('로그인 아이디를 입력해주세요.');

			return false;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			form.loginPw.focus();

			alert('로그인 비번을 입력해주세요.');

			return false;
		}

		form.submit();
	}
</script>
</head>
<h1>로그인 화면</h1>
<body>

	<form action="./doLogin.sbs" method="POST"
		onsubmit="submitLoginForm(this); return false;">
		<table>
			<thead>

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
				<tr>
					<th>로그인</th>
					<td><input type="submit" value="로그인"></td>
				</tr>

				<tr>
					<th>회원가입</th>
					<td><input type="button" value="회원가입"
						onclick="location.href = './join.jsp'"></td>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
		</form>
</body>
</html>