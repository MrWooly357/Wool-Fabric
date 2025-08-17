package net.mrwooly357.wool.util.data;

import java.util.ArrayList;
import java.util.List;

public final class UnifiedData {

    private final List<Data<?>> dataList;

    private UnifiedData(Data<?>... data) {
        dataList = List.of(data);
    }


    public static UnifiedData of(Data<?>... data) {
        return new UnifiedData(data);
    }

    public static UnifiedData union(UnifiedData... unifiedData) {
        List<Data<?>> dataList = new ArrayList<>();

        for (UnifiedData unifiedData1 : unifiedData)
            dataList.addAll(unifiedData1.dataList);

        return of(dataList.toArray(new Data[0]));
    }

    public Data<?> get(int index) {
        return dataList.get(index);
    }

    public void set(int index, Data<?> data) {
        dataList.set(index, data);
    }

    public List<Data<?>> getAll() {
        return List.copyOf(dataList);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("UnifiedData[");

        for (Data<?> data : dataList)
            builder.append(data);

        builder.append("]");

        return builder.toString();
    }
}
