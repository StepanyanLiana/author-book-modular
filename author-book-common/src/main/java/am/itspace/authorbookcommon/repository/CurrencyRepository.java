package am.itspace.authorbookcommon.repository;

import am.itspace.authorbookcommon.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
