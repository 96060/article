
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Map<String, Object> article = (Map) request.getAttribute("article");
%>

<!DOCTYPE html>

<html lang="ko">

<head>

<meta charset="UTF-8">

<title>게시물 수정</title>

<script>
	function modifyFormSubmited(form) {
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

		form.submit();
	}
</script>

</head>

<body>

	<form action="./doModify.sbs" method="POST"
		onsubmit="modifyFormSubmited(this); return false;">

		<input type="hidden" name="id" value="<%=article.get("id")%>">

		<div>
			<input value="<%=article.get("title")%>" type="text" name="title"
				placeholder="제목">
		</div>

		<div>
			<textarea name="body" placeholder="내용"><%=article.get("body")%></textarea>
		</div>

		<div>

			<input type="password" name="passwd" placeholder="비밀번호">

		</div>

		<div>

			<input type="submit" value="수정"> <input type="button"
				value="취소"
				onclick="location.href = './detail.sbs?id=<%=article.get("id")%>';">

		</div>

	</form>

</body>

</html>