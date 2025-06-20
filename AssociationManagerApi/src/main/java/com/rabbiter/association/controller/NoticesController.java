package com.rabbiter.association.controller;

import com.rabbiter.association.entity.Notices;
import com.rabbiter.association.entity.Users;
import com.rabbiter.association.handle.CacheHandle;
import com.rabbiter.association.msg.PageData;
import com.rabbiter.association.msg.R;
import com.rabbiter.association.service.NoticesService;
import com.rabbiter.association.service.UsersService;
import com.rabbiter.association.utils.DateUtils;
import com.rabbiter.association.utils.IDUtils;
import com.rabbiter.association.utils.StringUtils;
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
 * 通知记录
 */
@Controller
@RequestMapping("/notices")
public class NoticesController extends BaseController {

    protected static final Logger Log = LoggerFactory.getLogger(NoticesController.class);

    @Autowired
    private CacheHandle cacheHandle;

    @Autowired
    private NoticesService noticesService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("")
    public String index() {

        return "pages/Notices";
    }

    @GetMapping("/info")
    @ResponseBody
    public R getInfo(String id) {

        Log.info("查找指定通知记录，ID：{}", id);

        Notices notices = noticesService.getOne(id);

        return R.successData(notices);
    }

    @GetMapping("/page")
    @ResponseBody
    public R getPageInfos(Long pageIndex, Long pageSize,
                          String token, String title, String teamName) {

        Users user = usersService.getOne(cacheHandle.getUserInfoCache(token));
        if(ObjectUtils.isEmpty(user)) {
            return R.error("登录信息不存在，请重新登录");
        }
        if (user.getType() == 0) {

            Log.info("分页查找指通知记录，当前页码：{}，"
                            + "每页数据量：{}, 模糊查询，通知标题：{}，社团名称：{}", pageIndex,
                    pageSize, title, teamName);

            PageData page = noticesService.getPageAll(pageIndex, pageSize, title, teamName);

            return R.successData(page);
        } else {

            Log.info("分页查找指定用户相关通知记录，当前页码：{}，"
                            + "每页数据量：{}, 模糊查询，通知标题：{}，社团名称：{}", pageIndex,
                    pageSize, title, teamName);

            PageData page = noticesService.getPageById(pageIndex, pageSize, user.getId(), title, teamName);

            return R.successData(page);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public R addInfo(Notices notices) {

        notices.setId(IDUtils.makeIDByCurrent());
        notices.setCreateTime(DateUtils.getNowDate("yyyy-MM-dd"));

        if(StringUtils.isNullOrEmpty(notices.getTeamId())){

            notices.setTeamId(null);
        }

        Log.info("添加通知记录，传入参数：{}", notices);

        noticesService.add(notices);

        return R.success();
    }

    @PostMapping("/upd")
    @ResponseBody
    public R updInfo(Notices notices) {

        Log.info("修改通知记录，传入参数：{}", notices);

        noticesService.update(notices);

        return R.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public R delInfo(String id) {

        Log.info("删除通知记录, ID:{}", id);

        Notices notices = noticesService.getOne(id);

        noticesService.delete(notices);

        return R.success();
    }
}