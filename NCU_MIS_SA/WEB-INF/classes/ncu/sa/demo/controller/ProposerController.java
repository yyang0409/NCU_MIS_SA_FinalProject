package ncu.sa.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;


import ncu.sa.demo.app.MemberHelper;
import ncu.sa.demo.app.ProposalHelper;
import ncu.sa.demo.app.Proposer;
import ncu.sa.demo.app.ProposerHelper;
import ncu.sa.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class ProposerController<br>
 * ProposerController類別（class）主要用於處理Proposer相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/proposer.do")
public class ProposerController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	 /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
	 /** mh，ProposerHelper之物件與Member相關之資料庫方法（Sigleton） */
    private ProposerHelper ph =  ProposerHelper.getHelper();
    private ProposalHelper plh =  ProposalHelper.getHelper();
    String email;
    String password;
    
    public ProposerController() {
        super();
    }
    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
    	
    	JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        this.email=jso.getString("emai");
       this.password = jso.getString("password");
       int is_premium=jso.getInt("is_premium");
        /** 取出經解析到JSONObject之Request參數 */
        
        int id=mh.getIDByEmail(email);
        String proposer_id = Integer.toString(id);
        JSONObject jso_member=mh.getByID(proposer_id);
        
        int member_id = jso_member.getInt("id");
        String name =  jso_member.getString("name");
        String phone = jso_member.getString("phone");
        String email = jso_member.getString("email");
        String address =  jso_member.getString("address");
        String salt =  jso_member.getString("salt");
        int role =  jso_member.getInt("role");
        String password = jso_member.getString("hashed_password");
       
        /** 建立一個新的提案物件 */
        Proposer p = new Proposer(name,phone,email,address,salt,role,password,member_id,is_premium);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
     

        String category_id =jso.getString("category_id");
        String title = jso.getString(" title");
        String content = jso.getString("content");
        String image = jso.getString("image");
        String raised_funds = jso.getString("raised_funds");
        String goal = jso.getString("goal");
        String proposal_status = jso.getString("proposal_status");
        String viewed_num = jso.getString("viewed_num");
        String created_date=jso.getString("created_date");
        String due_date=jso.getString("due_date");
       
    
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(category_id.isEmpty() || title.isEmpty()|| content.isEmpty() || image.isEmpty()|| raised_funds.isEmpty()
        		|| goal.isEmpty()|| proposal_status.isEmpty()|| viewed_num.isEmpty()|| created_date.isEmpty()||due_date.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過ProposerHelper物件的Check()檢查該提案人是否存在 */
        else if (!ph.Check(p)) {
            /** 透過ProposerHelper物件的Propose()方法新建一個提案至資料庫 */
             ph.Propose(jso);
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "提案成功!");

            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'提案失敗\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */

        int proposer_id=jso.getInt("proposer_id");
        String title = jso.getString("title");
        
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(title.isEmpty() || proposer_id==0) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }else {
        	JSONObject proposal_data=plh.getByKeyWord(title);
            
            int proposal_id=proposal_data.getInt("proposal_id");
            
            JSONObject data = ph.create(proposal_id,proposer_id,1);

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增提案人資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        
	}
    
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
    	
    	int member_id_for_proposal = jso.getInt("member_id");
    	JSONObject data = ph.getProposalId(member_id_for_proposal);
    	
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "查詢成功");
        resp.put("response", data);
        System.out.print(resp.toString());
        
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
        
    }

}