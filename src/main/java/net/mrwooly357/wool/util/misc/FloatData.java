package net.mrwooly357.wool.util.misc;

public final class FloatData {

    private float value;
    private final byte precision;

    private static final byte VALUE_BITS = 60;
    private static final byte PRECISION_BITS = 3;
    private static final byte MIN_PRECISION = 0;
    private static final byte MAX_PRECISION = 7;

    public FloatData(float value, byte precision) {
        this.value = value;

        if (precision < MIN_PRECISION || precision > MAX_PRECISION) {
            throw new IllegalArgumentException("Precision isn't within bounds! Must be 0-7!, Value is " + precision + ".");
        } else
            this.precision = precision;
    }

    public FloatData(long packed) {
        long rawValue = packed & ((1L << VALUE_BITS) - 1);
        byte precision = (byte) ((packed >>> VALUE_BITS) & 0b111);
        boolean negative = ((packed >>> (VALUE_BITS + PRECISION_BITS)) & 1L) == 1;
        float unpacked = (float) (rawValue / Math.pow(10, precision));

        if (negative)
            unpacked = -unpacked;

        this.value = unpacked;
        this.precision = precision;
    }

    public FloatData(int[] split) {
        this((((long) split[0] << 32) | (split[1] & 0xFFFFFFFFL)));
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public long pack() {
        long converted = (long) (Math.abs(value) * Math.pow(10, precision));
        boolean negative = value < 0;
        long packed = 0L;

        packed |= (negative ? 1L : 0L) << (VALUE_BITS + PRECISION_BITS);
        packed |= ((long) precision & ((1L << PRECISION_BITS) - 1)) << VALUE_BITS;
        packed |= converted & ((1L << VALUE_BITS) - 1);

        return packed;
    }

    public static int[] packAndSplit(long packed) {
        return new int[] {(int) (packed >>> 32), (int) packed};
    }
}
