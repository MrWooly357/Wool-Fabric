package net.mrwooly357.wool.util;

import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.context.custom.ResultContext;

@FunctionalInterface
public interface Acceptable<R extends ResultContext<?>, C extends Context> {


    R accept(C context);
}
