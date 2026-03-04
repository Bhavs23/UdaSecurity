package com.udacity.catpoint.data;

import java.util.Set;

public interface SecurityRepository {

    void addSensor(Sensor sensor);

    void removeSensor(Sensor sensor);

    void updateSensor(Sensor sensor);

    Set<Sensor> getSensors();

    AlarmStatus getAlarmStatus();

    void setAlarmStatus(AlarmStatus status);

    ArmingStatus getArmingStatus();

    void setArmingStatus(ArmingStatus status);
}
