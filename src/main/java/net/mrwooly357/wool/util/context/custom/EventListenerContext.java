package net.mrwooly357.wool.util.context.custom;

import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.event.Event;

/**
 * A {@link Context context} for {@link Event.Listener event listeners}.
 * @param context the initial {@link Context}.
 * @param returnContext the initial {@link ResultContext}.
 * @param event the {@link Event}.
 * @param <C> the type of initial {@link Context context}.
 * @param <R> the type of initial {@link ResultContext result context}.
 */
public record EventListenerContext<C extends Context, R extends ResultContext<?>>(C context, R returnContext, Event<R, C> event) implements Context {}
