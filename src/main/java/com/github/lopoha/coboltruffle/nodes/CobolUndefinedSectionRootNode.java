package com.github.lopoha.coboltruffle.nodes;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.runtime.CobolUndefinedNameException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class CobolUndefinedSectionRootNode extends CobolRootNode {
  public CobolUndefinedSectionRootNode(CobolLanguage language, String name) {
    super(language, null, null, null, name);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    throw CobolUndefinedNameException.undefinedFunction(null, getName());
  }
}
