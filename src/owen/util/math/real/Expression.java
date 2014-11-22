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

/**
 *
 * @author Owen
 */
public interface Expression {

    Expression abs();

    Expression negate();

    Expression reciprocal();

    Expression add(Expression e);

    Expression add(BigInteger i);

    Expression subtract(Expression e);

    Expression subtract(BigInteger i);

    Expression multiply(Expression e);

    Expression multiply(BigInteger i);

    Expression divide(Expression e);

    Expression divide(BigInteger i);

    Expression pow(int i);

    Expression pow(BigInteger i);

    default Expression pow2() {
        return pow(2);
    }

    Expression root(int i);

    Expression root(BigInteger i);

    Expression sqrt();

    Expression cqrt();

    BigInteger unknownCount();

    Expression maxExponent();

    boolean equalsZero();

    boolean isCalculated();

    @Override
    String toString();

    Expression ZERO = new Expression() {

        @Override
        public Expression abs() {
            return this;
        }

        @Override
        public Expression negate() {
            return this;
        }

        @Override
        public Expression add(Expression e) {
            return e;
        }

        @Override
        public Expression add(BigInteger i) {
            return new Fraction(i, BigInteger.ONE);
        }

        @Override
        public Expression subtract(Expression e) {
            return e.subtract(e).subtract(e);
        }

        @Override
        public Expression subtract(BigInteger i) {
            return new Fraction(Fraction.Signum.NEGATIVE, i, BigInteger.ONE);
        }

        @Override
        public Expression multiply(Expression e) {
            return this;
        }

        @Override
        public Expression multiply(BigInteger i) {
            return this;
        }

        @Override
        public Expression divide(Expression e) {
            return this;
        }

        @Override
        public Expression divide(BigInteger i) {
            return this;
        }

        @Override
        public Expression pow(int i) {
            return this;
        }

        @Override
        public Expression pow(BigInteger i) {
            return this;
        }

        @Override
        public Expression root(int i) {
            return this;
        }

        @Override
        public Expression root(BigInteger i) {
            return this;
        }

        @Override
        public Expression sqrt() {
            return this;
        }

        @Override
        public Expression cqrt() {
            return this;
        }

        @Override
        public BigInteger unknownCount() {
            return BigInteger.ZERO;
        }

        @Override
        public Expression maxExponent() {
            return this;
        }

        @Override
        public boolean equalsZero() {
            return true;
        }

        @Override
        public boolean isCalculated() {
            return true;
        }

        @Override
        public String toString() {
            return "0";
        }
    };
}
