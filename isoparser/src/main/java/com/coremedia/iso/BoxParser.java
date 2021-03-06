package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.ContainerBox;

import java.io.IOException;

/**
 * Basic interface to create boxes from a <code>IsoBufferWrapper</code> and its parent.
 */
public interface BoxParser {
    Box parseBox(IsoBufferWrapper in, ContainerBox parent, Box lastMovieFragmentBox) throws IOException;
}
