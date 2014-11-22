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
package owen.util.math.real;

import java.math.BigInteger;
import java.util.Objects;
import owen.util.math.DenominatorCannotBeZeroException;
import owen.util.math.DivideZeroException;

/**
 *
 * @author Owen
 */
public class Fraction implements Expression, Comparable<Fraction> {

    public Fraction() {
        this(null, null);
    }

    public Fraction(long num) {
        this(BigInteger.valueOf(num), BigInteger.ZERO);
    }

    public Fraction(long num, long den) {
        this(BigInteger.valueOf(num), BigInteger.valueOf(den));
    }

    public Fraction(BigInteger num) {
        this(num, BigInteger.ONE);
    }

    public Fraction(BigInteger num, BigInteger den) {
        this(null, num, den);
    }

    public Fraction(Signum s, BigInteger num, BigInteger den) {
        if (s != null && s.is0) {
            signum = Signum.ZERO;
            numerator = BigInteger.ZERO;
            denominator = BigInteger.ONE;
        } else {
            BigInteger _num, _den;
            Signum _signum = Signum.ZERO;
            if (num == null)
                _num = BigInteger.ZERO;
            else if (num.signum() == -1) {
                _num = num.negate();
                _signum = Signum.NEGATIVE;
            } else if (num.signum() == 0) {
                _num = num;
                _signum = Signum.ZERO;
            } else {
                _num = num;
                _signum = Signum.POSITIVE;
            }
            if (den == null)
                _den = BigInteger.ONE;
            else if (den.signum() == -1) {
                _den = den.negate();
                _signum = _signum.negate();
            } else if (den.signum() == 0)
                throw new DenominatorCannotBeZeroException();
            else
                _den = den;
            if (s == null)
                signum = _signum;
            else if (s.greaterThan0)
                signum = _signum;
            else
                signum = _signum.negate();
            BigInteger gcd = _num.gcd(_den);
            numerator = _num.divide(gcd);
            denominator = _den.divide(gcd);
        }
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public Fraction add(Fraction f) {
        Objects.requireNonNull(f);
        if (signum.is0 || f.signum.is0)
            return ZERO;
        if (signum.greaterThan0)
            if (f.signum.greaterThan0)
                return new Fraction(numerator.multiply(f.denominator)
                        .add(f.numerator.multiply(denominator)),
                        denominator.multiply(f.denominator));
            else
                return new Fraction(numerator.multiply(f.denominator)
                        .subtract(f.numerator.multiply(denominator)),
                        denominator.multiply(f.denominator));
        else if (f.signum.greaterThan0)
            return new Fraction(f.numerator.multiply(denominator)
                    .subtract(numerator.multiply(f.denominator)),
                    denominator.multiply(f.denominator));
        else
            return new Fraction(Signum.NEGATIVE,
                    numerator.multiply(f.denominator)
                    .add(f.numerator.multiply(denominator)),
                    denominator.multiply(f.denominator));
    }

    @Override
    public Fraction add(BigInteger i) {
        Objects.requireNonNull(i);
        if (i.equals(BigInteger.ZERO))
            return this;
        switch (signum) {
            case POSITIVE:
                return new Fraction(i.multiply(denominator)
                        .add(numerator), denominator);
            case ZERO:
                return new Fraction(i, BigInteger.ONE);
            case NEGATIVE:
                return new Fraction(Signum.NEGATIVE,
                        i.multiply(denominator)
                        .subtract(numerator), denominator);
            default:
                return null;
        }
    }

    public Fraction add(long l) {
        if (l == 0L)
            return this;
        return add(BigInteger.valueOf(l));
    }

    public Fraction subtract(Fraction f) {
        Objects.requireNonNull(f);
        if (signum.is0 || f.signum.is0)
            return ZERO;
        if (signum.greaterThan0)
            if (f.signum.greaterThan0)
                return new Fraction(numerator.multiply(f.denominator)
                        .subtract(f.numerator.multiply(denominator)),
                        denominator.multiply(f.denominator));
            else
                return new Fraction(numerator.multiply(f.denominator)
                        .add(f.numerator.multiply(denominator)),
                        denominator.multiply(f.denominator));
        else if (f.signum.greaterThan0)
            return new Fraction(Signum.NEGATIVE,
                    f.numerator.multiply(denominator)
                    .add(numerator.multiply(f.denominator)),
                    denominator.multiply(f.denominator));
        else
            return new Fraction(f.numerator.multiply(denominator)
                    .add(numerator.multiply(f.denominator)),
                    denominator.multiply(f.denominator));
    }

    @Override
    public Fraction subtract(BigInteger i) {
        Objects.requireNonNull(i);
        if (i.equals(BigInteger.ZERO))
            return this;
        switch (signum) {
            case POSITIVE:
                return new Fraction(numerator
                        .subtract(i.multiply(denominator)), denominator);
            case ZERO:
                return new Fraction(i.negate(), BigInteger.ONE);
            case NEGATIVE:
                return new Fraction(Signum.NEGATIVE,
                        i.multiply(denominator).add(numerator), denominator);
            default:
                return null;
        }
    }

    public Fraction subtract(long l) {
        if (l == 0L)
            return this;
        return subtract(BigInteger.valueOf(l));
    }

    public Fraction multiply(Fraction f) {
        Objects.requireNonNull(f);
        if (signum.is0 || f.signum.is0)
            return ZERO;
        return new Fraction(signum.and(f.signum),
                numerator.multiply(f.numerator),
                denominator.multiply(f.denominator));
    }

    @Override
    public Fraction multiply(BigInteger i) {
        Objects.requireNonNull(i);
        if (signum.is0 || i.equals(BigInteger.ZERO))
            return ZERO;
        return new Fraction(signum.and(i.signum()),
                numerator.multiply(i.abs()), denominator);
    }

    public Fraction multiply(long l) {
        if (l == 0L)
            return ZERO;
        return multiply(BigInteger.valueOf(l));
    }

    public boolean isInfinite() {
        return false;
    }

    public boolean isInfinitesimal() {
        return false;
    }

    @Override
    public int compareTo(Fraction f) {
        if (f == null)
            throw new NullPointerException("The fraction is null.");
        if (signum.greaterThan0)
            if (f.signum.greaterThanOrIs0) {
                BigInteger lcm = denominator.gcd(f.denominator);
                return numerator.multiply(lcm)
                        .compareTo(f.denominator.multiply(lcm));
            } else
                return -1;
        else if (f.signum.greaterThanOrIs0)
            return 1;
        else {
            BigInteger lcm = denominator.gcd(f.denominator);
            return numerator.negate().multiply(lcm)
                    .compareTo(f.denominator.negate().multiply(lcm));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != getClass())
            return false;
        return compareTo((Fraction) obj) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + signum.hashCode();
        hash = 79 * hash + numerator.hashCode();
        hash = 79 * hash + denominator.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
    private final Signum signum;
    private final BigInteger numerator, denominator;

    @Override
    public Fraction abs() {
        return new Fraction(numerator, denominator);
    }

    @Override
    public Fraction negate() {
        return new Fraction(signum.negate(), numerator, denominator);
    }

    @Override
    public Fraction reciprocal() {
        if (equalsZero())
            throw new DivideZeroException();
        return new Fraction(signum, denominator, numerator);
    }

    @Override
    public Expression add(Expression e) {
        Objects.requireNonNull(e);
        if (this.equalsZero())
            return e;
        if (e.getClass() == Fraction.class)
            return add((Fraction) e);
        return e.add(this);
    }

    @Override
    public Expression subtract(Expression e) {
        Objects.requireNonNull(e);
        if (this.equalsZero())
            return e.negate();
        if (e.getClass() == Fraction.class)
            return subtract((Fraction) e);
        return e.add(this).negate();
    }

    @Override
    public Expression multiply(Expression e) {
        Objects.requireNonNull(e);
        if (this.equalsZero())
            return ZERO;
        if (e.getClass() == Fraction.class)
            return multiply((Fraction) e);
        return e.multiply(this);
    }

    @Override
    public Expression divide(Expression e) {
        Objects.requireNonNull(e);
        if (e.equalsZero())
            throw new DivideZeroException();
        if (this.equalsZero())
            return ZERO;
        if (e.getClass() == Fraction.class)
            return divide((Fraction) e);
        return e.reciprocal().multiply(this);
    }

    public Fraction divide(Fraction f) {
        Objects.requireNonNull(f);
        if (f.equalsZero())
            throw new DivideZeroException();
        if (equalsZero())
            return ZERO;
        return new Fraction(numerator.multiply(f.denominator),
                denominator.multiply(numerator));
    }

    @Override
    public Expression divide(BigInteger i) {
        Objects.requireNonNull(i);
        return new Fraction(numerator, denominator.multiply(i));
    }

    @Override
    public Expression pow(int i) {
        return new Fraction((i & 1) == 1 ? signum : Signum.POSITIVE,
                numerator.pow(i), denominator.pow(i));
    }

    @Override
    public Expression pow(BigInteger i) {
        Objects.requireNonNull(i);
    }

    @Override
    public Expression root(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression root(BigInteger i) {
        Objects.requireNonNull(i);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression cqrt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigInteger unknownCount() {
        return BigInteger.ZERO;
    }

    @Override
    public Expression maxExponent() {
        return ZERO;
    }

    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction TEN = new Fraction(10, 1);
    public static final Fraction INFINITESIMAL = new Fraction(0, 1) {
        @Override
        public boolean isInfinitesimal() {
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || obj instanceof Fraction)
                return false;
            return ((Fraction) obj).isInfinitesimal();
        }

        @Override
        public int hashCode() {
            return Integer.MIN_VALUE;
        }

        @Override
        public int compareTo(Fraction f) {
            if (equals(f))
                return 0;
            return -1;
        }
    };
    public static final Fraction INFINITY = new Fraction(0, 1) {
        @Override
        public boolean isInfinite() {
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || obj instanceof Fraction)
                return false;
            return ((Fraction) obj).isInfinite();
        }

        @Override
        public int hashCode() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int compareTo(Fraction f) {
            if (equals(f))
                return 0;
            return 1;
        }
    };

    @Override
    public boolean equalsZero() {
        return signum.is0;
    }

    @Override
    public boolean isCalculated() {
        return true;
    }

    public static enum Signum {

        POSITIVE(-1), ZERO(0), NEGATIVE(1);

        private Signum(int hash) {
            this.hash = hash;
            this.greaterThan0 = hash == 1;
            this.greaterThanOrIs0 = hash >= 0;
            this.is0 = hash == 0;
            this.isNot0 = hash != 0;
            this.lessThanOrIs0 = hash <= 0;
            this.lessThan0 = hash == -1;
        }

        public int hash() {
            return hash;
        }

        public boolean greaterThanZero() {
            return greaterThan0;
        }

        public boolean greaterThanOrIsZero() {
            return greaterThanOrIs0;
        }

        public boolean isZero() {
            return is0;
        }

        public boolean isNotZero() {
            return isNot0;
        }

        public boolean lessThanOrIsZero() {
            return lessThanOrIs0;
        }

        public boolean lessThanZero() {
            return lessThan0;
        }

        public Signum negate() {
            switch (hash) {
                case -1:
                    return POSITIVE;
                case 0:
                    return ZERO;
                case 1:
                    return NEGATIVE;
                default:
                    return null;
            }
        }

        public Signum and(int s) {
            int h = hash * s;
            switch (h) {
                case 1:
                    return Signum.POSITIVE;
                case 0:
                    return Signum.ZERO;
                case -1:
                    return Signum.NEGATIVE;
                default:
                    return null;
            }
        }

        public Signum and(Signum s) {
            return and(s.hash);
        }
        private final int hash;
        private final boolean greaterThan0, greaterThanOrIs0,
                is0, isNot0, lessThanOrIs0, lessThan0;
    }

    public static Fraction valueOf(double d) {
        String dStr = String.valueOf(d);
        int dot = dStr.indexOf('.');
        if (dot == -1)
            return new Fraction(BigInteger.valueOf((long) d));
        else {
            String f = dStr.substring(0, dot);
            String l = dStr.substring(dot);
            int ll = l.length();
            if (ll <= 18)
                return new Fraction(Long.valueOf(f), (ll + 1) * 10)
                        .add(Long.valueOf(f));
            else
                return new Fraction(Long.valueOf(f), (ll + 1) * 10)
                        .add(new BigInteger(f, 10));
        }
    }
}
