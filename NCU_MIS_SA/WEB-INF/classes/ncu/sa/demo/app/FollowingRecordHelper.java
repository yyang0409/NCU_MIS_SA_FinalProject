package ncu.sa.demo.app;

import java.sql.*;
import org.json.*;
import ncu.sa.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class FollowingRecordHelper<br>
 * FollowingRecordHelper類別（class）主要管理所有與FollowingRecord相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class FollowingRecordHelper {

	 protected FollowingRecordHelper() {
	        
	    }
	 private static FollowingRecordHelper frh;
    /**
     * 實例化（Instantiates）一個新的（new）FollowingRecordHelper物件<br>
     * 採用Singleton不需要透過new
     */
 
   private static FollowingRecord fr;
   

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static FollowingRecordHelper getHelper() {
        /** Singleton檢查是否已經有FollowingRecordHelper物件，若無則new一個，若有則直接回傳 */
        if(frh == null) frh = new FollowingRecordHelper();
        return frh;
    }
    
    public JSONObject getAll() {
      	 /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
          FollowingRecord fr = null;
          /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
          JSONArray jsa = new JSONArray();
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
              String sql = "SELECT * FROM `missa`.`followingrecord`";
              
              /** 將參數回填至SQL指令當中 */
              pres = conn.prepareStatement(sql);
              
              /** 執行查詢之SQL指令並記錄其回傳之資料 */
              rs = pres.executeQuery();

              /** 紀錄真實執行的SQL指令，並印出 **/
              exexcute_sql = pres.toString();
              System.out.println(exexcute_sql);
              
              /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
              /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
              while(rs.next()) {
                  /** 每執行一次迴圈表示有一筆資料 */
                  row += 1;
                  
                  /** 將 ResultSet 之資料取出 */
                  int followingRecord_id = rs.getInt("followingRecord_id");
                  int member_id = rs.getInt("member_id");
                  int proposal_id = rs.getInt("proposal_id");
                  
                  
                  fr = new FollowingRecord(followingRecord_id,member_id, proposal_id);
                 
                  jsa.put(fr.getData());
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
          
          /** 紀錄程式結束執行時間 */
          long end_time = System.nanoTime();
          /** 紀錄程式執行時間 */
          long duration = (end_time - start_time);
          
          /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
          JSONObject response = new JSONObject();
          response.put("sql", exexcute_sql);
          response.put("row", row);
          response.put("time", duration);
          response.put("data", jsa);

          return response;
      
      }

    public JSONObject create(int proposal_id,int member_id) {
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
            String sql = "INSERT INTO `missa`.`followingrecord`(`proposal_id`, `member_id`)"
                    + " VALUES(?, ?)";
            
  
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, proposal_id);
            pres.setInt(2, member_id);
        

            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
           
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
 
            System.out.println("這個是真實執行的SQL指令"+exexcute_sql);
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


    public int getIDByProposalandMember(int proposal_id,int member_id) {
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int followingRecord_id =0 ;
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`followingrecord` WHERE `proposal_id` = ? AND `member_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, proposal_id);
            pres.setInt(2,  member_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            rs.next();
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
           
            /** 將 ResultSet 之資料取出 */
            followingRecord_id = rs.getInt("followingRecord_id");
          
            
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
        
       

        return followingRecord_id;
    }
    
    
    
    
    
    public JSONObject deleteByID(int followingRecord_id) {
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
            String sql = "DELETE FROM `missa`.`followingrecord` WHERE `followingRecord_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, followingRecord_id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
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
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }
    
    public void follow(FollowingRecord fr) {
    	int member_id=fr.getMemberId();
    	int proposal_id=fr.getProposalId();
    	create(proposal_id, member_id);
    }
    public void unfollow(FollowingRecord fr) {
    	int member_id=fr.getMemberId();
    	int proposal_id=fr.getProposalId();
    	int followingRecord_id=frh.getIDByProposalandMember(proposal_id, member_id);
    	deleteByID(followingRecord_id);
    }
    
    
    
	
	
	
}