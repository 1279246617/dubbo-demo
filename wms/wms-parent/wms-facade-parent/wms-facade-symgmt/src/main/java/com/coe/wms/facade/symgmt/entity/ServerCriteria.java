package com.coe.wms.facade.symgmt.entity;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class ServerCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ServerCriteria() {
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

        public Criteria andServerCodeIsNull() {
            addCriterion("server_code is null");
            return (Criteria) this;
        }

        public Criteria andServerCodeIsNotNull() {
            addCriterion("server_code is not null");
            return (Criteria) this;
        }

        public Criteria andServerCodeEqualTo(String value) {
            addCriterion("server_code =", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeNotEqualTo(String value) {
            addCriterion("server_code <>", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeGreaterThan(String value) {
            addCriterion("server_code >", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("server_code >=", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeLessThan(String value) {
            addCriterion("server_code <", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeLessThanOrEqualTo(String value) {
            addCriterion("server_code <=", value, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeLike(String value) {
            addCriterion("server_code like", "%" + value + "%", "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeNotLike(String value) {
            addCriterion("server_code not like", "%" + value + "%", "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeIn(List<String> values) {
            addCriterion("server_code in", values, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeNotIn(List<String> values) {
            addCriterion("server_code not in", values, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeBetween(String value1, String value2) {
            addCriterion("server_code between", value1, value2, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerCodeNotBetween(String value1, String value2) {
            addCriterion("server_code not between", value1, value2, "serverCode");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNull() {
            addCriterion("server_name is null");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNotNull() {
            addCriterion("server_name is not null");
            return (Criteria) this;
        }

        public Criteria andServerNameEqualTo(String value) {
            addCriterion("server_name =", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotEqualTo(String value) {
            addCriterion("server_name <>", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThan(String value) {
            addCriterion("server_name >", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("server_name >=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThan(String value) {
            addCriterion("server_name <", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThanOrEqualTo(String value) {
            addCriterion("server_name <=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLike(String value) {
            addCriterion("server_name like", "%" + value + "%", "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotLike(String value) {
            addCriterion("server_name not like", "%" + value + "%", "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameIn(List<String> values) {
            addCriterion("server_name in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotIn(List<String> values) {
            addCriterion("server_name not in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameBetween(String value1, String value2) {
            addCriterion("server_name between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotBetween(String value1, String value2) {
            addCriterion("server_name not between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlIsNull() {
            addCriterion("server_index_url is null");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlIsNotNull() {
            addCriterion("server_index_url is not null");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlEqualTo(String value) {
            addCriterion("server_index_url =", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlNotEqualTo(String value) {
            addCriterion("server_index_url <>", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlGreaterThan(String value) {
            addCriterion("server_index_url >", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlGreaterThanOrEqualTo(String value) {
            addCriterion("server_index_url >=", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlLessThan(String value) {
            addCriterion("server_index_url <", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlLessThanOrEqualTo(String value) {
            addCriterion("server_index_url <=", value, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlLike(String value) {
            addCriterion("server_index_url like", "%" + value + "%", "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlNotLike(String value) {
            addCriterion("server_index_url not like", "%" + value + "%", "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlIn(List<String> values) {
            addCriterion("server_index_url in", values, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlNotIn(List<String> values) {
            addCriterion("server_index_url not in", values, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlBetween(String value1, String value2) {
            addCriterion("server_index_url between", value1, value2, "serverIndexUrl");
            return (Criteria) this;
        }

        public Criteria andServerIndexUrlNotBetween(String value1, String value2) {
            addCriterion("server_index_url not between", value1, value2, "serverIndexUrl");
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