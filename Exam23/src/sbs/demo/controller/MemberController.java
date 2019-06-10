package sbs.demo.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sbs.demo.util.CookieUtil;
import sbs.demo.util.DBUtil;

public class MemberController {

	public DBUtil.DBLink dbLink;

	private static void forwardJsp(HttpServletRequest request, HttpServletResponse response, String jspPath)
			throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(jspPath); // 데이터 넘길 페이지 주소
		dispatcher.forward(request, response);
	}

	public void _join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forwardJsp(request, response, "/member/join.jsp");
	}

	public void _login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forwardJsp(request, response, "/member/login.jsp");
	}

	public void _doJoin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");

		String sql = "SELECT COUNT(*) AS cnt FROM member WHERE loginId = '" + loginId + "'";

		if (dbLink.getRowIntValue(sql) > 0) {
			String msg = "이미 사용중인 아이디 입니다.";
			response.getWriter().append("<script> alert('" + msg + "'); history.back(); </script>");

			return;
		}

		sql = "SELECT COUNT(*) AS cnt FROM member WHERE nickname = '" + nickname + "'";

		if (dbLink.getRowIntValue(sql) > 0) {
			String msg = "이미 사용중인 닉네임 입니다.";
			response.getWriter().append("<script> alert('" + msg + "'); history.back(); </script>");

			return;
		}

		sql = "INSERT INTO member SET regDate = NOW()";
		sql += ", loginId = '" + loginId + "'";
		sql += ", loginPw = '" + loginPw + "'";
		sql += ", name = '" + name + "'";
		sql += ", nickname = '" + nickname + "';";

		dbLink.executeQuery(sql);

		String msg = "가입 되었습니다.";
		response.getWriter().append("<script> alert('" + msg + "'); location.replace('./login.sbs'); </script>");
	}

	public void _doLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		System.out.println(loginId);
		String sql = "SELECT * FROM member WHERE loginId = '" + loginId + "' AND loginPw = '"
				+ loginPw + "';";

		Map<String, Object> member = dbLink.getRow(sql);

		if (member.size() == 0) {

			String msg = "일치하는 회원이 없습니다.";

			response.getWriter().append("<script> alert('" + msg + "'); history.back(); </script>");

			return;
		}

		request.getSession().setAttribute("loginedMember", member);

		String msg = "로그인 되었습니다.";

		response.getWriter().append("<script> alert('" + msg + "'); location.replace('./myPage.sbs'); </script>");

	}

	public void _myPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String loginId = CookieUtil.getAttribute(request, "loginId");
		// String loginId = (String) request.getSession().getAttribute("loginId");

		if (loginId == null || loginId.length() == 0) {
			String msg = "로그인 후 접근해주세요.";
			response.getWriter().append("<script> alert('" + msg + "'); location.replace('./login.sbs'); </script>");

			return;
		}

		Map<String, Object> loginedMember = dbLink
				.getRow("SELECT * FROM member WHERE loginId = '" + loginId + "' LIMIT 1");

		request.setAttribute("loginedMember", loginedMember);

		forwardJsp(request, response, "/member/myPage.jsp");
	}

	public void _modify(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String loginId = (String) request.getSession().getAttribute("loginId");

		if (loginId == null) {
			String msg = "로그인 후 접근해주세요.";
			response.getWriter()
					.append("<script> alert('" + msg + "'); location.replace('../article/login.sbs'); </script>");

			return;
		}

		Map<String, Object> memberInfo = dbLink
				.getRow("SELECT * FROM member WHERE loginId = '" + loginId + "'");

		request.setAttribute("memberInfo", memberInfo);

		forwardJsp(request, response, "/member/modify.jsp");

	}

	public void _doMemberModify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String loginId = (String) request.getSession().getAttribute("loginId");

		if (loginId == null) {
			response.getWriter().append("<script> alert('로그인 후 이용해주세요.'); history.back(); </script>");
			return;
		}

		String nickname = (String) request.getParameter("nickname");
		String name = (String) request.getParameter("name");
		String loginPw = (String) request.getParameter("loginPw");
		String newloginPw = (String) request.getParameter("newloginPw");

		if (name == null) {
			response.getWriter().append("<script> alert('입력값이 없습니다.'); history.back(); </script>");
			return;
		}

		name = name.trim();

		if (name.length() == 0) {
			response.getWriter().append("<script> alert('이름이 입력되지 않았습니다.'); history.back(); </script>");
			return;
		}

		name = name.replaceAll("\'", "\\\\'");

		if (loginPw == null) {
			response.getWriter().append("<script> alert('입력값이 없습니다.'); history.back(); </script>");
			return;
		}

		loginPw = loginPw.trim();

		if (loginPw.length() == 0) {
			response.getWriter().append("<script> alert('비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
			return;
		}

		loginPw = loginPw.replaceAll("\'", "\\\\'");

		if (loginPw == null) {
			response.getWriter().append("<script> alert('입력값이 없습니다.'); history.back(); </script>");
			return;
		}
		
		newloginPw = newloginPw.trim();

		if (newloginPw.length() == 0) {
			response.getWriter().append("<script> alert('새 비밀번호가 입력되지 않았습니다.'); history.back(); </script>");
			return;
		}

		newloginPw = newloginPw.replaceAll("\'", "\\\\'");

		Map<String, Object> loginedMember = dbLink.getRow("SELECT * FROM member WHERE id = '" + loginId + "'");

		if (loginedMember.get("loginPw").equals(loginPw) == false) {
			response.getWriter().append("<script> alert('기존 비밀번호를 정확히 입력해주세요.'); history.back(); </script>");
			return;
		}

		String sql = "UPDATE member SET name = '" + name + "', loginPw = '" + newloginPw + "'WHERE id = '"
				+ loginId + "'";

		dbLink.executeQuery(sql);

		String myPageUrl = "./myPage.sbs";

		response.getWriter().append("<script> alert('회원 정보가 수정되었습니다.'); </script>");
		response.getWriter().append("<script> location.replace('" + myPageUrl + "'); </script>");

	}
}
	

