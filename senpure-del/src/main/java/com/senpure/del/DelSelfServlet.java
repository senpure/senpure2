package com.senpure.del;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 罗中正 on 2017/9/4.
 */
public class DelSelfServlet extends HttpServlet {

    private String token = "senpure-del";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (token != null && token.equalsIgnoreCase(this.token)) {
            PrintWriter out = resp.getWriter();
            out.print( DelSelf.delSelf());
        } else {
            PrintWriter out = resp.getWriter();
            out.print("HELLO WORLD !");
        }
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String token = config.getInitParameter("token");
        if (token != null && token.trim().length() > 0) {

            this.token = token;
        }
    }
}
