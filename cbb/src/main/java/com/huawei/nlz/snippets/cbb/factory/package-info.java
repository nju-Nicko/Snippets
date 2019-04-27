/**
 * 一个基于Spring framework的工厂模式的实现，用于根据key获取对应的单例bean。
 * <p>
 * 通过扩展指定的抽象类，实现业务对象bean自动注册到工厂bean内的注册中心属性上，然后客户端调用工厂bean的方法，通过key获取到对象实例。
 * 这种设计使得当想扩展被创建的对象种类时，只需要增加类，然后客户端使用对应的key去获取即可，主体框架代码完全无需修改。
 * <p>
 * 需要注意的是，业务对象bean必须是单例作用域的。
 */

package com.huawei.nlz.snippets.cbb.factory;