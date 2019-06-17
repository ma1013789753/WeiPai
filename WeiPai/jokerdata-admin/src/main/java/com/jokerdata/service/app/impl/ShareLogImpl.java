package com.jokerdata.service.app.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.utils.CommonUtil;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.mapper.app.custom.ShareLogCustomMapper;
import com.jokerdata.mapper.app.generator.ShareLogMapper;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.ShareLogService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class ShareLogImpl extends ServiceImpl<ShareLogMapper, ShareLog> implements ShareLogService {
    @Resource
    ShareLogMapper targetMapper;

    @Resource
    ShareLogCustomMapper shareLogCustomMapper;


    @Override
    public List<ShareIndexVo> getshareInfo() {
        return shareLogCustomMapper.getshareInfo();
    }

    @Override
    public List<ShareIndexVo> getshareInfoLimit() {
        return shareLogCustomMapper.getshareInfoLimit();
    }

    @Override
    public List<Map<String, Object>> getshareInfoUser(Integer shareId) {
        return shareLogCustomMapper.getshareInfoUser(shareId);
    }

    @Override
    public IPage<Map<String, Object>> shareUserList(IPage<ShareLog> iPage, String share_id) {
        return shareLogCustomMapper.shareUserList(iPage,share_id);
    }

    @Override
    public IPage<SpreadBeanVo> getSPreadList(IPage<SpreadBeanVo> shareListPage, Integer userId) {
        IPage<SpreadBeanVo> page = shareLogCustomMapper.getSPreadList( shareListPage, userId);
        page.getRecords().forEach(s->{
//            if(CommonUtil.isBase64(s.getShareContent())){
            s.setShareContent(ShareUtil.Base64Decode(s.getShareContent()));
//            }
            s.setAddTime(ShareUtil.getTaxt(s.getAddTime()));
            s.setContent((s.getContent()));
            s.setBackgroundImage(ShareUtil.getPic(s.getBackgroundImage()));
            s.setAvatarHd(ShareUtil.getPic(s.getAvatarHd()));
            s.setAvatarHd2(ShareUtil.getPic(s.getAvatarHd2()));
            if("2".equals(s.getShareType())){
                s.setAccountName(ShareUtil.Base64Decode(s.getAccountName()));
                s.setAccountName2(ShareUtil.Base64Decode(s.getAccountName2()));
            }

        });

        return page;
    }
}
