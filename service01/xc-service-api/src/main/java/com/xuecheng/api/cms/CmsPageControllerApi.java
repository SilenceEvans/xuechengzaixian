package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CmsPageControllerApi
 * @Description 页面查询接口
 * @Author wang
 * @Date 2019/08/02 0:28:08
 * @Version 1.0
 */
@Api(value = "cms管理接口",description = "cms管理接口，提供页面的增删改查")
public interface CmsPageControllerApi {

    /**
     * @param page 当前页
     * @param size 页面容纳数量
     * @param queryPageRequest 其他查询条件封装
     * @return
     * 添加显示开发文档swagger的注解
     */
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",paramType = "path",required =
                    true,dataType = "int"),
            @ApiImplicitParam(name = "size",value = "当前页容量",paramType = "path",
                    required = true,dataType = "int")
    })
    QueryResponseResult findList(int page, int size,
                                        QueryPageRequest queryPageRequest);

    /**
     * 新增页面的方法
     * @param cmsPage 传入新增的对象
     * @return 新增页面的结果
     */
    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据id查询页面对象
     * @param id 查询页面的id
     * @return 查询到的结果
     */
    @ApiOperation("根据id查询页面")
    CmsPage findById(String id);

    /**
     * 根据id和cmsPage修改页面
     * @param id 传入页面对象的id
     * @param cmsPage 传入页面对向
     * @return 修改后的结果
     */
    @ApiOperation("根据id和页面对象修改页面")
    CmsPageResult edit(String id,CmsPage cmsPage);

    /**
     * 根据id删除cmsPage对象
     * @param id 被删除对象的id
     * @return 删除的结果
     */
    @ApiOperation("根据id删除页面对象")
    ResponseResult deleteById(String id);
}
