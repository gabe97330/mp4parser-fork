package com.coremedia.iso.boxes.sampleentry;

import com.coremedia.iso.IsoOutputStream;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: gabe
 * Date: 4/15/11
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextSampleEntryTest {

    @Test
    public void sizeOfOutputSameAsGetSize() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        IsoOutputStream ios = new IsoOutputStream(baos);

        TextSampleEntry tx3g = new TextSampleEntry(null);

        tx3g.getContent(ios);

        assertThat((long)baos.size(), equalTo(tx3g.getSize()));
    }
}
