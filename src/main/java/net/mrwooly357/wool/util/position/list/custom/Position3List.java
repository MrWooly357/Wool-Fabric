package net.mrwooly357.wool.util.position.list.custom;

import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.util.position.custom.Position3;
import net.mrwooly357.wool.util.position.list.PositionList;

import java.util.List;

public interface Position3List<P extends Position3<?>> extends PositionList<P> {


    List<BlockPos> toBlockPosList();

    List<BlockPos> toBlockPosRoundedList();
}
