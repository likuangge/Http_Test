package com.http.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpClient {

    private String result = "";
    private BufferedReader br = null;
    private PrintWriter pw = null;
    private StringBuffer sb = null;
    private String params = null;
    private Map<String, String> results;
    private List<String> dirs;
    private String queryDir = null;
    private HttpURLConnection connection = null;

    public Map<String, String> sendGet(String url, Map<String, Object> mapParams, String dir_name){
        params = getParams(mapParams);
        try {
            String httpUrl = url + "?" + params;
            URL connectUrl = new URL(httpUrl);
            connection = (HttpURLConnection)connectUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setUseCaches(true);
            httpConnect(connection);

            getResponse(connection, dir_name);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(br != null){
                    br.close();
                }
                connection.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return results;
    }

    /*public void sendPost(String url, Map<String, Object> mapParams){
        params = getParams(mapParams);
        try {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            httpConnect(connection);

            pw = new PrintWriter(connection.getOutputStream());
            pw.write(params);
            pw.flush();

            getResponse(connection);
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public String getParams(Map<String, Object> mapParams){
        sb = new StringBuffer();
        String params = null;
        try{
            if(mapParams.size() == 1){
                for(Map.Entry<String, Object> entry : mapParams.entrySet()){
                    sb.append(entry.getKey()).append("=").append(java.net.URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
                params =  sb.toString();
            } else {
                for (Map.Entry<String, Object> entry : mapParams.entrySet()){
                    sb.append(entry.getKey()).append("=").append(java.net.URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");
                }
                params =  sb.toString().substring(0, sb.toString().length() - 1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }

    public void httpConnect(HttpURLConnection connection){
        try {
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("UserAgent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            connection.setDoInput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Length", params.getBytes().length + "");
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
        } /*finally {
            try{
                if(pw != null){
                    pw.close();
                }
                if(br != null){
                    br.close();
                }
                connection.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
        }*/
    }

    public void getResponse(HttpURLConnection connection, String name){
        results = new HashMap<String, String>();
        dirs = new ArrayList<String>();
        try {
            int code = connection.getResponseCode();
            if (code == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println("响应内容: " + line);
                    String[] content = line.split("&");
                    String[] type = content[0].split("=");
                    if(type[1].equals("queryDir")){
                        String[] dir_name = content[1].split("=");
                        System.out.println("Type: " + type[1] + "; Dir_Name: " + dir_name[1]);
                        this.queryDir = dir_name[1];
                    }else if(type[1].equals("dir")){
                        String[] dir_name = content[1].split("=");
                        System.out.println("Type: " + type[1] + "; Dir_Name: " + dir_name[1]);
                        if(dir_name[1].equals(name)){
                            dirs.add(dir_name[1]);
                        } else{
                            dirs.add(name + "\\" + dir_name[1]);
                        }
                    } else{
                        String[] file_name = content[1].split("=");
                        String[] file_parent = content[2].split("=");
                        System.out.println("File_Name: " + file_name[1] + "; Parent_Dir_Name: " + file_parent[1]);
                        /*String[] parent = file_parent[1].split(File.separator);
                        if(parent[parent.length - 1].equals(name)){
                            results.put(file_name[1], parent[parent.length - 1]);
                        } else {
                            results.put(file_name[1], name + "\\" + parent[parent.length - 1]);
                        }*/
                        results.put(file_name[1], file_parent[1]);
                    }
                    result += line;
                }
            }
            System.out.println("响应结果:" + result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getQueryDir(){
        return this.queryDir;
    }

    public List<String> getDirs(){
        return dirs;
    }

}
