package com.jokerdata.service.app.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.utils.CommonUtil;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.mapper.app.custom.ShareCustomMapper;
import com.jokerdata.mapper.app.custom.ShareLogCustomMapper;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.service.app.ShareService;
import com.jokerdata.vo.CShareLog;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.PShareLog;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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
        page.getRecords().forEach(monetListVo -> {
            if(b){
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUserList(shareLogList);
            }

        });
        return parseData(page);
    }

    @Override
    public IPage<MonetListVo> tuiJianList(IPage<MonetListVo> recPage, boolean b) {

        IPage<MonetListVo> page = shareCustomMapper.tuiJianList(recPage);
        page.getRecords().forEach(monetListVo -> {
            if(b){
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUserList(shareLogList);
            }
        });
        return parseData(page);
    }

    @Override
    public IPage<MonetListVo> moneyMore(IPage<MonetListVo> sharePage, boolean b) {
        IPage<MonetListVo> page = shareCustomMapper.moneyMore(sharePage);
        page.getRecords().forEach(monetListVo -> {
            if(b){
                List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
                monetListVo.setUserList(shareLogList);
            }
        });
        return parseData(page);
    }

    @Override
    public IPage<MonetListVo> shareList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams) {
        IPage<MonetListVo> page = shareCustomMapper.shareList(sharePage, shareIndexParams);
        page.getRecords().forEach(monetListVo -> {
            List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
            monetListVo.setUserList(shareLogList);
            monetListVo.setAddTimeText(ShareUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent( ShareUtil.Base64Decode(monetListVo.getShareContent()));
            monetListVo.setUser_avatr(ShareUtil.getAvatar(monetListVo.getUserId()+""));
            monetListVo.setRatio(((double)monetListVo.getHaveSharedNum())/((double)monetListVo.getShareNum())*100+"");
        });
        return page;

    }

    @Override
    public IPage<MonetListVo> shareMoneyList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams) {
        IPage<MonetListVo> page = shareCustomMapper.shareMoneyList(sharePage, shareIndexParams);
        page.getRecords().forEach(monetListVo -> {
            List<UserListVo> shareLogList = shareLogCustomMapper.selectUserListVo(monetListVo.getShareId());
            monetListVo.setUserList(shareLogList);
            monetListVo.setAddTimeText(ShareUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setShareContent( ShareUtil.Base64Decode(monetListVo.getShareContent()));
            monetListVo.setUser_avatr(ShareUtil.getAvatar(monetListVo.getUserId()+""));
            monetListVo.setRatio(String.valueOf(Double.valueOf(monetListVo.getHaveSharedNum())/Double.valueOf(monetListVo.getShareNum())*100));
        });
        return page;
    }

    @Override
    public IPage<MonetListVo> userShareList(IPage<MonetListVo> shareListPage, Integer userId) {

        IPage<MonetListVo> page = shareCustomMapper.userShareList(shareListPage,userId);

        return parseData(page);
    }

    @Override
    public IPage<MonetListVo> parseData(IPage<MonetListVo> page) {
        page.getRecords().forEach(monetListVo -> {
            monetListVo.setAddTimeText(ShareUtil.getTaxt(monetListVo.getAddTime()));
            monetListVo.setUser_avatr(ShareUtil.getAvatar(monetListVo.getUserId()+""));
            monetListVo.setAvatar_hd(ShareUtil.getPic(monetListVo.getAvatar_hd()));
            if(StringUtils.isEmpty(monetListVo.getBackgroundImage())){
                if(!StringUtils.isEmpty(monetListVo.getShareImage())){
                    if("2".equals(monetListVo.getShareType())){
                        JSONArray img = JSON.parseArray(monetListVo.getShareImage());
                        monetListVo.setBackgroundImage(img.getJSONObject(0).getString("data-src")+"="+img.getJSONObject(0).getString("data-msgType"));
                    }else{
                        JSONArray img = JSON.parseArray(monetListVo.getShareImage());
                        monetListVo.setBackgroundImage(img.getJSONObject(0).getString("original_pic"));
                    }
                }else{
                    monetListVo.setBackgroundImage(ShareUtil.URL+"Upload/money_push.jpg");

                }
            }else{
                monetListVo.setBackgroundImage(ShareUtil.getPic(monetListVo.getBackgroundImage()));
            }
            if(CommonUtil.isBase64(monetListVo.getShareContent())){
                monetListVo.setShareContent(ShareUtil.Base64Decode(monetListVo.getShareContent()));
            }
            if(!CommonUtil.isEmpty(monetListVo.getShareVideo())){
                monetListVo.setShareVideoJson(JSON.parse(monetListVo.getShareVideo()));
            }
            if(!CommonUtil.isEmpty(monetListVo.getShareImg())){
                monetListVo.setShareImgJson(JSON.parseArray(monetListVo.getShareImg()));
            }
            if(!CommonUtil.isEmpty(monetListVo.getShareImage())){
                monetListVo.setShareImageJson(JSON.parseArray(monetListVo.getShareImage()));
            }
            monetListVo.setRatio(String.valueOf(monetListVo.getHaveSharedNum()/monetListVo.getShareNum()*100));
        });


        return page;
    }

    @Override
    public IPage<PShareLog> getPPage(MyPage page) {

        return shareCustomMapper.getSharePPage(page);
    }

    @Override
    public IPage<CShareLog> getCPage(MyPage page) {
        return shareCustomMapper.getShareCPage(page);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        java.lang.String str = "5b2T5bm056yU56yU5ZSx6L+Z6aaW44CKI+S8oOWlhy3lkajnrJTnlYVb6Z+z5LmQXSMg44CL5aW95YOP5piv56ys5LiA5qyh5Zyo5aSn5bqt5bm/5LyX56m/5ouW5Zyw6ZW/6KOZ5ZCn77yf5b2T5pe255qE6YKj5byg6Iie5Y+w54Wn5oOK6Imz5p6B5LqG77yM5Zyo5b6u5Y2a5LiK5Lmf5piv54Gr55qE5LiA6Laf57OK5raCW+aGp+aGrF1b5oan5oasXVvmhqfmhqxdI+WRqOeslOeVhSPmrYzlo7DkuK3pgI/pnLLlh7roh6rlt7HnmoTpo47moLzkuI7nibnoibLvvIznnJ/nmoTlvojmo5LlkaLvvIFb6IiU5bGPXVvoiJTlsY9dW+iIlOWxj10KI+aDheaEnyMgWW91J2xsIG5ldmVyIGZpbmQgdGhlIHJpZ2h0IHBlcnNvbiBpZiB5b3UgZG9uJ3QgbGV0IGdvIG9mIHRoZSAuLi7lhajmlofvvJogaHR0cDovL20ud2VpYm8uY24vMjQzNzQ3NTA0NC80MzY1NjcyMzAzMzEyNDI0IOKAiw==";
        System.out.println(
                ShareUtil.Base64Decode(str)
             );
        System.out.println(
                ShareUtil.Base64Encode(ShareUtil.Base64Decode(str))
        );

    }
}
