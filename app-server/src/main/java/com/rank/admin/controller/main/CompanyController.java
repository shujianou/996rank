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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        IPage<CompanyEntity> dataPage = service.page(page, new QueryWrapper<CompanyEntity>().lambda().orderByDesc(CompanyEntity::getPoint));
        for (CompanyEntity record : dataPage.getRecords()) {
            record.setPoint(record.getPoint());
        }
        return new R<>(dataPage);
    }

    @Override
    public R<?> save(CompanyEntity entity) {
        if (StringUtils.isBlank(entity.getId())) {
            if (service.count(new QueryWrapper<CompanyEntity>().lambda().eq(CompanyEntity::getCompanyName, entity.getCompanyName())) > 0) {
                return R.fail("小子你居心不良呀~~重新输个名字再试试");
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
    @Transactional(rollbackFor = Exception.class)
    public R<?> fuck(String id) {
        Set set = redisUtils.zsetRange(id, 0, -1);
        if (set != null && set.size() > 0) {
            //点赞一次
            redisUtils.zsetIncrementScore(id, "point", 1);
        } else {
            redisUtils.zset(id, "point", 1);
        }
        return R.ok();
    }


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CompanyServiceImpl service;

    @Override
    protected CompanyServiceImpl getService() {
        return service;
    }
}
