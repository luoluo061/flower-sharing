package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.mapper.FolwerAppletCategoryMapper;
import org.dromara.flowerapplet.mapper.FolwerAppletProductMapper;
import org.dromara.flowerapplet.mapper.FolwerAppletSkuMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.dromara.flower.service.domain.ProductCategoryDomainService;
import org.dromara.system.service.ISysOssService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletCatalogReadServiceTest {

    @Mock
    private FolwerAppletCategoryMapper categoryMapper;
    @Mock
    private ISysOssService ossService;
    @Mock
    private FolwerAppletProductMapper productMapper;
    @Mock
    private IFolwerAppletSkuService appletSkuService;
    @Mock
    private IFlowerAppletUserInformationService userInformationService;
    @Mock
    private IFolwerAppletCategoryService categoryService;
    @Mock
    private FolwerAppletSkuMapper skuMapper;

    @Test
    void categoryQueryByIdShouldAttachSortedChildrenForNonRootParent() {
        FolwerAppletCategoryServiceImpl service = new FolwerAppletCategoryServiceImpl(categoryMapper, ossService, new ProductCategoryDomainService());
        FolwerAppletCategoryVo parent = new FolwerAppletCategoryVo();
        parent.setId(1L);
        parent.setParentId(9L);

        FolwerAppletCategoryVo childLow = new FolwerAppletCategoryVo();
        childLow.setId(11L);
        childLow.setParentId(0L);
        childLow.setSeq(1L);
        FolwerAppletCategoryVo childHigh = new FolwerAppletCategoryVo();
        childHigh.setId(12L);
        childHigh.setParentId(0L);
        childHigh.setSeq(5L);

        when(categoryMapper.selectVoById(1L)).thenReturn(parent);
        when(categoryMapper.selectVoList(any())).thenReturn(List.of(childLow, childHigh));

        FolwerAppletCategoryVo result = service.queryById(1L);

        assertEquals(2, result.getChildren().size());
        assertEquals(12L, result.getChildren().get(0).getId());
        assertEquals(11L, result.getChildren().get(1).getId());
    }

    @Test
    void categoryQueryByIdShouldSkipChildrenForRootParent() {
        FolwerAppletCategoryServiceImpl service = new FolwerAppletCategoryServiceImpl(categoryMapper, ossService, new ProductCategoryDomainService());
        FolwerAppletCategoryVo parent = new FolwerAppletCategoryVo();
        parent.setId(2L);
        parent.setParentId(0L);

        when(categoryMapper.selectVoById(2L)).thenReturn(parent);

        FolwerAppletCategoryVo result = service.queryById(2L);

        assertNull(result.getChildren());
        verify(categoryMapper, never()).selectVoList(any());
    }

    @Test
    void productQueryPageListShouldMaskGuestPrices() {
        TestProductService service = new TestProductService(productMapper, appletSkuService, userInformationService, categoryService);
        service.loggedIn = false;

        FolwerAppletProductVo first = new FolwerAppletProductVo();
        first.setId(21L);
        first.setSeq(1L);
        first.setOriPrice(new BigDecimal("99"));
        first.setDerlinePrice(new BigDecimal("120"));

        Page<FolwerAppletProductVo> page = new Page<>();
        page.setRecords(List.of(first));
        when(productMapper.selectVoPage(any(), any())).thenReturn(page);

        TableDataInfo<FolwerAppletProductVo> result = service.queryPageList(new FolwerAppletProductBo(), pageQuery());
        FolwerAppletProductVo row = result.getRows().get(0);

        assertEquals(new BigDecimal("-1"), row.getOriPrice());
        assertEquals(new BigDecimal("-1"), row.getDerlinePrice());
    }

    @Test
    void productQueryPageListShouldKeepAuthenticatedPrices() {
        TestProductService service = new TestProductService(productMapper, appletSkuService, userInformationService, categoryService);
        service.loggedIn = true;
        service.userId = 100L;
        service.authStatus = 1L;

        FolwerAppletProductVo first = new FolwerAppletProductVo();
        first.setId(22L);
        first.setSeq(2L);
        first.setOriPrice(new BigDecimal("88"));
        first.setDerlinePrice(new BigDecimal("99"));

        Page<FolwerAppletProductVo> page = new Page<>();
        page.setRecords(List.of(first));
        when(productMapper.selectVoPage(any(), any())).thenReturn(page);

        TableDataInfo<FolwerAppletProductVo> result = service.queryPageList(new FolwerAppletProductBo(), pageQuery());
        FolwerAppletProductVo row = result.getRows().get(0);

        assertEquals(new BigDecimal("88"), row.getOriPrice());
        assertEquals(new BigDecimal("99"), row.getDerlinePrice());
    }

    @Test
    void productQueryAllByCategoryShouldMaskPendingAuthPrices() {
        TestProductService service = new TestProductService(productMapper, appletSkuService, userInformationService, categoryService);
        service.loggedIn = true;
        service.userId = 101L;
        service.authStatus = 0L;

        FolwerAppletProductVo first = new FolwerAppletProductVo();
        first.setId(23L);
        first.setOriPrice(new BigDecimal("88"));
        first.setDerlinePrice(new BigDecimal("99"));
        when(productMapper.selectListByAll(10, 0)).thenReturn(List.of(first));

        List<FolwerAppletProductVo> result = service.queryAllBycategoryId(0L, 1, 10);

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("-2"), result.get(0).getOriPrice());
        assertEquals(new BigDecimal("-2"), result.get(0).getDerlinePrice());
    }

    @Test
    void skuQueryByIdShouldMaskGuestPriceAndSetSkuName() {
        TestSkuService service = new TestSkuService(skuMapper, userInformationService);
        service.loggedIn = false;

        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuId(31L);
        skuVo.setColour("red");
        skuVo.setSize("L");
        skuVo.setPrice(new BigDecimal("19.9"));
        when(skuMapper.selectVoById(31L)).thenReturn(skuVo);

        FolwerAppletSkuVo result = service.queryById(31L);

        assertEquals("red L", result.getSkuName());
        assertEquals(new BigDecimal("-1"), result.getPrice());
    }

    @Test
    void skuQueryPageListShouldMaskPendingAuthAndSortBySeqDescending() {
        TestSkuService service = new TestSkuService(skuMapper, userInformationService);
        service.loggedIn = true;
        service.userId = 102L;
        service.authStatus = 0L;

        FolwerAppletSkuVo lowSeq = new FolwerAppletSkuVo();
        lowSeq.setSkuId(41L);
        lowSeq.setColour("white");
        lowSeq.setSize("S");
        lowSeq.setSeq(1L);
        lowSeq.setPrice(new BigDecimal("10"));

        FolwerAppletSkuVo highSeq = new FolwerAppletSkuVo();
        highSeq.setSkuId(42L);
        highSeq.setColour("pink");
        highSeq.setSize("M");
        highSeq.setSeq(9L);
        highSeq.setPrice(new BigDecimal("20"));

        Page<FolwerAppletSkuVo> page = new Page<>();
        page.setRecords(List.of(lowSeq, highSeq));
        when(skuMapper.selectVoPage(any(), any())).thenReturn(page);

        TableDataInfo<FolwerAppletSkuVo> result = service.queryPageList(new FolwerAppletSkuBo(), pageQuery());

        assertEquals(42L, result.getRows().get(0).getSkuId());
        assertEquals(new BigDecimal("-2"), result.getRows().get(0).getPrice());
        assertEquals("pink M", result.getRows().get(0).getSkuName());
        assertEquals(new BigDecimal("-2"), result.getRows().get(1).getPrice());
    }

    private PageQuery pageQuery() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(1);
        pageQuery.setPageSize(10);
        return pageQuery;
    }

    private static final class TestProductService extends FolwerAppletProductServiceImpl {
        private boolean loggedIn;
        private Long userId;
        private Long authStatus;

        private TestProductService(
            FolwerAppletProductMapper baseMapper,
            IFolwerAppletSkuService folwerAppletSkuService,
            IFlowerAppletUserInformationService flowerAppletUserInformationService,
            IFolwerAppletCategoryService folwerCategoryService
        ) {
            super(baseMapper, folwerAppletSkuService, flowerAppletUserInformationService, folwerCategoryService);
        }

        @Override
        protected boolean isUserLoggedIn() {
            return loggedIn;
        }

        @Override
        protected Long getCurrentUserId() {
            return userId;
        }

        @Override
        protected Long getCurrentUserAuthStatus(Long userId) {
            return authStatus;
        }
    }

    private static final class TestSkuService extends FolwerAppletSkuServiceImpl {
        private boolean loggedIn;
        private Long userId;
        private Long authStatus;

        private TestSkuService(
            FolwerAppletSkuMapper baseMapper,
            IFlowerAppletUserInformationService flowerAppletUserInformationService
        ) {
            super(baseMapper, flowerAppletUserInformationService);
        }

        @Override
        protected boolean isUserLoggedIn() {
            return loggedIn;
        }

        @Override
        protected Long getCurrentUserId() {
            return userId;
        }

        @Override
        protected Long getCurrentUserAuthStatus(Long userId) {
            return authStatus;
        }
    }
}
