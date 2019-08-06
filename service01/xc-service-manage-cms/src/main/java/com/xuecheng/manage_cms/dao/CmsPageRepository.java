package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    /**
     * 查询该要插入的页面该页面在数据库是否存在
     * @param pageName 页面名称
     * @param siteId 站点Id
     * @param pageWebPath 页面路径
     * @return
     */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,
                                                  String pageWebPath);
}
