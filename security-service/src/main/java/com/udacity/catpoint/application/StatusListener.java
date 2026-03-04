package com.udacity.catpoint.application;

import com.udacity.catpoint.data.AlarmStatus;

/**
 * Interface for components that should be notified when system status changes.
 */
public interface StatusListener {
    void notify(AlarmStatus status);
    void catDetected(boolean catDetected);
    void sensorStatusChanged();
}