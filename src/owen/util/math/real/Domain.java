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

import java.util.Objects;

/**
 *
 * @author Owen
 */
public final class Domain {

    public Domain(Fraction min, boolean includingMin,
            Fraction max, boolean includingMax) {
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);
        int ct = min.compareTo(max);
        if (includingMin && min)
        infinity = max.isInfinite();
    }
    private final boolean infinity, infinitesimal;
    private final Fraction min, max;
    private final boolean includingMin, includingMax;
}
