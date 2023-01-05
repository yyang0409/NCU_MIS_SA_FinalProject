package ncu.sa.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.sa.demo.app.Member;
import ncu.sa.demo.app.MemberCredentialHelper;
import ncu.sa.demo.app.MemberHelper;
import ncu.sa.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberController<br>
 * MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class MemberController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    private MemberCredentialHelper mch =  MemberCredentialHelper.getHelper();
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
            String type = jso.getString("type");
            if(type.equals("sign_up")) {
            	/** 取出經解析到JSONObject之Request參數 */
                String name = jso.getString("name");
                String phone = jso.getString("phone");
                String email = jso.getString("email");
                String address = jso.getString("address");
                String password = jso.getString("password");
                
                /** 建立一個新的會員物件 */
                Member m = new Member(name,phone,email,address,password);
                
                /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
                if(name.isEmpty() || phone.isEmpty() || email.isEmpty()|| address.isEmpty()||password.isEmpty()) {
                    /** 以字串組出JSON格式之資料 */
                    String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
                    /** 透過JsonReader物件回傳到前端（以字串方式） */
                    jsr.response(resp, response);
                }
                /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
                else if (!mh.checkDuplicate(m)) {
                    /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
                    JSONObject data = mh.create(m);
                    
                    /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                    JSONObject resp = new JSONObject();
                    resp.put("status", "200");
                    resp.put("message", "成功! 註冊會員資料...");
                    resp.put("response", data);
                    
                    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                    jsr.response(resp, response);
                }
                else {
                    /** 以字串組出JSON格式之資料 */
                    String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
                    /** 透過JsonReader物件回傳到前端（以字串方式） */
                    jsr.response(resp, response);
                }
            }else if(type.equals("sign_in")) {
            	
                try {
        	        String email = jso.getString("email");
        	        String password = jso.getString("password");
        	        Member m = new Member(email,password);
        	        /*看密碼是否正確*/
        	        if (!email.isEmpty()) {
        	            JSONObject is_password=mh.checkPassword(m);
        	            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        	            JSONObject resp = new JSONObject();
        	            resp.put("status", "200");
        	            resp.put("message", "會員資料取得成功");
        	            resp.put("response", is_password);
        	            /*System.out.println("這是sign_in的response:"+resp);*/
        	            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        	            jsr.response(resp, response);
        	        }
                }catch(JSONException e) {
                	e.printStackTrace();
                }
            }else if(type.equals("getMemberID")) {
            	
            	String email = jso.getString("email");
            	
            	if(!email.isEmpty()) {
                	int member_id = mh.getIDByEmail(email);
                    /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                    JSONObject resp = new JSONObject();
                    resp.put("status", "200");
                    resp.put("message", "會員ID取得成功");
                    resp.put("response", member_id);
                    /*System.out.println("這是getMemberID的response:"+resp);*/
                    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                    jsr.response(resp, response);
            	}
            }else if(type.equals("update_one")) {
            	/** 取出經解析到JSONObject之Request參數 */
                String name = jso.getString("name");
                String phone = jso.getString("phone");
                String email = jso.getString("email");
                String address = jso.getString("address");
                int member_id = jso.getInt("member_id");
                
                /** 建立一個新的會員物件 */
                Member m = new Member(name,phone,email,address,member_id);
                
                /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
                if(name.isEmpty() || phone.isEmpty() || email.isEmpty()|| address.isEmpty()) {
                    /** 以字串組出JSON格式之資料 */
                    String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
                    /** 透過JsonReader物件回傳到前端（以字串方式） */
                    jsr.response(resp, response);
                }
                /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
                else if (mh.checkDuplicate(m)) {
                    /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
                    JSONObject data = mh.update(m);
                    
                    /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                    JSONObject resp = new JSONObject();
                    resp.put("status", "200");
                    resp.put("message", "成功! 更改會員資料...");
                    resp.put("response", data);
                    /*System.out.println("這是update_one的response:"+resp);*/
                    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                    jsr.response(resp, response);
                }
            	
            }else if(type.equals("update_two")) {
            	/** 取出經解析到JSONObject之Request參數 */
            	int member_id = jso.getInt("member_id");
            	String email = jso.getString("email");
                String password = jso.getString("password");
                /** 建立一個新的會員物件 */
                Member m = new Member(email,password);
                
                /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
                if(password.isEmpty()) {
                    /** 以字串組出JSON格式之資料 */
                    String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
                    /** 透過JsonReader物件回傳到前端（以字串方式） */
                    jsr.response(resp, response);
                }
                /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
                else if (mh.checkDuplicate(m)) {
                	String salt =mh.getSalt(member_id);
                	m.setID(member_id);
                	m.setSalt(salt);
                	m.checkSalt(salt);
                	m.setHashedPassword();
                    /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
                    JSONObject data = mch.update(m);
                    
                    /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                    JSONObject resp = new JSONObject();
                    resp.put("status", "200");
                    resp.put("message", "成功! 更改會員密碼...");
                    resp.put("response", data);
                    /*System.out.println("這是update_two的response:"+resp);*/
                    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                    jsr.response(resp, response);
                }
            	
            }
       }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,JSONException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
    	
    	JsonReader jsr = new JsonReader(request);
   
        	JSONObject query =mh.getAll();
        	/** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員資料取得成功");
            resp.put("response", query);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        
    }
    
    public static boolean isNum (String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    

    /**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    /*public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        /*JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        /*int id = jso.getInt("id");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        /*JSONObject query = mh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        /*JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        /*jsr.response(resp, response);
    }*/

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");
        String name = jso.getString("name");
        String phone = jso.getString("phone");
        String email = jso.getString("email");
        String salt = jso.getString("salt");
        String address = jso.getString("address");
        int role = jso.getInt("role");
        String password = jso.getString("password");
        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
        Member m = new Member(id,name,phone,email,salt,address,role,password);
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = m.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
}