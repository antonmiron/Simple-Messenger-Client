package com.example.mymessenger;

import com.example.mymessenger.viewmodels.MessageViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MessageViewModelUnitTest {
    private MessageViewModel messageVM;

    @Before
    public void initialize(){
        messageVM = new MessageViewModel();
    }

    @Test
    public void isStringEmpty_Test() {
        boolean actual = messageVM.isStringEmpty(null);
        assertTrue(actual);

        actual = messageVM.isStringEmpty("");
        assertTrue(actual);

        Random r = new Random();
        StringBuffer sb = new StringBuffer(" ");
        for(int i=0;i<r.nextInt(10);i++)
            sb.append(" ");


        actual = messageVM.isStringEmpty(sb.toString());
        assertTrue(actual);

        actual = messageVM.isStringEmpty("some text");
        assertFalse(actual);
    }

    @Test
    public void isIpCorrect_Test(){
        boolean actual = messageVM.isIpCorrect("256.123.12.1");
        assertFalse(actual);

        actual = messageVM.isIpCorrect("2131");
        assertFalse(actual);

        actual = messageVM.isIpCorrect("text");
        assertFalse(actual);

        actual = messageVM.isIpCorrect("192.168.0.1");
        assertTrue(actual);
    }

    @Test
    public void isPortCorrect_Test(){
        boolean actual = messageVM.isPortCorrect(-1);
        assertFalse(actual);

        actual=messageVM.isPortCorrect(65536);
        assertFalse(actual);

        actual=messageVM.isPortCorrect(4040);
        assertTrue(actual);
    }


}