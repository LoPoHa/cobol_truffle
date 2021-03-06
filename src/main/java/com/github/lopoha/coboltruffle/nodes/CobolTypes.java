package com.github.lopoha.coboltruffle.nodes;

// modeled / copied from SimpleLanguage
import com.github.lopoha.coboltruffle.runtime.CobolNull;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;

/** TODO. */
@TypeSystem({long.class, boolean.class})
public abstract class CobolTypes {

  @TypeCheck(CobolNull.class)
  public static boolean isCobolNull(Object value) {
    return value == CobolNull.SINGLETON;
  }

  @TypeCast(CobolNull.class)
  public static CobolNull asCobolNull(Object value) {
    assert isCobolNull(value);
    return CobolNull.SINGLETON;
  }
}
