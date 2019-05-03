package com.jokerdata.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.ShareLogService;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.service.app.SmsService;
import com.jokerdata.service.app.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Pred")
public class PredController {


    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareLogService shareLogService;


    @GetMapping(value = "/share_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule share_list(String key, @RequestParam(required = false) int curpage){
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<MonetListVo> shareListPage = new Page<>();
        shareListPage.setCurrent(curpage);
        shareListPage.setSize(10);
        shareListPage = shareService.userShareList(shareListPage,user.getUserId());

        Map<String,Object> data = new HashMap<>();
        data.put("list", ShareUtil.toLowBeanList(shareListPage.getRecords()));
        data.put("curpage",shareListPage.getCurrent());
        return PageResule.success(data).setPage((Page) shareListPage);

    }

    @GetMapping(value = "/my_spread",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule my_spread(String key, @RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<SpreadBeanVo> shareListPage = new Page<>();
        shareListPage.setCurrent(curpage);
        shareListPage.setSize(10);
        shareListPage = shareLogService.getSPreadList(shareListPage,user.getUserId());
        Map<String,Object> data = new HashMap<>();
        data.put("list", ShareUtil.toLowBeanList(shareListPage.getRecords()));
        data.put("curpage",shareListPage.getCurrent());
        return PageResule.success(data).setPage((Page) shareListPage);
    }


}
