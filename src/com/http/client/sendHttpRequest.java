package com.http.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/fileQuery")
public class sendHttpRequest extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, String> result;

    public sendHttpRequest(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        this.doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        result = new HashMap<String, String>();
        String dir_name = request.getParameter("dirname");
        HttpClient client = new HttpClient();
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("id", "1");
        mapParams.put("dir_name", dir_name);
        result = client.sendGet("http://127.0.0.1:8888", mapParams);
        String queryDir = client.getQueryDir();
        request.setAttribute("input_dir", dir_name);
        request.setAttribute("queryDir", queryDir);
        request.setAttribute("resultMap" , result);
        request.getRequestDispatcher("/FileList.jsp").forward(request, response);
    }

}
