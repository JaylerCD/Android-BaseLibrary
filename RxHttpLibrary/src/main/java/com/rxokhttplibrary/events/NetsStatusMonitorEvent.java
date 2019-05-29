package com.rxokhttplibrary.events;

/**
 * @author mac
 */
public interface NetsStatusMonitorEvent {
     void onNetChange(boolean netStatus);
}

