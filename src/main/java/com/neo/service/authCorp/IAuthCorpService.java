package com.neo.service.authCorp;

import com.neo.model.po.PtsAuthCorpPO;
import com.neo.model.qo.PtsAuthCorpQO;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/11/12 3:58 下午
 */
public interface IAuthCorpService {

    Integer insertPtsAuthCorpPO(List<PtsAuthCorpPO> list);

    List<PtsAuthCorpPO> selectPtsAuthCorpPO(PtsAuthCorpQO ptsAuthCorpQO);

    Integer deletePtsAuthCorp(Long cropId);

    List<PtsAuthCorpPO> selectAuthCorp(PtsAuthCorpQO ptsAuthCorpQO);
}
