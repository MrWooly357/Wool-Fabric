package net.mrwooly357.wool.config.restriction.custom.range;

public class ByteRangeRestriction extends RangeRestriction<Byte> {

    public ByteRangeRestriction(byte bottom, byte top) {
        super(bottom, top);
    }


    @Override
    public Byte normalize(Byte value) {
        byte normalized;

        if (value < bottom) {
            normalized = bottom;
        } else if (value > top) {
            normalized = top;
        } else {
            normalized = value;
        }

        return normalized;
    }
}
