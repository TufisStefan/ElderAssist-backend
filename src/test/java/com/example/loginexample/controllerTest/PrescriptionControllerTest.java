package com.example.loginexample.controllerTest;

import com.example.loginexample.controller.PrescriptionController;
import com.example.loginexample.model.Medicament;
import com.example.loginexample.model.Prescription;
import com.example.loginexample.payload.request.MedicamentRequest;
import com.example.loginexample.repository.TimeOfDayRepository;
import com.example.loginexample.service.MedicamentService;
import com.example.loginexample.service.PrescriptionService;
import com.example.loginexample.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class PrescriptionControllerTest {

    JacksonTester<MedicamentRequest> requestJson;
    private MockMvc mockMvc;
    @Mock
    private MedicamentService medicamentService;
    @Mock
    private PrescriptionService prescriptionService;
    @Mock
    private UserService userService;
    @Mock
    private TimeOfDayRepository timeOfDayRepository;

    @InjectMocks
    private PrescriptionController prescriptionController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(prescriptionController)
                .build();
    }

    @Test
    @WithMockUser
    void getPrescriptionForPatient_WhenGivenValidPatient_ReturnOkResponse() throws Exception {
        String username = "user";
        when(prescriptionService.getPrescriptionForUsername(any(String.class))).thenReturn(new Prescription());
        MockHttpServletResponse response = mockMvc.perform(
                        get("/prescription/get/")
                                .param("username", username))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser
    void getPrescriptionForPatient_WhenGivenPatientWithoutPrescription_ReturnBadRequest() throws Exception {
        String username = "user";
        when(prescriptionService.getPrescriptionForUsername(any(String.class))).thenReturn(null);
        MockHttpServletResponse response = mockMvc.perform(
                        get("/prescription/get/")
                                .param("username", username))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("No prescription assigned.", response.getContentAsString());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void getMedicaments_WhenCalled_ReturnAllMedicaments() throws Exception {
        when(medicamentService.getAllMedicaments()).thenReturn(new ArrayList<Medicament>());
        MockHttpServletResponse response = mockMvc.perform(
                        get("/prescription/medicament/get-all"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
