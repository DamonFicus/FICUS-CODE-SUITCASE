package ficus.suitcase.localcache;


import java.util.Date;

/**
 * 类说明：<br>
   卡BIN信息实体
 * 详细描述：<br>
 *
 * @author DamonFicus
 */
public class DebitCardBinInfo extends BaseEntity {

    private static final long serialVersionUID = -3728930046533214255L;
    /**
     * 银行代码
     */
    private String bankCode;
    /**
     * 发卡行代码
     */
    private String bankNo;
    /**
     * 发卡行名称
     */
    private String bankName;
    /**
     * 卡名
     */
    private String cardName;
    /**
     * 主账号起始字节
     */
    private String startByte;
    /**
     * 主账号长度
     */
    private String accountNoLen;
    /**
     * 银行卡号
     */
    private String accountNo;
    /**
     * 发卡行标识取值
     */
    private String cardFlag;
    /**
     * 标识长度
     */
    private String cardFlagLen;
    /**
     * 卡种类型
     */
    private CardType cardType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 用于记录返回错误信息，不存在于表中
     */
    private String rtnImportErrMsg;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getStartByte() {
        return startByte;
    }

    public void setStartByte(String startByte) {
        this.startByte = startByte;
    }

    public String getAccountNoLen() {
        return accountNoLen;
    }

    public void setAccountNoLen(String accountNoLen) {
        this.accountNoLen = accountNoLen;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(String cardFlag) {
        this.cardFlag = cardFlag;
    }

    public String getCardFlagLen() {
        return cardFlagLen;
    }

    public void setCardFlagLen(String cardFlagLen) {
        this.cardFlagLen = cardFlagLen;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getCreateTime() {
        if (createTime != null) {
            return (Date) createTime.clone();
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        if (updateTime != null) {
            return (Date) updateTime.clone();
        }
        return null;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = (Date) updateTime.clone();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRtnImportErrMsg() {
        return rtnImportErrMsg;
    }

    public void setRtnImportErrMsg(String rtnImportErrMsg) {
        this.rtnImportErrMsg = rtnImportErrMsg;
    }


}
