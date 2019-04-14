package com.jokerdata.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MyPage<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;
    public long current = 1;
    public long size = 10;//默认查询长度
    public String search1 = null;
    public String search2 = null;


    public MyPage() {

    }

    public MyPage(long current, long size, String search1, String search2) {
        super(current, size);
        this.search1 = search1;
        this.search2 = search2;
    }

    public MyPage(long current, long size) {
        super(current, size);
    }
}
