package ncu.sa.demo.app;

import org.json.*;
import java.math.BigInteger;  
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;  


// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
    
	/** name，會員姓名 */
    private String name;
 
    /** phone，會員電子郵件信箱 */
    private String phone;
    
    /** email，會員電子郵件信箱 */
    private String email;
    
    /** salt，會員密碼 */
    private String salt;
    
    /** salt，會員密碼 */
    private byte[] bytesalt=null;
    
    
    /** password，會員密碼 */
    private String address;
    
    /** role，會員之身份 Member,Proposer,Sponsor*/
    private int role;
    
    /** password，會員密碼 */
    private String password;
    
    /** password，會員密碼 */
    private String hashed_password;
    
    private byte byteData[];
    
    /** id，會員編號 */
    private int id;
    
    

    
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    public Member(String email,String password){
    	
        setEmail(email);
        setPassword(password);

    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     * @param name 會員姓名
     * @param phone 會員電話
     * @param email 會員電子信箱
     * @param address 會員住址
     * @param password 會員密碼
     *  
     */
    public Member(String name,String phone,String email,String address,String password){
    	setName(name);
        setPhone(phone);
        setEmail(email);
        setSalt();
        setAddress(address);
        setRole();
        setPassword(password);
        setHashedPassword();
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時
     * 
     * @param id 會員編號  
     * @param name 會員姓名
     * @param phone 會員電話
     * @param email 會員電子信箱
     * @param salt 會員加鹽值
     * @param address 會員住址
     * @param role 會員身份
     * @param password 會員密碼
     */
    public Member(int id, String name,String phone,String email,String salt,String address,int role,String password) {
        setID(id); 
    	setPhone(phone);
        setEmail(email);
        setSalt(salt);
        setAddress(address);
        setRole(role);
        setHashedPassword(password);
    	setName(name);
    }
    /*更新會員角色*/
    public Member(int id, String email,int role) {
    	setID(id);
        setEmail(email);
        setRole(role);
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    public Member(String name,String phone, String email,String salt,String address,int role,String password,int id) {
        setPhone(phone);
        setEmail(email);
        setSalt(salt);
        setAddress(address);
        setRole(role);
        setHashedPassword(password);
   	 	setName(name);
   	 	setID(id);
    }
    
    public Member(String name,String phone, String email,String address,int id) {
        setPhone(phone);
        setEmail(email);
        setAddress(address);
   	 	setName(name);
   	 	setID(id);
    }
    
    /**
     * 設定會員之編號
     *
     * 
     */
    public void setID(int id) {
       this.id=id;
    }

    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 設定會員之姓名
     *
     * 
     */
    public void setName(String name) {
    	this.name = name;
    }

    /**
     * 取得會員之姓名
     *
     * @return the name 回傳會員姓名
     */
    public String getName() {
        return this.name;
    }
    /**
     * 設定會員之密碼
     *
     * 
     */
    public void setPassword(String password) {
    	this.password = password;
    }

    /**
     * 取得會員之密碼
     *
     * @return the name 回傳會員姓名
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * 設定會員之電話
     *
     * 
     */
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    
    /**
     * 取得會員之電話
     *
     * @return the phone 回傳會員電話
     */
    public String getPhone() {
        return this.phone;
    }
    
    
    /**
     * 設定會員之電子郵件信箱
     *
     * 
     */
    public void setEmail(String email) {
    	this.email = email;
    }
    
    
    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * 設立會員之加鹽值
     * @throws NoSuchAlgorithmException 
     *
     * 
     */
    public void setSalt(){
       SecureRandom securerandom;
	try {
		securerandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytesalt = new byte[16];
	       securerandom.nextBytes(bytesalt);
	       this.bytesalt=bytesalt;
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	}
	this.salt=toHex(bytesalt);
	
    }
    
    

    /**
     * 設定會員之加鹽值
     * @throws NoSuchAlgorithmException 
     *
     * 
     */
    public void setSalt(String salt){
    	this.salt=salt;
    }
    /**
     * 確認會員之加鹽值
     * 
     *
     * 
     */
    public void checkSalt(String salt){
    	this.bytesalt=fromHex(salt);
    }
    /**
     * 實作toHex方法
     *
     * @return String 型態的會員salt
     */
    public static String toHex (byte[]  array) {
    	BigInteger bi=new BigInteger(1,array);
    	String hex = bi.toString(16);
    	int paddingLength =(array.length*2)-hex.length();
    	if(paddingLength>0) {

    		return String.format("%0"+paddingLength+"d",0)+hex;
    	}else {
    		
    		return hex;
    	}
    }
    /**
     * 實作fromHex方法
     *
     * @return byte[]  型態的會員salt
     */
    public static byte[] fromHex (String hex) {
    	byte[] binary = new byte[hex.length()/2];
    	for(int i =0;i<binary.length;i++) {
    		binary[i]=(byte) Integer.parseInt(hex.substring(2*i,2*i+2),16);
    	}
    	return binary;
    }
    /**
     * 取得會員之加鹽值
     *
     * @return the salt 回傳會員加鹽值
     */
    public String getSalt() {
    		return this.salt;
    }
    
    /**
     * 設定會員之地址
     *
     * 
     */
    public void setAddress(String address) {
        this.address=address;
    }
    
    
    /**
     * 取得會員之地址
     *
     * @return the password 回傳會員地址
     */
    public String getAddress() {
        return this.address;
    }
    
    
    /**
     * 設立會員資料之會員身份
     *
     * 
     */
    
    public void setRole(){
        this.role=0;
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
     * 設立會員資料之加密後密碼
     * @throws NoSuchAlgorithmException 
     *
     * 
     */
    
    public void setHashedPassword(){
       MessageDigest md;
	try {
		md = MessageDigest.getInstance("SHA-512");
		 md.update(bytesalt);
	       byteData =md.digest(this.password.getBytes());
	       md.reset();
	} catch (NoSuchAlgorithmException e) {
		
		e.printStackTrace();
	}
	this.hashed_password=toHex(byteData);
    }
    
    public void setHashedPassword(String hashed_password){
       this.hashed_password=hashed_password;
       
     }
    
  
    /**
     * 取得會員資料之加密後密碼
     *
     * @return the hashed_password 回傳會員加密後密碼
     */
    
    public String getHashedPassword(){
        return this.hashed_password;
    }
    
  
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = mh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
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