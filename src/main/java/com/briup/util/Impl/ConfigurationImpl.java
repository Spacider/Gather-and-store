package com.briup.util.Impl;


import com.briup.Client.EnvClient;
import com.briup.Client.Gather;
import com.briup.Client.Impl.EnvClientImpl;
import com.briup.Client.Impl.GatherImpl;
import com.briup.Server.DBStore;
import com.briup.Server.EnvServer;
import com.briup.Server.Impl.DBStoreImpl;
import com.briup.Server.Impl.EnvServerImpl;
import com.briup.util.BackUp;
import com.briup.util.Configuration;
import com.briup.util.Log;

/**
 * 配置模块实现
 */

public class ConfigurationImpl implements Configuration {

    @Override
    public Gather getGather() {
        return new GatherImpl();
    }

    @Override
    public EnvClient getClient() {
        return new EnvClientImpl();
    }

    @Override
    public EnvServer getServer() {
        return new EnvServerImpl();
    }

    @Override
    public DBStore getDBStore() {
        return new DBStoreImpl();
    }

    @Override
    public Log getLog() {
        return new LogImpl();
    }

    @Override
    public BackUp getBackUp() {
        return new BackUpImpl();
    }
}
