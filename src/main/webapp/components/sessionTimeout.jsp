<%
    HttpSession s = request.getSession(false);

    if (s == null || s.getAttribute("userId") == null) {
        if (s != null) s.invalidate();

        String loginUrl = request.getContextPath() + "/login.jsp";

        out.print("<script>");
        out.print("alert('Session timed out. Please login again.');");
        out.print("window.location.href='" + loginUrl + "';");
        out.print("</script>");
        return;
    }
%>
