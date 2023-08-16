package br.org.curitiba.ici.saude.common.query;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepository<T> {
  public Page<T> search(Map<String, Object> parameters, Pageable pageable);
}
