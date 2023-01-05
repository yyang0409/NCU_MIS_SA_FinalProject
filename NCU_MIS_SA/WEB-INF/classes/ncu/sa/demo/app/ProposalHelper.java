package ncu.sa.demo.app;

import java.sql.*;

import org.json.*;

import ncu.sa.demo.util.DBMgr;

 

public class ProposalHelper {
	private ProposalHelper() {
        
    }
    private static ProposalHelper ph;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ProposalOptionHelper poh =  ProposalOptionHelper.getHelper();
    
    public static ProposalHelper getHelper() {
        /** Singleton檢查是否已經有ProductHelper物件，若無則new一個，若有則直接回傳 */
        if(ph == null) ph = new ProposalHelper();
        
        return ph;
    }
    
    public JSONObject getAll() {
        /** 新建一個 Proposal 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
    	Proposal p = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
            
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`proposal`";
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
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
                int proposal_id = rs.getInt("proposal_id");
                int category_id = rs.getInt("category_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String image = rs.getString("image");
                int raised_funds = rs.getInt("raised_funds");
                int goal = rs.getInt("goal");
                int proposal_status = rs.getInt("proposal_status");
                int viewed_num = rs.getInt("viewed_num");
                String  created_date = rs.getString("created_date");
                String due_date = rs.getString("due_date");
                
                /** 將每一筆商品資料產生一名新Proposal物件 */
                p = new Proposal(proposal_id,category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(p.getData());
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
    
    public JSONObject getByIdList(String data) {
        /** 新建一個 Proposal 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        Proposal p = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
            String[] in_para = DBMgr.stringToArray(data, ",");
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`proposal` WHERE `proposal`.`proposal_id`";
            for (int i=0 ; i < in_para.length ; i++) {
                sql += (i == 0) ? "in (?" : ", ?";
                sql += (i == in_para.length-1) ? ")" : "";
            }
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            for (int i=0 ; i < in_para.length ; i++) {
              pres.setString(i+1, in_para[i]);
            }
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
                int proposal_id = rs.getInt("proposal_id");
                int category_id = rs.getInt("category_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String image = rs.getString("image");
                int raised_funds = rs.getInt("raised_funds");
                int goal = rs.getInt("goal");
                int proposal_status = rs.getInt("proposal_status");
                int viewed_num = rs.getInt("viewed_num");
                String  created_date = rs.getString("created_date");
                String due_date = rs.getString("due_date");
                
                /** 將每一筆商品資料產生一名新Proposal物件 */
                p = new Proposal(proposal_id,category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(p.getData());
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

    
    public JSONObject getByKeyWord(String key) {
        /** 新建一個 Proposal 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        Proposal p = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
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
            String sql = "SELECT * FROM `missa`.`proposal` WHERE `proposal`.`title` LIKE ? LIMIT 1"; 
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, key);
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
                int proposal_id = rs.getInt("proposal_id");
                int category_id = rs.getInt("category_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String image = rs.getString("image");
                int raised_funds = rs.getInt("raised_funds");
                int goal = rs.getInt("goal");
                int proposal_status = rs.getInt("proposal_status");
                int viewed_num = rs.getInt("viewed_num");
                String  created_date = rs.getString("created_date");
                String due_date = rs.getString("due_date");
                
                /** 將每一筆商品資料產生一名新Proposal物件 */
                p = new Proposal(proposal_id,category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jso=p.getData();
                System.out.println(jso);
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
        //long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        //long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
       /* JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jso);
        System.out.println("response:"+response);*/
        return jso;
    }
    public Proposal getById(String id) {
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
        Proposal p = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`proposal` WHERE `proposal`.`proposal_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
            	 /** 將 ResultSet 之資料取出 */
                int proposal_id = rs.getInt("proposal_id");
                int category_id = rs.getInt("category_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String image = rs.getString("image");
                int raised_funds = rs.getInt("raised_funds");
                int goal = rs.getInt("goal");
                int proposal_status = rs.getInt("proposal_status");
                int viewed_num = rs.getInt("viewed_num");
                String  created_date = rs.getString("created_date");
                String due_date = rs.getString("due_date");
                
                /** 將每一筆商品資料產生一名新Proposal物件 */
                p = new Proposal(proposal_id,category_id,title,content,image,raised_funds,goal,proposal_status ,viewed_num,created_date,due_date);
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

        return p;
    }
    public JSONObject deleteById(int id) {
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
            String sql = "DELETE FROM `missa`.`proposal` WHERE `proposal_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
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
    
    public boolean checkDuplicate(Proposal p){
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `missa`.`proposal` WHERE `proposal_id` = ?";
            
            /** 取得所需之參數 */
            int proposal_id = p.getProposalId();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, proposal_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

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
         * 判斷是否已經有一筆該proposal_id之資料
         * 若無一筆則回傳False，否則回傳True 
         */
        return (row == 0) ? false : true;
    }
    
    /**
     * 建立該提案至資料庫
     *
     * @param p 一名提案之Proposal物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(Proposal p) {
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
            String sql = "INSERT INTO `missa`.`proposal`(`category_id`,`title`, `content`, `raised_funds`,`goal`, `image`,`proposal_status`, `viewed_num`,`created_date`,`due_date`)"
                    + " VALUES(?,?, ?, ?, ?, ?, ?, ?, ?,?)";
            
            /** 取得所需之參數 */
            int category_id = p.getCategoryId();
            String title = p.getTitle();
            String content = p.getContent();
            int raised_funds = p.getRaisedFunds();
            int goal = p.getGoal();
            String image = p.getImage();
            int proposal_status = p.getProposalStatus();
            int viewed_num = p.getViewedNum();
            String created_date = p.getCreatedDate();
            String due_date = p.getDueDate();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,category_id);
            pres.setString(2, title);
            pres.setString(3, content);
            pres.setInt(4,raised_funds);
            pres.setInt(5, goal);
            pres.setString(6,image);
            pres.setInt(7, proposal_status);
            pres.setInt(8, viewed_num);
            pres.setString(9, created_date);
            pres.setString(10, due_date);
            
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
    
    /**
     * 更新一提案之資料
     *
     * @param p 一提案之Proposal物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
    public JSONObject update(Proposal p) {
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
            String sql = "Update `missa`.`proposal` SET `category_id` = ? ,`title` = ? , `content` = ? ,`raised_funds` = ? ,`goal` = ? ,`image` = ?,`proposal_status` = ? ,`viewed_num` = ? ,`created_date` = ? ,`due_date` = ? WHERE `proposal_id` = ?";
            /** 取得所需之參數 */
            int proposal_id = p.getProposalId();
            int category_id = p.getCategoryId();
            String title = p.getTitle();
            String content = p.getContent();
            int raised_funds = p.getRaisedFunds();
            int goal = p.getGoal();
            String image =p.getImage();
            int proposal_status = p.getProposalStatus();
            int viewed_num = p.getViewedNum();
            String created_date = p.getCreatedDate();
            String due_date = p.getDueDate();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,category_id);
            pres.setString(2, title);
            pres.setString(3, content);
            pres.setInt(4,raised_funds);
            pres.setInt(5, goal);
            pres.setString(6,image);
            pres.setInt(7, proposal_status);
            pres.setInt(8, viewed_num);
            pres.setString(9, created_date);
            pres.setString(10, due_date);
            pres.setInt(11,proposal_id);
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
    
    public void updateRaisedFunds(int totalprice,int proposal_id) {
    	/** 紀錄回傳之資料 */
        //JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        //long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            
            /** SQL指令 */
            String sql = "Update `missa`.`proposal` SET `raised_funds` = ?  WHERE `proposal_id` = ?";
            
            int raised_funds=getRaisedFunds(proposal_id); 
            raised_funds=totalprice+raised_funds;
            
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,raised_funds);
            pres.setInt(2,proposal_id);
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
        
        
    }
    
    public int getRaisedFunds(int id) {
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
        Proposal p = null;
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int raised_funds = 0;
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`proposal` WHERE `proposal`.`proposal_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
            	 /** 將 ResultSet 之資料取出 */
              raised_funds = rs.getInt("raised_funds");
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

        return raised_funds;
    }
}
