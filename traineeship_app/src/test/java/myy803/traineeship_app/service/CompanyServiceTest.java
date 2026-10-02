package myy803.traineeship_app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship_app.domain.Company;
import myy803.traineeship_app.domain.TraineeshipPosition;
import myy803.traineeship_app.mappers.CompanyMapper;
import myy803.traineeship_app.mappers.TraineeshipPositionsMapper;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private TraineeshipPositionsMapper positionsMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company("testCompany");
        testCompany.setPositions(new java.util.ArrayList<>());
    }

    @Test
    void testAddPosition() {
        when(companyMapper.findByUsername("testCompany")).thenReturn(testCompany);

        TraineeshipPosition position = new TraineeshipPosition();
        companyService.addPosition(position, "testCompany");

        assertEquals(testCompany, position.getCompany());
        assertEquals(1, testCompany.getAvailablePositions().size());
        verify(companyMapper, times(1)).save(testCompany);
    }

    @Test
    void testDeletePosition_OwnedByCompany() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);
        position.setCompany(testCompany);

        when(positionsMapper.findById(1)).thenReturn(Optional.of(position));

        companyService.deletePosition(1, "testCompany");

        verify(positionsMapper, times(1)).deleteById(1);
    }

    @Test
    void testDeletePosition_NotOwnedByCompany() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);
        position.setCompany(new Company("otherCompany"));

        when(positionsMapper.findById(1)).thenReturn(Optional.of(position));

        companyService.deletePosition(1, "testCompany");

        verify(positionsMapper, never()).deleteById(1);
    }
}
