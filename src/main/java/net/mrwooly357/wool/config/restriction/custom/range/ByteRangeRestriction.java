package net.mrwooly357.wool.config.restriction.custom.range;

public class ByteRangeRestriction extends RangeRestriction<Byte> {

    public ByteRangeRestriction(byte min, byte max) {
        super(min, max);
    }


    @Override
    public Byte normalize(Byte value) {
        byte normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
