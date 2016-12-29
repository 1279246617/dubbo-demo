package com.coe.message.dao.mapper;

import com.coe.message.entity.MessageResponse;
import com.coe.message.entity.MessageResponseExample;
import com.coe.message.entity.MessageResponseWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageResponseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int countByExample(MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int deleteByExample(MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int insert(MessageResponseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int insertSelective(MessageResponseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    List<MessageResponseWithBLOBs> selectByExampleWithBLOBs(MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    List<MessageResponse> selectByExample(MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    MessageResponseWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByExampleSelective(@Param("record") MessageResponseWithBLOBs record, @Param("example") MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") MessageResponseWithBLOBs record, @Param("example") MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByExample(@Param("record") MessageResponse record, @Param("example") MessageResponseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByPrimaryKeySelective(MessageResponseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(MessageResponseWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_response
     *
     * @mbggenerated Thu Dec 29 13:58:39 CST 2016
     */
    int updateByPrimaryKey(MessageResponse record);
}