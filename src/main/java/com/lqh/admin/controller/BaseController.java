package com.lqh.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqh.admin.dto.QueryPage;
import com.lqh.admin.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseController {

    protected static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    protected User getCurrentUser(){
        Object principal = getSubject().getPrincipal();
        return null;
    }

    protected Session getSession(){
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag){
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token){
        getSubject().login(token);
    }

    private Map<String,Object> getData(PageInfo<?> pageInfo){
        HashMap<String, Object> data = new HashMap<>();
        data.put("rows",pageInfo.getList());
        data.put("total",pageInfo.getTotal());
        return data;
    }

    public Map<String, Object> selectByPageNumSize(QueryPage page, Supplier<?> s) {
        PageHelper.startPage(page.getPageCode(), page.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>((List<?>)s.get());
        PageHelper.clearPage();
        return getData(pageInfo);
    }

    public Map<String, Object> getToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", getSession().getId());
        return map;
    }
}
