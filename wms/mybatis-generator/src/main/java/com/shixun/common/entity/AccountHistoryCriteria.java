package com.shixun.common.entity;

import org.mybatis.plugin.model.QueryParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountHistoryCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountHistoryCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", "%" + value + "%", "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", "%" + value + "%", "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNull() {
            addCriterion("account_no is null");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNotNull() {
            addCriterion("account_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNoEqualTo(String value) {
            addCriterion("account_no =", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotEqualTo(String value) {
            addCriterion("account_no <>", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThan(String value) {
            addCriterion("account_no >", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("account_no >=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThan(String value) {
            addCriterion("account_no <", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThanOrEqualTo(String value) {
            addCriterion("account_no <=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLike(String value) {
            addCriterion("account_no like", "%" + value + "%", "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotLike(String value) {
            addCriterion("account_no not like", "%" + value + "%", "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoIn(List<String> values) {
            addCriterion("account_no in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotIn(List<String> values) {
            addCriterion("account_no not in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoBetween(String value1, String value2) {
            addCriterion("account_no between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotBetween(String value1, String value2) {
            addCriterion("account_no not between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andFundPositiveIsNull() {
            addCriterion("fund_positive is null");
            return (Criteria) this;
        }

        public Criteria andFundPositiveIsNotNull() {
            addCriterion("fund_positive is not null");
            return (Criteria) this;
        }

        public Criteria andFundPositiveEqualTo(Boolean value) {
            addCriterion("fund_positive =", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveNotEqualTo(Boolean value) {
            addCriterion("fund_positive <>", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveGreaterThan(Boolean value) {
            addCriterion("fund_positive >", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("fund_positive >=", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveLessThan(Boolean value) {
            addCriterion("fund_positive <", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveLessThanOrEqualTo(Boolean value) {
            addCriterion("fund_positive <=", value, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveIn(List<Boolean> values) {
            addCriterion("fund_positive in", values, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveNotIn(List<Boolean> values) {
            addCriterion("fund_positive not in", values, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveBetween(Boolean value1, Boolean value2) {
            addCriterion("fund_positive between", value1, value2, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andFundPositiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("fund_positive not between", value1, value2, "fundPositive");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettIsNull() {
            addCriterion("is_allow_sett is null");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettIsNotNull() {
            addCriterion("is_allow_sett is not null");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettEqualTo(Boolean value) {
            addCriterion("is_allow_sett =", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettNotEqualTo(Boolean value) {
            addCriterion("is_allow_sett <>", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettGreaterThan(Boolean value) {
            addCriterion("is_allow_sett >", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_allow_sett >=", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettLessThan(Boolean value) {
            addCriterion("is_allow_sett <", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettLessThanOrEqualTo(Boolean value) {
            addCriterion("is_allow_sett <=", value, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettIn(List<Boolean> values) {
            addCriterion("is_allow_sett in", values, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettNotIn(List<Boolean> values) {
            addCriterion("is_allow_sett not in", values, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettBetween(Boolean value1, Boolean value2) {
            addCriterion("is_allow_sett between", value1, value2, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsAllowSettNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_allow_sett not between", value1, value2, "isAllowSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettIsNull() {
            addCriterion("is_complete_sett is null");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettIsNotNull() {
            addCriterion("is_complete_sett is not null");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettEqualTo(Boolean value) {
            addCriterion("is_complete_sett =", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettNotEqualTo(Boolean value) {
            addCriterion("is_complete_sett <>", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettGreaterThan(Boolean value) {
            addCriterion("is_complete_sett >", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_complete_sett >=", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettLessThan(Boolean value) {
            addCriterion("is_complete_sett <", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettLessThanOrEqualTo(Boolean value) {
            addCriterion("is_complete_sett <=", value, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettIn(List<Boolean> values) {
            addCriterion("is_complete_sett in", values, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettNotIn(List<Boolean> values) {
            addCriterion("is_complete_sett not in", values, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettBetween(Boolean value1, Boolean value2) {
            addCriterion("is_complete_sett between", value1, value2, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andIsCompleteSettNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_complete_sett not between", value1, value2, "isCompleteSett");
            return (Criteria) this;
        }

        public Criteria andRequestNoIsNull() {
            addCriterion("request_no is null");
            return (Criteria) this;
        }

        public Criteria andRequestNoIsNotNull() {
            addCriterion("request_no is not null");
            return (Criteria) this;
        }

        public Criteria andRequestNoEqualTo(String value) {
            addCriterion("request_no =", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotEqualTo(String value) {
            addCriterion("request_no <>", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoGreaterThan(String value) {
            addCriterion("request_no >", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoGreaterThanOrEqualTo(String value) {
            addCriterion("request_no >=", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLessThan(String value) {
            addCriterion("request_no <", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLessThanOrEqualTo(String value) {
            addCriterion("request_no <=", value, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoLike(String value) {
            addCriterion("request_no like", "%" + value + "%", "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotLike(String value) {
            addCriterion("request_no not like", "%" + value + "%", "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoIn(List<String> values) {
            addCriterion("request_no in", values, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotIn(List<String> values) {
            addCriterion("request_no not in", values, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoBetween(String value1, String value2) {
            addCriterion("request_no between", value1, value2, "requestNo");
            return (Criteria) this;
        }

        public Criteria andRequestNoNotBetween(String value1, String value2) {
            addCriterion("request_no not between", value1, value2, "requestNo");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIsNull() {
            addCriterion("trx_type is null");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIsNotNull() {
            addCriterion("trx_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrxTypeEqualTo(String value) {
            addCriterion("trx_type =", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotEqualTo(String value) {
            addCriterion("trx_type <>", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeGreaterThan(String value) {
            addCriterion("trx_type >", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trx_type >=", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeLessThan(String value) {
            addCriterion("trx_type <", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeLessThanOrEqualTo(String value) {
            addCriterion("trx_type <=", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeLike(String value) {
            addCriterion("trx_type like", "%" + value + "%", "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotLike(String value) {
            addCriterion("trx_type not like", "%" + value + "%", "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIn(List<String> values) {
            addCriterion("trx_type in", values, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotIn(List<String> values) {
            addCriterion("trx_type not in", values, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeBetween(String value1, String value2) {
            addCriterion("trx_type between", value1, value2, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotBetween(String value1, String value2) {
            addCriterion("trx_type not between", value1, value2, "trxType");
            return (Criteria) this;
        }

        public Criteria andRiskDayIsNull() {
            addCriterion("risk_day is null");
            return (Criteria) this;
        }

        public Criteria andRiskDayIsNotNull() {
            addCriterion("risk_day is not null");
            return (Criteria) this;
        }

        public Criteria andRiskDayEqualTo(Short value) {
            addCriterion("risk_day =", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayNotEqualTo(Short value) {
            addCriterion("risk_day <>", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayGreaterThan(Short value) {
            addCriterion("risk_day >", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayGreaterThanOrEqualTo(Short value) {
            addCriterion("risk_day >=", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayLessThan(Short value) {
            addCriterion("risk_day <", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayLessThanOrEqualTo(Short value) {
            addCriterion("risk_day <=", value, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayIn(List<Short> values) {
            addCriterion("risk_day in", values, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayNotIn(List<Short> values) {
            addCriterion("risk_day not in", values, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayBetween(Short value1, Short value2) {
            addCriterion("risk_day between", value1, value2, "riskDay");
            return (Criteria) this;
        }

        public Criteria andRiskDayNotBetween(Short value1, Short value2) {
            addCriterion("risk_day not between", value1, value2, "riskDay");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIsNull() {
            addCriterion("bank_trx_no is null");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIsNotNull() {
            addCriterion("bank_trx_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoEqualTo(String value) {
            addCriterion("bank_trx_no =", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotEqualTo(String value) {
            addCriterion("bank_trx_no <>", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoGreaterThan(String value) {
            addCriterion("bank_trx_no >", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_trx_no >=", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLessThan(String value) {
            addCriterion("bank_trx_no <", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLessThanOrEqualTo(String value) {
            addCriterion("bank_trx_no <=", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLike(String value) {
            addCriterion("bank_trx_no like", "%" + value + "%", "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotLike(String value) {
            addCriterion("bank_trx_no not like", "%" + value + "%", "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIn(List<String> values) {
            addCriterion("bank_trx_no in", values, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotIn(List<String> values) {
            addCriterion("bank_trx_no not in", values, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoBetween(String value1, String value2) {
            addCriterion("bank_trx_no between", value1, value2, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotBetween(String value1, String value2) {
            addCriterion("bank_trx_no not between", value1, value2, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayIsNull() {
            addCriterion("trx_gateway is null");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayIsNotNull() {
            addCriterion("trx_gateway is not null");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayEqualTo(String value) {
            addCriterion("trx_gateway =", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayNotEqualTo(String value) {
            addCriterion("trx_gateway <>", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayGreaterThan(String value) {
            addCriterion("trx_gateway >", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayGreaterThanOrEqualTo(String value) {
            addCriterion("trx_gateway >=", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayLessThan(String value) {
            addCriterion("trx_gateway <", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayLessThanOrEqualTo(String value) {
            addCriterion("trx_gateway <=", value, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayLike(String value) {
            addCriterion("trx_gateway like", "%" + value + "%", "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayNotLike(String value) {
            addCriterion("trx_gateway not like", "%" + value + "%", "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayIn(List<String> values) {
            addCriterion("trx_gateway in", values, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayNotIn(List<String> values) {
            addCriterion("trx_gateway not in", values, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayBetween(String value1, String value2) {
            addCriterion("trx_gateway between", value1, value2, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andTrxGatewayNotBetween(String value1, String value2) {
            addCriterion("trx_gateway not between", value1, value2, "trxGateway");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", "%" + value + "%", "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", "%" + value + "%", "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}