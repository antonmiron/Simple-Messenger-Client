package com.example.mymessenger;

import android.text.Editable;
import android.widget.EditText;

import com.example.mymessenger.tools.BindingAdapters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BindingAdaptersUnitTest {

   @Test
    public void convertStringTest(){
       /**Для использования getText, а затем и toString у EditText нужно иницилизировать Editable
        * иначе он при вызове toString он кидет NullPointerException.
        * Первый вариант заключался в том, что бы просто создать его
        *
        * Editable editable = Editable.Factory.getInstance().newEditable("test");
        * when(mockedEditText.getText()).thenReturn(editable);
        *
        * но этот вариант не прошел из-за того что Method getInstance not mocked.
        * Позже я узнал (http://g.co/androidstudio/not-mocked), что это сделано специально
        * что бы абстрагироваться от Android`а, и в прицнипи можно это обойти прописав
        * unitTests.returnDefaultValues = true (в Gradle)
        * Но я решил не идти этим путем, а просто создать еще один Mock, но уже для Editable
        * **/
      Editable mockedEditable = mock(Editable.class);
      when(mockedEditable.toString()).thenReturn("text");

      EditText mockedEditText = mock(EditText.class);
      when(mockedEditText.getText()).thenReturn(mockedEditable);

      int actual = BindingAdapters.convertString(mockedEditText);
      assertEquals(0,actual);

      when(mockedEditable.toString()).thenReturn("12345");

      actual = BindingAdapters.convertString(mockedEditText);
      assertEquals(12345,actual);
   }
}

