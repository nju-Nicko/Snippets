package com.huawei.nlz.snippets.cbb.retry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 操作响应类
 */
@Data
@AllArgsConstructor
public class RpcResponse {
    /**
     * 结果码，0表示成功，非0表示失败。
     * 非0错误码信息请参阅文档。
     */
    int resultCode;

    /**
     * 结果描述。
     */
    String resultDesc;
}