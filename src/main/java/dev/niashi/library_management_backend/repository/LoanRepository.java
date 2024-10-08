package dev.niashi.library_management_backend.repository;

import dev.niashi.library_management_backend.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
