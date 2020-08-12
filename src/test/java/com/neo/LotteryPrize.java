package com.neo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xujun
 * @Date: 2020/8/6 10:06 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotteryPrize {

     private String  prizename;
    private Integer  prizeWeight;

    private Long time;
    private Integer lim;
}
