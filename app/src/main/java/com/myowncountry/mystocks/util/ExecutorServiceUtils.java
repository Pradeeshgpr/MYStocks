package com.myowncountry.mystocks.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtils {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ExecutorServiceUtils() {

    }

    public static final ExecutorService getInstance() {
        return executorService;
    }


}
