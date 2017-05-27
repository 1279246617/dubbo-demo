package com.coe.wms.facade.mgmt.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class UserCriteria extends QueryParam {
	private static final long serialVersionUID = -8658621674991088466L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserCriteria() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNull() {
            addCriterion("user_code is null");
            return (Criteria) this;
        }

        public Criteria andUserCodeIsNotNull() {
            addCriterion("user_code is not null");
            return (Criteria) this;
        }

        public Criteria andUserCodeEqualTo(String value) {
            addCriterion("user_code =", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotEqualTo(String value) {
            addCriterion("user_code <>", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThan(String value) {
            addCriterion("user_code >", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("user_code >=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThan(String value) {
            addCriterion("user_code <", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLessThanOrEqualTo(String value) {
            addCriterion("user_code <=", value, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeLike(String value) {
            addCriterion("user_code like", "%" + value + "%", "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotLike(String value) {
            addCriterion("user_code not like", "%" + value + "%", "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeIn(List<String> values) {
            addCriterion("user_code in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotIn(List<String> values) {
            addCriterion("user_code not in", values, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeBetween(String value1, String value2) {
            addCriterion("user_code between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserCodeNotBetween(String value1, String value2) {
            addCriterion("user_code not between", value1, value2, "userCode");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", "%" + value + "%", "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", "%" + value + "%", "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserPwdIsNull() {
            addCriterion("user_pwd is null");
            return (Criteria) this;
        }

        public Criteria andUserPwdIsNotNull() {
            addCriterion("user_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andUserPwdEqualTo(String value) {
            addCriterion("user_pwd =", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotEqualTo(String value) {
            addCriterion("user_pwd <>", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdGreaterThan(String value) {
            addCriterion("user_pwd >", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdGreaterThanOrEqualTo(String value) {
            addCriterion("user_pwd >=", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLessThan(String value) {
            addCriterion("user_pwd <", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLessThanOrEqualTo(String value) {
            addCriterion("user_pwd <=", value, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdLike(String value) {
            addCriterion("user_pwd like", "%" + value + "%", "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotLike(String value) {
            addCriterion("user_pwd not like", "%" + value + "%", "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdIn(List<String> values) {
            addCriterion("user_pwd in", values, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotIn(List<String> values) {
            addCriterion("user_pwd not in", values, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdBetween(String value1, String value2) {
            addCriterion("user_pwd between", value1, value2, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPwdNotBetween(String value1, String value2) {
            addCriterion("user_pwd not between", value1, value2, "userPwd");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIsNull() {
            addCriterion("user_phone is null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIsNotNull() {
            addCriterion("user_phone is not null");
            return (Criteria) this;
        }

        public Criteria andUserPhoneEqualTo(String value) {
            addCriterion("user_phone =", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotEqualTo(String value) {
            addCriterion("user_phone <>", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneGreaterThan(String value) {
            addCriterion("user_phone >", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("user_phone >=", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneLessThan(String value) {
            addCriterion("user_phone <", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneLessThanOrEqualTo(String value) {
            addCriterion("user_phone <=", value, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneLike(String value) {
            addCriterion("user_phone like", "%" + value + "%", "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotLike(String value) {
            addCriterion("user_phone not like", "%" + value + "%", "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneIn(List<String> values) {
            addCriterion("user_phone in", values, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotIn(List<String> values) {
            addCriterion("user_phone not in", values, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneBetween(String value1, String value2) {
            addCriterion("user_phone between", value1, value2, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserPhoneNotBetween(String value1, String value2) {
            addCriterion("user_phone not between", value1, value2, "userPhone");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNull() {
            addCriterion("user_email is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNotNull() {
            addCriterion("user_email is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailEqualTo(String value) {
            addCriterion("user_email =", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotEqualTo(String value) {
            addCriterion("user_email <>", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThan(String value) {
            addCriterion("user_email >", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThanOrEqualTo(String value) {
            addCriterion("user_email >=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThan(String value) {
            addCriterion("user_email <", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThanOrEqualTo(String value) {
            addCriterion("user_email <=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLike(String value) {
            addCriterion("user_email like", "%" + value + "%", "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotLike(String value) {
            addCriterion("user_email not like", "%" + value + "%", "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailIn(List<String> values) {
            addCriterion("user_email in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotIn(List<String> values) {
            addCriterion("user_email not in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailBetween(String value1, String value2) {
            addCriterion("user_email between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotBetween(String value1, String value2) {
            addCriterion("user_email not between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpIsNull() {
            addCriterion("dict_is_emp is null");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpIsNotNull() {
            addCriterion("dict_is_emp is not null");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpEqualTo(String value) {
            addCriterion("dict_is_emp =", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpNotEqualTo(String value) {
            addCriterion("dict_is_emp <>", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpGreaterThan(String value) {
            addCriterion("dict_is_emp >", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpGreaterThanOrEqualTo(String value) {
            addCriterion("dict_is_emp >=", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpLessThan(String value) {
            addCriterion("dict_is_emp <", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpLessThanOrEqualTo(String value) {
            addCriterion("dict_is_emp <=", value, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpLike(String value) {
            addCriterion("dict_is_emp like", "%" + value + "%", "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpNotLike(String value) {
            addCriterion("dict_is_emp not like", "%" + value + "%", "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpIn(List<String> values) {
            addCriterion("dict_is_emp in", values, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpNotIn(List<String> values) {
            addCriterion("dict_is_emp not in", values, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpBetween(String value1, String value2) {
            addCriterion("dict_is_emp between", value1, value2, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictIsEmpNotBetween(String value1, String value2) {
            addCriterion("dict_is_emp not between", value1, value2, "dictIsEmp");
            return (Criteria) this;
        }

        public Criteria andDictSexIsNull() {
            addCriterion("dict_sex is null");
            return (Criteria) this;
        }

        public Criteria andDictSexIsNotNull() {
            addCriterion("dict_sex is not null");
            return (Criteria) this;
        }

        public Criteria andDictSexEqualTo(String value) {
            addCriterion("dict_sex =", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexNotEqualTo(String value) {
            addCriterion("dict_sex <>", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexGreaterThan(String value) {
            addCriterion("dict_sex >", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexGreaterThanOrEqualTo(String value) {
            addCriterion("dict_sex >=", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexLessThan(String value) {
            addCriterion("dict_sex <", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexLessThanOrEqualTo(String value) {
            addCriterion("dict_sex <=", value, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexLike(String value) {
            addCriterion("dict_sex like", "%" + value + "%", "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexNotLike(String value) {
            addCriterion("dict_sex not like", "%" + value + "%", "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexIn(List<String> values) {
            addCriterion("dict_sex in", values, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexNotIn(List<String> values) {
            addCriterion("dict_sex not in", values, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexBetween(String value1, String value2) {
            addCriterion("dict_sex between", value1, value2, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictSexNotBetween(String value1, String value2) {
            addCriterion("dict_sex not between", value1, value2, "dictSex");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusIsNull() {
            addCriterion("dict_pos_status is null");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusIsNotNull() {
            addCriterion("dict_pos_status is not null");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusEqualTo(String value) {
            addCriterion("dict_pos_status =", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusNotEqualTo(String value) {
            addCriterion("dict_pos_status <>", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusGreaterThan(String value) {
            addCriterion("dict_pos_status >", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusGreaterThanOrEqualTo(String value) {
            addCriterion("dict_pos_status >=", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusLessThan(String value) {
            addCriterion("dict_pos_status <", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusLessThanOrEqualTo(String value) {
            addCriterion("dict_pos_status <=", value, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusLike(String value) {
            addCriterion("dict_pos_status like", "%" + value + "%", "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusNotLike(String value) {
            addCriterion("dict_pos_status not like", "%" + value + "%", "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusIn(List<String> values) {
            addCriterion("dict_pos_status in", values, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusNotIn(List<String> values) {
            addCriterion("dict_pos_status not in", values, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusBetween(String value1, String value2) {
            addCriterion("dict_pos_status between", value1, value2, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andDictPosStatusNotBetween(String value1, String value2) {
            addCriterion("dict_pos_status not between", value1, value2, "dictPosStatus");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNull() {
            addCriterion("site_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("site_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(Long value) {
            addCriterion("site_id =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(Long value) {
            addCriterion("site_id <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(Long value) {
            addCriterion("site_id >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(Long value) {
            addCriterion("site_id >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(Long value) {
            addCriterion("site_id <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(Long value) {
            addCriterion("site_id <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<Long> values) {
            addCriterion("site_id in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<Long> values) {
            addCriterion("site_id not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(Long value1, Long value2) {
            addCriterion("site_id between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(Long value1, Long value2) {
            addCriterion("site_id not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteCodeIsNull() {
            addCriterion("site_code is null");
            return (Criteria) this;
        }

        public Criteria andSiteCodeIsNotNull() {
            addCriterion("site_code is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCodeEqualTo(String value) {
            addCriterion("site_code =", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeNotEqualTo(String value) {
            addCriterion("site_code <>", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeGreaterThan(String value) {
            addCriterion("site_code >", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("site_code >=", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeLessThan(String value) {
            addCriterion("site_code <", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeLessThanOrEqualTo(String value) {
            addCriterion("site_code <=", value, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeLike(String value) {
            addCriterion("site_code like", "%" + value + "%", "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeNotLike(String value) {
            addCriterion("site_code not like", "%" + value + "%", "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeIn(List<String> values) {
            addCriterion("site_code in", values, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeNotIn(List<String> values) {
            addCriterion("site_code not in", values, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeBetween(String value1, String value2) {
            addCriterion("site_code between", value1, value2, "siteCode");
            return (Criteria) this;
        }

        public Criteria andSiteCodeNotBetween(String value1, String value2) {
            addCriterion("site_code not between", value1, value2, "siteCode");
            return (Criteria) this;
        }

        public Criteria andDictDeptIsNull() {
            addCriterion("dict_dept is null");
            return (Criteria) this;
        }

        public Criteria andDictDeptIsNotNull() {
            addCriterion("dict_dept is not null");
            return (Criteria) this;
        }

        public Criteria andDictDeptEqualTo(String value) {
            addCriterion("dict_dept =", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptNotEqualTo(String value) {
            addCriterion("dict_dept <>", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptGreaterThan(String value) {
            addCriterion("dict_dept >", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptGreaterThanOrEqualTo(String value) {
            addCriterion("dict_dept >=", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptLessThan(String value) {
            addCriterion("dict_dept <", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptLessThanOrEqualTo(String value) {
            addCriterion("dict_dept <=", value, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptLike(String value) {
            addCriterion("dict_dept like", "%" + value + "%", "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptNotLike(String value) {
            addCriterion("dict_dept not like", "%" + value + "%", "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptIn(List<String> values) {
            addCriterion("dict_dept in", values, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptNotIn(List<String> values) {
            addCriterion("dict_dept not in", values, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptBetween(String value1, String value2) {
            addCriterion("dict_dept between", value1, value2, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictDeptNotBetween(String value1, String value2) {
            addCriterion("dict_dept not between", value1, value2, "dictDept");
            return (Criteria) this;
        }

        public Criteria andDictPositionIsNull() {
            addCriterion("dict_position is null");
            return (Criteria) this;
        }

        public Criteria andDictPositionIsNotNull() {
            addCriterion("dict_position is not null");
            return (Criteria) this;
        }

        public Criteria andDictPositionEqualTo(String value) {
            addCriterion("dict_position =", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionNotEqualTo(String value) {
            addCriterion("dict_position <>", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionGreaterThan(String value) {
            addCriterion("dict_position >", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionGreaterThanOrEqualTo(String value) {
            addCriterion("dict_position >=", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionLessThan(String value) {
            addCriterion("dict_position <", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionLessThanOrEqualTo(String value) {
            addCriterion("dict_position <=", value, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionLike(String value) {
            addCriterion("dict_position like", "%" + value + "%", "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionNotLike(String value) {
            addCriterion("dict_position not like", "%" + value + "%", "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionIn(List<String> values) {
            addCriterion("dict_position in", values, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionNotIn(List<String> values) {
            addCriterion("dict_position not in", values, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionBetween(String value1, String value2) {
            addCriterion("dict_position between", value1, value2, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictPositionNotBetween(String value1, String value2) {
            addCriterion("dict_position not between", value1, value2, "dictPosition");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightIsNull() {
            addCriterion("dict_is_enable_right is null");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightIsNotNull() {
            addCriterion("dict_is_enable_right is not null");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightEqualTo(String value) {
            addCriterion("dict_is_enable_right =", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightNotEqualTo(String value) {
            addCriterion("dict_is_enable_right <>", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightGreaterThan(String value) {
            addCriterion("dict_is_enable_right >", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightGreaterThanOrEqualTo(String value) {
            addCriterion("dict_is_enable_right >=", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightLessThan(String value) {
            addCriterion("dict_is_enable_right <", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightLessThanOrEqualTo(String value) {
            addCriterion("dict_is_enable_right <=", value, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightLike(String value) {
            addCriterion("dict_is_enable_right like", "%" + value + "%", "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightNotLike(String value) {
            addCriterion("dict_is_enable_right not like", "%" + value + "%", "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightIn(List<String> values) {
            addCriterion("dict_is_enable_right in", values, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightNotIn(List<String> values) {
            addCriterion("dict_is_enable_right not in", values, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightBetween(String value1, String value2) {
            addCriterion("dict_is_enable_right between", value1, value2, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andDictIsEnableRightNotBetween(String value1, String value2) {
            addCriterion("dict_is_enable_right not between", value1, value2, "dictIsEnableRight");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNull() {
            addCriterion("create_operator is null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNotNull() {
            addCriterion("create_operator is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorEqualTo(String value) {
            addCriterion("create_operator =", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotEqualTo(String value) {
            addCriterion("create_operator <>", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThan(String value) {
            addCriterion("create_operator >", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("create_operator >=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThan(String value) {
            addCriterion("create_operator <", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThanOrEqualTo(String value) {
            addCriterion("create_operator <=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLike(String value) {
            addCriterion("create_operator like", "%" + value + "%", "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotLike(String value) {
            addCriterion("create_operator not like", "%" + value + "%", "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIn(List<String> values) {
            addCriterion("create_operator in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotIn(List<String> values) {
            addCriterion("create_operator not in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorBetween(String value1, String value2) {
            addCriterion("create_operator between", value1, value2, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotBetween(String value1, String value2) {
            addCriterion("create_operator not between", value1, value2, "createOperator");
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

        public Criteria andUpdateOperatorIsNull() {
            addCriterion("update_operator is null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorIsNotNull() {
            addCriterion("update_operator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorEqualTo(String value) {
            addCriterion("update_operator =", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotEqualTo(String value) {
            addCriterion("update_operator <>", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorGreaterThan(String value) {
            addCriterion("update_operator >", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("update_operator >=", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLessThan(String value) {
            addCriterion("update_operator <", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLessThanOrEqualTo(String value) {
            addCriterion("update_operator <=", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLike(String value) {
            addCriterion("update_operator like", "%" + value + "%", "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotLike(String value) {
            addCriterion("update_operator not like", "%" + value + "%", "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorIn(List<String> values) {
            addCriterion("update_operator in", values, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotIn(List<String> values) {
            addCriterion("update_operator not in", values, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorBetween(String value1, String value2) {
            addCriterion("update_operator between", value1, value2, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotBetween(String value1, String value2) {
            addCriterion("update_operator not between", value1, value2, "updateOperator");
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