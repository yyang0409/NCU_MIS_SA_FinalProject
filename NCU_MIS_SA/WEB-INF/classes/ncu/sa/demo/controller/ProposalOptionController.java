package ncu.sa.demo.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONObject;
import ncu.sa.demo.app.ProposalOptionHelper;
import ncu.sa.tools.JsonReader;


@WebServlet("/api/option.do")
public class ProposalOptionController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private ProposalOptionHelper poh =  ProposalOptionHelper.getHelper();
	
	public ProposalOptionController() {
        super();
    }
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
	        //String proposal_id = jsr.getParameter("proposal_id");
	        
	        JSONObject resp = new JSONObject();
	        JSONObject query = poh.getAll();
	        /** 判斷該字串是否存在，若存在代表要取回贊助紀錄內產品之資料，否則代表要取回全部資料庫內產品之資料 */
	        
	          //JSONObject query = poh.getById(proposal_id);

	          resp.put("status", "200");
	          resp.put("message", "所有提案資料取得成功");
	          resp.put("response", query);
	         
	        jsr.response(resp, response);
	}
	
	 /**
	     * 處理Http Method請求DELETE方法（刪除）
	     *
	     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
	     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	     * @throws ServletException the servlet exception
	     * @throws IOException Signals that an I/O exception has occurred.
	     */
	    public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
	        JSONObject query = poh.deleteByProposalOptionId(id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "提案移除成功！");
	        resp.put("response", query);

	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
	    
}
