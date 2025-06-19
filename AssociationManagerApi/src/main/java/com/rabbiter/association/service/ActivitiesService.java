package com.rabbiter.association.service;

import com.rabbiter.association.entity.Members;
import com.rabbiter.association.msg.PageData;

import com.rabbiter.association.entity.Activities;

/**
 * 业务层处理
 * 活动信息
 */
public interface ActivitiesService extends BaseService<Activities, String> {

    /**
     * 分页查询活动信息信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param activeName 活动名称
     * @param teamName 团队名称
     * @return
     */
    public PageData getPageAll(Long pageIndex, Long pageSize, String activeName, String teamName);


    /**
     * 分页查询指定成员相关活动信息信息
     * @param pageIndex 当前页码
     * @param pageSize 每页数据量
     * @param userId 指定成员ID
     * @param activeName 活动名称
     * @param teamName 团队名称
     * @return
     */
    public PageData getPageByUserId(Long pageIndex, Long pageSize, String userId, String activeName, String teamName);

    /**
     * 业务层处理
     * 成员信息
     */
    interface MembersService extends BaseService<Members, String> {

        /**
         * 指定用户是否是管理员
         * @param userId 指定用户ID
         * @param teamId 团队ID
         * @return
         */
        public Boolean isManager(String teamId, String userId);

        /**
         * 分页查询成员信息信息
         * @param pageIndex 当前页码
         * @param pageSize 每页数据量
         * @param teamName 社团名称
         * @param userName 成员姓名
         * @return
         */
        public PageData getPageAll(Long pageIndex, Long pageSize, String teamName, String userName);

        /**
         * 分页查询成员信息信息
         * @param pageIndex 当前页码
         * @param pageSize 每页数据量
         * @param manId 管理员ID
         * @param teamName 社团名称
         * @param userName 成员姓名
         * @return
         */
        public PageData getPageByManId(Long pageIndex, Long pageSize, String manId, String teamName, String userName);
    }
}