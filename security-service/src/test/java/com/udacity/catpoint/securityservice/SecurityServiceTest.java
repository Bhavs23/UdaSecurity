package com.udacity.catpoint.securityservice;

import com.udacity.catpoint.data.*;
import com.udacity.catpoint.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SecurityService.
 * Tests all 11 application requirements.
 */
@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private SecurityRepository securityRepository;

    @Mock
    private ImageService imageService;

    private SecurityService securityService;

    private Sensor sensor;

    @BeforeEach
    void setUp() {
        securityService = new SecurityService(securityRepository, imageService);
        sensor = new Sensor("Test Sensor", SensorType.DOOR);
    }

    // Requirement 1: If alarm is armed and a sensor becomes activated, put system into pending alarm status
    @Test
    void changeSensorActivationStatus_alarmArmedAndSensorActivated_statusPending() {
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
        sensor.setActive(false);

        securityService.changeSensorActivationStatus(sensor, true);

        verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);
    }

    // Requirement 2: If alarm is armed and sensor activated and system already pending, set alarm status to alarm
    @Test
    void changeSensorActivationStatus_alarmArmedAndSensorActivatedAndPending_statusAlarm() {
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        sensor.setActive(false);

        securityService.changeSensorActivationStatus(sensor, true);

        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    // Requirement 3: If pending alarm and all sensors inactive, return to no alarm state
    @Test
    void changeSensorActivationStatus_pendingAlarmAndAllSensorsInactive_statusNoAlarm() {
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        sensor.setActive(true);

        securityService.changeSensorActivationStatus(sensor, false);

        verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    // Requirement 4: If alarm is active, change in sensor state should not affect alarm state
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void changeSensorActivationStatus_alarmActive_noChangeToAlarmState(boolean active) {
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.ALARM);
        sensor.setActive(!active);

        securityService.changeSensorActivationStatus(sensor, active);

        verify(securityRepository, never()).setAlarmStatus(any(AlarmStatus.class));
    }

    // Requirement 5: If sensor activated while already active and system pending, change to alarm
    @Test
    void changeSensorActivationStatus_sensorActivatedWhileActiveAndPending_statusAlarm() {
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        sensor.setActive(false);

        securityService.changeSensorActivationStatus(sensor, true);

        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    // Requirement 6: If sensor deactivated while already inactive, make no changes to alarm state
    @Test
    void changeSensorActivationStatus_sensorDeactivatedWhileInactive_noChangeToAlarmState() {
        sensor.setActive(false);

        securityService.changeSensorActivationStatus(sensor, false);

        verify(securityRepository, never()).setAlarmStatus(any(AlarmStatus.class));
    }

    // Requirement 7: If image service identifies cat while system armed-home, put system into alarm
    @Test
    void processImage_catDetectedWhileArmedHome_statusAlarm() {
        when(imageService.imageContainsCat(any(BufferedImage.class), anyFloat())).thenReturn(true);
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        securityService.processImage(testImage);

        verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    // Requirement 8: If image service identifies no cat, change status to no alarm as long as sensors not active
    @Test
    void processImage_noCatDetected_statusNoAlarm() {
        when(imageService.imageContainsCat(any(BufferedImage.class), anyFloat())).thenReturn(false);
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        securityService.processImage(testImage);

        verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    // Requirement 9: If system is disarmed, set status to no alarm
    @Test
    void setArmingStatus_systemDisarmed_statusNoAlarm() {
        securityService.setArmingStatus(ArmingStatus.DISARMED);

        verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    // Requirement 10: If system is armed, reset all sensors to inactive
    @ParameterizedTest
    @EnumSource(value = ArmingStatus.class, names = {"ARMED_HOME", "ARMED_AWAY"})
    void setArmingStatus_systemArmed_allSensorsInactive(ArmingStatus armingStatus) {
        Set<Sensor> sensors = new HashSet<>();
        Sensor sensor1 = new Sensor("Sensor1", SensorType.DOOR);
        Sensor sensor2 = new Sensor("Sensor2", SensorType.WINDOW);
        sensor1.setActive(true);
        sensor2.setActive(true);
        sensors.add(sensor1);
        sensors.add(sensor2);

        when(securityRepository.getSensors()).thenReturn(sensors);

        securityService.setArmingStatus(armingStatus);

        assertFalse(sensor1.getActive());
        assertFalse(sensor2.getActive());
    }

    // Requirement 11: If system armed-home while camera shows cat, set alarm status to alarm
    @Test
    void processImage_catDetectedThenArmedHome_statusAlarm() {
        when(imageService.imageContainsCat(any(BufferedImage.class), anyFloat())).thenReturn(true);
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        // First detect cat (system can be in any state)
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.DISARMED);
        securityService.processImage(testImage);

        // Then arm home
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED_HOME);
        securityService.processImage(testImage);

        verify(securityRepository, atLeastOnce()).setAlarmStatus(AlarmStatus.ALARM);
    }
}