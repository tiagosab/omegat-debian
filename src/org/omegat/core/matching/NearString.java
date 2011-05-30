/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
**************************************************************************/

package org.omegat.core.matching;

import org.omegat.core.data.StringEntry;

/**
 * Class to hold a single fuzzy match.
 *
 * @author Keith Godfrey
 */
public class NearString implements Comparable<NearString>
{
    public NearString(StringEntry strEntry,
            int nearScore,
            int nearScoreNoStem,
            int adjustedScore,
            byte[] nearData,
            String projName)
    {
        str = strEntry;
        score = nearScore;
        scoreNoStem = nearScoreNoStem;
        this.adjustedScore = adjustedScore;
        attr = nearData;
        if (projName != null)
            proj = projName;
    }
    
    public int compareTo(NearString o)
    {
        if (o.score == score)
            return (o.adjustedScore < adjustedScore) ? -1 : 1;
        else
            return (o.score < score) ? -1 : 1;
    }
    
    public StringEntry str;
    public int score, scoreNoStem; // similarity score for match without non-word tokens
    public int adjustedScore; // adjusted similarity score for match including all tokens
    public byte[] attr;	// matching attributes of near strEntry
    public String proj = ""; // NOI18N
}
