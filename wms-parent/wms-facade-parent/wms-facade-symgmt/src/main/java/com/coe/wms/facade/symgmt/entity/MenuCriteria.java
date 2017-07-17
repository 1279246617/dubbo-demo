package com.coe.wms.facade.symgmt.entity;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.plugin.model.QueryParam;

public class MenuCriteria extends QueryParam {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuCriteria() {
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

        public Criteria andMenuCodeIsNull() {
            addCriterion("menu_code is null");
            return (Criteria) this;
        }

        public Criteria andMenuCodeIsNotNull() {
            addCriterion("menu_code is not null");
            return (Criteria) this;
        }

        public Criteria andMenuCodeEqualTo(String value) {
            addCriterion("menu_code =", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeNotEqualTo(String value) {
            addCriterion("menu_code <>", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeGreaterThan(String value) {
            addCriterion("menu_code >", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeGreaterThanOrEqualTo(String value) {
            addCriterion("menu_code >=", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeLessThan(String value) {
            addCriterion("menu_code <", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeLessThanOrEqualTo(String value) {
            addCriterion("menu_code <=", value, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeLike(String value) {
            addCriterion("menu_code like", "%" + value + "%", "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeNotLike(String value) {
            addCriterion("menu_code not like", "%" + value + "%", "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeIn(List<String> values) {
            addCriterion("menu_code in", values, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeNotIn(List<String> values) {
            addCriterion("menu_code not in", values, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeBetween(String value1, String value2) {
            addCriterion("menu_code between", value1, value2, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuCodeNotBetween(String value1, String value2) {
            addCriterion("menu_code not between", value1, value2, "menuCode");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNull() {
            addCriterion("menu_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("menu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("menu_name =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("menu_name <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("menu_name >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_name >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("menu_name <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("menu_name <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("menu_name like", "%" + value + "%", "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("menu_name not like", "%" + value + "%", "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("menu_name in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("menu_name not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("menu_name between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("menu_name not between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andMenuEventIsNull() {
            addCriterion("menu_event is null");
            return (Criteria) this;
        }

        public Criteria andMenuEventIsNotNull() {
            addCriterion("menu_event is not null");
            return (Criteria) this;
        }

        public Criteria andMenuEventEqualTo(String value) {
            addCriterion("menu_event =", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventNotEqualTo(String value) {
            addCriterion("menu_event <>", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventGreaterThan(String value) {
            addCriterion("menu_event >", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventGreaterThanOrEqualTo(String value) {
            addCriterion("menu_event >=", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventLessThan(String value) {
            addCriterion("menu_event <", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventLessThanOrEqualTo(String value) {
            addCriterion("menu_event <=", value, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventLike(String value) {
            addCriterion("menu_event like", "%" + value + "%", "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventNotLike(String value) {
            addCriterion("menu_event not like", "%" + value + "%", "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventIn(List<String> values) {
            addCriterion("menu_event in", values, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventNotIn(List<String> values) {
            addCriterion("menu_event not in", values, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventBetween(String value1, String value2) {
            addCriterion("menu_event between", value1, value2, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuEventNotBetween(String value1, String value2) {
            addCriterion("menu_event not between", value1, value2, "menuEvent");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoIsNull() {
            addCriterion("menu_sort_no is null");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoIsNotNull() {
            addCriterion("menu_sort_no is not null");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoEqualTo(Integer value) {
            addCriterion("menu_sort_no =", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoNotEqualTo(Integer value) {
            addCriterion("menu_sort_no <>", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoGreaterThan(Integer value) {
            addCriterion("menu_sort_no >", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_sort_no >=", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoLessThan(Integer value) {
            addCriterion("menu_sort_no <", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoLessThanOrEqualTo(Integer value) {
            addCriterion("menu_sort_no <=", value, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoIn(List<Integer> values) {
            addCriterion("menu_sort_no in", values, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoNotIn(List<Integer> values) {
            addCriterion("menu_sort_no not in", values, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoBetween(Integer value1, Integer value2) {
            addCriterion("menu_sort_no between", value1, value2, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andMenuSortNoNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_sort_no not between", value1, value2, "menuSortNo");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusIsNull() {
            addCriterion("dict_menu_status is null");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusIsNotNull() {
            addCriterion("dict_menu_status is not null");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusEqualTo(String value) {
            addCriterion("dict_menu_status =", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusNotEqualTo(String value) {
            addCriterion("dict_menu_status <>", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusGreaterThan(String value) {
            addCriterion("dict_menu_status >", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusGreaterThanOrEqualTo(String value) {
            addCriterion("dict_menu_status >=", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusLessThan(String value) {
            addCriterion("dict_menu_status <", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusLessThanOrEqualTo(String value) {
            addCriterion("dict_menu_status <=", value, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusLike(String value) {
            addCriterion("dict_menu_status like", "%" + value + "%", "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusNotLike(String value) {
            addCriterion("dict_menu_status not like", "%" + value + "%", "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusIn(List<String> values) {
            addCriterion("dict_menu_status in", values, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusNotIn(List<String> values) {
            addCriterion("dict_menu_status not in", values, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusBetween(String value1, String value2) {
            addCriterion("dict_menu_status between", value1, value2, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andDictMenuStatusNotBetween(String value1, String value2) {
            addCriterion("dict_menu_status not between", value1, value2, "dictMenuStatus");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeIsNull() {
            addCriterion("menu_right_code is null");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeIsNotNull() {
            addCriterion("menu_right_code is not null");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeEqualTo(Long value) {
            addCriterion("menu_right_code =", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeNotEqualTo(Long value) {
            addCriterion("menu_right_code <>", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeGreaterThan(Long value) {
            addCriterion("menu_right_code >", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("menu_right_code >=", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeLessThan(Long value) {
            addCriterion("menu_right_code <", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeLessThanOrEqualTo(Long value) {
            addCriterion("menu_right_code <=", value, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeIn(List<Long> values) {
            addCriterion("menu_right_code in", values, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeNotIn(List<Long> values) {
            addCriterion("menu_right_code not in", values, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeBetween(Long value1, Long value2) {
            addCriterion("menu_right_code between", value1, value2, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightCodeNotBetween(Long value1, Long value2) {
            addCriterion("menu_right_code not between", value1, value2, "menuRightCode");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosIsNull() {
            addCriterion("menu_right_pos is null");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosIsNotNull() {
            addCriterion("menu_right_pos is not null");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosEqualTo(Integer value) {
            addCriterion("menu_right_pos =", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosNotEqualTo(Integer value) {
            addCriterion("menu_right_pos <>", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosGreaterThan(Integer value) {
            addCriterion("menu_right_pos >", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_right_pos >=", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosLessThan(Integer value) {
            addCriterion("menu_right_pos <", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosLessThanOrEqualTo(Integer value) {
            addCriterion("menu_right_pos <=", value, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosIn(List<Integer> values) {
            addCriterion("menu_right_pos in", values, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosNotIn(List<Integer> values) {
            addCriterion("menu_right_pos not in", values, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosBetween(Integer value1, Integer value2) {
            addCriterion("menu_right_pos between", value1, value2, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andMenuRightPosNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_right_pos not between", value1, value2, "menuRightPos");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonIsNull() {
            addCriterion("dict_is_common is null");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonIsNotNull() {
            addCriterion("dict_is_common is not null");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonEqualTo(String value) {
            addCriterion("dict_is_common =", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonNotEqualTo(String value) {
            addCriterion("dict_is_common <>", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonGreaterThan(String value) {
            addCriterion("dict_is_common >", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonGreaterThanOrEqualTo(String value) {
            addCriterion("dict_is_common >=", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonLessThan(String value) {
            addCriterion("dict_is_common <", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonLessThanOrEqualTo(String value) {
            addCriterion("dict_is_common <=", value, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonLike(String value) {
            addCriterion("dict_is_common like", "%" + value + "%", "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonNotLike(String value) {
            addCriterion("dict_is_common not like", "%" + value + "%", "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonIn(List<String> values) {
            addCriterion("dict_is_common in", values, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonNotIn(List<String> values) {
            addCriterion("dict_is_common not in", values, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonBetween(String value1, String value2) {
            addCriterion("dict_is_common between", value1, value2, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictIsCommonNotBetween(String value1, String value2) {
            addCriterion("dict_is_common not between", value1, value2, "dictIsCommon");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeIsNull() {
            addCriterion("dict_menu_type is null");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeIsNotNull() {
            addCriterion("dict_menu_type is not null");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeEqualTo(String value) {
            addCriterion("dict_menu_type =", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeNotEqualTo(String value) {
            addCriterion("dict_menu_type <>", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeGreaterThan(String value) {
            addCriterion("dict_menu_type >", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dict_menu_type >=", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeLessThan(String value) {
            addCriterion("dict_menu_type <", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeLessThanOrEqualTo(String value) {
            addCriterion("dict_menu_type <=", value, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeLike(String value) {
            addCriterion("dict_menu_type like", "%" + value + "%", "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeNotLike(String value) {
            addCriterion("dict_menu_type not like", "%" + value + "%", "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeIn(List<String> values) {
            addCriterion("dict_menu_type in", values, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeNotIn(List<String> values) {
            addCriterion("dict_menu_type not in", values, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeBetween(String value1, String value2) {
            addCriterion("dict_menu_type between", value1, value2, "dictMenuType");
            return (Criteria) this;
        }

        public Criteria andDictMenuTypeNotBetween(String value1, String value2) {
            addCriterion("dict_menu_type not between", value1, value2, "dictMenuType");
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

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
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

        public Criteria andUpdateTimeEqualTo(Long value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Long value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Long value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Long value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Long value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Long> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Long> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Long value1, Long value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Long value1, Long value2) {
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