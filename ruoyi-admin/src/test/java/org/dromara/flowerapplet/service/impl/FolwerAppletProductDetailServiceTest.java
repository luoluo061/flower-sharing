package org.dromara.flowerapplet.service.impl;

import org.dromara.flowerapplet.domain.vo.FolwerAppletProductDetailVo;
import org.dromara.flowerapplet.mapper.FolwerAppletProductDetailMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletProductDetailServiceTest {

    @Mock
    private FolwerAppletProductDetailMapper detailMapper;

    @Test
    void queryByProductIdShouldKeepCurrentSkuBoundLookupSemantics() {
        FolwerAppletProductDetailServiceImpl service = new FolwerAppletProductDetailServiceImpl(detailMapper);
        FolwerAppletProductDetailVo detailVo = new FolwerAppletProductDetailVo();
        detailVo.setDetailId(66L);
        detailVo.setSkuId(100L);

        when(detailMapper.selectVoOne(any())).thenReturn(detailVo);

        FolwerAppletProductDetailVo result = service.queryByProductId(100L);

        assertEquals(66L, result.getDetailId());
        assertEquals(100L, result.getSkuId());
        verify(detailMapper).selectVoOne(any());
    }

    @Test
    void deleteWithValidByIdsShouldKeepCurrentDirectDeleteSemantics() {
        FolwerAppletProductDetailServiceImpl service = new FolwerAppletProductDetailServiceImpl(detailMapper);
        when(detailMapper.deleteByIds(List.of(33L))).thenReturn(1);

        boolean result = service.deleteWithValidByIds(List.of(33L), true);

        assertTrue(result);
        verify(detailMapper).deleteByIds(List.of(33L));
    }
}
