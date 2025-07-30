package net.mrwooly357.wool.util.misc;

import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.context.custom.ResultContext;

@FunctionalInterface
public interface Failable<R extends ResultContext<?>, C extends Context> {


    R fail(C context);
}
