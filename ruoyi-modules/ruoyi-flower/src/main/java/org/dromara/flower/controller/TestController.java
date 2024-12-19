package org.dromara.flower.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author: chazonglin
 * @date: 2024/11/25 10:24
 */
@RestController
@Tag(name = "Test Controller", description = "测试")
public class TestController {

    @RequestMapping("/test")
    @Operation(summary = "Get user by ID", description = "Returns a single user by ID")
    public String hello() {
        return "Hello World";
    }

}
