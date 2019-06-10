
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>게시물 작성</title>



<script>
	function addFormSubmited(form) {

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {

			alert('제목을 입력해주세요.');

			form.title.focus();

			return;

		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {

			alert('내용을 입력해주세요.');

			form.body.focus();

			return;

		}

		form.passwd.value = form.passwd.value.trim();

		if (form.passwd.value.length == 0) {

			alert('비밀번호를 입력해주세요.');

			form.passwd.focus();

			return;

		}

		else if (form.passwd.value.length < 4) {
			
			alert('비밀번호를 4자 이상 입력해주세요.');

			form.passwd.focus();

			return;
			
		}

		form.submit();

	}
</script>

</head>

<body>

	<h1>게시물 작성</h1>

	<form action="./doAdd.sbs" method="POST"
		onsubmit="addFormSubmited(this); return false;">

		<div>

			<input type="text" name="title" placeholder="제목">

		</div>

		<div>

			<textarea name="body" placeholder="내용"></textarea>

		</div>

		<div>

			<input type="password" name="passwd" placeholder="비밀번호">

		</div>

		<div>

			<input type="submit" value="작성"> <input type="button"
				value="취소" onclick="location.href = './list.sbs';">

		</div>

	</form>

</body>

</html>