package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flower.service.domain.OrderDetailDomainService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderDetailMapper;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletOrderDetailServiceTest {

    @Mock
    private FolwerAppletOrderDetailMapper baseMapper;
    @Mock
    private IFolwerAppletSkuService skuService;

    @Test
    void queryByIdShouldAttachSkuName() {
        FolwerAppletOrderDetailServiceImpl service =
            new FolwerAppletOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerAppletOrderDetailVo detailVo = new FolwerAppletOrderDetailVo();
        detailVo.setId(1L);
        detailVo.setSkuId(11L);
        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuId(11L);
        skuVo.setSkuName("red-l");

        when(baseMapper.selectVoById(1L)).thenReturn(detailVo);
        when(skuService.selsctById(11L)).thenReturn(skuVo);

        FolwerAppletOrderDetailVo result = service.queryById(1L);

        assertEquals("red-l", result.getSkuName());
    }

    @Test
    void queryPageListShouldAttachSkuNames() {
        FolwerAppletOrderDetailServiceImpl service =
            new FolwerAppletOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerAppletOrderDetailVo detailVo = new FolwerAppletOrderDetailVo();
        detailVo.setId(2L);
        detailVo.setSkuId(12L);
        Page<FolwerAppletOrderDetailVo> page = new Page<>();
        page.setRecords(List.of(detailVo));
        page.setTotal(1);
        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuName("white-m");

        when(baseMapper.selectVoPage(any(), any())).thenReturn(page);
        when(skuService.selsctById(12L)).thenReturn(skuVo);

        var result = service.queryPageList(new FolwerAppletOrderDetailBo(), new PageQuery());

        assertEquals("white-m", result.getRows().get(0).getSkuName());
        verify(skuService).selsctById(12L);
    }

    @Test
    void queryListShouldAttachSkuNames() {
        FolwerAppletOrderDetailServiceImpl service =
            new FolwerAppletOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerAppletOrderDetailVo detailVo = new FolwerAppletOrderDetailVo();
        detailVo.setId(3L);
        detailVo.setSkuId(13L);
        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuName("pink-s");

        when(baseMapper.selectVoList(any())).thenReturn(List.of(detailVo));
        when(skuService.selsctById(13L)).thenReturn(skuVo);

        List<FolwerAppletOrderDetailVo> result = service.queryList(new FolwerAppletOrderDetailBo());

        assertEquals("pink-s", result.get(0).getSkuName());
    }
}
