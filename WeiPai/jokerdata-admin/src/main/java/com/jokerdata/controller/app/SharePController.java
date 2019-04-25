package com.jokerdata.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.entity.app.generator.ShareTag;
import com.jokerdata.entity.app.generator.UserToken;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.service.app.ShareTagService;
import com.jokerdata.service.app.UserTokenService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ShareP")
public class SharePController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    ShareTagService shareTagService;

    @Autowired
    ShareService shareService;

    @GetMapping(value = "/pub_share_index",produces = "application/json;charset=UTF-8")
    public ApiResult pub_share_index(ShareIndexParams shareIndexParams) {
        UserToken userToken = userTokenService.getUserByToken(shareIndexParams.getKey());
        List<ShareTag> data = shareTagService.list(new QueryWrapper<ShareTag>().orderByDesc("tag_sort"));
        IPage<MonetListVo> sharePage = new Page<>();
        sharePage.setCurrent(Integer.parseInt(shareIndexParams.curpage));
        sharePage.setSize(10);
        sharePage = shareService.shareList(sharePage,shareIndexParams);

        return new ApiResult();
    }

}
