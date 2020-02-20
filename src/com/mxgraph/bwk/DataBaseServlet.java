package com.mxgraph.bwk;

import com.mxgraph.util.DBHelper;
import com.mxgraph.util.HttpUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;


public class DataBaseServlet extends HttpServlet {

	private static final long serialVersionUID = 2360583959079622105L;
	
	private static final Logger log = Logger.getLogger(DataBaseServlet.class.getName());


	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		log.info("调用dataBaseServlet");
		try
		{
			if(null!= request.getParameter("method") &&
					request.getParameter("method").equalsIgnoreCase("save")
					&& null!= request.getParameter("content")){ //保存方法
				this.save(request,response);
			}else if(null!= request.getParameter("method") &&
				request.getParameter("method").equalsIgnoreCase("list") ){ //保存方法
				this.list(request,response);
			}else if(null!= request.getParameter("method") &&
					request.getParameter("method").equalsIgnoreCase("getHtml")
					&& null!= request.getParameter("id")){ //获取网页方法
				this.getHtml(request,response);
			}
 		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			log.info("操作失败"+e.getMessage());
			Map<String,Object> result = new HashMap<>();
			result.put("status",500);
			result.put("message","操作失败");
			HttpUtil.response(response,result);

		}
	}



	private  void  save (HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("调用保存方法..");
		log.info("保存内容.." + request.getParameter("content").toString());
		log.info("保存名称.." + request.getParameter("name").toString());
		String name  =request.getParameter("name") ;
		List<Object> updParams = new ArrayList<>();
		updParams.add(name);
		updParams.add(request.getParameter("content").toString());
		updParams.add(new Date());
		List<Object> queryParams = new ArrayList<>();
		queryParams.add(name);
		Map<String,Object> result = new HashMap<>();
		DBHelper dbHelper = new DBHelper();
		List<Map> queryResult = dbHelper.executeQuery("select * from qjjs_draw_content where name  = ? " ,queryParams   );
		if(null!=queryResult && queryResult.size()>0){
			result.put("status",500);
			result.put("message","操作失败，已存在的文件名");
			HttpUtil.response(response,result);
			return ;
		}
		dbHelper.excuteUpdate(" INSERT INTO qjjs_draw_content(  name , content, create_date) VALUES (  ?,?, ?) "
				,updParams);
		result.put("status",200);
		result.put("message","操作成功");
		HttpUtil.response(response,result);
	}


	private  void  list (HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("调用查询方法..");


		Map<String,Object> result = new HashMap<>();
		result.put("status",200);
		result.put("message","操作成功");
		DBHelper dbHelper = new DBHelper();
		List<Map> queryResult = dbHelper.executeQuery("select * from qjjs_draw_content where 1=1  " , null );
		if(null!=queryResult && queryResult.size()>0){
			result.put("list" , queryResult);
			HttpUtil.response(response,result);
			return ;
		}

	}


	private  void  getHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("调用获取网页方法..");
		String id =request.getParameter("id").toString();
		log.info("ID.." +id );

		Map<String,Object> result = new HashMap<>();
		result.put("status",200);
		result.put("message","操作成功");

		DBHelper dbHelper = new DBHelper();
		List<Object> queryParams = new ArrayList<>();
		queryParams.add(id);
		List<Map> queryResult = dbHelper.executeQuery("select * from qjjs_draw_content where  id = ?   " , queryParams );
		if(null!=queryResult && queryResult.size()>0){
			response.setHeader("content-type","text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(queryResult.get(0).get("content").toString());
			//最后要记得清空缓存区，并且关闭。
			out.flush();
			out.close();

			return ;
		}

	}

}
