/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               Home page: http://www.omegat.org/omegat/omegat.html
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

package org.omegat.core.threads;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.omegat.util.OStrings;
import org.omegat.util.StaticUtils;

/**
 * An independent stream to save project,
 * created in order not to freese UI while project is saved (may take a lot)
 *
 * @author Keith Godfrey
 */
class SaveThread extends Thread
{
    public SaveThread()
    {
        setName("Save thread");	// NOI18N
    }
    
    public void run()
    {
        try
        {
            sleep(SAVE_DURATION);
        }
        catch (InterruptedException e2)
        {
            interrupt();
        }
        
        while( !interrupted() )
        {
            synchronized(CommandThread.core)
            {
                if( CommandThread.core.isProjectModified() )
                {
                    CommandThread.core.save();
                    CommandThread.core.m_transFrame.setMessageText(
                            StaticUtils.format(
                                    OStrings.getString("ST_PROJECT_AUTOSAVED"),
                                    new Object[]
                                    {
                                        DateFormat.getTimeInstance(DateFormat.SHORT).
                                        format(new Date())
                                    } ));
                }
            }
            
            try
            {
                sleep(SAVE_DURATION);
            }
            catch (InterruptedException e)
            {
                interrupt();
            }
        }
    }
    
    private static final int SAVE_DURATION = 10 * 60 * 1000;  // 10 minutes;
}
