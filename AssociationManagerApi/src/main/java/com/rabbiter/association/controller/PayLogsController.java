package com.rabbiter.association.controller;

import com.rabbiter.association.entity.PayLogs;
import com.rabbiter.association.entity.Users;
import com.rabbiter.association.handle.CacheHandle;
import com.rabbiter.association.msg.PageData;
import com.rabbiter.association.msg.R;
import com.rabbiter.association.service.PayLogsService;
import com.rabbiter.association.service.UsersService;
import com.rabbiter.association.utils.DateUtils;
import com.rabbiter.association.utils.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统请求响应控制器
 * 缴费记录
 */
@Controller
@RequestMapping("/payLogs")
public class PayLogsController extends BaseController {

    protected static final Logger Log = LoggerFactory.getLogger(PayLogsController.class);

    @Autowired
    private CacheHandle cacheHandle;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PayLogsService payLogsService;


    @RequestMapping("")
    public String index() {

        return "pages/PayLogs";
    }

    @GetMapping("/info")
    @ResponseBody
    public R getInfo(String id) {

        Log.info("查找指定缴费记录，ID：{}", id);

        PayLogs payLogs = payLogsService.getOne(id);

        return R.successData(payLogs);
    }

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize,
                          String token, String teamName, String userName) {

        Users user = usersService.getOne(cacheHandle.getUserInfoCache(token));
        if(ObjectUtils.isEmpty(user)) {
            return R.error("登录信息不存在，请重新登录");
        }
        if (user.getType() == 0) {

            Log.info("分页查看全部缴费记录，当前页码：{}，"
                            + "每页数据量：{}, 模糊查询，团队名称：{}，用户姓名：{}", pageIndex,
                    pageSize, teamName, userName);

            PageData page = payLogsService.getPageInfo(pageIndex, pageSize, null, teamName, userName);

            return R.successData(page);
        } else if (user.getType() == 1) {

            Log.info("团队管理员查看缴费记录，当前页码：{}，"
                            + "每页数据量：{}, 模糊查询，团队名称：{}，用户姓名：{}", pageIndex,
                    pageSize, teamName, userName);

            PageData page = payLogsService.getManPageInfo(pageIndex, pageSize, user.getId(), teamName, userName);

            return R.successData(page);
        } else {

            Log.info("分页用户相关缴费记录，当前页码：{}，"
                            + "每页数据量：{}, 模糊查询，团队名称：{}，用户姓名：{}", pageIndex,
                    pageSize, teamName, userName);

            PageData page = payLogsService.getPageInfo(pageIndex, pageSize, user.getId(), teamName, null);

            return R.successData(page);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo( PayLogs payLogs) {

        payLogs.setId(IDUtils.makeIDByCurrent());
        payLogs.setCreateTime(DateUtils.getNowDate());

        Log.info("添加缴费记录，传入参数：{}", payLogs);

        payLogsService.add(payLogs);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(PayLogs payLogs) {

        Log.info("修改缴费记录，传入参数：{}", payLogs);

        payLogsService.update(payLogs);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(String id) {

        Log.info("删除缴费记录, ID:{}", id);

        PayLogs payLogs = payLogsService.getOne(id);

        payLogsService.delete(payLogs);

        return R.success();
    }
}