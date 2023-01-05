package ncu.sa.demo.app;

import java.sql.*;
import org.json.*;
import ncu.sa.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class SponsorHelper<br>
 * SponsorHelper類別（class）主要管理所有與Sponsor相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */


public class SponsorHelper{
	 

    
    /** 靜態變數，儲存SponsorHelper物件 */
    private static SponsorHelper sh;
    
    /** 靜態變數，儲存SponsorRecordHelper物件 */
    private static SponsorRecordHelper srh;
    
    /** 靜態變數，儲存SponsorRecord物件 */
    private static SponsorRecord sr;
    
    /** 靜態變數，儲存MemberHelper物件 */
    private MemberHelper mh =   MemberHelper.getHelper();
   
   
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    /** 儲存該名會員 */
    private Sponsor sponsor = null;
    
    /** 儲存該名會員的id */
    private int id;
    
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個SponsorHelper 物件
     *
     * @return the helper 回傳SponsorHelper 物件
     */
    
    public static SponsorHelper getHelper() {
        /** Singleton檢查是否已經有SponsorHelper 物件，若無則new一個，若有則直接回傳 */
        if(sh == null) sh = new SponsorHelper ();
        return sh;
    }
    
    public void getIDByEmail(String email) {
    	this.id=mh.getIDByEmail(email);
    }
    

  
	    

    /**
     * 確認是否有此提案者
     *
     * 
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public boolean Check(Sponsor p) {
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
    
    
    /**
     * 建立該贊助至資料庫
     *
     * @param jso 一個提案之JSON object物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public void sponsor(JSONObject jso) {
 
    	
    	 int member_id = jso.getInt("member_id");
         int proposalOption_id =jso.getInt("proposalOption_id");
         int sponsor_payment_type =jso.getInt("sponsor_payment_type");
         int sponsor_amount = jso.getInt("sponsor_amount");
         int proposalOption_unitPrice = jso.getInt("proposalOption_unitPrice");
         int totalPrice = jso.getInt("totalPrice");
        
         sr=new SponsorRecord(member_id,proposalOption_id, 
        		sponsor_payment_type,sponsor_amount,proposalOption_unitPrice,totalPrice);
        srh.create(sr);   
        
        
        
    }
	
	
	
	
}