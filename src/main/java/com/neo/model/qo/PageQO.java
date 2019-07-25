package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PageQO {
    protected Integer page;      //页码
    protected Integer rows;     //每页多少个
    protected Integer startRow = 0;  //通过 setLimit计算后得到的结果 开始
    protected Integer total;         //通过 setLimit计算后得到的结果 总工显示多少个

    public void setLimit() {
        if (page == null || page < 1) {
            this.page = 1;
        }
        if (rows == null || rows < 1) {
            this.rows = 10;
        }
        this.startRow = (page - 1) * rows;
    }
}
