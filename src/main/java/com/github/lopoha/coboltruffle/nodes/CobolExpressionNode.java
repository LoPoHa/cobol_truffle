package com.github.lopoha.coboltruffle.nodes;

// modeled / copied from SimpleLanguage

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/** Required for builtin functions that produce a value. e.g. ReadLine */
@TypeSystemReference(CobolTypes.class)
@NodeInfo(description = "The abstract base node for all expressions")
@GenerateWrapper
public abstract class CobolExpressionNode extends CobolStatementNode {

  private boolean hasExpressionTag;

  /**
   * TODO.
   *
   * @param frame todo
   * @return todo
   */
  public abstract Object executeGeneric(VirtualFrame frame);

  /**
   * TODO.
   *
   * @param frame todo.
   */
  @Override
  public void executeVoid(VirtualFrame frame) {
    executeGeneric(frame);
  }

  @Override
  public WrapperNode createWrapper(ProbeNode probe) {
    return new CobolExpressionNodeWrapper(this, probe);
  }

  @Override
  public boolean hasTag(Class<? extends Tag> tag) {
    if (tag == StandardTags.ExpressionTag.class) {
      return hasExpressionTag;
    }
    return super.hasTag(tag);
  }

  /**
   * Marks this node as being a {@link StandardTags.ExpressionTag} for instrumentation purposes.
   */
  public final void addExpressionTag() {
    hasExpressionTag = true;
  }

  /*
   * Execute methods for specialized types. They all follow the same pattern: they call the
   * generic execution method and then expect a result of their return type. Type-specialized
   * subclasses overwrite the appropriate methods.
   */

  public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
    return CobolTypesGen.expectLong(executeGeneric(frame));
  }

  public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
    return CobolTypesGen.expectBoolean(executeGeneric(frame));
  }
}
