package ficus.tmp;

import java.util.Date;

public class CustInfoLogRecord {

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 会员号
     */
    private String memberNo;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 客户类别
     */
    private String memberType;

    /**
     * 客户名称
     */
    private String memberName;

    /**
     * 证件类型
     */
    private String certType;

    /**
     * 操作人工号
     */
    private String operateNo;

    /**
     * 变更字段
     */
    private String changeField;

    /**
     * 变更前内容
     */
    private String changeBefore;

    /**
     * 变更后内容
     */
    private String changeAfter;



    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getOperateNo() {
        return operateNo;
    }

    public void setOperateNo(String operateNo) {
        this.operateNo = operateNo;
    }

    public String getChangeField() {
        return changeField;
    }

    public void setChangeField(String changeField) {
        this.changeField = changeField;
    }

    public String getChangeBefore() {
        return changeBefore;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public String getChangeAfter() {
        return changeAfter;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }


}
