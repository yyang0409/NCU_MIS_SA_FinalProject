package ncu.sa.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.sa.demo.util.DBMgr;
import ncu.sa.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The ClassProposerHelperr<br>
 * ProposerHelper類別（class）主要管理所有與Proposer相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class  ProposerHelper {
    
    /**
     * 實例化（Instantiates）一個新的（new）ProposerHelper物件<br>
     * 採用Singleton不需要透過new
     */
   
    /** 靜態變數，儲存ProposerHelper ph物件 */
    private static ProposerHelper ph;
    
    /** 靜態變數，儲存ProposerHelper plh物件 */
    private static ProposalHelper plh;
    
    /** 靜態變數，儲存 Proposal pl物件 */
    private static Proposal pl;
    
    
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    
    
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個ProposerHelper物件
     *
     * @return the helper 回傳ProposerHelper物件
     */
    public static ProposerHelper getHelper() {
        /** Singleton檢查是否已經有ProposerHelper物件，若無則new一個，若有則直接回傳 */
        if(ph == null) ph = new ProposerHelper();
        return ph;
    }
  
    /**
     * 確認是否有此提案者
     *
     * 
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public boolean Check(Proposer p) {
    	/** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `missa`.`member` WHERE `member_id` = ?";
            
            /** 取得所需之參數 */
            int id = p.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.println("這個是row："+row);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 
         * 判斷是否已經有一筆該電子郵件信箱之資料
         * 若無一筆則回傳False，否則回傳True 
         */
        return (row == 0) ? false : true; 
    }
    
    
    
    public JSONObject create(int proposal_id,int member_id,int is_premium) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`proposalmember`(`proposal_id`,`member_id`, `is_premium`)"
                    + " VALUES(?,?, ?)";
            
       
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,proposal_id);
            pres.setInt(2,member_id);
            pres.setInt(3, is_premium);
            
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    public JSONObject getProposalId(int member_id) {
        

        /** 用於儲存所有檢索回之資料，以JSONArray方式儲存 */
	JSONObject jso = new JSONObject();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`proposalmember` WHERE `member_id` = ? ";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, member_id);
            /** 執行查詢之SQL指令並記錄回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int proposal_id = rs.getInt("proposal_id");
                
                
                jso.put("proposal_id", proposal_id);
                
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        
        
       
   
        return jso;
    }
    /**
     * 建立該提案至資料庫
     *
     * @param p 一個提案之JSON object物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public void Propose(JSONObject jso) {
   
        int category_id =jso.getInt("category_id");
        String title = jso.getString(" title");
        String content = jso.getString("content");
        String image = jso.getString("image");
        int raised_funds = jso.getInt("raised_funds");
        int goal = jso.getInt("goal");
        int proposal_status = jso.getInt("proposal_status");
        int viewed_num = jso.getInt("viewed_num");
        String created_date=jso.getString("created_date");
        String due_date=jso.getString("due_date");
        pl=new Proposal(category_id,title,content,image,raised_funds,
        		goal,proposal_status,viewed_num, created_date, due_date);
        plh.create(pl);   
    }
    
    
    
    
    

}