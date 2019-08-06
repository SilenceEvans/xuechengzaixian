package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @ClassName CmsPageRepositoryTest
 * @Description TODO
 * @Author wang
 * @Date 2019/08/02 9:57:30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    //分页测试
    @Test
    public void test1(){
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(1,10);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        System.out.println(cmsPages);
    }
    //测试更新
    @Test
    public void test2(){
        //先从数据库中进行查询
        Optional<CmsPage> optional = cmsPageRepository.findById("5a754adf6abb500ad05688d9");
        if (optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("index.html");
            cmsPageRepository.save(cmsPage);
        }
    }
}
