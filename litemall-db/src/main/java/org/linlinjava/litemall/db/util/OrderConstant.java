package org.linlinjava.litemall.db.util;

import org.springframework.web.bind.annotation.PathVariable;

public class OrderConstant {
    public static final Integer NONE = 0;
    public static final Integer EXPRESS = 1;
    public static final Integer DELIVERY = 2;
    public static final Integer SELF_PICKUP = 3;

    public static final Integer PROGRESS = 1;
    public static final Integer FINISHED = 2;
}
