package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName PageService
 * @Description TODO
 * @Author wang
 * @Date 2019/08/02 14:57:54
 * @Version 1.0
 */
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**
     * 分页查询的方法
     * @param page 当前页
     * @param size 每页容纳的数量
     * @param pageRequest 封装请求的参数
     * @return 请求的结果
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest pageRequest){
        if (page <= 0){
            page = 1;
        }
        if (size <= 0){
            size = 20;
        }
        page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        //Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        //增强查询功能，根据名称进行模糊查询
        //构建条件查询器,根据某字段进行查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());
        //设置字段值
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(pageRequest.getSiteId())){
            //设置站点id
            cmsPage.setSiteId(pageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(pageRequest.getPageAliase())){
            //设置页面别名
            cmsPage.setPageAliase(pageRequest.getPageAliase());
        }
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<CmsPage>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return  new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 往数据库中添加一条cmsPage信息
     * @param cmsPage cmsPage对象
     * @return 添加的结果
     */
    public CmsPageResult add(CmsPage cmsPage){
        CmsPage cmsPage1 =
                cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                cmsPage.getSiteId(),cmsPage.getPageWebPath());
        if (cmsPage1 != null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    /**
     * 根据页面id查询页面对象
     * @param id 页面id
     * @return 页面对象
     */
    public CmsPage findById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 根据传入的id，修改页面信息
     * @param id 页面id
     * @param cmsPage 提交的页面对象信息
     * @return 修改结果
     */
    public CmsPageResult edit(String id,CmsPage cmsPage){
        CmsPage cmsPage1 = this.findById(id);
        if (cmsPage1 != null){
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            cmsPage1.setSiteId(cmsPage.getSiteId());
            cmsPage1.setPageName(cmsPage.getPageName());
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            CmsPage cmsPage2 = cmsPageRepository.save(cmsPage1);
            if (cmsPage2 != null){
                return new CmsPageResult(CommonCode.SUCCESS,cmsPage2);
            }else {
                return new CmsPageResult(CommonCode.FAIL,null);
            }
        }else {
            return new CmsPageResult(CommonCode.FAIL,null);
        }
    }

    public ResponseResult deleteById(String id){
        CmsPage cmsPage = this.findById(id);
        if (cmsPage != null){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }
    }
}
