package util;

import localsearch.BaseStretegy;

/**
 * Created by ab792 on 2017/3/2.
 */
@FunctionalInterface
public interface ClassCreaterUtil<T extends BaseStretegy> {
    T create();
}
