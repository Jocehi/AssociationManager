package com.rabbiter.association.service;


import com.rabbiter.association.entity.ActiveLogs;
import com.rabbiter.association.entity.ApplyLogs;
import com.rabbiter.association.msg.PageData;

import java.util.List;
import java.util.Map;

/**
 * 业务层处理
 * 报名记录
 */
public interface ActiveLogsService extends BaseService<ActiveLogs, String> {

    /**
     * 用户是否参与活动
     * @param activeId 指定活动ID
     * @param userId 指定用户ID
     * @return
     */
    public Boolean isActive(String activeId, String userId);

    /**
     * 依据活动ID获取报名列表
     * @param activeId 活动ID
     * @return
     */
    public List<Map<String, Object>> getListByActiveId(String activeId);

    /**
     * 业务层处理
     * 申请记录
     */
    interface ApplyLogsService extends BaseService<ApplyLogs, String> {

        /**
         * 检查用户是否可以提交申请
         * @param userId 用户ID
         * @param teamId 团队ID
         * @return
         */
        public Boolean isApply(String userId, String teamId);

        /**
         * 团队管理员分页查询申请记录信息
         * @param pageIndex 当前页码
         * @param pageSize 每页数据量
         * @param userId 用户编号
         * @param teamName 团队名称
         * @param userName 用户姓名
         * @return
         */
        public PageData getManPageInfo(Long pageIndex, Long pageSize, String userId, String teamName, String userName);

        /**
         * 分页查询申请记录信息
         * @param pageIndex 当前页码
         * @param pageSize 每页数据量
         * @param userId 用户编号
         * @param teamName 团队名称
         * @param userName 用户姓名
         * @return
         */
        public PageData getPageInfo(Long pageIndex, Long pageSize, String userId, String teamName, String userName);
    }
}