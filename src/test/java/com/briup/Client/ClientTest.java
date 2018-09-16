package com.briup.Client;

import com.briup.Client.Impl.GatherImpl;
import org.junit.Before;
import org.junit.Test;

public class ClientTest{

    @Before
    public void init (){}

    @Test
    public void ReadFile(){
        new GatherImpl().gather();
    }
}
