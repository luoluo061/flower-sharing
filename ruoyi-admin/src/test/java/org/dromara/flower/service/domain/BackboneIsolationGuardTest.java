package org.dromara.flower.service.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("dev")
class BackboneIsolationGuardTest {

    private static final Path REPO_ROOT = Path.of(System.getProperty("user.dir")).getParent();
    private static final Path SOURCE_ROOT = REPO_ROOT.resolve("ruoyi-modules")
        .resolve("ruoyi-flower")
        .resolve("src")
        .resolve("main")
        .resolve("java");

    private static final List<String> FORBIDDEN_LEGACY_TOKENS = List.of(
        "Courses",
        "FriendsCommunity",
        "FolwerCreditOrder",
        "FolwerCreditProduct",
        "FolwerCreditCategory",
        "FolwerAppletCreditOrder",
        "FolwerAppletCreditProduct",
        "FolwerAppletCreditCategory",
        "MarketingMemberPromotion",
        "FolwerDeliveryBox",
        "FolwerDeliveryTemperature",
        "FolwerDeliveryRule",
        "FolwerDeliverySet"
    );

    private static final List<String> MAINLINE_SOURCE_FILES = List.of(
        "org/dromara/flower/service/domain/ProductCategoryDomainService.java",
        "org/dromara/flower/service/domain/ProductCoreDomainService.java",
        "org/dromara/flower/service/domain/ProductSkuAggregateDomainService.java",
        "org/dromara/flower/service/domain/ProductDetailDomainService.java",
        "org/dromara/flower/service/domain/OrderLifecycleDomainService.java",
        "org/dromara/flower/service/domain/OrderDetailDomainService.java",
        "org/dromara/flower/service/domain/OrderFulfillmentDomainService.java",
        "org/dromara/flower/service/domain/OrderRefundDomainService.java",
        "org/dromara/flower/service/domain/PaymentTransactionDomainService.java",
        "org/dromara/flower/service/domain/CouponAssetDomainService.java",
        "org/dromara/flower/service/domain/PointsAssetDomainService.java",
        "org/dromara/flower/service/domain/MemberAssetDomainService.java",
        "org/dromara/flower/service/impl/FolwerCategoryServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerProductServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerSkuServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerProductDetailServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerOrderServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerOrderDetailServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerOrderRefundServiceImpl.java",
        "org/dromara/flower/service/impl/MemberLevelServiceImpl.java",
        "org/dromara/flower/service/impl/MarketingCouponServiceImpl.java",
        "org/dromara/flower/service/impl/MarketingCouponReceiveServiceImpl.java",
        "org/dromara/flower/service/impl/FolwerCreditGetrecordsServiceImpl.java"
    );

    @Test
    void mainlineBackboneSourcesDoNotDependOnLegacyDomains() throws IOException {
        for (String relativeFile : MAINLINE_SOURCE_FILES) {
            List<String> imports = readImports(relativeFile);
            for (String forbiddenToken : FORBIDDEN_LEGACY_TOKENS) {
                assertFalse(imports.stream().anyMatch(line -> line.contains(forbiddenToken)),
                    () -> relativeFile + " should not depend on legacy token " + forbiddenToken);
            }
        }
    }

    private List<String> readImports(String relativeFile) throws IOException {
        try (Stream<String> lines = Files.lines(SOURCE_ROOT.resolve(relativeFile), StandardCharsets.UTF_8)) {
            return lines.filter(line -> line.startsWith("import ")).toList();
        }
    }
}
