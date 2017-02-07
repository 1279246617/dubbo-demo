package com.shixun.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountHistory implements Serializable {
    private String id;

    private String accountNo;

    private BigDecimal balance;

    private BigDecimal amount;

    private Boolean fundPositive;

    private Boolean isAllowSett;

    private Boolean isCompleteSett;

    private String requestNo;

    private String trxType;

    private Short riskDay;

    private String bankTrxNo;

    private String trxGateway;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public AccountHistory() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getFundPositive() {
        return fundPositive;
    }

    public void setFundPositive(Boolean fundPositive) {
        this.fundPositive = fundPositive;
    }

    public Boolean getIsAllowSett() {
        return isAllowSett;
    }

    public void setIsAllowSett(Boolean isAllowSett) {
        this.isAllowSett = isAllowSett;
    }

    public Boolean getIsCompleteSett() {
        return isCompleteSett;
    }

    public void setIsCompleteSett(Boolean isCompleteSett) {
        this.isCompleteSett = isCompleteSett;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo == null ? null : requestNo.trim();
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType == null ? null : trxType.trim();
    }

    public Short getRiskDay() {
        return riskDay;
    }

    public void setRiskDay(Short riskDay) {
        this.riskDay = riskDay;
    }

    public String getBankTrxNo() {
        return bankTrxNo;
    }

    public void setBankTrxNo(String bankTrxNo) {
        this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
    }

    public String getTrxGateway() {
        return trxGateway;
    }

    public void setTrxGateway(String trxGateway) {
        this.trxGateway = trxGateway == null ? null : trxGateway.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}