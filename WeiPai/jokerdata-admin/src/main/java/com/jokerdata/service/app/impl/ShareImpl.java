package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.mapper.app.custom.ShareCustomMapper;
import com.jokerdata.mapper.app.custom.ShareLogCustomMapper;
import com.jokerdata.mapper.app.generator.ShareMapper;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class ShareImpl extends ServiceImpl<ShareCustomMapper, Share> implements ShareService {

    @Resource
    ShareCustomMapper shareCustomMapper;

    @Resource
    ShareLogCustomMapper shareLogCustomMapper;

    @Override
    public IPage<MonetListVo> moneyList(IPage<MonetListVo> sharePage, boolean b) {

        IPage<MonetListVo> page = shareCustomMapper.moneyList(sharePage);
        if(b){
            page.getRecords().forEach(monetListVo -> {
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUser_list(shareLogList);
            });

        }
        return page;
    }

    @Override
    public IPage<MonetListVo> tuiJianList(IPage<MonetListVo> recPage, boolean b) {

        IPage<MonetListVo> page = shareCustomMapper.tuiJianList(recPage);
        if(b){
            page.getRecords().forEach(monetListVo -> {
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUser_list(shareLogList);
            });

        }
        return page;
    }

    @Override
    public IPage<MonetListVo> moneyMore(IPage<MonetListVo> sharePage, boolean b) {
        IPage<MonetListVo> page = shareCustomMapper.moneyMore(sharePage);
        if(b){
            page.getRecords().forEach(monetListVo -> {
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUser_list(shareLogList);
            });

        }
        return page;
    }
}
