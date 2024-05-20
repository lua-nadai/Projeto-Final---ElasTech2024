package com.soulcode.projetofinal.servicesTests;

import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.repositories.StatusRepository;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupportRequestServiceTest {

    @InjectMocks
    private SupportRequestService supportRequestService;

    @Mock
    private SupportRequestRepository supportRequestRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Test
    @Transactional
    void deleteTicketsByDepartmentIdTest() {
                int departmentId = 123;
        supportRequestService.deleteTicketsByDepartmentId(departmentId);
        verify(supportRequestRepository).deleteByDepartmentId(departmentId);
    }

    @Test
    void saveRequestTest() {
        SupportRequest supportRequest = new SupportRequest();
        supportRequestService.saveRequest(supportRequest);
        verify(supportRequestRepository, times(1)).save(supportRequest);
    }

    @Test
    void getRequestByIdTest() {
        int requestId = 1;
        SupportRequest expectedRequest = new SupportRequest();
        when(supportRequestRepository.findById(requestId)).thenReturn(Optional.of(expectedRequest));
        SupportRequest actualRequest = supportRequestService.getRequestById(requestId);
        assertEquals(expectedRequest, actualRequest);
        verify(supportRequestRepository, times(1)).findById(requestId);
    }


}
