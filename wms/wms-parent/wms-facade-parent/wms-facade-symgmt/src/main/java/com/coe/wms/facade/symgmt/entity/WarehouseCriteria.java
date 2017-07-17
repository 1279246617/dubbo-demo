package com.coe.wms.facade.symgmt.entity;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class WarehouseCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WarehouseCriteria() {
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

        public Criteria andWhseNameIsNull() {
            addCriterion("whse_name is null");
            return (Criteria) this;
        }

        public Criteria andWhseNameIsNotNull() {
            addCriterion("whse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWhseNameEqualTo(String value) {
            addCriterion("whse_name =", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameNotEqualTo(String value) {
            addCriterion("whse_name <>", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameGreaterThan(String value) {
            addCriterion("whse_name >", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameGreaterThanOrEqualTo(String value) {
            addCriterion("whse_name >=", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameLessThan(String value) {
            addCriterion("whse_name <", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameLessThanOrEqualTo(String value) {
            addCriterion("whse_name <=", value, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameLike(String value) {
            addCriterion("whse_name like", "%" + value + "%", "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameNotLike(String value) {
            addCriterion("whse_name not like", "%" + value + "%", "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameIn(List<String> values) {
            addCriterion("whse_name in", values, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameNotIn(List<String> values) {
            addCriterion("whse_name not in", values, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameBetween(String value1, String value2) {
            addCriterion("whse_name between", value1, value2, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseNameNotBetween(String value1, String value2) {
            addCriterion("whse_name not between", value1, value2, "whseName");
            return (Criteria) this;
        }

        public Criteria andWhseCodeIsNull() {
            addCriterion("whse_code is null");
            return (Criteria) this;
        }

        public Criteria andWhseCodeIsNotNull() {
            addCriterion("whse_code is not null");
            return (Criteria) this;
        }

        public Criteria andWhseCodeEqualTo(String value) {
            addCriterion("whse_code =", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeNotEqualTo(String value) {
            addCriterion("whse_code <>", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeGreaterThan(String value) {
            addCriterion("whse_code >", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("whse_code >=", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeLessThan(String value) {
            addCriterion("whse_code <", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeLessThanOrEqualTo(String value) {
            addCriterion("whse_code <=", value, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeLike(String value) {
            addCriterion("whse_code like", "%" + value + "%", "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeNotLike(String value) {
            addCriterion("whse_code not like", "%" + value + "%", "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeIn(List<String> values) {
            addCriterion("whse_code in", values, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeNotIn(List<String> values) {
            addCriterion("whse_code not in", values, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeBetween(String value1, String value2) {
            addCriterion("whse_code between", value1, value2, "whseCode");
            return (Criteria) this;
        }

        public Criteria andWhseCodeNotBetween(String value1, String value2) {
            addCriterion("whse_code not between", value1, value2, "whseCode");
            return (Criteria) this;
        }

        public Criteria andPCodeIsNull() {
            addCriterion("p_code is null");
            return (Criteria) this;
        }

        public Criteria andPCodeIsNotNull() {
            addCriterion("p_code is not null");
            return (Criteria) this;
        }

        public Criteria andPCodeEqualTo(String value) {
            addCriterion("p_code =", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotEqualTo(String value) {
            addCriterion("p_code <>", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeGreaterThan(String value) {
            addCriterion("p_code >", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeGreaterThanOrEqualTo(String value) {
            addCriterion("p_code >=", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLessThan(String value) {
            addCriterion("p_code <", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLessThanOrEqualTo(String value) {
            addCriterion("p_code <=", value, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeLike(String value) {
            addCriterion("p_code like", "%" + value + "%", "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotLike(String value) {
            addCriterion("p_code not like", "%" + value + "%", "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeIn(List<String> values) {
            addCriterion("p_code in", values, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotIn(List<String> values) {
            addCriterion("p_code not in", values, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeBetween(String value1, String value2) {
            addCriterion("p_code between", value1, value2, "pCode");
            return (Criteria) this;
        }

        public Criteria andPCodeNotBetween(String value1, String value2) {
            addCriterion("p_code not between", value1, value2, "pCode");
            return (Criteria) this;
        }

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(Long value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(Long value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(Long value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(Long value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(Long value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(Long value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<Long> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<Long> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(Long value1, Long value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(Long value1, Long value2) {
            addCriterion("p_id not between", value1, value2, "pId");
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