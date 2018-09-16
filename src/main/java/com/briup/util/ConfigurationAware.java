package com.briup.util;

/**
 * 在其中某个模块中需要其他模块对象
 * 让该模块实现 ConfigurationAware
 * 自动注入 Configuration
 * 注意： 使用和配置模块配合
 */
public interface ConfigurationAware {

    void SetConfiguration(Configuration conf);

}
