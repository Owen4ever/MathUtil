/*
 * Copyright (C) 2014 Owen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package owen.util.math;

import java.math.BigInteger;

/**
 *
 * @author Owen
 */
public final class MathUtil {

    private MathUtil() {
    }

    public static int gcd(int a, int b) {
        int r;
        if (a < b) {
            r = b;
            b = a;
            a = r;
        }
        do {
            r = a % b;
            a = b;
            b = r;
        } while (r != 0);
        return a;
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        long r;
        if (a < b) {
            r = b;
            b = a;
            a = r;
        }
        do {
            r = a % b;
            a = b;
            b = r;
        } while (r != 0);
        return a;
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(gcd(a, b));
    }
}
