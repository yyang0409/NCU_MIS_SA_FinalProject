package ncu.sa.demo.app;

import org.json.JSONObject;

public class Proposer extends Member{
	
	/** role，會員身份 */
	private int role;
	
	/** is_premium，會員是否為premium */
	private int is_premium;
	

	/**
     * 實例化（Instantiates）一個新的（new）Proposer物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢提案者資料時，產生一名新的提案者
     * @param id 會員編號
     * @param name 會員姓名
     * @param phone 會員電話
     * @param email 會員電子信箱
     * @param salt 會員加鹽值
     * @param address 會員住址
     * @param role 會員身份
     * @param password 會員密碼
     *  
     */
	public Proposer(String name, String phone, String email, String salt, 
			String address, int role,String password,int id,int is_premium) {
		super(name,phone, email, salt, address, role, password,id);
		setRole();
		setIsPremium(is_premium);
	}

    /**
     * 設立會員資料之會員身份
     *
     * 
     */
    
    public void setRole(){
        this.role=1;
    }
    
    /**
     * 設定會員資料之會員身份
     *
     * 
     */
    
    public void setRole(int role) {
        this.role=role;
    }
    
    
    /**
     * 取得會員資料之會員身份
     *
     * @return the role 回傳會員身份
     */
    
    public int getRole() {
        return this.role;
    }
    
    
    /**
     * 設定會員資料之是否為premium
     *
     * 
     */
    
    public void setIsPremium(int is_premium) {
        this.is_premium=is_premium;
    }
    
    
    /**
     * 取得會員資料之會員身份
     *
     * @return the role 回傳會員身份
     */
    
    public int getIsPremium() {
        return  this.is_premium;
    }
		
    /**
     * 取得該名贊助者所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("phone", getPhone());
        jso.put("email", getEmail());
        jso.put("salt", getSalt());
        jso.put("address", getAddress());
        jso.put("role", getRole());
        jso.put("password", getHashedPassword());
        
        return jso;
    }
  
    
		
	}
		