package com.jokerdata.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.ShareLog;

import java.util.List;

/**
 * <p>
 *  转发服务接口
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
public interface CustomShareLogService extends IService<ShareLog> {
    List<Integer> getForwardReport();
}
