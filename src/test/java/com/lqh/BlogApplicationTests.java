package com.lqh;

import com.lqh.admin.entity.ArticleCategory;
import com.lqh.admin.entity.Comments;
import com.lqh.admin.entity.Tags;
import com.lqh.admin.entity.User;
import com.lqh.admin.mapper.CommentsMapper;
import com.lqh.admin.service.ArticleCategoryService;
import com.lqh.admin.service.TagsService;
import com.lqh.admin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test01() {
        User user = userService.findByName("admin");
        System.out.println(user);
    }
    @Test
    public void test02(){
        Tags tag = tagsService.findByName("测试1");
        System.out.println(tag);
    }
    @Test
    public void test03(){
        User user = new User();
        user.setUsername("marry");
        user.setNickname("cat");
        user.setPassword("666666");
        userService.save(user);
    }
    @Test
    public void test04(){
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleId(1);
        articleCategory.setCategoryId(1);
        articleCategoryService.save(articleCategory);
    }

    @Test
    public void login() {
        String username = "tycoding";
        String password = "123";
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            logger.info("是否登录：" + subject.isAuthenticated());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
