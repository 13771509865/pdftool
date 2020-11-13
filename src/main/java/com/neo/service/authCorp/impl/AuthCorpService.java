package com.neo.service.authCorp.impl;

import com.neo.dao.PtsAuthCorpPOMapper;
import com.neo.model.po.PtsAuthCorpPO;
import com.neo.model.qo.PtsAuthCorpQO;
import com.neo.service.authCorp.IAuthCorpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/11/12 3:59 下午
 */
public class AuthCorpService implements IAuthCorpService {

    @Autowired
    private PtsAuthCorpPOMapper ptsAuthCorpPOMapper;





    @Override
    public Integer insertPtsAuthCorpPO(List<PtsAuthCorpPO> list) {
        return ptsAuthCorpPOMapper.insertPtsAuthCorpPO(list);
    }


    @Override
    public List<PtsAuthCorpPO> selectPtsAuthCorpPO(PtsAuthCorpQO ptsAuthCorpQO){
        return ptsAuthCorpPOMapper.selectPtsAuthCorpPO(ptsAuthCorpQO);
    }

    @Override
    public Integer deletePtsAuthCorp(Long cropId) {
        return ptsAuthCorpPOMapper.deletePtsAuthCorp(cropId);
    }





}
