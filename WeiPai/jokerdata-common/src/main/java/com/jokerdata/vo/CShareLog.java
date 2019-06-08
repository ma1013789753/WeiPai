package com.jokerdata.vo;

import com.jokerdata.entity.app.generator.CoinLog;
import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import lombok.Data;


@Data
public class CShareLog extends CoinLog {


    private ShareLog shareLog;
    private Share share;
}
