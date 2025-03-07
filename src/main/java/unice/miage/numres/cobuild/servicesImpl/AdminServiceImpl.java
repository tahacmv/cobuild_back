package unice.miage.numres.cobuild.servicesImpl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import unice.miage.numres.cobuild.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminData() {
        return "Admin Data";
    }
}
