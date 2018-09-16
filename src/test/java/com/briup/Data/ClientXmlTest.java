package com.briup.Data;


import com.briup.Data.Client.CarbonDioxideClient;
import com.briup.Data.Client.DataClient;
import com.briup.Data.Client.GuangClient;
import org.junit.Before;
import org.junit.Test;

public class ClientXmlTest {

    @Before
    public void init(){

    }

    @Test
    public void ClientGetXmlTest(){
        GuangClient.guangGetObj();
        DataClient.DataGetObj();
        CarbonDioxideClient.CarbonDioxideGetObj();
    }

}
