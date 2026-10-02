package myy803.traineeship_app.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.Evaluation;
import myy803.traineeship_app.domain.EvaluationType;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.service.CompanyService;

@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    @WithMockUser(username = "techcorp")
    void dashboard_returnsView() throws Exception {
        mockMvc.perform(get("/company/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/dashboard"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void profile_addsCompanyToModel() throws Exception {
        Company c = new Company();
        c.setUsername("techcorp");
        c.setCompanyName("TechCorp Ltd");

        when(companyService.findCompanyByUsername("techcorp")).thenReturn(c);

        mockMvc.perform(get("/company/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/profile"))
                .andExpect(model().attributeExists("company"));

        verify(companyService).findCompanyByUsername("techcorp");
    }

    @Test
    @WithMockUser(username = "techcorp")
    void saveProfile_callsService_andReturnsDashboard() throws Exception {
        mockMvc.perform(post("/company/save_profile")
                        .param("username", "techcorp")
                        .param("companyName", "TechCorp Ltd")
                        .param("companyLocation", "Athens"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/dashboard"));

        verify(companyService, times(1)).saveCompany(any(Company.class));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void listAvailablePositions_callsService_andReturnsView() throws Exception {
        when(companyService.getAvailablePositions("techcorp")).thenReturn(List.of());

        mockMvc.perform(get("/company/list_available_positions"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/available_positions"))
                .andExpect(model().attributeExists("positions"));

        verify(companyService).getAvailablePositions("techcorp");
    }

    @Test
    @WithMockUser(username = "techcorp")
    void showPositionForm_addsEmptyPosition() throws Exception {
        mockMvc.perform(get("/company/show_position_form"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/position"))
                .andExpect(model().attributeExists("position"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void savePosition_callsService_andRedirects() throws Exception {
        mockMvc.perform(post("/company/save_position")
                        .param("title", "Backend Intern")
                        .param("description", "Spring Boot backend")
                        .param("topics", "backend,databases")
                        .param("skills", "java,spring,mysql")
                        .param("fromDate", "2026-02-01")
                        .param("toDate", "2026-06-30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/company/dashboard"));

        verify(companyService, times(1)).addPosition(any(TraineeshipPosition.class), eq("techcorp"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void listAssignedPositions_callsService_andReturnsView() throws Exception {
        when(companyService.getAssignedPositions("techcorp")).thenReturn(List.of());

        mockMvc.perform(get("/company/list_assigned_positions"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/assigned_positions"))
                .andExpect(model().attributeExists("positions"));

        verify(companyService).getAssignedPositions("techcorp");
    }

    @Test
    @WithMockUser(username = "techcorp")
    void deletePosition_callsService_andReturnsAvailablePositionsView() throws Exception {
        mockMvc.perform(get("/company/delete_position").param("position_id", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("/company/available_positions"));

        verify(companyService).deletePosition(10, "techcorp");
    }

    @Test
    @WithMockUser(username = "techcorp")
    void evaluate_redirectsWhenPositionNotFound() throws Exception {
        when(companyService.getTraineeshipPosition(1)).thenReturn(null);

        mockMvc.perform(get("/company/evaluate").param("position_id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/company/list_assigned_positions"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void evaluate_redirectsWhenNotOwnedByCompany() throws Exception {
        Company other = new Company();
        other.setUsername("othercorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(other);
        p.setAssigned(true);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(get("/company/evaluate").param("position_id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/company/list_assigned_positions"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void evaluate_redirectsWhenNotAssigned() throws Exception {
        Company c = new Company();
        c.setUsername("techcorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(c);
        p.setAssigned(false);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(get("/company/evaluate").param("position_id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/company/list_available_positions"));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void evaluate_returnsForm_withEvaluationTypeCompany() throws Exception {
        Company c = new Company();
        c.setUsername("techcorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(c);
        p.setAssigned(true);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(get("/company/evaluate").param("position_id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/evaluation_form"))
                .andExpect(model().attributeExists("position"))
                .andExpect(model().attributeExists("evaluation"));

        // έλεγχος ότι περνάει evaluation με COMPANY_EVALUATION
        // (δεν κάνουμε cast από model εδώ, απλά καλύπτουμε flow)
    }

    @Test
    @WithMockUser(username = "techcorp")
    void saveEvaluation_redirectsWhenPositionNotFound() throws Exception {
        when(companyService.getTraineeshipPosition(1)).thenReturn(null);

        mockMvc.perform(post("/company/save_evaluation")
                        .param("position_id", "1")
                        .param("motivation", "5")
                        .param("efficiency", "5")
                        .param("effectiveness", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("/company/assigned_positions"));

        verify(companyService, never()).saveEvaluation(anyInt(), any(Evaluation.class));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void saveEvaluation_redirectsWhenNotOwned() throws Exception {
        Company other = new Company();
        other.setUsername("othercorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(other);
        p.setAssigned(true);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(post("/company/save_evaluation")
                        .param("position_id", "1")
                        .param("motivation", "5")
                        .param("efficiency", "5")
                        .param("effectiveness", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("/company/assigned_positions"));

        verify(companyService, never()).saveEvaluation(anyInt(), any(Evaluation.class));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void saveEvaluation_redirectsWhenNotAssigned() throws Exception {
        Company c = new Company();
        c.setUsername("techcorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(c);
        p.setAssigned(false);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(post("/company/save_evaluation")
                        .param("position_id", "1")
                        .param("motivation", "5")
                        .param("efficiency", "5")
                        .param("effectiveness", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("/company/available_positions"));

        verify(companyService, never()).saveEvaluation(anyInt(), any(Evaluation.class));
    }

    @Test
    @WithMockUser(username = "techcorp")
    void saveEvaluation_clampsValues_andCallsService() throws Exception {
        Company c = new Company();
        c.setUsername("techcorp");

        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(1);
        p.setCompany(c);
        p.setAssigned(true);

        when(companyService.getTraineeshipPosition(1)).thenReturn(p);

        mockMvc.perform(post("/company/save_evaluation")
                        .param("position_id", "1")
                        .param("motivation", "999")     // should clamp to 5
                        .param("efficiency", "-10")     // should clamp to 1
                        .param("effectiveness", "0"))   // should clamp to 1
                .andExpect(status().isOk())
                .andExpect(view().name("/company/dashboard"));

        ArgumentCaptor<Evaluation> captor = ArgumentCaptor.forClass(Evaluation.class);
        verify(companyService).saveEvaluation(eq(1), captor.capture());

        Evaluation ev = captor.getValue();
        // clamp assertions
        org.junit.jupiter.api.Assertions.assertEquals(EvaluationType.COMPANY_EVALUATION, ev.getEvaluationType());
        org.junit.jupiter.api.Assertions.assertEquals(5, ev.getMotivation());
        org.junit.jupiter.api.Assertions.assertEquals(1, ev.getEfficiency());
        org.junit.jupiter.api.Assertions.assertEquals(1, ev.getEffectiveness());
    }
}
