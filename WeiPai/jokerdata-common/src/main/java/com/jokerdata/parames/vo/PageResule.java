package com.jokerdata.parames.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageResule<T> {
    public Integer code;
    public boolean hasmore;
    public Long page_total;
    public T datas;

    public static <T> PageResule success(T t){
        PageResule<T> pageResule = new PageResule<>();

        pageResule.setCode(200);
        pageResule.setDatas(t);
        return pageResule;
    }

    public PageResule setPage(Page<T> page){
        if(page.getCurrent()*page.getSize()< page.getTotal()){
            hasmore = true;
        }else{
            hasmore = false;
        }
        page_total = page.getTotal()/page.getSize();
        if(page.getTotal()%page.getSize() >0){
            page_total++;
        }

        return this;
    }
}
