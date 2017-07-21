package com.coe.wms.facade.symgmt.entity;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class ConfigCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConfigCriteria() {
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

        public Criteria andSKeyIsNull() {
            addCriterion("s_key is null");
            return (Criteria) this;
        }

        public Criteria andSKeyIsNotNull() {
            addCriterion("s_key is not null");
            return (Criteria) this;
        }

        public Criteria andSKeyEqualTo(String value) {
            addCriterion("s_key =", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyNotEqualTo(String value) {
            addCriterion("s_key <>", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyGreaterThan(String value) {
            addCriterion("s_key >", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyGreaterThanOrEqualTo(String value) {
            addCriterion("s_key >=", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyLessThan(String value) {
            addCriterion("s_key <", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyLessThanOrEqualTo(String value) {
            addCriterion("s_key <=", value, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyLike(String value) {
            addCriterion("s_key like", "%" + value + "%", "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyNotLike(String value) {
            addCriterion("s_key not like", "%" + value + "%", "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyIn(List<String> values) {
            addCriterion("s_key in", values, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyNotIn(List<String> values) {
            addCriterion("s_key not in", values, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyBetween(String value1, String value2) {
            addCriterion("s_key between", value1, value2, "sKey");
            return (Criteria) this;
        }

        public Criteria andSKeyNotBetween(String value1, String value2) {
            addCriterion("s_key not between", value1, value2, "sKey");
            return (Criteria) this;
        }

        public Criteria andSValueIsNull() {
            addCriterion("s_value is null");
            return (Criteria) this;
        }

        public Criteria andSValueIsNotNull() {
            addCriterion("s_value is not null");
            return (Criteria) this;
        }

        public Criteria andSValueEqualTo(String value) {
            addCriterion("s_value =", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueNotEqualTo(String value) {
            addCriterion("s_value <>", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueGreaterThan(String value) {
            addCriterion("s_value >", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueGreaterThanOrEqualTo(String value) {
            addCriterion("s_value >=", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueLessThan(String value) {
            addCriterion("s_value <", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueLessThanOrEqualTo(String value) {
            addCriterion("s_value <=", value, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueLike(String value) {
            addCriterion("s_value like", "%" + value + "%", "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueNotLike(String value) {
            addCriterion("s_value not like", "%" + value + "%", "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueIn(List<String> values) {
            addCriterion("s_value in", values, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueNotIn(List<String> values) {
            addCriterion("s_value not in", values, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueBetween(String value1, String value2) {
            addCriterion("s_value between", value1, value2, "sValue");
            return (Criteria) this;
        }

        public Criteria andSValueNotBetween(String value1, String value2) {
            addCriterion("s_value not between", value1, value2, "sValue");
            return (Criteria) this;
        }

        public Criteria andIdentificationIsNull() {
            addCriterion("identification is null");
            return (Criteria) this;
        }

        public Criteria andIdentificationIsNotNull() {
            addCriterion("identification is not null");
            return (Criteria) this;
        }

        public Criteria andIdentificationEqualTo(String value) {
            addCriterion("identification =", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationNotEqualTo(String value) {
            addCriterion("identification <>", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationGreaterThan(String value) {
            addCriterion("identification >", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationGreaterThanOrEqualTo(String value) {
            addCriterion("identification >=", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationLessThan(String value) {
            addCriterion("identification <", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationLessThanOrEqualTo(String value) {
            addCriterion("identification <=", value, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationLike(String value) {
            addCriterion("identification like", "%" + value + "%", "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationNotLike(String value) {
            addCriterion("identification not like", "%" + value + "%", "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationIn(List<String> values) {
            addCriterion("identification in", values, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationNotIn(List<String> values) {
            addCriterion("identification not in", values, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationBetween(String value1, String value2) {
            addCriterion("identification between", value1, value2, "identification");
            return (Criteria) this;
        }

        public Criteria andIdentificationNotBetween(String value1, String value2) {
            addCriterion("identification not between", value1, value2, "identification");
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