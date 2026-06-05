package com.friedfish.uncompleted_way_spellbook.misc;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;

public class LogFlags {
    public static final boolean MAGIC_CIRCLE_DEBUG=true;
    public static final boolean SPELL_DEBUG=true;
    public static boolean onceLog=false;
    public static void onceLog(String msg){
        if(!onceLog) {
            onceLog = true;
            UncompletedWaySpellbook.LOGGER.debug(msg);
        }
    }
    public static void spellDebugLog(String msg){
        if(SPELL_DEBUG){
            UncompletedWaySpellbook.LOGGER.debug(msg);
        }
    }
}
