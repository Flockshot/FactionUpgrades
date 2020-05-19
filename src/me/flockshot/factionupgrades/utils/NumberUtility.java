package me.flockshot.factionupgrades.utils;

import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NumberUtility
{
	public static boolean isNum(String str)
	{
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	}
	   
    public boolean isNumMath(String str)
    {
        str = str.replaceAll("+", "");
        
        str = str.replaceAll("-", "");
        str = str.replaceAll("/", "");
        str = str.replaceAll("*", "");
        str = str.replaceAll("%", ""); 
        
        return isNum(str);
    }

    public Object applyMath(String string)
    {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        try {
            return engine.eval(string);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return string;
        
    }
}
