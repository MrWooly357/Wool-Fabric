package net.mrwooly357.wool.util;

import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.context.custom.ResultContext;

@FunctionalInterface
public interface Declinable<R extends ResultContext<?>, C extends Context> {


    R decline(C context);
}
