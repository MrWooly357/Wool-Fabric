package net.mrwooly357.wool.util;

import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.context.custom.ResultContext;

@FunctionalInterface
public interface Completable<R extends ResultContext<?>, C extends Context> {


    R complete(C context);
}
