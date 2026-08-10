package com.yourcompany.crm.repository;

import com.yourcompany.crm.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
