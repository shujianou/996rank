package com.rank.manager.company.service;

import com.rank.manager.company.dto.RankPage;
import com.rank.manager.company.entity.CompanyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redimybase.framework.bean.R;

/**
 * <p>
 * 公司表 服务类
 * </p>
 *
 * @author vim
 * @since 2019-03-28
 */
public interface CompanyService extends IService<CompanyEntity> {

    /**
     * 砍他
     */
    public R<?> fuck(String id);

    /**
     * 996排名
     */
    public R<?> rankList(RankPage<CompanyEntity> page);

}
