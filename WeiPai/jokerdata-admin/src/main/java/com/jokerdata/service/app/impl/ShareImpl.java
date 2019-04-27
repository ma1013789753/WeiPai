package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.common.ClassUtil;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.mapper.app.custom.ShareCustomMapper;
import com.jokerdata.mapper.app.custom.ShareLogCustomMapper;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.service.app.ShareService;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
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

    @Override
    public IPage<MonetListVo> shareList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams) {
        IPage<MonetListVo> page = shareCustomMapper.shareList(sharePage, shareIndexParams);
        page.getRecords().forEach(monetListVo -> {
            List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
            monetListVo.setUser_list(shareLogList);
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent( new String(Base64Utils.decode(monetListVo.getShareContent().getBytes())));
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });
        return page;

    }

    @Override
    public IPage<MonetListVo> shareMoneyList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams) {
        IPage<MonetListVo> page = shareCustomMapper.shareMoneyList(sharePage, shareIndexParams);
        page.getRecords().forEach(monetListVo -> {
            List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
            monetListVo.setUser_list(shareLogList);
            monetListVo.setAdd_time_text(ClassUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent( new String(Base64Utils.decode(monetListVo.getShareContent().getBytes())));
            monetListVo.setUser_avatr(ClassUtil.getAvatar(monetListVo.getUserId()+""));
        });
        return page;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        java.lang.String str = "5b2T5bm056yU56yU5ZSx6L+Z6aaW44CKI+S8oOWlhy3lkajnrJTnlYVb6Z+z5LmQXSMg44CL5aW95YOP5piv56ys5LiA5qyh5Zyo5aSn5bqt5bm/5LyX56m/5ouW5Zyw6ZW/6KOZ5ZCn77yf5b2T5pe255qE6YKj5byg6Iie5Y+w54Wn5oOK6Imz5p6B5LqG77yM5Zyo5b6u5Y2a5LiK5Lmf5piv54Gr55qE5LiA6Laf57OK5raCW+aGp+aGrF1b5oan5oasXVvmhqfmhqxdI+WRqOeslOeVhSPmrYzlo7DkuK3pgI/pnLLlh7roh6rlt7HnmoTpo47moLzkuI7nibnoibLvvIznnJ/nmoTlvojmo5LlkaLvvIFb6IiU5bGPXVvoiJTlsY9dW+iIlOWxj10KI+aDheaEnyMgWW91J2xsIG5ldmVyIGZpbmQgdGhlIHJpZ2h0IHBlcnNvbiBpZiB5b3UgZG9uJ3QgbGV0IGdvIG9mIHRoZSAuLi7lhajmlofvvJogaHR0cDovL20ud2VpYm8uY24vMjQzNzQ3NTA0NC80MzY1NjcyMzAzMzEyNDI0IOKAiw==";
        System.out.println(

             );
    }
}
