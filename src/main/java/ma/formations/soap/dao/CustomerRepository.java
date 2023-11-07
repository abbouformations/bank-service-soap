package ma.formations.soap.dao;

import ma.formations.soap.service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdentityRef(String identityRef);

    Optional<Customer> findByUsername(String username);


}
