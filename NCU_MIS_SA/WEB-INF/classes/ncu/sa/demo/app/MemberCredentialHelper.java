package ncu.sa.demo.app;

import java.sql.*;
import org.json.*;
import ncu.sa.demo.util.DBMgr;

public class MemberCredentialHelper {
	/**
     * 實例化（Instantiates）一個新的（new）MemberHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private MemberCredentialHelper() {
        
    }
    
    /** 靜態變數，儲存MemberHelper物件 */
    private static MemberCredentialHelper mch;
    private MemberHelper mh =  MemberHelper.getHelper();
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
    public static MemberCredentialHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(mch == null) mch = new MemberCredentialHelper();
        return mch;
    }
    
    
   public JSONObject create(String email,String password) {
	  int member_id =mh.getIDByEmail(email);
	  String hashed_password=password;
	  System.out.println("這是member_id:"+member_id);
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
          String sql = "INSERT INTO `missa`.`membercredential`(`hashed_member_id`,`hashed_password`)"
                  + " VALUES(?, ?)";

          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setInt(1, member_id);
          pres.setString(2, hashed_password);
          

          /** 執行新增之SQL指令並記錄影響之行數 */
          row = pres.executeUpdate();
         
          /** 紀錄真實執行的SQL指令，並印出 **/
          exexcute_sql = pres.toString();
          	
          
          System.out.println("這是真實執行的SQL指令:"+exexcute_sql);
         
          

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
   
   public JSONObject update(Member m) {
       /** 紀錄回傳之資料 */
       JSONArray jsa = new JSONArray();
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
           String sql = "Update `missa`.`membercredential` SET `hashed_password` = ?"
           		+ "WHERE `hashed_member_id` = ?";
           /** 取得所需之參數 */
           String hashed_password = m.getHashedPassword();
           int id=m.getID();
           
           /** 將參數回填至SQL指令當中 */
           pres = conn.prepareStatement(sql);
           pres.setString(1,hashed_password);
           pres.setInt(2,id);
           /** 執行更新之SQL指令並記錄影響之行數 */
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
       response.put("row", row);
       response.put("time", duration);
       response.put("data", jsa);

       return response;
   }
}
