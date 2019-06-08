package com.jokerdata.vo;

import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import lombok.Data;


@Data
public class PShareLog extends PdLog {


    private ShareLog shareLog;
    private Share share;
}
