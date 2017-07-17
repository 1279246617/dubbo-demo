package com.coe.wms.facade.symgmt.entity;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class OperatorCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OperatorCriteria() {
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

        public Criteria andLoginNameIsNull() {
            addCriterion("login_name is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("login_name is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("login_name =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("login_name <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("login_name >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("login_name >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("login_name <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("login_name <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("login_name like", "%" + value + "%", "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("login_name not like", "%" + value + "%", "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("login_name in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("login_name not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("login_name between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("login_name not between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", "%" + value + "%", "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", "%" + value + "%", "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Long value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Long value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Long value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Long value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Long value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Long> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Long> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Long value1, Long value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Long value1, Long value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdIsNull() {
            addCriterion("created_by_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdIsNotNull() {
            addCriterion("created_by_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdEqualTo(Long value) {
            addCriterion("created_by_admin_id =", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdNotEqualTo(Long value) {
            addCriterion("created_by_admin_id <>", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdGreaterThan(Long value) {
            addCriterion("created_by_admin_id >", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by_admin_id >=", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdLessThan(Long value) {
            addCriterion("created_by_admin_id <", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("created_by_admin_id <=", value, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdIn(List<Long> values) {
            addCriterion("created_by_admin_id in", values, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdNotIn(List<Long> values) {
            addCriterion("created_by_admin_id not in", values, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdBetween(Long value1, Long value2) {
            addCriterion("created_by_admin_id between", value1, value2, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andCreatedByAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("created_by_admin_id not between", value1, value2, "createdByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIsNull() {
            addCriterion("last_updated_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIsNotNull() {
            addCriterion("last_updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeEqualTo(Long value) {
            addCriterion("last_updated_time =", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotEqualTo(Long value) {
            addCriterion("last_updated_time <>", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeGreaterThan(Long value) {
            addCriterion("last_updated_time >", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("last_updated_time >=", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeLessThan(Long value) {
            addCriterion("last_updated_time <", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeLessThanOrEqualTo(Long value) {
            addCriterion("last_updated_time <=", value, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeIn(List<Long> values) {
            addCriterion("last_updated_time in", values, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotIn(List<Long> values) {
            addCriterion("last_updated_time not in", values, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeBetween(Long value1, Long value2) {
            addCriterion("last_updated_time between", value1, value2, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTimeNotBetween(Long value1, Long value2) {
            addCriterion("last_updated_time not between", value1, value2, "lastUpdatedTime");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdIsNull() {
            addCriterion("lasts_updated_by_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdIsNotNull() {
            addCriterion("lasts_updated_by_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdEqualTo(Long value) {
            addCriterion("lasts_updated_by_admin_id =", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdNotEqualTo(Long value) {
            addCriterion("lasts_updated_by_admin_id <>", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdGreaterThan(Long value) {
            addCriterion("lasts_updated_by_admin_id >", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("lasts_updated_by_admin_id >=", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdLessThan(Long value) {
            addCriterion("lasts_updated_by_admin_id <", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("lasts_updated_by_admin_id <=", value, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdIn(List<Long> values) {
            addCriterion("lasts_updated_by_admin_id in", values, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdNotIn(List<Long> values) {
            addCriterion("lasts_updated_by_admin_id not in", values, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdBetween(Long value1, Long value2) {
            addCriterion("lasts_updated_by_admin_id between", value1, value2, "lastsUpdatedByAdminId");
            return (Criteria) this;
        }

        public Criteria andLastsUpdatedByAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("lasts_updated_by_admin_id not between", value1, value2, "lastsUpdatedByAdminId");
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