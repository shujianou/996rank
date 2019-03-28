package com.rank.manager.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rank.manager.company.dto.RankPage;
import com.rank.manager.company.entity.CompanyEntity;
import com.rank.manager.company.mapper.CompanyMapper;
import com.rank.manager.company.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redimybase.common.util.RedisUtils;
import com.redimybase.framework.bean.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author vim
 * @since 2019-03-28
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, CompanyEntity> implements CompanyService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<?> fuck(String id) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object point = valueOperations.get(COMPANY_POINT_KEY + id);

        if (point != null) {
            //点赞一次
            valueOperations.increment(COMPANY_POINT_KEY + id, 1);
        } else {
            valueOperations.set(COMPANY_POINT_KEY + id, 1);
        }
        return R.ok();
    }

    @Override
    public R<?> rankList(RankPage<CompanyEntity> page) {
        IPage<CompanyEntity> dataPage = page(page, new QueryWrapper<CompanyEntity>().lambda().orderByDesc(CompanyEntity::getPoint));
        for (CompanyEntity record : dataPage.getRecords()) {
            Object point = redisTemplate.opsForValue().get(COMPANY_POINT_KEY + record.getId());
            if (point != null) {
                record.setPoint(record.getPoint() + Long.valueOf((String) point));
            }
        }
        return new R<>(dataPage);
    }


    private static final String COMPANY_POINT_KEY = "company:point:";
    @Autowired
    private RedisTemplate redisTemplate;

}
