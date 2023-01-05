package ncu.sa.demo.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.sa.demo.app.Proposal;
import ncu.sa.demo.app.ProposalHelper;
import ncu.sa.demo.app.ProposalOption;
import ncu.sa.demo.app.ProposalOptionHelper;
import ncu.sa.demo.app.ProposerHelper;
import ncu.sa.tools.JsonReader;

@WebServlet("/api/proposal.do")
public class ProposalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProposalHelper ph =  ProposalHelper.getHelper();
	private ProposalOptionHelper poh =  ProposalOptionHelper.getHelper();
	private ProposerHelper pph =  ProposerHelper.getHelper();

    public ProposalController() {
        super();
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id_list = jsr.getParameter("id_list");
       
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回贊助紀錄內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        if (!id_list.isEmpty()&&isNum(id_list) ) {  /*跳轉至填寫贊助資料畫面時 AJAX會叫這*/
            JSONObject query = ph.getByIdList(id_list);
            resp.put("status", "200");
            resp.put("message", "用id抓的提案資料取得成功");
            resp.put("response", query);
            
          }else if (!id_list.isEmpty()&&!isNum(id_list)){
            JSONObject query = ph.getByKeyWord(id_list);
            resp.put("status", "200");
            resp.put("message", "用關鍵字抓取之提案資料取得成功");
            resp.put("response", query);
            
          }else { 
	          JSONObject query = ph.getAll();
	
	          resp.put("status", "200");
	          resp.put("message", "所有提案資料取得成功");
	          resp.put("response", query);
          }
        jsr.response(resp, response);
	}
    
    public static boolean isNum (String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
    

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int proposer_id=jso.getInt("proposer_id");
        int category_id=jso.getInt("category_id");
        String title = jso.getString("title");
        String content =jso.getString("content");
        String image = jso.getString("image");
        int raised_funds = 0;
        int goal = jso.getInt("goal");
        int proposal_status = 2;
        int viewed_num = 0;
        String created_date = jso.getString("created_date");
        String due_date = jso.getString("due_date");
        
        String A_title = jso.getString("A_title");
        String B_title = jso.getString("B_title");
        String C_title = jso.getString("C_title");
        
        String A_description =jso.getString("A_description");
        String B_description =jso.getString("B_description");
        String C_description =jso.getString("C_description");
        
        String A_image = jso.getString("A_image");
        String B_image = jso.getString("B_image");
        String C_image = jso.getString("C_image");

        int  A_price = jso.getInt("A_price");
        int  B_price = jso.getInt("B_price");
        int  C_price = jso.getInt("C_price");
        
        
        
        /**建立一個新的提案物件 */
        Proposal p = new Proposal(category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(title.isEmpty() || content.isEmpty() ||image.isEmpty()||goal==0||created_date.isEmpty()||
        	due_date.isEmpty()||A_title.isEmpty()||B_title.isEmpty()||
        	C_title.isEmpty()||A_image.isEmpty()||B_image.isEmpty()||C_image.isEmpty()
        	||A_description.isEmpty()||B_description.isEmpty()||C_description.isEmpty()||category_id==0) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkDuplicate()檢查該提案id是否有重複 */
        else /**if (!ph.checkDuplicate(p))*/{
            /** 透過MemberHelper物件的create()方法新建一個提案至資料庫 */
            JSONObject data = ph.create(p);
            JSONObject proposal_data=ph.getByKeyWord(title);
           
            int proposal_id=proposal_data.getInt("proposal_id");
            ProposalOption A_po =new ProposalOption(proposal_id,A_title,A_image, A_description,A_price);
            JSONObject optionA_data = poh.create(A_po);
            ProposalOption B_po =new ProposalOption(proposal_id,B_title,B_image, B_description,B_price);
            JSONObject optionB_data = poh.create(B_po);
            ProposalOption C_po =new ProposalOption(proposal_id,C_title,C_image, C_description,C_price);
            JSONObject optionC_data = poh.create(C_po);
            
            pph.create(proposal_id,proposer_id,1);
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增提案資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        /**else {
            /** 以字串組出JSON格式之資料 */
            //String resp = "{\"status\": \'400\', \"message\": \'新增提案失敗，此提案重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            //jsr.response(resp, response);
        //}
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
        JSONObject query = ph.deleteById(id);
        JSONObject option_query = poh.deleteByProposalId(id);
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "提案移除成功！");
        resp.put("response", query);
        resp.put("response", option_query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
    
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
        int proposal_id=jso.getInt("proposal_id");
        int category_id=jso.getInt("category_id");
        String title = jso.getString("title");
        String content =jso.getString("content");
        String image = jso.getString("image");
        int raised_funds = jso.getInt("raised_funds");
        int goal = jso.getInt("goal");
        int proposal_status = jso.getInt("proposal_status");
        int viewed_num = jso.getInt("viewed_num");
        String  created_date = jso.getString("created_date");
        String due_date = jso.getString("due_date");
        
        int proposalOption_id=jso.getInt("proposalOption_id");
        String proposal_option_title = jso.getString("proposal_option_title");
        String proposal_option_image = jso.getString("proposal_option_image");
        String proposal_option_description =jso.getString("proposal_option_description");
        int  proposal_option_price = jso.getInt("proposal_option_price");

        /** 透過傳入之參數，新建一個以這些參數之Proposal物件 */
        Proposal p = new Proposal(proposal_id,category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
        ProposalOption po = new ProposalOption(proposal_id ,proposalOption_id,proposal_option_title,proposal_option_image,proposal_option_description,proposal_option_price);
        /** 透過Proposal物件的update()方法至資料庫更新該名提案資料，回傳之資料為JSONObject物件 */
        JSONObject data = p.update();
        JSONObject option_data = po.update();
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新提案資料...");
        resp.put("response", data);
        resp.put("response", option_data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
}

