package com.starfy.laAgencia.services.implementations;

import com.starfy.laAgencia.models.Admin;
import com.starfy.laAgencia.repositories.AdminRepository;
import com.starfy.laAgencia.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Admin getByUsername(String username){
        return adminRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Admin getById(Integer id){
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }


}
