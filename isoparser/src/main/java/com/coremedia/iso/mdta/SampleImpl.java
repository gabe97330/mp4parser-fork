/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.coremedia.iso.mdta;

import com.coremedia.iso.IsoBufferWrapper;
import com.coremedia.iso.IsoOutputStream;
import com.coremedia.iso.boxes.TrackMetaDataContainer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * An object of this class represents a single sample of an IsoFile as in the file read.
 * The sample has a {@link Chunk} as parent.
 *
 * @see Chunk
 * @see Track
 * @see Sample
 */
public final class SampleImpl<T extends TrackMetaDataContainer> implements Sample<T>, Comparable<SampleImpl<T>> {

  private final Chunk<T> parent;
  private final IsoBufferWrapper buffer;
  private final long offset;
  private final long size;
  private boolean syncSample;

  public SampleImpl(IsoBufferWrapper buffer, long offset, long size, Chunk<T> parent, boolean syncSample) {
    this.parent = parent;
    this.buffer = buffer;
    this.offset = offset;
    this.size = size;
    this.syncSample = syncSample;
  }

  public void getContent(IsoOutputStream os) throws IOException {
    ByteBuffer[] segments = buffer.getSegment(offset, size);
    for (ByteBuffer segment : segments) {
      while (segment.remaining() > 1024) {
        byte[] buf = new byte[1024];
        segment.get(buf);
        os.write(buf);
      }
      while (segment.remaining() > 0) {
        os.write(segment.get());
      }
    }
  }

  public long getSize() {
    return size;
  }

  public long getOffset() {
    return offset;
  }

  public String toString() {
    return "Offset: " + calculateOffset() + " Size: " + size + " Chunk: " + parent.getFirstSample().calculateOffset() + " Track: " + parent.getParentTrack().getTrackId() + " SyncSample: " + syncSample;
  }

  public int compareTo(SampleImpl<T> o) {
    return (int) (this.offset - o.offset);
  }

  public Chunk<T> getParent() {
    return parent;
  }

  public long calculateOffset() {
    long offsetFromChunkStart = 0;
    List<Sample<T>> samples = parent.getSamples();
    for (Sample<T> sample : samples) {
      if (!this.equals(sample)) {
        offsetFromChunkStart += sample.getSize();
      }
    }
    return parent.calculateOffset() + offsetFromChunkStart;
  }

  public boolean isSyncSample() {
    return syncSample;
  }
}
