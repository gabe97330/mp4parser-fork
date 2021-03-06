package com.coremedia.iso.gui.transferhelper;

import com.coremedia.iso.boxes.AbstractBox;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: sannies
 * Date: 11.11.2008
 * Time: 23:12:52
 * To change this template use File | Settings | File Templates.
 */
public class IntegerTransferValue implements TransferValue {
    JFormattedTextField source;
    Method writeMethod;
    AbstractBox box;

    public IntegerTransferValue(JFormattedTextField source, AbstractBox box, Method writeMethod) {
        this.source = source;
        this.writeMethod = writeMethod;
        this.box = box;
    }

    public void go() {
        try {
            writeMethod.invoke(box, ((Number) source.getFormatter().stringToValue(source.getText())).intValue());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
