package ncu.sa.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.sa.demo.app.FollowingRecord;
import ncu.sa.demo.app.FollowingRecordHelper;
import ncu.sa.demo.app.Member;
import ncu.sa.demo.app.MemberCredentialHelper;
import ncu.sa.demo.app.MemberHelper;
import ncu.sa.tools.JsonReader;
@WebServlet("/api/follow_record.do")
public class FollowingRecordController  extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
  
    private FollowingRecordHelper frh =  FollowingRecordHelper.getHelper();
    
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();
            int proposal_id = jso.getInt("proposal_id");
            int member_id = jso.getInt("member_id");
            FollowingRecord fr=new FollowingRecord(member_id,proposal_id);
            String type = jso.getString("type");
            
          if(type.equals("follow")) {
                 frh.follow(fr);
                 /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                 JSONObject resp = new JSONObject();
                 resp.put("status", "200");
                 resp.put("message", "追蹤成功!");
                 /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                 jsr.response(resp, response);
                 
            }
            else if(type.equals("unfollow")) {
           
                frh.unfollow(fr);
                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "取消追蹤成功!");
                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
                
           }
        }
    
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,JSONException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	    	
	    	JsonReader jsr = new JsonReader(request);	    	
	        JSONObject query =frh.getAll();
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "所有會員資料取得成功");
	        resp.put("response", query);
	            
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	        
	    }

}