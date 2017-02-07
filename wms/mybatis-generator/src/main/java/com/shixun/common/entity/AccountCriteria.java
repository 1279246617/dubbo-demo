package com.shixun.common.entity;

import org.mybatis.plugin.model.QueryParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountCriteria() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", "%" + value + "%", "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", "%" + value + "%", "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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

        public Criteria andUnbalanceIsNull() {
            addCriterion("unbalance is null");
            return (Criteria) this;
        }

        public Criteria andUnbalanceIsNotNull() {
            addCriterion("unbalance is not null");
            return (Criteria) this;
        }

        public Criteria andUnbalanceEqualTo(BigDecimal value) {
            addCriterion("unbalance =", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceNotEqualTo(BigDecimal value) {
            addCriterion("unbalance <>", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceGreaterThan(BigDecimal value) {
            addCriterion("unbalance >", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unbalance >=", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceLessThan(BigDecimal value) {
            addCriterion("unbalance <", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unbalance <=", value, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceIn(List<BigDecimal> values) {
            addCriterion("unbalance in", values, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceNotIn(List<BigDecimal> values) {
            addCriterion("unbalance not in", values, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unbalance between", value1, value2, "unbalance");
            return (Criteria) this;
        }

        public Criteria andUnbalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unbalance not between", value1, value2, "unbalance");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyIsNull() {
            addCriterion("security_money is null");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyIsNotNull() {
            addCriterion("security_money is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyEqualTo(BigDecimal value) {
            addCriterion("security_money =", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyNotEqualTo(BigDecimal value) {
            addCriterion("security_money <>", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyGreaterThan(BigDecimal value) {
            addCriterion("security_money >", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("security_money >=", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyLessThan(BigDecimal value) {
            addCriterion("security_money <", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("security_money <=", value, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyIn(List<BigDecimal> values) {
            addCriterion("security_money in", values, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyNotIn(List<BigDecimal> values) {
            addCriterion("security_money not in", values, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("security_money between", value1, value2, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andSecurityMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("security_money not between", value1, value2, "securityMoney");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeIsNull() {
            addCriterion("total_income is null");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeIsNotNull() {
            addCriterion("total_income is not null");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeEqualTo(BigDecimal value) {
            addCriterion("total_income =", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeNotEqualTo(BigDecimal value) {
            addCriterion("total_income <>", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeGreaterThan(BigDecimal value) {
            addCriterion("total_income >", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_income >=", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeLessThan(BigDecimal value) {
            addCriterion("total_income <", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_income <=", value, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeIn(List<BigDecimal> values) {
            addCriterion("total_income in", values, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeNotIn(List<BigDecimal> values) {
            addCriterion("total_income not in", values, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_income between", value1, value2, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_income not between", value1, value2, "totalIncome");
            return (Criteria) this;
        }

        public Criteria andTotalExpendIsNull() {
            addCriterion("total_expend is null");
            return (Criteria) this;
        }

        public Criteria andTotalExpendIsNotNull() {
            addCriterion("total_expend is not null");
            return (Criteria) this;
        }

        public Criteria andTotalExpendEqualTo(BigDecimal value) {
            addCriterion("total_expend =", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendNotEqualTo(BigDecimal value) {
            addCriterion("total_expend <>", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendGreaterThan(BigDecimal value) {
            addCriterion("total_expend >", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_expend >=", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendLessThan(BigDecimal value) {
            addCriterion("total_expend <", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_expend <=", value, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendIn(List<BigDecimal> values) {
            addCriterion("total_expend in", values, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendNotIn(List<BigDecimal> values) {
            addCriterion("total_expend not in", values, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_expend between", value1, value2, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTotalExpendNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_expend not between", value1, value2, "totalExpend");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeIsNull() {
            addCriterion("today_income is null");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeIsNotNull() {
            addCriterion("today_income is not null");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeEqualTo(BigDecimal value) {
            addCriterion("today_income =", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeNotEqualTo(BigDecimal value) {
            addCriterion("today_income <>", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeGreaterThan(BigDecimal value) {
            addCriterion("today_income >", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("today_income >=", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeLessThan(BigDecimal value) {
            addCriterion("today_income <", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("today_income <=", value, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeIn(List<BigDecimal> values) {
            addCriterion("today_income in", values, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeNotIn(List<BigDecimal> values) {
            addCriterion("today_income not in", values, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_income between", value1, value2, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_income not between", value1, value2, "todayIncome");
            return (Criteria) this;
        }

        public Criteria andTodayExpendIsNull() {
            addCriterion("today_expend is null");
            return (Criteria) this;
        }

        public Criteria andTodayExpendIsNotNull() {
            addCriterion("today_expend is not null");
            return (Criteria) this;
        }

        public Criteria andTodayExpendEqualTo(BigDecimal value) {
            addCriterion("today_expend =", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendNotEqualTo(BigDecimal value) {
            addCriterion("today_expend <>", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendGreaterThan(BigDecimal value) {
            addCriterion("today_expend >", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("today_expend >=", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendLessThan(BigDecimal value) {
            addCriterion("today_expend <", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendLessThanOrEqualTo(BigDecimal value) {
            addCriterion("today_expend <=", value, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendIn(List<BigDecimal> values) {
            addCriterion("today_expend in", values, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendNotIn(List<BigDecimal> values) {
            addCriterion("today_expend not in", values, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_expend between", value1, value2, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andTodayExpendNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("today_expend not between", value1, value2, "todayExpend");
            return (Criteria) this;
        }

        public Criteria andSettAmountIsNull() {
            addCriterion("sett_amount is null");
            return (Criteria) this;
        }

        public Criteria andSettAmountIsNotNull() {
            addCriterion("sett_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSettAmountEqualTo(BigDecimal value) {
            addCriterion("sett_amount =", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountNotEqualTo(BigDecimal value) {
            addCriterion("sett_amount <>", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountGreaterThan(BigDecimal value) {
            addCriterion("sett_amount >", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sett_amount >=", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountLessThan(BigDecimal value) {
            addCriterion("sett_amount <", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sett_amount <=", value, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountIn(List<BigDecimal> values) {
            addCriterion("sett_amount in", values, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountNotIn(List<BigDecimal> values) {
            addCriterion("sett_amount not in", values, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sett_amount between", value1, value2, "settAmount");
            return (Criteria) this;
        }

        public Criteria andSettAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sett_amount not between", value1, value2, "settAmount");
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