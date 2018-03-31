package com.heima.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-12-25
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
import java.io.Serializable;
import java.util.Date;

/**
 * id, 坐席id  订单id  是否支付
 * pan;     mobile;            productInfo;            tranAmt;               tranDateTime;
 * currencyType;  merOrderNum;     merchantID;     sysTraceNum;  流水号(000000-989999)
 * orderType;     idCard;      settlementDate;  addition;     backAddition;
 */

public class PayTrans implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String userId;
    private String orderId;

    private String reference;  //银联参考号

    private String payType;    //回呼支付、一线通支付

    private String payMethod;  //支付、退款

    private String payStates;  //支付状态 已经支付1 未支付 0

    private String payInfo;  //支付返回编码

    private String payDesc;  //支付状态描述  已经支付1 未支付 0

    private String callTime;  //呼叫时间

    private String callStates; //呼叫状态，0 不呼叫  1 立即呼叫 2 正在呼叫 3 完成

    private String panCode; //添加Sales用

    private String idCardCode; //添加Sales用

    private String pan;
    private String mobile;
    private String productInfo;
    private String tranAmt;
    private String tranDateTime;
    private String currencyType;
    private String merOrderNum;
    private String merchantID;
    private String sysTraceNum;
    private String orderType;     //
    private String idCard;
    private String settlementDate;
    private String addition;
    private String backAddition;

    private String tranDateTime2;  //原交易时间

    private String sysTraceNum2;  //原交易流水号

    private Date dateCreated;  //创建日期

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayStates() {
        return payStates;
    }

    public void setPayStates(String payStates) {
        this.payStates = payStates;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getTranDateTime() {
        return tranDateTime;
    }

    public void setTranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getSysTraceNum() {
        return sysTraceNum;
    }

    public void setSysTraceNum(String sysTraceNum) {
        this.sysTraceNum = sysTraceNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getBackAddition() {
        return backAddition;
    }

    public void setBackAddition(String backAddition) {
        this.backAddition = backAddition;
    }

    public String getCallStates() {
        return callStates;
    }

    public void setCallStates(String callStates) {
        this.callStates = callStates;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getPayDesc() {
        return payDesc;
    }

    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    public String getPanCode() {
        return panCode;
    }

    public void setPanCode(String panCode) {
        this.panCode = panCode;
    }

    public String getIdCardCode() {
        return idCardCode;
    }

    public void setIdCardCode(String idCardCode) {
        this.idCardCode = idCardCode;
    }


    public String getTranDateTime2() {
        return tranDateTime2;
    }

    public void setTranDateTime2(String tranDateTime2) {
        this.tranDateTime2 = tranDateTime2;
    }

    public String getSysTraceNum2() {
        return sysTraceNum2;
    }

    public void setSysTraceNum2(String sysTraceNum2) {
        this.sysTraceNum2 = sysTraceNum2;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
