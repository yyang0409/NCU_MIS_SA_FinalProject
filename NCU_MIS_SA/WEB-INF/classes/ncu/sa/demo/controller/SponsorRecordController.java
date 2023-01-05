package ncu.sa.demo.controller;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import ncu.sa.demo.app.ProposalHelper;
import ncu.sa.demo.app.SponsorRecord;
import ncu.sa.demo.app.SponsorRecordHelper;
import ncu.sa.tools.JsonReader;

@WebServlet("/api/sponsor_record.do")

public class SponsorRecordController extends HttpServlet {
	
	private SponsorRecordHelper sh =  SponsorRecordHelper.getHelper();
	private ProposalHelper ph =  ProposalHelper.getHelper();
    
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	
	        /** 取出經解析到 JSONObject 之 Request 參數 */
	        int proposal_id = jso.getInt("proposal_id");
	        int member_id = jso.getInt("member_id");
	        int proposal_option_id = jso.getInt("proposal_option_id");
	        int sponsor_payment_type = jso.getInt("sponsor_payment_type");
	        int sponsor_amount = jso.getInt("sponsor_amount");
	        int proposal_unit_price = jso.getInt("proposal_unit_price");
	        int total_price = jso.getInt("total_price");
	
	        SponsorRecord sr = new SponsorRecord(member_id,proposal_option_id,sponsor_payment_type,sponsor_amount,proposal_unit_price,total_price);
	        System.out.println(proposal_id);
	        System.out.println(member_id);
	        System.out.println(proposal_option_id);
	        System.out.println(sponsor_payment_type);
	        System.out.println(sponsor_amount);
	        System.out.println(proposal_unit_price);
	        System.out.println(total_price);
	
	        /** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 */
	        JSONObject data = sh.create(sr);
	        ph.updateRaisedFunds(total_price,proposal_id);
	        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "訂單新增成功！");
	        resp.put("response", data);
	
	        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
	        jsr.response(resp, response);
	        
	        
	        
		}
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,JSONException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	    	
	    	JsonReader jsr = new JsonReader(request);	    	
	        JSONObject query =sh.getAll();
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "所有會員資料取得成功");
	        resp.put("response", query);
	            
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	        
	    }
	 
	 
}