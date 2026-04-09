package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flower.domain.bo.FolwerOrderDetailBo;
import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.mapper.FolwerOrderDetailMapper;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flower.service.domain.OrderDetailDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerOrderDetailServiceTest {

    @Mock
    private FolwerOrderDetailMapper baseMapper;
    @Mock
    private IFolwerSkuService skuService;

    @Test
    void queryByIdShouldAttachSkuView() {
        FolwerOrderDetailServiceImpl service =
            new FolwerOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setId(1L);
        detailVo.setSkuId(11L);
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(11L);

        when(baseMapper.selectVoById(1L)).thenReturn(detailVo);
        when(skuService.queryById(11L)).thenReturn(skuVo);

        FolwerOrderDetailVo result = service.queryById(1L);

        assertNotNull(result.getFolwerSkuVo());
        assertEquals(11L, result.getFolwerSkuVo().getSkuId());
    }

    @Test
    void queryPageListShouldAttachSkuViews() {
        FolwerOrderDetailServiceImpl service =
            new FolwerOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setId(2L);
        detailVo.setSkuId(12L);
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(12L);
        Page<FolwerOrderDetailVo> page = new Page<>();
        page.setRecords(List.of(detailVo));
        page.setTotal(1);

        when(baseMapper.selectVoPage(any(), any())).thenReturn(page);
        when(skuService.queryById(12L)).thenReturn(skuVo);

        var result = service.queryPageList(new FolwerOrderDetailBo(), new PageQuery());

        assertEquals(1, result.getRows().size());
        assertEquals(12L, result.getRows().get(0).getFolwerSkuVo().getSkuId());
        verify(skuService).queryById(12L);
    }

    @Test
    void queryListShouldAttachSkuViews() {
        FolwerOrderDetailServiceImpl service =
            new FolwerOrderDetailServiceImpl(baseMapper, skuService, new OrderDetailDomainService());
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setId(3L);
        detailVo.setSkuId(13L);
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(13L);

        when(baseMapper.selectVoList(any())).thenReturn(List.of(detailVo));
        when(skuService.queryById(13L)).thenReturn(skuVo);

        List<FolwerOrderDetailVo> result = service.queryList(new FolwerOrderDetailBo());

        assertEquals(1, result.size());
        assertEquals(13L, result.get(0).getFolwerSkuVo().getSkuId());
    }
}
