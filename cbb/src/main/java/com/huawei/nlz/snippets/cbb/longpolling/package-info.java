/**
 * 长轮询服务端实现。
 * <p>
 * 所谓长轮询，是指客户端向服务端发起轮询时，如果有消息，则返回；如果没有消息，则服务端hold住请求，等有消息可以返回或者超过超时时间时再返回。
 */

package com.huawei.nlz.snippets.cbb.longpolling;