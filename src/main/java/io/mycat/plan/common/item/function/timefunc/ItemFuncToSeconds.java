package io.mycat.plan.common.item.function.timefunc;

import io.mycat.plan.common.item.Item;
import io.mycat.plan.common.item.function.ItemFunc;
import io.mycat.plan.common.item.function.primary.ItemIntFunc;
import io.mycat.plan.common.time.MySQLTime;
import io.mycat.plan.common.time.MyTime;

import java.math.BigInteger;
import java.util.List;

public class ItemFuncToSeconds extends ItemIntFunc {

    public ItemFuncToSeconds(List<Item> args) {
        super(args);
    }

    @Override
    public final String funcName() {
        return "to_seconds";
    }

    @Override
    public BigInteger valInt() {
        MySQLTime ltime = new MySQLTime();
        if (getArg0Date(ltime, MyTime.TIME_NO_ZERO_DATE))
            return BigInteger.ZERO;
        long seconds = ltime.hour * 3600L + ltime.minute * 60 + ltime.second;
        seconds = ltime.neg ? -seconds : seconds;
        long days = MyTime.calcDaynr(ltime.year, ltime.month, ltime.day);
        return BigInteger.valueOf(seconds + days * 24L * 3600L);
    }

    @Override
    public void fixLengthAndDec() {
        maxLength = 6;
        maybeNull = true;
    }

    @Override
    public ItemFunc nativeConstruct(List<Item> realArgs) {
        return new ItemFuncToSeconds(realArgs);
    }

}
