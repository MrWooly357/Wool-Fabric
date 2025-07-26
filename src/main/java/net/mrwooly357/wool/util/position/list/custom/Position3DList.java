package net.mrwooly357.wool.util.position.list.custom;

import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.util.position.custom.Position3D;
import net.mrwooly357.wool.util.position.list.PositionList;

import java.util.ArrayList;
import java.util.List;

public final class Position3DList implements Position3List<Position3D> {

    private final List<Position3D> positions;

    private Position3DList(Position3D... positions) {
        this.positions = new ArrayList<>();

        this.positions.addAll(List.of(positions));
    }


    @Override
    public PositionList<Position3D> of() {
        return new Position3DList();
    }

    @Override
    public PositionList<Position3D> of(Position3D... positions) {
        return new Position3DList(positions);
    }

    @Override
    public List<Position3D> getAll() {
        return positions;
    }

    @Override
    public Position3D get(int index) {
        return positions.get(index);
    }

    @Override
    public void add(Position3D position) {
        positions.add(position);
    }

    @Override
    public List<BlockPos> toBlockPosList() {
        List<BlockPos> list = new ArrayList<>();

        for (Position3D position : positions)
            list.add(position.toBlockPos());

        return list;
    }

    @Override
    public List<BlockPos> toBlockPosRoundedList() {
        List<BlockPos> list = new ArrayList<>();

        for (Position3D position : positions)
            list.add(position.toBlockPosRounded());

        return list;
    }
}
