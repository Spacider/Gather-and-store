package com.briup.util.Impl;

import com.briup.Bean.Environment;
import com.briup.util.BackUp;

import java.util.Collection;
import java.util.Properties;

public class BackUpImpl implements BackUp {


    @Override
    public void storeEnvs(Collection<Environment> coll, String path) {

    }

    @Override
    public Collection <Environment> loadEnvs() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}
