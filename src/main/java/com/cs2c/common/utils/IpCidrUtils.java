package com.cs2c.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpCidrUtils {
    public static final IpCidrUtils INSTANCE = new IpCidrUtils();
    private IpCidrUtils(){}

    private static final Pattern addressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");

    public Integer cidrToInteger(String cidrAddress) {
        return this.pop(toInteger(cidrAddress));
    }

    public String integerToCidr(int integer) {
        int netmask = 0;
        for(int j = 0; j < integer; ++j) {
            netmask |= 1 << 31 - j;
        }
        return this.format(toArray(netmask));
    }

    private int pop(int x) {
        x -= x >>> 1 & 1431655765;
        x = (x & 858993459) + (x >>> 2 & 858993459);
        x = x + (x >>> 4) & 252645135;
        x += x >>> 8;
        x += x >>> 16;
        return x & 63;
    }

    private String format(int[] octets) {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < octets.length; ++i) {
            str.append(octets[i]);
            if (i != octets.length - 1) {
                str.append(".");
            }
        }

        return str.toString();
    }

    private int[] toArray(int val) {
        int[] ret = new int[4];

        for(int j = 3; j >= 0; --j) {
            ret[j] |= val >>> 8 * (3 - j) & 255;
        }

        return ret;
    }

    private int toInteger(String address) {
        Matcher matcher = addressPattern.matcher(address);
        if (matcher.matches()) {
            return matchAddress(matcher);
        } else {
            throw new IllegalArgumentException("Could not parse [" + address + "]");
        }
    }

    private int matchAddress(Matcher matcher) {
        int addr = 0;

        for(int i = 1; i <= 4; ++i) {
            int n = rangeCheck(Integer.parseInt(matcher.group(i)), 0, 255);
            addr |= (n & 255) << 8 * (4 - i);
        }

        return addr;
    }

    private int rangeCheck(int value, int begin, int end) {
        if (value >= begin && value <= end) {
            return value;
        } else {
            throw new IllegalArgumentException("Value [" + value + "] not in range [" + begin + "," + end + "]");
        }
    }
}
