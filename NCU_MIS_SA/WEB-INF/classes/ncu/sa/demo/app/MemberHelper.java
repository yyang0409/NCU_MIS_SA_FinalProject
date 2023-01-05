package ncu.sa.demo.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import org.json.*;
import ncu.sa.demo.util.DBMgr;
import java.security.SecureRandom;
import java.security.MessageDigest;  

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberHelper<br>
 * MemberHelper類別（class）主要管理所有與Member相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class MemberHelper {
    
    /**
     * 實例化（Instantiates）一個新的（new）MemberHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private MemberHelper() {
        
    }
    
    /** 靜態變數，儲存MemberHelper物件 */
    private static MemberHelper mh;
    private static MemberCredentialHelper mch = MemberCredentialHelper.getHelper();
    private static FollowingRecordHelper frh;
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    private PreparedStatement pres2 = null;
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static MemberHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(mh == null) mh = new MemberHelper();
        return mh;
    }
    
    
    /**
     * 檢查該名會員之電子郵件信箱是否重複註冊
     *
     * @param m 一名會員之Member物件
     * @return boolean 若重複註冊回傳False，若該信箱不存在則回傳True
     */
    public boolean checkDuplicate(Member m){
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `missa`.`member` WHERE `email` = ?";
            
            /** 取得所需之參數 */
            String email = m.getEmail();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
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
     * 建立該名會員至資料庫
     *
     * @param m 一名會員之Member物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(Member m) {
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
            String sql = "INSERT INTO `missa`.`member`(`name`, `phone`,`email`,`salt`,`address`,`role`)"
                    + " VALUES(?, ?, ?,?, ?, ?)";
            
            /** 取得所需之參數 */
            String name = m.getName();
            String email = m.getEmail();
            String phone = m.getPhone();
            String salt = m.getSalt();
            String address = m.getAddress();
            int role = m.getRole();
            String hashed_password = m.getHashedPassword();
            
  
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, phone);
            pres.setString(3, email);
            pres.setString(4, salt);
            pres.setString(5, address);
            pres.setInt(6, role);

            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
           
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
 
            System.out.println("這個是真實執行的SQL指令"+exexcute_sql);
            
            
            /**/
            mch.create(email,hashed_password);
            

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
    
    /**
     * 取回所有會員資料
     *
     * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
     */
    public JSONObject getAll() {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Member m = null;
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
            String sql = "SELECT * FROM `missa`.`member`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int member_id = rs.getInt("member_id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String salt = rs.getString("salt");
                String address = rs.getString("address");
                int  role = rs.getInt("role");
                
                String password = getHashedPasswordByID(email);
                
                /** 將每一筆會員資料產生一名新Member物件 */
                m = new Member(member_id,name,phone,email,salt,address,role,password);
                
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getData());
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
    
    /**
     * 透過會員編號（ID）取得會員資料
     *
     * @param id 會員編號
     * @return the JSON object 回傳SQL執行結果與該會員編號之會員資料
     */
    public JSONObject getByID(String id) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        Member m = null;
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
        ResultSet rs2 = null;
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`member` WHERE `member_id` = ? LIMIT 1";
            
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
                int member_id = rs.getInt("id");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String salt = rs.getString("salt");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                String name = rs.getString("name");
                
                /*透過getHashedPasswordByID()抓HashedPassword*/
                String hashed_password=getHashedPasswordByID(email);
                
                /** 將每一筆會員資料產生一名新Member物件 */
                m = new Member(member_id,name,phone,email,salt,address,role,hashed_password);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getData());
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
            DBMgr.close(rs2, pres2, conn);
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
    
    
    public int getIDByEmail(String email) {
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        int member_id=0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`member` WHERE `email` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            rs.next();
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
           
            /** 將 ResultSet 之資料取出 */
            member_id = rs.getInt("member_id");
          
            
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
        
       

        return member_id;
    }
    
    
    
    public String getHashedPasswordByID(String email){

        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        String hashed_password = null;
        try {
        	 int member_id=getIDByEmail(email);
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            
            /** SQL指令 */
            String sql = "SELECT `hashed_password` FROM `missa`.`membercredential` WHERE `hashed_member_id` = ?";
           
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,member_id);
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往下一列，取得回傳資料 */
            rs.next();
            
            /** 將 ResultSet 之資料取出 */
            hashed_password = rs.getString("hashed_password");  
            
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

        return hashed_password;
    }
    
    /**
     * 更新一名會員之會員資料
     *
     * @param m 一名會員之Member物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
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
            String sql = "Update `missa`.`member` SET `name` = ? ,`phone` = ?,`email` = ? ,`address` = ? "
            		+ "WHERE `member_id` = ?";
            /** 取得所需之參數 */
            String name = m.getName();
            String email = m.getEmail();
            String phone = m.getPhone();
            String address = m.getAddress();
            int id=m.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, phone);
            pres.setString(3, email);
            pres.setString(4, address);
            pres.setInt(5,id);
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
    
    
   
    /**
     * 透過會員編號（ID）刪除會員
     *
     * @param id 會員編號
     * @return the JSONObject 回傳SQL執行結果
     */
    public JSONObject deleteByID(String id) {
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
            String sql = "DELETE FROM `missa`.`member` WHERE `member_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
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
    
    
    
    
    
        
       

    /**
     * 檢查該名會員之密碼是否正確
     *
     * @param email 會員的信箱
     * @param password 會員的密碼
     * @return boolean 若重複註冊回傳False，若該密碼不一致則回傳True
     */
    public String getHashedPassword(int member_id){

        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        String hashed_password = null;	
        
        try {

            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            
            /** SQL指令 */
            String sql = "SELECT `hashed_password` FROM `missa`.`membercredential` WHERE `hashed_member_id` = ?";
           
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,member_id);
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往下一列，取得回傳資料 */
            rs.next();
            
            /** 將 ResultSet 之資料取出 */
            hashed_password = rs.getString("hashed_password");  
            
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
        return hashed_password;
    }
    
    
    public JSONObject checkPassword(Member m) {
    	String is_password="false";
        String hashed_password = null;
    	String salt=null;
    	String email=m.getEmail();
        String password=m.getPassword();
        int member_id=getIDByEmail(email);

	  
	   	
	   	hashed_password=getHashedPassword(member_id);
	   	System.out.println("這是資料庫抓的hashed_password:"+hashed_password);
    	/** 
         * 判斷輸入值是否資料庫的ㄧ致
         * 若一致則回傳True，否則回傳False
         */
        salt=getSalt(member_id);/*透過id從資料庫抓salt*/
        System.out.println("這是資料庫抓的salt:"+salt);
        m.checkSalt(salt);
        m.setSalt(salt);/*弄到member物件中*/
        m.setHashedPassword();/*把password加密*/
        password=m.getHashedPassword();/*抓出加密完的password*/
        System.out.println("使用者輸入密碼 加密後:"+password);
        if(password.equals(hashed_password)) {	/*判斷使用者輸入的密碼經過加密後跟資料庫的一樣嗎*/
        	is_password="true";			
        }
        System.out.println("is_password:"+is_password);
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("is_password", is_password);
        
        return response;
    }
    
    public String getSalt(int member_id) {
    	/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        String salt = null;
        try {
	    	 /** 取得資料庫之連線 */
	        conn = DBMgr.getConnection();
	        
	        /** SQL指令 */
	        String sql = "SELECT `salt` FROM `missa`.`member` WHERE `member_id` = ?";
	       
	        /** 將參數回填至SQL指令當中 */
	        pres = conn.prepareStatement(sql);
	        pres.setInt(1,member_id);
	        
	        /** 執行查詢之SQL指令並記錄其回傳之資料 */
	        rs = pres.executeQuery();
	
	        /** 讓指標移往下一列，取得回傳資料 */
	        rs.next();
	        
	        /** 將 ResultSet 之資料取出 */
	        salt = rs.getString("salt");  
	        
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
    	return salt;
    }
    

}