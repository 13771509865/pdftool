package com.neo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 总统竞选抽奖活动 Controller.
 * @author qixuan.chen
 * @version 1.0
 */
@Controller
@RequestMapping({"/activity/lotteryController"})
public class LotteryController {



    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     *
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public int getPrizeIndex(List<LotteryPrize> prizes) {
        DecimalFormat df = new DecimalFormat("######0.00");
        int random = -1;
        try {
            //计算总权重
            double sumWeight = 0;
            for (LotteryPrize p : prizes) {
                sumWeight += p.getPrizeWeight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for (int i = 0; i < prizes.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getPrizeWeight())) / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(prizes.get(i - 1).getPrizeWeight())) / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            random = -1;
        }
        return random;
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String args[]) {

        int i = 0;
        int[] result = new int[5];
        List<LotteryPrize> prizes = new ArrayList<LotteryPrize>();

        LotteryPrize p1 = new LotteryPrize();
        p1.setPrizename("iPhone7");
        p1.setPrizeWeight(1);//奖品的权重设置成1
        prizes.add(p1);

        LotteryPrize p2 = new LotteryPrize();
        p2.setPrizename("扫地机器人");
        p2.setPrizeWeight(2);//奖品的权重设置成2
        prizes.add(p2);

//        LotteryPrize p3 = new LotteryPrize();
//        p3.setPrizename("充电宝");
//        p3.setPrizeWeight(3);//奖品的权重设置成3
//        prizes.add(p3);
//
//        LotteryPrize p4 = new LotteryPrize();
//        p4.setPrizename("京东电子卡");
//        p4.setPrizeWeight(4);//奖品的权重设置成4
//        prizes.add(p4);
//
//        LotteryPrize p5 = new LotteryPrize();
//        p5.setPrizename("手气不佳");
//        p5.setPrizeWeight(10);//奖品的权重设置成4
//        prizes.add(p5);


        LotteryController a1 = new LotteryController();
        //Thread t1 = new Thread(a1);
        System.out.println("抽奖开始");
        for (i = 0; i < 100; i++){// 打印100个测试概率的准确性
            int selected = a1.getPrizeIndex(prizes);
            System.out.println("第" + i + "次抽中的奖品为：" + prizes.get(selected).getPrizename() + "==========" + selected);
            result[selected]++;
            System.out.println("--------------------------------");
        }


        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        System.out.println("一等奖：" + result[0]);
        System.out.println("二等奖：" + result[1]);
        System.out.println("三等奖：" + result[2]);
        System.out.println("四等奖：" + result[3]);
        System.out.println("无等奖：" + result[4]);

    }



}


