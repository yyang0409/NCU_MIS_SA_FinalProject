package ncu.sa.demo.app;

import org.json.JSONObject;

public class FollowingRecord {

	private int following_record_id;
	
	private int member_id;

	private int proposal_id;
	
	public FollowingRecord(int member_id,int proposal_id) {
		setMemberId(member_id);
		setProposalId(proposal_id);
	}
	
	public FollowingRecord(int following_record_id,int member_id,int proposal_id) {
		setFollowingRecordId(following_record_id);
		setMemberId(member_id);
		setProposalId(proposal_id);
	}

    public void setFollowingRecordId(int following_record_id){
        this.following_record_id=following_record_id;
    }
    
 
    public int getFollowingRecordId() {
        return this.following_record_id;
    }
  
    public void setMemberId(int member_id){
        this.member_id= member_id;
    }
    
    
    public int getMemberId(){
        return this.member_id;
    }
    

    public void setProposalId(int proposal_id){
        this.proposal_id= proposal_id;
    }
    
    
    public int getProposalId(){
       return this.proposal_id;
    }
    
    
	public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("following_record_id", getFollowingRecordId());
        jso.put("member_id", getMemberId());
        jso.put("proposal_id", getProposalId());
      
        
        return jso;
    }
	
}