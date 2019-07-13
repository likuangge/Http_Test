package com.http.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/fileUpload")
public class FileUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, String> result;

    public FileUpload(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpClient client = new HttpClient();
        request.setCharacterEncoding("utf-8");
        String file_name = request.getParameter("filename");
        System.out.println("File Name: " + file_name);
        client.fileUpload("http://127.0.0.1:8888", file_name);
        //client.sendPost("http://127.0.0.1:8888");
    }
}
