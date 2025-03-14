package org.fp.constant;

public final class Constants {
    // При корректировке значений должны соблюдаться следующие ограничения:
    // 1) MIN_HEIGHT * MIN_LENGTH >= MIN_CAPACITY
    // 2) (MAX_HEIGHT-1) * (MAX_LENGTH-1) >= MAX_CAPACITY-1

    public static final int RECTANGULAR_AQUARIUM_MIN_HEIGHT = 30;
    public static final int RECTANGULAR_AQUARIUM_MAX_HEIGHT = 50;

    public static final int RECTANGULAR_AQUARIUM_MIN_LENGTH = 40;
    public static final int RECTANGULAR_AQUARIUM_MAX_LENGTH = 60;

    public static final int RECTANGULAR_AQUARIUM_MIN_CAPACITY = 1200;
    public static final int RECTANGULAR_AQUARIUM_MAX_CAPACITY = 2880;

    private Constants(){}
}