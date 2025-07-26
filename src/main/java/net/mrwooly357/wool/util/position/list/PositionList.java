package net.mrwooly357.wool.util.position.list;

import net.mrwooly357.wool.util.position.Position;

import java.util.List;

public interface PositionList<P extends Position<?>> {


    PositionList<P> of();

    PositionList<P> of(P... positions);

    List<P> getAll();

    P get(int index);

    void add(P position);
}
