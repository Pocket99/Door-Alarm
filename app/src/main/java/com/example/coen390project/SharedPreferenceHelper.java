package com.example.coen390project;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context)
    {
        sharedPreferences = context.getSharedPreferences("ProfilePreference",
                Context.MODE_PRIVATE );
    }
    public void saveRedTheme(int red){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("themeKey",red );
        editor.commit();
    }
    public int getTheme(){
        return sharedPreferences.getInt("themeKey", 0);
    }
    public void saveBlueTheme(int blue){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("themeKey",blue );
        editor.commit();
    }

    public void saveStartHour(int sh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartHour",sh );
        editor.commit();
    }
    public int getStartHour()
    {
        return sharedPreferences.getInt("StartHour", 0);
    }

    public void saveStartMinute(int sm)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartMinute",sm );
        editor.commit();
    }
    public int getStartMinute()
    {
        return sharedPreferences.getInt("StartMinute", 0);
    }

    public void saveEndHour(int eh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndHour",eh );
        editor.commit();
    }
    public int getEndHour()
    {
        return sharedPreferences.getInt("EndHour", 0);
    }

    public void saveEndMinute(int em)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndMinute",em );
        editor.commit();
    }
    public int getEndMinute()
    {
        return sharedPreferences.getInt("EndMinute", 0);
    }



    public void saveBackStartHour(int sh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartBackHour",sh );
        editor.commit();
    }
    public int getBackStartHour()
    {
        return sharedPreferences.getInt("StartBackHour", 0);
    }

    public void saveBackStartMinute(int sm)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartBackMinute",sm );
        editor.commit();
    }
    public int getBackStartMinute()
    {
        return sharedPreferences.getInt("StartBackMinute", 0);
    }

    public void saveBackEndHour(int eh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndBackHour",eh );
        editor.commit();
    }
    public int getBackEndHour()
    {
        return sharedPreferences.getInt("EndBackHour", 0);
    }

    public void saveBackEndMinute(int em)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndBackMinute",em );
        editor.commit();
    }
    public int getBackEndMinute()
    {
        return sharedPreferences.getInt("EndBackMinute", 0);
    }
    public void saveWinStartHour(int sh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartWinHour",sh );
        editor.commit();
    }
    public int getWinStartHour()
    {
        return sharedPreferences.getInt("StartWinHour", 0);
    }

    public void saveWinStartMinute(int sm)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StartWinMinute",sm );
        editor.commit();
    }
    public int getWinStartMinute()
    {
        return sharedPreferences.getInt("StartWinMinute", 0);
    }

    public void saveWinEndHour(int eh)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndWinHour",eh );
        editor.commit();
    }
    public int getWinEndHour()
    {
        return sharedPreferences.getInt("EndWinHour", 0);
    }

    public void saveWinEndMinute(int em)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EndWinMinute",em );
        editor.commit();
    }
    public int getWinEndMinute()
    {
        return sharedPreferences.getInt("EndWinMinute", 0);
    }


}