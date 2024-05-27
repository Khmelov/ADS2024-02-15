package by.it.group310901.baradzin.lesson06;

interface Lambda3<T, U, V, R> {
    R apply(T t, U u, V v);
}

class SeqCheck {
    private final Lambda3<Integer[], Integer, Integer, Boolean> checker;

    SeqCheck(Lambda3<Integer[], Integer, Integer, Boolean> checker) {
        this.checker = checker;
    }

    public int getLength(Integer[] arr) {
        return getLength(arr, -1, 0);
    }

    int getLength(Integer[] arr, int current, int next) {
        if (next == arr.length) return 0;
        var includes = 0;
        if (current == -1 || checker.apply(arr, current, next))
            includes = 1 + getLength(arr, next, next + 1);
        var excludes = getLength(arr, current, next + 1);
        return Math.max(includes, excludes);
    }
}
