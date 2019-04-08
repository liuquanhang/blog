package com.lqh.admin.service.impl;

import com.lqh.admin.entity.Links;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.mapper.LinksMapper;
import com.lqh.admin.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *@Author: null
 *@Date: 12:32 2019/3/29
 *
 */
@Service
public class LinksServiceImpl implements LinksService {

    @Autowired
    private LinksMapper linksMapper;


    @Override
    public Long findAllCount() {
        return linksMapper.findAllCount();
    }

    @Override
    public List<Links> findAll() {
        return linksMapper.findAll();
    }

    @Override
    public List<Links> findByPage(Links links) {
        return linksMapper.findByPage(links);
    }

    @Override
    public Links findById(Long id) {
        if ( id != 0) {
            return linksMapper.findById(id);
        } else {
            throw new GlobalException("参数错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Links links) {
        try {
            linksMapper.save(links);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Links links) {
        if (links.getId() != 0) {
            try {
                linksMapper.update(links);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long... ids) {
        if (ids.length > 0) {
            try {
                for (long id : ids) {
                    linksMapper.delete(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }
}
