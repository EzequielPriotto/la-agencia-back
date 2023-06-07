package com.starfy.laAgencia.services;

import com.starfy.laAgencia.models.Admin;

public interface AdminService {
    Admin getByUsername(String username);

    Admin getById(Integer id);

    void save(Admin admin);
}
