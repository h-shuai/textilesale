package com.rosellete.textilesale.jasypt;

import com.rosellete.textilesale.TextilesaleApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TextilesaleApplication.class)
public class JasyptUtils {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encrypt(){
        System.setProperty("jasypt.encryptor.password","jasypt");
        System.out.println("encrypt:" + stringEncryptor.encrypt("sales"));
    }

    @Test
    public void decrypy(){
        System.setProperty("jasypt.encryptor.password","jasypt");
        System.out.println("decrypt:" + stringEncryptor.decrypt(""));
    }
}
