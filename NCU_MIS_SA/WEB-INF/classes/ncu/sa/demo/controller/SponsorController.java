package ncu.sa.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.sa.demo.app.Member;
import ncu.sa.demo.app.MemberHelper;
import ncu.sa.demo.app.Proposal;
import ncu.sa.demo.app.Proposer;
import ncu.sa.demo.app.ProposerHelper;
import ncu.sa.demo.app.Sponsor;
import ncu.sa.demo.app.SponsorHelper;
import ncu.sa.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class SponsorController<br>
 * SponserController類別（class）主要用於處理Sponsor相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class SponsorController {
	 /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
	 /** sh，SponsorHelper之物件與Member相關之資料庫方法（Sigleton） */
    private SponsorHelper sh = SponsorHelper.getHelper();
    
    String email;
    String password;

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
        int payment_type=jso.getInt("payment_type");
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
       
        /** 建立一個新的贊助者物件 */
        Sponsor s = new Sponsor(name,phone,email,address,salt,role,password,member_id,payment_type);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */

        
        
        /** 透過SponsorHelper物件的Check()檢查該贊助人是否存在 */
       if (!sh.Check(s)) {
            /** 透過SponsorHelper物件的Sponsor()方法新建一個贊助資料至資料庫 */
             sh.sponsor(jso);
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "贊助成功!");

            
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
    
	
	
	
	
}