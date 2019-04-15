package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.PdRecharge;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 预存款充值表 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface PdRechargeCustomMapper extends BaseMapper<PdRecharge> {

    MyPage<PdRecharge> selectPage(@Param("param") MyPage page);
}
