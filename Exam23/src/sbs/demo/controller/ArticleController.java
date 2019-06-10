package sbs.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sbs.demo.util.DBUtil.DBLink;

public class ArticleController {

	public DBLink dbLink;

	private static void forwardJsp(HttpServletRequest request, HttpServletResponse response, String jspPath)
			throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(jspPath); // 데이터 넘길 페이지 주소
		dispatcher.forward(request, response);
	}

	public String _list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String sql = "";
		sql += "SELECT A.id, A.regDate, A.title, SUM(IF(AR.id IS NULL, 0, 1)) AS repliesCount ";
		sql += "FROM article AS A ";
		sql += "LEFT OUTER JOIN articleReply AS AR ";
		sql += "ON A.id = AR.articleId ";
		sql += "GROUP BY A.id ";
		sql += "ORDER BY id DESC ";

		List<Map<String, Object>> articles = dbLink.getRows(sql);

		request.setAttribute("articles", articles);

		forwardJsp(request, response, "/article/list.jsp");

		return "";
	}

	public String _detail(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idStr = (String) request.getParameter("id");

		if (idStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		String sqlA = "SELECT * FROM article WHERE id = '" + id + "';";
		String sqlAR = "SELECT * FROM articleReply WHERE articleId = '" + id + "';";

		Map<String, Object> article = dbLink.getRow(sqlA);

		List<Map<String, Object>> articleReplies = dbLink.getRows(sqlAR);

		request.setAttribute("article", article);
		request.setAttribute("articleReplies", articleReplies);
		request.setAttribute("id", idStr);

		forwardJsp(request, response, "/article/detail.jsp");

		return "";
	}

	public String _modify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idStr = (String) request.getParameter("id");

		if (idStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		String sql = "SELECT * FROM article WHERE id = '" + id + "';";

		Map<String, Object> article = dbLink.getRow(sql);

		request.setAttribute("article", article);

		forwardJsp(request, response, "/article/modify.jsp");

		return "";

	}

	public String _doAdd(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String title = (String) request.getParameter("title");
		String body = (String) request.getParameter("body");
		String passwd = (String) request.getParameter("passwd");

		if (title == null) {
			response.getWriter().append("<script> alert('제목이 입력되지 않았습니다.'); history.back(); </script>");
		}

		title = title.trim();

		if (title.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		title = title.replaceAll("\'", "\\\\'");

		if (body == null) {
			response.getWriter().append("<script> alert('내용이 입력되지 않았습니다.'); history.back(); </script>");
		}

		body = body.trim();

		if (body.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		body = body.replaceAll("\'", "\\\\'");

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		String sql = "INSERT INTO article SET regDate = NOW(), title = '" + title + "', body = '" + body
				+ "', passwd = '" + passwd + "';";

		dbLink.executeQuery(sql);

		int newId = dbLink.getLastInsertId();

		String detailUrl = "./detail.sbs?id=" + newId;

		response.getWriter().append("<script> alert('" + newId + "번 글이 생성되었습니다.'); </script>");
		response.getWriter().append("<script> location.replace('" + detailUrl + "'); </script>");

		return "";
	}

	public String _doDelete(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idStr = (String) request.getParameter("id");

		String passwd = (String) request.getParameter("passwd");

		if (idStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		if (dbLink.getRowIntValue(
				"SELECT COUNT(*) FROM article WHERE id = '" + id + "' AND passwd = '" + passwd + "'") == 0) {
			response.getWriter().append("<script> alert('비밀번호가 맞지 않습니다.'); history.back(); </script>");
		} else {

			String sql = "DELETE FROM article WHERE id = '" + id + "';";
			String sql2 = "DELETE FROM articleReply WHERE articleId = '" + id + "';";

			// 댓글이 존재하는 게시물을 지워버리면 데이터베이스에 댓글 데이터는 남는다.

			dbLink.executeQuery(sql);
			dbLink.executeQuery(sql2);

			response.getWriter().append("<script> alert('" + id + "번 글이 삭제되었습니다.'); </script>");
			response.getWriter().append("<script> location.replace('./list.sbs'); </script>");

		}

		return "";
	}

	public String _doModify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idStr = (String) request.getParameter("id");
		String title = (String) request.getParameter("title");
		String body = (String) request.getParameter("body");
		String passwd = (String) request.getParameter("passwd");

		if (idStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		if (title == null) {
			response.getWriter().append("<script> alert('제목이 입력되지 않았습니다.'); history.back(); </script>");
		}

		title = title.trim();

		if (title.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		title = title.replaceAll("\'", "\\\\'");

		if (body == null) {
			response.getWriter().append("<script> alert('내용이 입력되지 않았습니다.'); history.back(); </script>");
		}

		body = body.trim();

		if (body.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		body = body.replaceAll("\'", "\\\\'");

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		if (dbLink.getRowIntValue(
				"SELECT COUNT(*) FROM article WHERE id = '" + id + "' AND passwd = '" + passwd + "'") == 0) {
			response.getWriter().append("<script> alert('비밀번호가 맞지 않습니다.'); history.back(); </script>");
		} else {

			String sql = "UPDATE article SET title = '" + title + "', body = '" + body + "'WHERE id = '" + id + "';";

			dbLink.executeQuery(sql);

			String detailUrl = "./detail.sbs?id=" + id;

			response.getWriter().append("<script> alert('" + id + "번 글이 수정되었습니다.'); </script>");

			response.getWriter().append("<script> location.replace('" + detailUrl + "'); </script>");

		}

		return "";
	}

	public String _doAddReply(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String articleIdStr = (String) request.getParameter("articleId");

		String body = (String) request.getParameter("body");

		String passwd = (String) request.getParameter("passwd");

		if (articleIdStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int articleId = 0;

		try {
			articleId = Integer.parseInt(articleIdStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (articleId < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		if (body == null) {
			response.getWriter().append("<script> alert('내용이 입력되지 않았습니다.'); history.back(); </script>");
		}

		body = body.trim();

		if (body.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		body = body.replaceAll("\'", "\\\\'");

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		String sql = "INSERT INTO articleReply SET regDate = NOW(), articleId = '" + articleId + "', body = '" + body
				+ "', passwd = '" + passwd + "';";

		dbLink.executeQuery(sql);

		int newId = dbLink.getLastInsertId();

		response.getWriter().append("<script> alert('" + newId + "번 댓글이 생성되었습니다.'); </script>");

		response.getWriter().append("<script> location.replace('" + request.getHeader("REFERER") + "'); </script>");

		return "";
	}

	public String _doDeleteReply(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String idStr = (String) request.getParameter("id");

		String passwd = (String) request.getParameter("passwd");

		if (idStr == null) {
			response.getWriter().append("<script> alert('게시물 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('게시물 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('게시물 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		if (dbLink.getRowIntValue(
				"SELECT COUNT(*) FROM articleReply WHERE id = '" + id + "' AND passwd = '" + passwd + "'") == 0) {
			response.getWriter().append("<script> alert('비밀번호가 맞지 않습니다.'); history.back(); </script>");
		} else {
			String sql = "DELETE FROM articleReply WHERE id = '" + id + "';";

			dbLink.executeQuery(sql);

			response.getWriter().append("<script> alert('" + id + "번 댓글이 삭제되었습니다.'); </script>");

			response.getWriter().append("<script> location.replace('" + request.getHeader("REFERER") + "'); </script>");

		}

		return "";
	}

	public String _doModifyReply(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idStr = (String) request.getParameter("id");

		String body = (String) request.getParameter("body");

		String passwd = (String) request.getParameter("passwd");

		if (idStr == null) {
			response.getWriter().append("<script> alert('댓글 번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		int id = 0;

		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.getWriter().append("<script> alert('댓글 번호가 숫자로 입력되지 않았습니다.'); history.back(); </script>");
		}

		if (id < 1) {
			response.getWriter().append("<script> alert('댓글 번호는 1보다 작을 수 없습니다.'); history.back(); </script>");
		}

		if (body == null) {
			response.getWriter().append("<script> alert('내용이 입력되지 않았습니다.'); history.back(); </script>");
		}

		body = body.trim();

		if (body.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		body = body.replaceAll("\'", "\\\\'");

		if (passwd == null) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
		}

		passwd = passwd.trim();

		if (passwd.length() == 0) {
			response.getWriter().append("<script> alert('공백으로 입력되었습니다.'); history.back(); </script>");
		}

		passwd = passwd.replaceAll("\'", "\\\\'");

		if (dbLink.getRowIntValue(
				"SELECT COUNT(*) FROM articleReply WHERE id = '" + id + "' AND passwd = '" + passwd + "'") == 0) {
			response.getWriter().append("<script> alert('비밀번호가 맞지 않습니다.'); history.back(); </script>");
		} else {
			String sql = "UPDATE articleReply SET body = '" + body + "' WHERE id = '" + id + "';";

			dbLink.executeQuery(sql);

			response.getWriter().append("<script> alert('" + id + "번 댓글이 수정되었습니다.'); </script>");

			response.getWriter().append("<script> location.replace('" + request.getHeader("REFERER") + "'); </script>");

		}

		return "";
	}

}