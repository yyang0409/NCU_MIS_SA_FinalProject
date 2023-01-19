package ncu.sa.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.sa.demo.app.Member;
import ncu.sa.demo.util.DBMgr;
import ncu.sa.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The ClassSponsorRecordHelper<br>
 * SponsorRecordHelper類別（class）主要管理所有與SponsorRecord相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class  SponsorRecordHelper {
    
    /**
     * 實例化（Instantiates）一個新的（new）SponsorRecordHelper物件<br>
     * 採用Singleton不需要透過new
     */

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    /** 靜態變數，儲存Sponsor s物件 */
    private static Sponsor s;
    
    /** 靜態變數，儲存SponsorRecord sr物件 */
    private static SponsorRecord  sr;
    
    /**
     * 實例化（Instantiates）一個新的（new）SponsorRecordHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private SponsorRecordHelper() {
        
    }
    
    /** 靜態變數，儲存MemberHelper物件 */
    private static SponsorRecordHelper srh;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個SponsorRecordHelper物件
     *
     * @return the helper 回傳SponsorRecordHelper物件
     */
    public static SponsorRecordHelper getHelper() {
        /** Singleton檢查是否已經有ProposerHelper物件，若無則new一個，若有則直接回傳 */
        if(srh == null) srh = new SponsorRecordHelper();
        return srh;
    }
  

    
    /**
     * 建立該贊助紀錄至資料庫
     *
     * @param s 一名會員之Sponsor物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(SponsorRecord  sr) {
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
            String sql = "INSERT INTO `missa`.`sponsorrecord`(`member_id`, `proposalOption_id`,`sponsor_payment_type`,`sponsor_amount`,`proposalOption_unitPrice`,`totalPrice`)"
                    + " VALUES(?, ?, ?,?, ?, ?)";
            
            /** 取得所需之參數 */
            int member_id = sr.getMember_id(); 
            int proposalOption_id = sr.getProposalOption_id();
            int sponsor_payment_type = sr.getSponsor_payment_type();
            int sponsor_amount = sr.getSponsor_amount();
            int proposalOption_unitPrice = sr.getProposalOption_unitPrice();
            int totalPrice = sr.getTotalPrice();
           
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, member_id);
            pres.setInt(2, proposalOption_id);
            pres.setInt(3, sponsor_payment_type);
            pres.setInt(4, sponsor_amount);
            pres.setInt(5, proposalOption_unitPrice);
            pres.setInt(6, totalPrice);

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
    
    
    public JSONObject getAll() {
   	 /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
       SponsorRecord sr = null;
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
           String sql = "SELECT * FROM `missa`.`sponsorrecord`";
           
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
               int sponsorRecord_id = rs.getInt("sponsorRecord_id");
               int member_id = rs.getInt("member_id");
               int proposalOption_id = rs.getInt("proposalOption_id");
               int sponsor_payment_type = rs.getInt("sponsor_payment_type");
               int sponsor_amount = rs.getInt("sponsor_amount");
               int proposalOption_unitPrice = rs.getInt("proposalOption_unitPrice");
               int totalPrice = rs.getInt("totalPrice");
               
               sr = new SponsorRecord(sponsorRecord_id,member_id, proposalOption_id, sponsor_payment_type, sponsor_amount, proposalOption_unitPrice, totalPrice);
              
               jsa.put(sr.getData());
               
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
    
    
    
    public JSONObject getByMemberID(String id) {
    	 /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        SponsorRecord sr = null;
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
            String sql = "SELECT * FROM `missa`.`sponsorrecord` WHERE `member_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
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
                int sponsorRecord_id = rs.getInt("sponsorRecord_id");
                int member_id = rs.getInt("member_id");
                int proposalOption_id = rs.getInt("proposalOption_id");
                int sponsor_payment_type = rs.getInt("sponsor_payment_type");
                int sponsor_amount = rs.getInt("sponsor_amount");
                int proposalOption_unitPrice = rs.getInt("proposalOption_unitPrice");
                int totalPrice = rs.getInt("totalPrice");
                
                sr = new SponsorRecord(sponsorRecord_id,member_id, proposalOption_id, sponsor_payment_type, sponsor_amount, proposalOption_unitPrice, totalPrice);
               
                jsa.put(sr.getData());
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
}