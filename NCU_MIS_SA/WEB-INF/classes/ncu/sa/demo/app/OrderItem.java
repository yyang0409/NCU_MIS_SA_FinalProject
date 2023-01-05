package ncu.sa.demo.app;

import org.json.JSONObject;
import ncu.sa.demo.util.Arith;
import ncu.sa.demo.app.Proposal;

public class OrderItem {

    /** id，提案選項細項編號 */
    private int id;

    /** po，提案選項 */
    private ProposalOption po;

    /** price，產品價格 */
    private int price;

    /** ph，ProposalOption 之物件與 OrderItem 相關之資料庫方法（Sigleton） */
    private ProposalOptionHelper poh =  ProposalOptionHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）OrderItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單細項時
     *
     * @param proposalOption 提案
     */
    public OrderItem(ProposalOption po) {
        this.po = po;
        this.price = this.po.getPrice();
    }

    /**
     * 實例化（Instantiates）一個新的（new）OrderItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單細項時
     *
     * @param order_proposaloption_id 訂單產品編號
     * @param order_id 訂單編號
     * @param proposaloption_id 產品編號
     * @param price 產品價格
     */
    public OrderItem(int order_proposaloption_id, int order_id, int proposaloption_id, int price) {
        this.id = order_proposaloption_id;
        this.price = price;
        getProposalOptionFromDB(proposaloption_id);
    }

    /**
     * 從 DB 中取得產品
     */
    private void getProposalOptionFromDB(int proposaloption_id) {
        String id = String.valueOf(proposaloption_id);
        this.po = poh.getProposalOptionById(id);
    }


    /**
     * 取得產品
     *
     * @return ProposalOption 回傳提案選項
     */
    public ProposalOption getProposalOption() {
        return po;
    }

    /**
     * 設定訂單細項編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單細項編號
     *
     * @return int 回傳訂單細項編號
     */
    public int getId() {
        return id;
    }


    /**
     * 取得提案選項價格
     *
     * @return double 回傳提案選項價格
     */
    public int getPrice() {
        return price;
    }

   
    /**
     * 取得產品細項資料
     *
     * @return JSONObject 回傳產品細項資料
     */
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("proposaloption", getProposalOption().getData());
        data.put("price", getPrice());

        return data;
    }
}