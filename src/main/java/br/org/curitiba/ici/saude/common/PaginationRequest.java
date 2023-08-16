package br.org.curitiba.ici.saude.common;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PaginationRequest {
  private int page = 0;
  private int size = 10;
  private String direction = "asc";
  private String sortField;


  public Pageable toPageable() {
    if (StringUtils.hasText(direction) && StringUtils.hasText(sortField)) {
      Sort sort = Sort.by(Direction.fromString(direction), sortField);
      return PageRequest.of(page, size, sort);
    }
    return PageRequest.of(page, size);
  }
}
