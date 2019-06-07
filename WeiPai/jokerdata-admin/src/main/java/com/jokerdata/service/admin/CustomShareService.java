package com.jokerdata.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.vo.MyPage;

import java.util.List;


/**
 * <p>
 * [互推管理]  服务接口
 * </p>
 *
 * @author aozhang
 * @since 2019-5-28
 */
public interface CustomShareService extends IService<Share> {

    MyPage<Share> getSharePage(MyPage page, int typeId);
    Share getShareById(int shareId);
    List<Integer> getShareReport();
}
