package com.rank.admin.controller.main;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rank.manager.company.dto.RankPage;
import com.rank.manager.company.entity.CompanyEntity;
import com.rank.manager.company.mapper.CompanyMapper;
import com.rank.manager.company.service.CompanyService;
import com.rank.manager.company.service.impl.CompanyServiceImpl;
import com.redimybase.common.util.RedisUtils;
import com.redimybase.framework.bean.R;
import com.redimybase.framework.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

/**
 * 首页接口
 * Created by Vim 2019/2/15 18:07
 *
 * @author Vim
 */
@RestController
@RequestMapping("company")
public class CompanyController extends BaseController<String, CompanyEntity, CompanyMapper, CompanyServiceImpl> {

    /**
     * 996公司排名
     */
    @PostMapping("rankList")
    public R<?> rankList(@RequestBody RankPage<CompanyEntity> page) {
        return new R<>(service.rankList(page));
    }

    @Override
    public R<?> save(CompanyEntity entity) {
        if (StringUtils.isBlank(entity.getId())) {
            CompanyEntity companyEntity = service.getOne(new QueryWrapper<CompanyEntity>().lambda().eq(CompanyEntity::getCompanyName, entity.getCompanyName()).select(CompanyEntity::getId));

            if (companyEntity!=null) {
                service.fuck(companyEntity.getId());
                return R.ok();
            }
            entity.setCreateTime(new Date());
            entity.setPoint(0L);
            return new R<>(service.save(entity));
        }
        return R.ok();
    }


    @Override
    public R<?> deleteBatchIds(String ids) {
        return R.ok();
    }

    @Override
    public R<?> delete(String id) {
        return R.ok();
    }


    /**
     * 砍他
     */
    @PostMapping("fuck")
    public R<?> fuck(String id) {
        if (StringUtils.isBlank(id)) {
            return R.ok();
        }
        return service.fuck(id);
    }


    @Autowired
    private CompanyServiceImpl service;

    @Override
    protected CompanyServiceImpl getService() {
        return service;
    }
}
