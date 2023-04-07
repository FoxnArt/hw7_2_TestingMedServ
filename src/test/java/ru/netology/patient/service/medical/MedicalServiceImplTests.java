package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTests {
    @Test
    void test_sending_message_during_checkBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1c0f854d-fc80-49bb-81fe-7b1b0efe2481"))
                .thenReturn(new PatientInfo("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", new BloodPressure(110, 80));

        Mockito.verify(alertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    void test_not_sending_message_during_checkBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1c0f854d-fc80-49bb-81fe-7b1b0efe2481"))
                .thenReturn(new PatientInfo("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", new BloodPressure(120, 80));

        Mockito.verify(alertService, Mockito.times(0)).send(Mockito.anyString());
    }

    @Test
    void test_message_interception_during_checkBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1c0f854d-fc80-49bb-81fe-7b1b0efe2481"))
                .thenReturn(new PatientInfo("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", new BloodPressure(110, 80));

        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 1c0f854d-fc80-49bb-81fe-7b1b0efe2481, need help", argumentCaptor.getValue());
    }

    @Test
    void test_sending_message_during_checkTemperature() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1c0f854d-fc80-49bb-81fe-7b1b0efe2481"))
                .thenReturn(new PatientInfo("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", new BigDecimal("35.0"));

        Mockito.verify(alertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    void test_not_sending_message_during_checkTemperature() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1c0f854d-fc80-49bb-81fe-7b1b0efe2481"))
                .thenReturn(new PatientInfo("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1c0f854d-fc80-49bb-81fe-7b1b0efe2481", new BigDecimal("36.6"));

        Mockito.verify(alertService, Mockito.times(0)).send(Mockito.anyString());
    }
}
