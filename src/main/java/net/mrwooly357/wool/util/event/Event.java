package net.mrwooly357.wool.util.event;

import net.mrwooly357.wool.util.Completable;
import net.mrwooly357.wool.util.context.Context;
import net.mrwooly357.wool.util.Failable;
import net.mrwooly357.wool.util.context.custom.EventListenerContext;
import net.mrwooly357.wool.util.context.custom.ResultContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Event<R extends ResultContext<?>, C extends Context> implements Completable<R, C>, Failable<R, C> {

    protected State<C, R> state;
    private final State<C, R> completedState;
    private final State<C, R> failedState;
    private final Function<C, R> complete;
    private final Function<C, R> fail;
    private final List<Listener<EventListenerContext<C, R>>> listeners;

    @SafeVarargs
    public Event(Function<C, R> complete, Function<C, R> fail, State<C, R> noneState, State<C, R> completedState, State<C, R> failedState, Listener<EventListenerContext<C, R>>... initialListeners) {
        state = noneState;
        this.complete = complete;
        this.fail = fail;
        this.completedState = completedState;
        this.failedState = failedState;
        this.listeners = new ArrayList<>();

        listeners.addAll(List.of(initialListeners));
    }


    @Override
    public R complete(C context) {
        R result = complete.apply(context);
        state = completedState;

        listeners.forEach(listener -> listener.onComplete(new EventListenerContext<>(context, result, this)));

        return result;
    }

    @Override
    public R fail(C context) {
        R result = fail.apply(context);
        state = failedState;

        listeners.forEach(listener -> listener.onFail(new EventListenerContext<>(context, result, this)));

        return result;
    }


    public static class Listener<E extends EventListenerContext<? extends Context, ? extends ResultContext<?>>> {

        private final Consumer<E> onComplete;
        private final Consumer<E> onFail;

        public Listener(Consumer<E> onComplete, Consumer<E> onFail) {
            this.onComplete = onComplete;
            this.onFail = onFail;
        }


        public void onComplete(E context) {
            onComplete.accept(context);
        }

        public void onFail(E context) {
            onFail.accept(context);
        }

        public void onEventStateUpdated(Event<? extends ResultContext<?>, ? extends Context> event) {}
    }


    @FunctionalInterface
    public interface State<C extends Context, R extends ResultContext<?>> {


        void onUpdated(EventListenerContext<C, R> context);

        default void updateListeners(EventListenerContext<C, R> context) {
            Event<R, C> event = context.event();

            event.listeners.forEach(listener -> listener.onEventStateUpdated(event));
        }
    }
}
