package com.github.lopoha.coboltruffle.nodes.expression.heap;

import com.github.lopoha.coboltruffle.nodes.Helper;
import com.github.lopoha.coboltruffle.nodes.expression.CobolProgramStateNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CobolHeapPointerString extends CobolHeapPointer {
  /**
   * Create a Pointer to the heap.
   * The heap must be on the frame.
   * @param name name of the variable.
   * @param position start position on the heap.
   * @param length length of the variable
   * @param defaultValue the default value.
   * @param level the level of the variable declaration.
   */
  public CobolHeapPointerString(String name,
                                int position,
                                int length,
                                String defaultValue,
                                int level) {
    super(name, position, length, defaultValue, level);
  }

  @Override
  public void initialize(CobolProgramStateNode programState) {
    // is this even possible?
    if (defaultValue != null) {
      this.setValue(this.defaultValue, programState);
    }
  }

  @Override
  public void setValue(List<Character> value, CobolProgramStateNode programState) {
    assert value != null : "Value required but was null";
    // todo: check if the alignment etc. is correct
    // todo: is the default for number here space or zero?
    // todo: is parallel always faster?
    if (value.size() < this.length) {
      final String val
          = new String(new char[this.length - value.size()]).replace('\0', ' ') + value;
      IntStream.range(0, value.size())
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, val.charAt(i)));
    } else if (value.size() == this.length) {
      IntStream.range(0, value.size())
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, value.get(i)));
    } else {
      IntStream.range(0, this.length)
          .parallel()
          .forEach(i -> programState.getLocalFileHeap().set(position + i, value.get(i)));
    }
  }

  @Override
  public String getValue(CobolProgramStateNode programState) {
    // safe, since the parent always returns List<Character>
    List<Character> chars = (List<Character>) super.getValue(programState);
    return chars.stream().map(String::valueOf).collect(Collectors.joining());
  }

  @Override
  public int compareTo(CobolHeapPointer o) {
    // todo
    // return this.getValue(null).compareTo(o.getValue(null));
    return 0;
  }

  @Override
  public String executeGeneric(VirtualFrame frame) {
    return this.getValue(Helper.getProgramStateFromFrame(frame));
  }
}
