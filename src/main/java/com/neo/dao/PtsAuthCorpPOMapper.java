/**
 * 
 */
package com.neo.dao;

import com.neo.model.po.PtsAuthCorpPO;
import com.neo.model.qo.PtsAuthCorpQO;

import java.util.List;

/**
 * @author xujun
 *
 */
public interface PtsAuthCorpPOMapper {

	Integer insertPtsAuthCorpPO(List<PtsAuthCorpPO> list);

	List<PtsAuthCorpPO> selectPtsAuthCorpPO(PtsAuthCorpQO ptsAuthCorpQO);

	Integer deletePtsAuthCorp(Long corpId);

	List<PtsAuthCorpPO> selectAuthCorp(PtsAuthCorpQO ptsAuthCorpQO);
	


}
